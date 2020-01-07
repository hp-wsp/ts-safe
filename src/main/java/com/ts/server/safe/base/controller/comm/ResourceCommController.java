package com.ts.server.safe.base.controller.comm;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.base.domain.Resource;
import com.ts.server.safe.base.service.ResourceService;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.common.utils.HttpUtils;
import com.ts.server.safe.configure.properties.UploadProperties;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.security.Credential;
import com.ts.server.safe.security.CredentialContextUtils;
import com.ts.server.safe.security.annotation.ApiACL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/comm/resource")
@ApiACL
@Api(value = "/comm/resource", tags = "C-上传资源API接口")
public class ResourceCommController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceCommController.class);
    private static final long MAX_RAGE = (Integer.MAX_VALUE/2);

    private final ResourceService service;
    private final UploadProperties properties;

    @Autowired
    public ResourceCommController(ResourceService service, UploadProperties properties) {
        this.service = service;
        this.properties = properties;
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("上传资源")
    public ResultVo<Resource> save(@RequestParam(value = "file") @ApiParam(value = "上传文件", required = true) MultipartFile file){
        String filename = file.getOriginalFilename();

        if(StringUtils.isBlank(filename)){
            throw new BaseException("文件名不能为空");
        }

        if(StringUtils.length(filename) > 64){
            throw new BaseException("文件名不能超过64个字符");
        }

        String id = IdGenerators.uuid();
        Resource t = saveFile(id, file).map(e -> saveResource(id, e, file))
                .orElseThrow(() -> new BaseException("上传文件失败"));
        return ResultVo.success(t);
    }

    private Optional<String> saveFile(String id, MultipartFile file) {
        try{
            String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
            String dir = properties.getResource()+ "/" + dateDir;
            LOGGER.debug("Save file path={}", dir);
            File f = new File(dir);
            if(!f.exists()){
                boolean ok = f.mkdirs();
                LOGGER.debug("Create dir path={}, success={}", dir, ok);
            }

            String path = dir + "/" + id;
            file.transferTo(new File(path));

            return Optional.of(path);
        }catch (IOException e){
            LOGGER.error("Upload file id={},throw={}", id, e.getMessage());
            return Optional.empty();
        }
    }

    private Resource saveResource(String id, String path, MultipartFile file){
        Resource t = new Resource();

        t.setId(id);
        String filename = StringUtils.remove(file.getOriginalFilename(), " ");
        t.setFileName(filename);
        t.setFileSize((int)file.getSize());
        t.setPath(path);
        t.setContentType(file.getContentType());
        t.setUsername(getCredential().getUsername());
        service.save(t);

        return t;
    }

    /**
     * 下载资源
     *
     * @param id 资源编号
     * @param range header range
     * @param response {@link HttpServletResponse}
     */
    @GetMapping(value = "{id}")
    @ApiOperation("下载资源")
    public void download(@PathVariable(value = "id")String id,
                         @RequestHeader(value = HttpHeaders.RANGE, required = false) String range,
                         HttpServletResponse response){

        Optional<Resource> optional = service.get(id);

        if(optional.isEmpty()){
            LOGGER.warn("Download resource not exist id={}", id);
            responseNotFound(response);
            return ;
        }

        Resource t = optional.get();
        File file = new File(t.getPath());
        if(!file.isFile()){
            LOGGER.warn("Download file not exist id={}, path={}", id, t.getPath());
            responseNotFound(response);
            return ;
        }

        RangeSetting rangeSetting = buildRangeSetting(file.length(), range);
        setDownlandHttpHeader(response, t.getFileName(), rangeSetting);
        LOGGER.debug("Downland file id={}, path={}, size={}", id, t.getPath(), file.length());

        try(OutputStream outputStream = response.getOutputStream();
            WritableByteChannel writableByteChannel = Channels.newChannel(outputStream);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")){
            long len = rangeSetting.getTotalLen() - rangeSetting.getStart();
            int c = (int)((len + MAX_RAGE) / MAX_RAGE);
            for(int i = 0; i < c; i++){
                long start = rangeSetting.getStart() + i * MAX_RAGE;
                long remain = rangeSetting.getTotalLen() - start;
                long contentLen = Math.min(remain, MAX_RAGE);
                randomAccessFile.getChannel().transferTo(start, contentLen, writableByteChannel);
            }
        }catch (IOException e){
            LOGGER.debug("Download resource fail id={}, throw={}", id, e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * 输出资源部存在
     *
     * @param response {@link HttpServletResponse}
     */
    private void responseNotFound(HttpServletResponse response){
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }

    /**
     * 构建{@link RangeSetting}
     *
     * @param totalLen 下载文件总长度
     * @param range http header中Range
     * @return {@link RangeSetting}
     */
    private RangeSetting buildRangeSetting(long totalLen, String range){
        return new RangeSetting.Builder(totalLen, range).build();
    }

    /**
     * 设置下载头
     *
     * @param response {@link HttpServletResponse}
     * @param filename 下载文件名
     * @param rangeSetting {@link RangeSetting}
     */
    private void setDownlandHttpHeader(HttpServletResponse response, String filename, RangeSetting rangeSetting){
        response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
        response.setContentType("application/force-download");
        HttpUtils.setContentDisposition(response, filename);
        String contentRange = "bytes " + rangeSetting.getStart() + "-" + rangeSetting.getEnd() + "/" + rangeSetting.getTotalLen();
        response.setHeader(HttpHeaders.CONTENT_RANGE, contentRange);
    }

    private Credential getCredential(){
        return CredentialContextUtils.getCredential();
    }
}

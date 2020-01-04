package com.ts.server.safe.task.controller.man;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.controller.credential.ManCredential;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.security.CredentialContextUtils;
import com.ts.server.safe.security.annotation.ApiACL;
import com.ts.server.safe.task.controller.man.form.CheckTaskSaveForm;
import com.ts.server.safe.task.controller.man.form.CheckTaskUpdateForm;
import com.ts.server.safe.task.controller.man.vo.CheckTaskVo;
import com.ts.server.safe.task.domain.CheckContent;
import com.ts.server.safe.task.domain.CheckTask;
import com.ts.server.safe.task.service.CheckContentService;
import com.ts.server.safe.task.service.CheckTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理检查任务API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/checkTask")
@ApiACL
@Api(value = "/man/checkTask", tags = "M-管理检查任务API接口")
public class CheckTaskManController {
    private final CheckTaskService service;
    private final CheckContentService contentService;

    @Autowired
    public CheckTaskManController(CheckTaskService service, CheckContentService contentService) {
        this.service = service;
        this.contentService = contentService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增检查任务")
    public ResultVo<CheckTaskVo> save(@Valid @RequestBody CheckTaskSaveForm form){
        CheckTask t = form.toDomain();
        t.setChannelId(getCredential().getChannelId());
        CheckTask n = service.save(t, form.getContentIds());
        List<CheckContent> contents = contentService.query(n.getId());
        return ResultVo.success(new CheckTaskVo(n, contents));
    }

    public ResultVo<CheckTaskVo> update(){
        return null;
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("修改检查任务")
    public ResultVo<CheckTaskVo> update(@Valid @RequestBody CheckTaskUpdateForm form){
        CheckTask t = form.toDomain();
        t.setChannelId(getCredential().getChannelId());
        CheckTask n = service.update(t, form.getContentIds());
        List<CheckContent> contents = contentService.query(n.getId());
        return ResultVo.success(new CheckTaskVo(n, contents));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到检查任务")
    public ResultVo<CheckTaskVo> get(@PathVariable("id")String id){
        CheckTask t = service.get(id);
        List<CheckContent> contents = contentService.query(id);
        return ResultVo.success(new CheckTaskVo(t, contents));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除检查任务")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        CheckTask t = service.get(id);
        if(!StringUtils.equals(t.getChannelId(), getCredential().getChannelId())){
            throw new BaseException("不能删除检查任务");
        }
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询检查任务")
    public ResultPageVo<CheckTask> query(
            @RequestParam @ApiParam(value = "公司名称") String compName,
            @RequestParam @ApiParam(value = "检查员姓名") String checkUserName,
            @RequestParam @ApiParam(value = "状态") String status,
            @RequestParam @ApiParam(value = "检查开始时间") Date checkTimeFrom,
            @RequestParam @ApiParam(value = "检查结束时间") Date checkTimeTo,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String channelId = getCredential().getChannelId();
        CheckTask.Status statusObj = CheckTask.Status.valueOf(status);
        return new ResultPageVo.Builder<>(page, rows, service.query(channelId, compName, checkUserName, statusObj, checkTimeFrom, checkTimeTo, page * rows, rows))
                .count(isCount, () -> service.count(channelId, compName, checkUserName, statusObj, checkTimeFrom, checkTimeTo))
                .build();
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}

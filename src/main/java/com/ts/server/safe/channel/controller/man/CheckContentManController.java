package com.ts.server.safe.channel.controller.man;

import com.ts.server.safe.base.domain.UniCheckContent;
import com.ts.server.safe.base.domain.UniCheckItem;
import com.ts.server.safe.base.service.UniCheckContentService;
import com.ts.server.safe.base.service.UniCheckItemService;
import com.ts.server.safe.channel.controller.man.form.CheckContentSaveForm;
import com.ts.server.safe.channel.controller.man.form.CheckContentUpdateForm;
import com.ts.server.safe.channel.domain.CheckContent;
import com.ts.server.safe.channel.service.CheckContentService;
import com.ts.server.safe.controller.credential.ManCredential;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.security.CredentialContextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 查询检查项目API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/check/content")
@Api(value = "/man/check/content", tags = "M-查询检查项目")
public class CheckContentManController {
    private final UniCheckContentService uniService;
    private final UniCheckItemService itemService;
    private final CheckContentService service;

    @Autowired
    public CheckContentManController(UniCheckContentService uniService,
                                     UniCheckItemService itemService, CheckContentService service) {

        this.uniService = uniService;
        this.itemService = itemService;
        this.service = service;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增检查表")
    public ResultVo<UniCheckContent> save(@Valid @RequestBody CheckContentSaveForm form){
        CheckContent t = form.toDomain(getCredential().getChannelId());
        setItem(t);
        return ResultVo.success(service.save(t));
    }

    private void setItem(CheckContent t){
        UniCheckItem item = itemService.get(t.getItemId());
        t.setTypeId(item.getTypeId());
        t.setTypeName(item.getTypeName());
        t.setItemName(item.getName());
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("修改检查表")
    public ResultVo<UniCheckContent> update(@Valid @RequestBody CheckContentUpdateForm form){
        CheckContent t = form.toDomain(getCredential().getChannelId());
        setItem(t);
        return ResultVo.success(service.update(t));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到检查内容")
    public ResultVo<CheckContent> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除检查表")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询检查内容")
    public ResultPageVo<CheckContent> query(
            @ApiParam(value = "检查类型") @RequestParam(required = false) String typeName,
            @ApiParam(value = "检查项目") @RequestParam(required = false) String itemName,
            @ApiParam(value = "检查内容") @RequestParam(required = false) String content,
            @ApiParam(value = "法律内容") @RequestParam(required = false) String lawItem,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String channelId = getCredential().getChannelId();
        return new ResultPageVo.Builder<>(page, rows, service.query(channelId, typeName, itemName, content, lawItem, page * rows, rows))
                .count(isCount, () -> service.count(channelId, typeName, itemName, content, lawItem))
                .build();
    }

    @GetMapping(value = "item/{itemId}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询检查内容")
    public ResultVo<List<UniCheckContent>> queryOfItem(@PathVariable("itemId") String itemId){
        List<UniCheckContent> all = new ArrayList<>();
        String channelId = getCredential().getChannelId();
        all.addAll(uniService.queryOfItem(itemId));
        all.addAll(service.queryOfItem(channelId, itemId));
        return ResultVo.success(all);
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}

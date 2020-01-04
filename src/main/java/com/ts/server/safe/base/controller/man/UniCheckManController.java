package com.ts.server.safe.base.controller.man;

import com.ts.server.safe.base.controller.man.vo.UniCheckTypeVo;
import com.ts.server.safe.base.domain.UniCheckContent;
import com.ts.server.safe.base.domain.UniCheckType;
import com.ts.server.safe.base.service.UniCheckItemService;
import com.ts.server.safe.base.service.UniCheckContentService;
import com.ts.server.safe.base.service.UniCheckTypeService;
import com.ts.server.safe.controller.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 查询检查项目API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/check")
@Api(value = "/man/check", tags = "M-查询检查项目")
public class UniCheckManController {
    private final UniCheckTypeService typeService;
    private final UniCheckItemService itemService;
    private final UniCheckContentService tableService;

    @Autowired
    public UniCheckManController(UniCheckTypeService typeService,
                                 UniCheckItemService itemService,
                                 UniCheckContentService tableService) {

        this.typeService = typeService;
        this.itemService = itemService;
        this.tableService = tableService;
    }

    @GetMapping(value = "type", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("获取检查类别")
    public ResultVo<List<UniCheckTypeVo>> getTypes(){
        List<UniCheckTypeVo> vos = typeService.queryAll().stream()
                .map(this::buildVo).collect(Collectors.toList());
        return ResultVo.success(vos);
    }

    /**
     * 构建{@link UniCheckTypeVo}
     *
     * @param t {@link UniCheckType}
     * @return {@link UniCheckTypeVo}
     */
    private UniCheckTypeVo buildVo(UniCheckType t){
        List<UniCheckTypeVo.UniCheckItemVo> items = itemService.queryOfType(t.getId())
                .stream().map(e -> new UniCheckTypeVo.UniCheckItemVo(e.getId(), e.getName()))
                .collect(Collectors.toList());
        return new UniCheckTypeVo(t.getId(), t.getName(), items);
    }

    @GetMapping(value = "table/{itemId}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询检查表")
    public ResultVo<List<UniCheckContent>> queryTable(@PathVariable("itemId") String itemId){
        List<UniCheckContent> tables = tableService.queryOfItem(itemId);
        return ResultVo.success(tables);
    }
}

package com.ts.server.safe.base.controller.man;

import com.ts.server.safe.base.domain.UniIndCtg;
import com.ts.server.safe.base.service.UniIndCtgService;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.security.annotation.ApiACL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 查询行业分类API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@ApiACL
@RequestMapping("/man/indCtg")
@Api(value = "man/indCtg", tags = "M-查询行业分类")
public class UniIndCtgManController {
    private final UniIndCtgService service;

    @Autowired
    public UniIndCtgManController(UniIndCtgService service) {
        this.service = service;
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到监管分类")
    public ResultVo<UniIndCtg> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @GetMapping(value = "children/{parentId}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询下级监管分类")
    public ResultVo<List<UniIndCtg>> queryChildren(@PathVariable("parentId")String parentId){
        return ResultVo.success(service.queryChildren(parentId));
    }
}

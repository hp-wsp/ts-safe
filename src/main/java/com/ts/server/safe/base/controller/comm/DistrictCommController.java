package com.ts.server.safe.base.controller.comm;

import com.ts.server.safe.base.controller.comm.vo.DistrictVo;
import com.ts.server.safe.base.service.DistrictService;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.security.annotation.ApiACL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 行政区划API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/comm/district")
@ApiACL
@Api(value = "/comm/district", tags = "C-行政区划API接口")
public class DistrictCommController {
    private final DistrictService service;

    @Autowired
    public DistrictCommController(DistrictService service) {
        this.service = service;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询行政区划")
    public ResultVo<Collection<DistrictVo>> query(@RequestParam(value = "id", required = false, defaultValue = "root") String id){
        return ResultVo.success(service.query(id).stream().map(DistrictVo::new).collect(Collectors.toList()));
    }
}

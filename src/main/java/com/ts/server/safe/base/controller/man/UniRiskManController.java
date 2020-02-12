package com.ts.server.safe.base.controller.man;

import com.ts.server.safe.base.domain.UniRisk;
import com.ts.server.safe.base.service.UniRiskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 查询危险识别API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/risk")
@Api(value = "/man/risk", tags = "M-查询危险识别API接口")
public class UniRiskManController {
    private final UniRiskService service;

    @Autowired
    public UniRiskManController(UniRiskService service) {
        this.service = service;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询危险识别")
    public List<UniRisk> query(String name){
        return service.query(name, 0, 1000);
    }
}

package com.ts.server.safe.tencent.controller;

import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.tencent.domain.District;
import com.ts.server.safe.tencent.service.DistrictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 行政区划API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/map")
@Api(value = "/map", tags = "P-行政区划API接口")
public class DistrictController {
    private final DistrictService service;

    @Autowired
    public DistrictController(DistrictService service) {
        this.service = service;
    }

    @GetMapping(value = "district", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询行政区划")
    public ResultVo<Collection<District>> district(@RequestParam(value = "id", required = false, defaultValue = "root") String id){
        return ResultVo.success(service.query(id));
    }

    @GetMapping(value = "init", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("初始化行政区划")
    public ResultVo<OkVo> init(){
        Thread thread = new Thread(service::initDistrict);
        thread.start();
        return ResultVo.success(new OkVo(true));
    }

//    @GetMapping(value = "address", produces = APPLICATION_JSON_VALUE)
//    @ApiOperation("查询地址")
//    public ResultVo<Collection<SearchResponse.Address>> search(@RequestParam("district") String district,
//                                                               @RequestParam("keyword") String keyword,
//                                                               @RequestParam(value = "page", defaultValue = "1")int page,
//                                                               @RequestParam(value = "row", defaultValue = "20")int row){
//
//        SearchResponse response = service.search(e -> e.setDistrict(district)
//                .setKeyword(keyword).setPageIndex(page).setPageSize(row).build());
//        return ResultVo.success(response.getAddresses());
//    }
}

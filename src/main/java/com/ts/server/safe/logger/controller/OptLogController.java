package com.ts.server.safe.logger.controller;

import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.logger.domain.OptLog;
import com.ts.server.safe.logger.service.OptLogService;
import com.ts.server.safe.security.annotation.ApiACL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 日志查询API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/logger")
@ApiACL("ROLE_SYS")
@Api(value = "/sys/logger", tags = "日志查询API接口")
public class OptLogController {
    private final OptLogService service;

    @Autowired
    public OptLogController(OptLogService service) {
        this.service = service;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询短信日志")
    public ResultPageVo<OptLog> query(
            @ApiParam(value = "名称") @RequestParam(required = false) String name,
            @ApiParam(value = "描述") @RequestParam(required = false) String detail,
            @ApiParam(value = "用户名") @RequestParam(required = false) String username,
            @ApiParam(value = "开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate,
            @ApiParam(value = "结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        final String type = "";
        return new ResultPageVo.Builder<>(page, rows, service.query(type, name, detail, username, fromDate, toDate,page * rows, rows))
                .count(isCount, () -> service.count(type, name, detail,  username, fromDate, toDate))
                .build();
    }
}

package com.lvch.scaffold.common.controller;

import com.lvch.scaffold.common.domain.vo.request.RegisterRequest;
import com.lvch.scaffold.common.domain.vo.response.ApiResult;
import com.lvch.scaffold.common.service.IBaseLoginAccountAuthService;
import com.lvch.scaffold.common.service.LoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chunhelv
 * @date 2023-08-09
 * @apiNote
 */
@RestController
@Slf4j
@RequestMapping("/capi/excel")
public class PrivateController {

    @Autowired
    private LoginService loginService;

    private static final Pattern PATTERN = Pattern.compile("(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)");

    public static Map<String, String> getAddressResolution(String address) {
        Matcher matcher = PATTERN.matcher(address);
        String province, city, county, town, village;
        Map<String, String> row = new HashMap<>();
        while (matcher.find()) {
            province = matcher.group("province");
            row.put("province", province == null ? "" : province.trim());
            city = matcher.group("city");
            row.put("city", city == null ? "" : city.trim());
            county = matcher.group("county");
            row.put("county", county == null ? "" : county.trim());
            town = matcher.group("town");
            row.put("town", town == null ? "" : town.trim());
            village = matcher.group("village");
            row.put("village", village == null ? "" : village.trim());
        }
        return row;
    }


    public static void main(String[] args) {
        String address = "湖南省长沙市岳麓区永青路668号";
    }
    @GetMapping("/test")
    public  ApiResult<String> appExportReport() {
        log.error("xxxxxxxxxxx");
        return ApiResult.success("算你行！");
    }

}

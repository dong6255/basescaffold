package com.lvch.scaffold.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class ExportReportController {

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

    public static int distMoney(int money, int children) {
        if (money < children) {
            return -1;
        }
        money -= children;
        int cnt = Math.min(money / 7, children);
        money -= cnt * 7;
        children -= cnt;
        if ((children == 0 && money > 0) || (children == 1 && money == 3)) {
            cnt--;
        }
        return cnt;

    }

    public static String dynamicPassword(String password, int target) {
        String res = "";
        int n = target + password.length();
        for(int i = target; i < n; i++) {
            res += password.charAt(i % password.length());
        }
        return res;

    }

    public static int canBeTypedWords(String text, String brokenLetters) {
        int[] nums = {0,1,2};
        int[] sortNums = Arrays.stream(nums).sorted().toArray();

        String[] testArgs = text.split(" ");
        Set<Character> set = new HashSet<>();
        int ans = 0;
        for (int i = 0; i < brokenLetters.length(); i++) {
            set.add(brokenLetters.charAt(i));
        }
        for (String testArg : testArgs) {
            boolean flag = true;
            for (int i = 0; i < testArg.length(); i++) {
                if (set.contains(testArg.charAt(i))) {
                    flag = false;
                }
            }
            ans += flag? 1 : 0;
        }
        return ans;
    }

    public static void main(String[] args) {
        String address = "湖南省长沙市岳麓区永青路668号";
        //distMoney(20, 3);
        dynamicPassword("password", 4);
        //System.out.println(getAddressResolution(address));
        // {province=湖南省, city=长沙市, county=岳麓区, town=, village=永青路668号}
    }
    @GetMapping("/test")
    public String appExportReport() {
        log.error("xxxxxxxxxxx");
        return "success!";
    }

    @GetMapping("/testtttt")
    public String test() {
        log.info("ccccccccccc");

        return "===============success!============";
    }

}

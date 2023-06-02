package com.old.business.user.controller;

import cn.hutool.core.util.StrUtil;
import com.old.common.domain.login.LoginInfoBo;
import com.old.common.domain.login.html.HtmlLoginUser;
import com.old.common.enums.redis.RedisKey;
import com.old.common.redis.util.RedisUtil;
import com.old.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/monitor")
public class MonitorController {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @PostMapping("/online/{token}")
    public R<Void> forceLogout(@PathVariable("token") String token) {
        boolean del = RedisUtil.del(RedisKey.LOGIN_USER, token);
        log.debug("强制退出用户：{}，{}", token, del);
        return R.ok();
    }


    @GetMapping("/online/list")
    public R<List<LoginInfoBo>> list(@RequestParam(value = "ipaddr", required = false) String ipaddr,
                                     @RequestParam(value = "userName", required = false) String userName,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        var loginUsers = new ArrayList<LoginInfoBo>(pageSize);
        RedisUtil.execute(redisTemplate -> {

            try (
                    Cursor<Object> cursor = redisTemplate.scan(ScanOptions.scanOptions()
                            .match(RedisKey.LOGIN_USER.getKey() + "*")
                            .type(DataType.STRING)
                            .build());
            ) {
                int count = 0;
                while (cursor.hasNext() && count < pageNum * pageSize) {
                    var loginUser = (HtmlLoginUser) redisTemplate.opsForValue().get(cursor.next());
                    var bo = new LoginInfoBo(loginUser.getToken(), loginUser.loginUserName(), loginUser.getIpAddress(), loginUser.getLoginTime());
                    if (StrUtil.isNotBlank(ipaddr)) {
                        if (ipaddr.equals(loginUser.getIpAddress())) {
                            loginUsers.add(bo);
                        }
                    } else if (StrUtil.isNotBlank(userName)) {
                        if (userName.equals(loginUser.loginUserName())) {
                            loginUsers.add(bo);
                        }
                    } else {
                        loginUsers.add(bo);
                    }

                }
            }

        });

        return R.ok(loginUsers);
    }

    @GetMapping("/cache")
    public R<Map<String, Object>> getInfo() throws Exception {
        var info = redisTemplate.execute((RedisCallback<Properties>) connection -> connection.serverCommands().info());
        var commandStats = redisTemplate.execute((RedisCallback<Properties>) connection -> connection.serverCommands().info("commandstats"));
        var dbSize = redisTemplate.execute((RedisCallback<Long>) connection -> connection.serverCommands().dbSize());

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);

        List<Map<String, String>> pieList = new ArrayList<>();
        commandStats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(2);
            String property = commandStats.getProperty(key);
            data.put("name", StrUtil.removePrefix(key, "cmdstat_"));
            data.put("value", StrUtil.subBetween(property, "calls=", ",usec"));
            pieList.add(data);
        });
        result.put("commandStats", pieList);
        return R.ok(result);
    }
}

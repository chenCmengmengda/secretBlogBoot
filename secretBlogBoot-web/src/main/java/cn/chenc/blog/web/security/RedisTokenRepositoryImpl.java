package cn.chenc.blog.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 　@description: redis 保存用户token
 * 　@author 陈_C
 * 　@date 2020/5/24 21:27
 *
 */
@Slf4j
@Component
public class RedisTokenRepositoryImpl implements PersistentTokenRepository {

    /**
     * 成功
     */
    private static final String SUCCESS = "{\"result_code\": \"00000\", \"result_msg\": \"处理成功\"}";

    /**
     * 失败
     */
    private static final String FAILED = "{\"result_code\": \"99999\", \"result_msg\": \"处理失败\"}";

    /**
     * 登录过期
     */
    private static final String LOGIN_EXPIRE = "{\"result_code\": \"10001\", \"result_msg\": \"登录过期\"}";

    /**
     * 权限限制
     */
    private static final String ROLE_LIMIT = "{\"result_code\": \"10002\", \"result_msg\": \"权限不足\"}";

    /**
     * 登录 URL
     */
    private static final String LOGIN_URL = "/authc/login";

    /**
     * 登出 URL
     */
    private static final String LOGOUT_URL = "/authc/logout";

    /**
     * 授权 URL
     */
    private static final String AUTH_URL = "/autch/";

    /**
     * 授权 URL 正则
     */
    private static final String AUTH_URL_REG = AUTH_URL + "**";

    /**
     * 登录用户名参数名
     */
    private static final String LOGIN_NAME = "username";

    /**
     * 登录密码参数名
     */
    private static final String LOGIN_PWD = "password";

    /**
     * 记住登录参数名
     */
    private static final String REMEMBER_ME = "remember-me";

    /**
     * token有效时间10天
     * 框架实现 {@link RememberMeConfigurer#tokenValiditySeconds}
     * 此处使用redis实现
     */
    private static final Long TOKEN_VALID_DAYS = 10L;


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        if (log.isDebugEnabled()) {
            log.debug("token create seriesId: [{}]", token.getSeries());
        }
        String key = generateKey(token.getSeries());
        HashMap<String, String> map = new HashMap();
        map.put("username", token.getUsername());
        map.put("tokenValue", token.getTokenValue());
        map.put("date", String.valueOf(token.getDate().getTime()));
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, TOKEN_VALID_DAYS, TimeUnit.DAYS);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        String key = generateKey(series);
        HashMap<String, String> map = new HashMap();
        map.put("tokenValue", tokenValue);
        map.put("date", String.valueOf(lastUsed.getTime()));
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, TOKEN_VALID_DAYS, TimeUnit.DAYS);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        String key = generateKey(seriesId);

        List<String> hashKeys = new ArrayList<>();
        hashKeys.add("username");
        hashKeys.add("tokenValue");
        hashKeys.add("date");
        List<String> hashValues = redisTemplate.opsForHash().multiGet(key, hashKeys);
        String username = hashValues.get(0);
        String tokenValue = hashValues.get(1);
        String date = hashValues.get(2);
        if (null == username || null == tokenValue || null == date) {
            return null;
        }
        Long timestamp = Long.valueOf(date);
        Date time = new Date(timestamp);
        PersistentRememberMeToken token = new PersistentRememberMeToken(username, seriesId, tokenValue, time);
        return token;
    }

    @Override
    public void removeUserTokens(String username) {
        if (log.isDebugEnabled()) {
            log.debug("token remove username: [{}]", username);
        }
        byte[] hashKey = redisTemplate.getHashKeySerializer().serialize("username");
        RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
        try (Cursor<byte[]> cursor = redisConnection.scan(ScanOptions.scanOptions().match(generateKey("*")).count(1024).build())) {
            while (cursor.hasNext()) {
                byte[] key = cursor.next();
                byte[] hashValue = redisConnection.hGet(key, hashKey);
                String storeName = (String) redisTemplate.getHashValueSerializer().deserialize(hashValue);
                if (username.equals(storeName)) {
                    redisConnection.expire(key, 0L);
                    return;
                }
            }
        } catch (IOException ex) {
            log.warn("token remove exception", ex);
        }
    }

    /**
     * 生成key
     *
     * @param series series
     * @return object
     */
    private String generateKey(String series) {
        return "spring:security:rememberMe:token:" + series;
    }
}


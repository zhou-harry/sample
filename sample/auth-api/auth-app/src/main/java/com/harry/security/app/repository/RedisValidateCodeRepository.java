package com.harry.security.app.repository;

import com.harry.security.core.constant.ValidateCodeTypeEnum;
import com.harry.security.core.util.AuthUtil;
import com.harry.security.core.validate.code.ValidateCode;
import com.harry.security.core.validate.code.ValidateCodeRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author harry
 * @version 1.0
 * @title: RedisValidateCodeRepository
 * @description: Redis方式管理验证码
 * @date 2019/5/23 16:21
 */
public class RedisValidateCodeRepository implements ValidateCodeRepository {


    public static final String VALIDATECODE_REDISKEY_PREFIX = "code:";

    private final RedisTemplate redisTemplate;

    public RedisValidateCodeRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeTypeEnum codeType) {
        redisTemplate.opsForValue().set(getRedisKey(request, codeType), code);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeTypeEnum codeType) {
        Object codeInRedis = redisTemplate.opsForValue().get(getRedisKey(request, codeType));
        if (codeInRedis != null) {
            return (ValidateCode) codeInRedis;
        } else {
            return null;
        }
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeTypeEnum codeType) {
        redisTemplate.delete(getRedisKey(request, codeType));
    }

    private String getRedisKey(ServletWebRequest request, ValidateCodeTypeEnum codeType) {
        String deviceId = AuthUtil.getDeviceId(request);
        StringBuilder buff = new StringBuilder(VALIDATECODE_REDISKEY_PREFIX);
        buff.append(codeType).append(":").append(deviceId);
        return buff.toString();
    }

}

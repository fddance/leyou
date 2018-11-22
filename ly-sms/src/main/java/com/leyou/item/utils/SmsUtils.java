package com.leyou.item.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.leyou.item.config.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsUtils {

    @Autowired
    private SmsProperties prop;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "sms:phone:";
    private static final long TIME_LIMIT = 1;

    public SendSmsResponse sendSms(String phone, String signName, String templateCode, String templateParam) {
        try {
            // 计时的key
            String key = KEY_PREFIX + phone;
            // 判断是否发送过
            String time = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isBlank(time)) {
                return null;
            }

            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", prop.getConnectTimeout());
            System.setProperty("sun.net.client.defaultReadTimeout", prop.getReadTimeout());

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", prop.getAccessKeyId(), prop.getAccessKeySecret());
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            request.setMethod(MethodType.POST);
            //必填:待发送手机号
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam(templateParam);

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

            // 记录本次发送的时间戳
            redisTemplate.opsForValue().set(key, String.valueOf(System.currentTimeMillis()), TIME_LIMIT, TimeUnit.MINUTES);

            return sendSmsResponse;
        }catch (Exception e){
            log.error("【短信服务】发送短信失败，手机号：{}", phone, e);
            return null;
        }
    }
}

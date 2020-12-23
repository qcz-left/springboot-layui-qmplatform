package com.qcz.qmplatform.module.notify.service.ali;

import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.qcz.qmplatform.module.notify.bean.SmsConfig;
import com.qcz.qmplatform.module.notify.service.INotifyService;

/**
 * 阿里云短信服务
 */
public class AliyunSmsNotifyService implements INotifyService {

    private SmsConfig smsConfig;

    @Override
    public void send() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsConfig.getSecretId(), smsConfig.getSecretKey());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(smsConfig.getEndpoint());
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", smsConfig.getPhones().get(0));
        request.putQueryParameter("SignName", smsConfig.getSign());
        request.putQueryParameter("TemplateCode", smsConfig.getTemplateID());
        request.putQueryParameter("TemplateParam", JSONUtil.toJsonStr(smsConfig.getTemplateParams()));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSmsConfig(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
    }
}

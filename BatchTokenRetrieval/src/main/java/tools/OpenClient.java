package tools;

import java.util.HashMap;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.BearerTokenCredentials;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @author edward
 * @date 2018/5/18
 */
public class OpenClient {
    /**
     * 生产环境的Region是cn-shanghai
     */
    private static final String REGION = "cn-shanghai";
    private static final String PRODUCT = "CCC";
    private static final String ENDPOINT = "CCC";
    /**
     * 生产环境的Region是ccc.cn-shanghai.aliyuncs.com
     */
    private static final String DOMAIN = "ccc.cn-shanghai.aliyuncs.com";
    private static final String VERSION = "2017-07-05";

    public static String invokeByStsToken(TokenPackage.Credentials credentials, String action, String request)
        throws ClientException {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(PRODUCT, DOMAIN);

        IClientProfile stsProfile = DefaultProfile.getProfile(REGION, map, credentials.getAccessKeyId(),
            credentials.getAccessKeySecret(), credentials.getSecurityToken());
        DefaultAcsClient stsClient = new DefaultAcsClient(stsProfile);
        stsClient.setAutoRetry(false);

        /**
         * 使用CommonAPI调用POP API时，和使用传统产品SDK相比，请求和返回参数的格式都有所不同，因此需要进行一定的格式转换。
         */
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setDomain(DOMAIN);
        commonRequest.setVersion(VERSION);
        commonRequest.setAction(action);

        JSONObject jsonObject = JSONObject.parseObject(request);

        for (Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey().trim();
            if (key.length() > 1) {
                key = key.substring(0, 1).toUpperCase() + key.substring(1);
            } else if (key.length() == 1) {
                key = key.toUpperCase();
            } else {
                continue;
            }
            commonRequest.putQueryParameter(key, entry.getValue().toString());
        }

        commonRequest.putQueryParameter("SecurityToken", credentials.getSecurityToken());
        commonRequest.putQueryParameter("AccessKeyId", credentials.getAccessKeyId());

        CommonResponse response = stsClient.getCommonResponse(commonRequest);

        return JSONObject.toJSONString(response);
    }

    public static String invokeByBearerToken(String bearerToken, String action, String request) throws ClientException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(PRODUCT, DOMAIN);

        DefaultProfile.addEndpoint(ENDPOINT, REGION, PRODUCT, DOMAIN);
        DefaultProfile bearerProfile = DefaultProfile.getProfile(REGION);

        BearerTokenCredentials bearerCredentials = new BearerTokenCredentials(bearerToken);
        DefaultAcsClient bearerClient = new DefaultAcsClient(bearerProfile, bearerCredentials);
        bearerClient.setAutoRetry(false);

        /**
         * 使用CommonAPI调用POP API时，和使用传统产品SDK相比，请求和返回参数的格式都有所不同，因此需要进行一定的格式转换。
         */
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setDomain(DOMAIN);
        commonRequest.setVersion(VERSION);
        commonRequest.setAction(action);
        JSONObject jsonObject = JSONObject.parseObject(request);

        for (Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey().trim();
            if (key.length() > 1) {
                key = key.substring(0, 1).toUpperCase() + key.substring(1);
            } else if (key.length() == 1) {
                key = key.toUpperCase();
            } else {
                continue;
            }
            commonRequest.putQueryParameter(key, entry.getValue().toString());
        }

        commonRequest.putQueryParameter("accessToken", bearerToken);
        CommonResponse response = bearerClient.getCommonResponse(commonRequest);

        return JSONObject.toJSONString(response);
    }
}

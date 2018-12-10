package tools;

import java.io.IOException;
import java.text.ParseException;

import com.alibaba.fastjson.JSON;

/**
 * @author edward
 * @date 2018/5/19
 */
public abstract class AbstractTest {
    private static TokenPackage tokenPackage = null;

    private static String ACCESS_TOKEN = "{\"accessKeyId\":\"STS.NJwUZF2dumHZn7NFLE11YAWRm\","
        + "\"accessKeySecret\":\"4xS5ToZGk6pZnJcApMoTayGBFLq3eAAHnWZLCUzUDhWN\","
        + "\"securityToken\":\"CAISjAJ1q6Ft5B2yfSjIr4nCHuDy37tU2oqxbBH/okwQPb51rpL5jzz3IX5KfHVpBOEft"
        + "/g1lW1X6vYchYMSGsUdHBOdNZAptgX3UOl0J9ivgde8yJBZopDHcDHhA3yW9cvWZPqDP7G5U/yxalfCuzZuyL"
        + "/hD1uLVECkNpv74vwOLK5FPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeieigcswFn22h04vb9kJTEt0uO1AanlrNF"
        + "/dSsesn2LesUZc8iAofth7EqKvqbgHMIuiInrvkm0PZ2gRbCptyHGF5X6gmLP9fP/8dnRA0CPPZkQ/Id8amlzKEi67GMy9SpmgwjL"
        + "+xTSSnZVFVzRxBZvWvDGoABF1rpzhmlJ8qvMO+QaHKhHXOch1W0Hnwg7N/L6W7he31HrEsT6FPaplsVFaJ88D5dL7R7SFrxZaP7KyI"
        + "/6K9iPGD9RjqL+8xOmNgfd66Gr1+oiVnnOcm0knuBEtnwjFhxYxCmgqV0wW2Cbzm7EG9dyzC9SvjemLbr5HXhbEsE6jY=\"}";

    private static boolean retrievalTokenInFly = true;

    protected TokenPackage getTokenPackage() throws IOException, ParseException {
        if (tokenPackage == null) {
            if (retrievalTokenInFly) {
                String username = Environment.get("username");
                String password = Environment.get("password");
                String clientId = Environment.get("clientId");
                String clientSecret = Environment.get("clientSecret");
                String registeredCallbackUrl = Environment.get("registeredCallbackUrl");
                String token = BatchTokenRetrieval.retrieve(username, password, clientId, clientSecret,
                    registeredCallbackUrl);
                tokenPackage = TokenPackage.parse(token);
            } else {
                tokenPackage = new TokenPackage();
                TokenPackage.Credentials credentials = JSON.parseObject(ACCESS_TOKEN, TokenPackage.Credentials.class);
                tokenPackage.setCredentials(credentials);
            }
        }
        return tokenPackage;
    }
}

package tools;

/**
 * @author edward
 * @date 2018/5/18
 */
public class Application {
    public static void main(String[] args) {
        String username = Environment.get("username");
        String password = Environment.get("password");
        String clientId = Environment.get("clientId");
        String clientSecret = Environment.get("clientSecret");
        String registeredCallbackUrl = Environment.get("registeredCallbackUrl");
        String token = BatchTokenRetrieval.retrieve(username, password, clientId, clientSecret, registeredCallbackUrl);
        System.out.println(token);
    }
}

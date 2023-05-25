package utility;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaseClass {
    public static String accessToken;
    public static String productCode;
    public static Map<String, Object> urls = new HashMap<>();
    public static Map<String, Object> headers = new HashMap<>();

    static {
       //accessToken = RESTUtility.getAccessToken();
        accessToken = "u30a0cd32-270c-48dd-9cc7-4d87c8f7ad0851867";
        //System.out.println("access-token  " + accessToken);
        try {
            urls = JSONUtility.getJsonDataInMap("./src/main/resources/URL.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            headers = JSONUtility.getJsonDataInMap("./src/main/resources/headers.json");
            headers.put("access-token", accessToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

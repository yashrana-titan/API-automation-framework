package utility;
import org.testng.annotations.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaseClass {
    public static String accessToken;
    public static String productCode;
    public static Map<String, Object> urls = new HashMap<>();
    public static Map<String, Object> headers = new HashMap<>();
    public static Map<String, Object> tokens = new HashMap<>();

    @BeforeSuite
    public void beforeSuite() {
        tokens=RESTUtility.getAccessToken();
        accessToken = (String) tokens.get("Access-Token");
        //accessToken = "ud6c878f3-cdaf-4e14-a166-9341a20df60751867";
        System.out.println("Access-Token: " + accessToken);

        try {
            urls = JSONUtility.getJsonDataInMap("./src/main/resources/URL.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            headers = JSONUtility.getJsonDataInMap("./src/main/resources/headers.json");
            headers.put("access-token", accessToken); // Assigning value to the headers map
            System.out.println("Headers: " + headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @AfterSuite
    public void afterSuite()
    {
        System.out.println("Execution Completed");
    }
}
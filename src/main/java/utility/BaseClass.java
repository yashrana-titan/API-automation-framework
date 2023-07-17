package utility;
import org.testng.annotations.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaseClass {
    public static String accessToken;
    public static Map<String, Object> urls = new HashMap<>();
    public  Map<String, Object> headers = new HashMap<>();
    public static Map<String, Object> tokens = new HashMap<>();

    @BeforeSuite
    public void beforeClass() {
        tokens = RESTUtility.getAccessToken();
        accessToken = (String) tokens.get("Access-Token");
//        accessToken = "ue51f7ae2-18a4-4154-9721-d40c5891b33451867";
        System.out.println("Access-Token: " + accessToken);
        try {
            urls = JSONUtility.getJsonDataInMap("./src/main/resources/URL.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Parameters("productId")
    @BeforeClass
    public void beforeTest(String productId)
    {
        try {
            headers = JSONUtility.getJsonDataInMap("./src/main/resources/headers.json");
            headers.put("access-token", accessToken);
            headers.put("titan-context-product-code",productId);// Assigning value to the headers map
            System.out.println("Headers: " + headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    @AfterSuite
    public void afterClass()
    {
        System.out.println("Execution Completed");
    }
}
package utility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.io.File;
import java.util.Map;

public class SettingsURLMethods{
    public RESTUtility restUtil;
//    public String url = (String) urls.get("HeartRate");

    public Response getData( String url,Map<String,Object> headers) {
        restUtil = new RESTUtility();
        Response res = RestAssured.given().headers(headers).contentType(ContentType.JSON).get(url);
        System.out.println("response   "+ res.prettyPrint());
        return res;
    }

    public Response putData(String url,String filePath,Map<String,Object> headers)
    {
        Response res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON)
                .body(new File(filePath))
                .put(url);
        System.out.println(res.statusCode());
        return res;
    }
}

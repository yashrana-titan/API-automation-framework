package utility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.io.File;
import java.util.Map;

public class SettingsURLMethods extends BaseClass{
    public RESTUtility restUtil;
//    public String url = (String) urls.get("HeartRate");
    public String settingsURL = (String) urls.get("Settings");

    public Response getData(Map<String,Object> headers) {
        restUtil = new RESTUtility();
        Response res = RestAssured.given().headers(headers).contentType(ContentType.JSON).get(settingsURL);
        System.out.println("response   "+ res.prettyPrint());
        return res;
    }

    public Response putDataHealthAPIFromCSV(String CSVfilePath,String TemplateFilePath,Map<String,Object> headers)
    {
        System.out.println(settingsURL);
        Response res;
        String jsonString = DataGenerationUtility.CreateJsonFromCSV(CSVfilePath,TemplateFilePath).toString();
        System.out.println(jsonString);
        res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON)
                .body(jsonString)
                .put(settingsURL);
        //System.out.println(res.statusCode());
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }
}

package savedatamethods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utility.BaseClass;
import utility.JSONUtility;
import utility.RESTUtility;

import java.util.Map;

public class HealthItems extends BaseClass {
    public static RESTUtility restUtility;

    public static void main(String[] args) {
        }

    public static void getDataHealthAPIinFile(Map<String,Object> headers, String HealthApiItem, String scope, String date) {
        String healthURL = (String)urls.get("health");
        restUtility = new RESTUtility();
        String url = healthURL + HealthApiItem;
        System.out.println(url);
        Response res = RestAssured.given()
                .headers(headers).
                contentType(ContentType.JSON).param("scope",scope).param("date",date)
                .get(url);
        String filePath = "./src/main/resources/SavedData/"+HealthApiItem+".json";
        if(res.statusCode()!=200)
        {
            System.out.println("Response code is not 200");
        }
        else
        {
            JSONUtility.saveResponseInFile(res,filePath);
        }
    }
}

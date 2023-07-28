package utility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WomenHealthURLMethods extends HealthURLMethods{
    public RESTUtility restUtil;

    public Response getLogsHealthAPIWithId(String HealthApiItem,String id) {
        restUtil = new RESTUtility();
        String url = healthURL + HealthApiItem+"/"+id+"/logs";
        System.out.println(url);
        Response res = RestAssured.given()
                .headers(headers).
                contentType(ContentType.JSON)
                .get(url);
//        System.out.println("response   "+ res.prettyPrint());
        System.out.println("Response Code for Get Daily Data Request : "+res.statusCode());
        return res;
    }

    public Response putPeriodLogsWithId(String HealthAPIItem,String id)
    {
        String url = healthURL+"pt/"+id+"/logs";
        Response res;
        List<JSONObject>data = DataGenerationUtility.jsonGenerator(HealthAPIItem);
        res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON)
                .body(data.toString())
                .put(url);
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }

    public boolean verifyPutAndGetPeriodLogs(String HealthApiItem,String id) {
        String url = healthURL +HealthApiItem+"/" + id + "/logs";
        String productId = (String) headers.get("titan-context-product-code");
        Response resPut=putPeriodLogsWithId(HealthApiItem,id);
        Response resGet=getLogsHealthAPIWithId(HealthApiItem,id);
        if(resPut.statusCode()!=200)
        {
            System.out.println("Put request not successful status code is "+resPut.statusCode());
            return false;
        }
        if(resGet.statusCode()!=200)
        {
            System.out.println("Get request not successful status code is "+resGet.statusCode());
            return false;
        }
        restUtil = new RESTUtility();
        System.out.println(url);
        return JSONUtility.compareJsonArrays(resPut.toString(),resGet.toString(),productId);
    }
}

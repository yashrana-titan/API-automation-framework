package utility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        List<JSONObject>data = DataGenerationUtility.jsonGenerator("health/"+HealthAPIItem);
        res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON)
                .body(data.toString())
                .put(url);
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }

    public boolean verifyPutAndGetPeriodLogs(String id) {
        String url = healthURL +"pt/" + id + "/logs";
        String productId = (String) headers.get("titan-context-product-code");
        Response resPut=putPeriodLogsWithId("pt",id);
        Response resGet=getLogsHealthAPIWithId("pt",id);
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

    public List<Integer> getIdForPT()
    {
        Response jsonResponse = getDataFromAPI("health","pt/history");
//        System.out.println(jsonResponse.getBody().asString());
        JSONArray jsonArray = new JSONArray(jsonResponse.getBody().asString());

        List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            idList.add(id);
        }
        System.out.println(idList);
        return idList;

    }

}

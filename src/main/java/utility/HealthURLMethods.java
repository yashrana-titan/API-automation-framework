package utility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HealthURLMethods extends BaseClass{
    public RESTUtility restUtil;
    public String healthURL = (String) urls.get("health");
    public Response getDataHealthAPI(String date, Map<String,Object> headers,String HealthApiItem,String scope) {
        restUtil = new RESTUtility();
        String url = healthURL + HealthApiItem;
        System.out.println(url);
        Response res = RestAssured.given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .param("scope",scope).param("date", date).get(url);
//        System.out.println("response   "+ res.prettyPrint());
        System.out.println(res.asPrettyString());
        System.out.println("Response Code for Get Daily Data Request : "+res.statusCode());
        return res;
    }
    public Response getDataHealthAPI(Map<String,Object> headers,String HealthApiItem,String id) {
        restUtil = new RESTUtility();
        String url = healthURL + HealthApiItem+"/"+id;
        System.out.println(url);
        Response res = RestAssured.given()
                .headers(headers).
                contentType(ContentType.JSON)
                .get(url);
//        System.out.println("response   "+ res.prettyPrint());
        System.out.println(res.asPrettyString());
        System.out.println("Response Code for Get Daily Data Request : "+res.statusCode());
        return res;
    }
    public Response getDataHealthAPI(Map<String,Object> headers,String HealthApiItem) {
        restUtil = new RESTUtility();
        String url = healthURL + HealthApiItem;
        System.out.println(url);
        Response res = RestAssured.given()
                .headers(headers).
                contentType(ContentType.JSON)
                .get(url);
//        System.out.println("response   "+ res.prettyPrint());
        System.out.println("Response Code for Get Daily Data Request : "+res.statusCode());
        return res;
    }

    public Response putDataHealthAPI(String filePath,Map<String,Object> headers,String HealthApiItem)
    {
        String url = healthURL+HealthApiItem;
        System.out.println(url);
        Response res;
        if(filePath.contains("xlsx"))
        {
            System.out.println("entered excel file condition");
            String jsonString = JSONUtility.excelToJson(filePath);
            System.out.println(jsonString);
            res = RestAssured.given().headers(headers)
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .put(url);
        } else {
            res = RestAssured.given().headers(headers)
                    .contentType(ContentType.JSON)
                    .body(new File(filePath))
                    .put(url);

        }
        //System.out.println(res.statusCode());
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }



    public Response putDataHealthAPIFromCSV(String CSVfilePath,String TemplateFilePath,Map<String,Object> headers,String HealthApiItem)
    {
        String url = healthURL+HealthApiItem;
        System.out.println(url);
        Response res;
        String jsonString = JSONPlaceholderReplacer.CreateJsonFromCSV(CSVfilePath,TemplateFilePath).toString();
        System.out.println(jsonString);
        res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON)
                .body(jsonString)
                .put(url);
        //System.out.println(res.statusCode());
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }

    public boolean verifyPutAndGetHealthAPI(String CSVfilePath,String JsonTemplate,Map<String,Object> headers,String HealthApiItem) throws IOException {
        String url = healthURL+HealthApiItem;
        String productId = (String) headers.get("titan-context-product-code");
        System.out.println("\n"+productId+"\n");
        System.out.println(url);
        List<JSONObject>data = JSONPlaceholderReplacer.CreateJsonFromCSV(CSVfilePath,JsonTemplate);
        //System.out.println(data);
        List<String>dates = JSONUtility.fetchDatesfromJSONList(data);
        System.out.println(dates);
        restUtil = new RESTUtility();
        Response resGet;
        Response resPut = RestAssured.given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(data.toString()).put(url);
        if(restUtil.getStatusCode(resPut) != 200)
        {
            System.out.println("Put Method failed Status Code not equal to 200");
            System.out.println("Status Code is "+resPut.getStatusCode());
//            System.out.println(resPut.body().prettyPrint());
            return false;
        }
        int noOfDates = dates.size();
        String startDate=dates.get(0);
        if(noOfDates == 1)
        {
            resGet = RestAssured.given()
                    .headers(headers)
                    .contentType(ContentType.JSON)
                    .param("date", startDate).param("scope","DAY").get(url);
        }
        else if (noOfDates<=7){
            resGet = RestAssured.given()
                    .headers(headers)
                    .contentType(ContentType.JSON)
                    .param("date", startDate).param("scope","WEEK").get(url);
        }
        else
        {
            resGet = RestAssured.given()
                    .headers(headers)
                    .contentType(ContentType.JSON)
                    .param("date", startDate).param("scope","MONTH").get(url);
        }
        if(restUtil.getStatusCode(resGet) != 200){
            System.out.println("Get Method failed Status Code not equal to 200");
            System.out.println("Status Code is "+resPut.getStatusCode());
            System.out.println(resGet.body().prettyPrint());
            return false;
        }
        String responsePut = data.toString();
        System.out.println(headers);
        System.out.println("put data "+responsePut);
        System.out.println("get data "+resGet.asString());
        return JSONUtility.compareJsonArrays(responsePut,resGet.asString(),productId);
    }

    public Response updateDataUsingIDHealthAPI(String filePath,Map<String,Object> headers,String HealthApiItem,String id)
    {
        String url = healthURL+HealthApiItem+"/"+id;
        Response res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON)
                .body(new File(filePath))
                .put(url);
        //System.out.println(res.statusCode());
        System.out.println("Response Code for Update Data Request : "+res.statusCode());
        return res;
    }
}

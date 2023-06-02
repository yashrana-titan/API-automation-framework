package utility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
                .headers(headers).
                contentType(ContentType.JSON)
                .param("scope",scope).param("date", date).get(url);
//        System.out.println("response   "+ res.prettyPrint());
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
        }
        else {
            res = RestAssured.given().headers(headers)
                    .contentType(ContentType.JSON)
                    .body(new File(filePath))
                    .put(url);

        }
        //System.out.println(res.statusCode());
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }

    public boolean verifyPutAndGetHealthAPI(String filePath,Map<String,Object> headers,String HealthApiItem) throws IOException {
        String url = healthURL+HealthApiItem;
        List<String> dates = JSONUtility.fetchDatesFromJson(filePath);
        System.out.println(dates);
        restUtil = new RESTUtility();
        Response resGet;
        Response resPut = RestAssured.given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(new File(filePath)).put(url);
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
        String responsePut = new String(Files.readAllBytes(Paths.get(filePath)));
        System.out.println("put data "+responsePut);
        System.out.println("get data "+resGet.asPrettyString());
        return JSONUtility.compareUsingCommonFields(resGet.asString(),responsePut);
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

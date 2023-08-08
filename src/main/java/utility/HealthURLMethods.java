package utility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;

public class HealthURLMethods extends URLMethods {
    public RESTUtility restUtil;
    public String healthURL = BaseUrl+"health/";
    public Response getDataHealthAPI(String date,String HealthApiItem,String scope) {
        restUtil = new RESTUtility();
        String url = healthURL + HealthApiItem;
        System.out.println(url);
        System.out.println("headers in get method"+headers);
        Response res = RestAssured.given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .param("scope",scope).param("date", date).get(url);
//        System.out.println("response   "+ res.prettyPrint());
        System.out.println(res.asPrettyString());
        System.out.println("Response Code for Get Daily Data Request : "+res.statusCode());
        return res;
    }
    public Response getDataHealthAPI(String HealthApiItem,String id) {
        restUtil = new RESTUtility();
        String url = healthURL + HealthApiItem+"/"+id;
        System.out.println(url);
        Response res = RestAssured.given()
                .headers(headers).
                contentType(ContentType.JSON)
                .get(url);
        System.out.println(res.asPrettyString());
        System.out.println("Response Code for Get Daily Data Request : "+res.statusCode());
        return res;
    }


    public Response putDataHealthAPI(String HealthApiItem)
    {
        String url = healthURL+HealthApiItem;
        System.out.println(url);
        Response res;
        List<JSONObject>data = DataGenerationUtility.jsonGenerator("health/"+HealthApiItem);
        res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON)
                .body(data.toString())
                .put(url);
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }

    public Response putDataHealthAPIWithFilename(String HealthApiItem,String Filename)
    {
        String url = healthURL+HealthApiItem;
        System.out.println(url);
        Response res;
        List<JSONObject>data = DataGenerationUtility.jsonGenerator(Filename);
        res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON)
                .body(data.toString())
                .put(url);
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }



    public Response putDataHealthAPIFromFile(String HealthApiItem,String filePath)
    {
        String url = healthURL+HealthApiItem;
        System.out.println(url);
        Response res;
        String data = null;
        try {
            data = JSONUtility.getJSONString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON)
                .body(data)
                .put(url);
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }


    public Response updateDataUsingIDHealthAPI(String HealthApiItem,String id)
    {
        String url = healthURL+HealthApiItem+"/"+id;
        System.out.println(url);
        Response res;
        List<JSONObject>data = DataGenerationUtility.jsonGenerator("health/"+HealthApiItem);
        res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON)
                .body(data.toString())
                .put(url);
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }



    public boolean verifyPutAndGetHealthAPI(String HealthApiItem) throws IOException {
        String url = healthURL+HealthApiItem;
        String productId = (String) headers.get("titan-context-product-code");
        System.out.println("\n"+productId+"\n");
        System.out.println(url);
        List<JSONObject>data = DataGenerationUtility.jsonGenerator("health",HealthApiItem);
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


    public void getDataHealthAPIinFile(String HealthApiItem, String scope, String date) {
        String url = healthURL + HealthApiItem;
        System.out.println(url);
        Response res = RestAssured.given()
                .headers(headers).
                contentType(ContentType.JSON).param("scope",scope).param("date",date)
                .get(url);
        String filePath = "./src/main/resources/SavedData/"+HealthApiItem+"ptTemplate.json";
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

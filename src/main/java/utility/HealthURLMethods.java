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
import java.util.Objects;

public class HealthURLMethods{
    public RESTUtility restUtil;
    public Response getDailyData( String url,String date, Map<String,Object> headers) {
        restUtil = new RESTUtility();
        System.out.println(url);
        Response res = RestAssured.given()
                .headers(headers).
                contentType(ContentType.JSON)
                .param("date", date).get(url);
//        System.out.println("response   "+ res.prettyPrint());
        System.out.println("Response Code for Get Daily Data Request : "+res.statusCode());
        return res;
    }

    public Response getWeeklyData(String url,String date,Map<String,Object> headers)
    {
        System.out.println("url"+url);
        restUtil = new RESTUtility();
        Response res = RestAssured.given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .param("date", date)
                .param("scope","WEEK").get(url);
        //System.out.println("response of weeklyData"+ res.prettyPrint());
        System.out.println("Response Code for Get Weekly Data Request : "+restUtil.getStatusCode(res));
        return res;
    }

    public Response getMonthlyData(String url,String date,Map<String,Object> headers)
    {
        restUtil = new RESTUtility();
        Response res = RestAssured.given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .param("date", date).param("scope","MONTH").get(url);
        //System.out.println("response   "+ res.prettyPrint());
        System.out.println("Response Code for Get Monthly Data Request : "+restUtil.getStatusCode(res));
        return res;
    }

    public Response putData(String url,String filePath,Map<String,Object> headers)
    {
        Response res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON)
                .body(new File(filePath))
                .put(url);
        //System.out.println(res.statusCode());
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }
    public Response getDataWithoutDate( String url, Map<String,Object> headers) {
        restUtil = new RESTUtility();
        Response res = RestAssured.given()
                .headers(headers)
                .contentType(ContentType.JSON).get(url);
        //System.out.println("response   "+ res.prettyPrint());
        return res;
    }

    public boolean verifyPutAndGet(String url,String filePath,Map<String,Object> headers) throws IOException {
        List<String> dates = JSONUtility.fetchDatesFromJson(filePath);
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
        return JSONUtility.areEqualIgnoringProductField(resGet.asString(),responsePut);
    }

}

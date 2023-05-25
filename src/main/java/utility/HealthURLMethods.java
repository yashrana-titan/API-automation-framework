package utility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HealthURLMethods{
    public RESTUtility restUtil;
//    public String url = (String) urls.get("HeartRate");

    public Response getDailyData( String url,String date, Map<String,Object> headers) {
        restUtil = new RESTUtility();
        Response res = RestAssured.given().headers(headers).contentType(ContentType.JSON).param("date", date).get(url);
        System.out.println("response   "+ res.prettyPrint());
        return res;
    }

    public Response getWeeklyData(String url,String date,Map<String,Object> headers)
    {
        restUtil = new RESTUtility();
        Response res = RestAssured.given().headers(headers).contentType(ContentType.JSON).param("date", date)
                .param("scope","WEEK").get(url);
        System.out.println("response of weeklyData"+ res.prettyPrint());
        return res;
    }

    public Response getMonthlyData(String url,String date,Map<String,Object> headers)
    {
        restUtil = new RESTUtility();
        Response res = RestAssured.given().headers(headers).contentType(ContentType.JSON).param("date", date).param("scope","MONTH").get(url);
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
    public Response getDataWithoutDate( String url, Map<String,Object> headers) {
        restUtil = new RESTUtility();
        Response res = RestAssured.given().headers(headers).contentType(ContentType.JSON).get(url);
        System.out.println("response   "+ res.prettyPrint());
        return res;
    }

    public boolean verifyPutAndGet(String url,String filePath,Map<String,Object> headers)
    {
        List<String> dates = JSONUtility.fetchDatesFromJson(filePath);
        restUtil = new RESTUtility();
        Response resPut = RestAssured.given().headers(headers).contentType(ContentType.JSON)
                .body(new File(filePath)).put(url);
        Response resGet;
        int noOfDates = dates.size();
        String startDate=dates.get(0);
        if(noOfDates == 1)
        {
            resGet = RestAssured.given().headers(headers).contentType(ContentType.JSON)
                    .param("date", startDate).param("scope","DAY").get(url);
        }
        else if (noOfDates<=7){
            resGet = RestAssured.given().headers(headers).contentType(ContentType.JSON)
                    .param("date", startDate).param("scope","WEEK").get(url);
        }
        else
        {
            resGet = RestAssured.given().headers(headers).contentType(ContentType.JSON)
                    .param("date", startDate).param("scope","MONTH").get(url);
        }
        return JSONUtility.areEqualIgnoringProductField(resGet.toString(),resPut.toString());
    }

}

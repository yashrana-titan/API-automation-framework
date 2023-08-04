package utility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class URLMethods extends BaseClass{

    RESTUtility restUtil;

    public Response getDataFromAPI(String URI) {
        restUtil = new RESTUtility();
        String url = BaseUrl+URI;
        System.out.println("URL : "+url);
        Response res = RestAssured.given()
                .headers(headers).
                contentType(ContentType.JSON)
                .get(url);
//        System.out.println("response   "+ res.prettyPrint());
        System.out.println("Response Code for Get Daily Data Request : "+res.statusCode());
        System.out.println("Response body: "+res.asPrettyString());

        return res;
    }

    public Response getDataFromAPI(String URI,String URIendpoint)
    {
        String url = BaseUrl+URI+"/"+URIendpoint;
        System.out.println(url);
        Response res = RestAssured.given()
                .headers(headers).
                contentType(ContentType.JSON)
                .get(url);
        System.out.println("Response Code for Get User Data Request : "+res.statusCode());
        System.out.println("Response body: "+res.asPrettyString());

        return res;
    }

    public Response getDataFromAPI(String URI,String URIendpoint,String date) {
        restUtil = new RESTUtility();
        String url = BaseUrl+URI+"/"+URIendpoint;

        System.out.println(url);
        System.out.println("headers in get method"+headers);
        Response res = RestAssured.given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .param("date", date).get(url);
//        System.out.println("response   "+ res.prettyPrint());
        System.out.println(res.asPrettyString());
        System.out.println("Response Code for Get Daily Data Request : "+res.statusCode());
        return res;
    }
    public Response putDataAPI(String URI,String URIEndpoint)
    {
        String url = BaseUrl+URI+"/"+URIEndpoint;
        System.out.println(url);
        Response res;
        List<JSONObject> data = DataGenerationUtility.jsonGenerator(URI,URIEndpoint);
        System.out.println(data);

        if(data.size()==1 && URIEndpoint !="pt/logs")
        {
            System.out.println("condition for size one");
            res = RestAssured.given().headers(headers)
                    .contentType(ContentType.JSON)
                    .body(data.get(0).toString())
                    .put(url);
        }
        else {
            res = RestAssured.given().headers(headers)
                    .contentType(ContentType.JSON)
                    .body(data.toString())
                    .put(url);
        }
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }

    public Response putDataAPI(String URI)
    {
        String url = BaseUrl+URI;
        System.out.println(url);
        Response res;
        List<JSONObject> data = DataGenerationUtility.jsonGenerator(URI);
        if(data.size()==1)
        {
            res = RestAssured.given().headers(headers)
                    .contentType(ContentType.JSON)
                    .body(data.get(0).toString())
                    .put(url);
        }
        else {
            res = RestAssured.given().headers(headers)
                    .contentType(ContentType.JSON)
                    .body(data.toString())
                    .put(url);
        }

        System.out.println("put data: "+data);
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }

    public Response putDataAPIWithEmptyBody(String URI,String URIEndpoint,String date)
    {
        String url = BaseUrl+URI+"/"+URIEndpoint;
        System.out.println(url);
        Response res;

        res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON).param("date",date)
                .put(url);
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }

    public Response putDataAPIWithEmptyBody(String URI)
    {
        String url = BaseUrl+URI;
        System.out.println(url);
        Response res;

        res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON)
                .put(url);
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }

    public Response postDataAPI(String URI,String URIEndpoint)
    {
        String url = BaseUrl+URI+"/"+URIEndpoint;
        System.out.println(url);
        Response res;
        List<JSONObject> data = DataGenerationUtility.jsonGenerator(URI,URIEndpoint);
        System.out.println(data);
        if(data.size()==1)
        {
            res = RestAssured.given().headers(headers)
                    .contentType(ContentType.JSON)
                    .body(data.get(0).toString())
                    .post(url);
        }
        else {
            res = RestAssured.given().headers(headers)
                    .contentType(ContentType.JSON)
                    .body(data.toString())
                    .post(url);
        }
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }

    public Response postDataAPI(String URI)
    {
        String url = BaseUrl+URI;
        System.out.println(url);
        Response res;
        List<JSONObject> data = DataGenerationUtility.jsonGenerator(URI);
        if(data.size()==1)
        {
            res = RestAssured.given().headers(headers)
                    .contentType(ContentType.JSON)
                    .body(data.get(0).toString())
                    .post(url);
        }
        else {
            res = RestAssured.given().headers(headers)
                    .contentType(ContentType.JSON)
                    .body(data.toString())
                    .post(url);
        }

        System.out.println("put data: "+data);
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }

    public Response deleteDataAPI(String URI,String URIEndpoint)
    {
        String url = BaseUrl+URI+"/"+URIEndpoint;
        System.out.println(url);
        Response res;
        res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON)
                .delete(url);
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }

    public Response putFileInFormData(String URI,String URLItem,String FilePath){
        Map<String, Object> TempHeaders = headers;
        TempHeaders.remove("Content-Type");
        String url = BaseUrl+URI+"/"+URLItem;
        System.out.println("url is: "+url);
        System.out.println(TempHeaders);
        File imageFile = new File(FilePath);
        Response res = RestAssured.given()
                .multiPart("imageFile",imageFile)
                .headers(TempHeaders)
                .put(url);
        System.out.println("Response Code for Get User Data Request : "+res.statusCode());
        return res;
    }



    public static String todayDateString()
    {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return today.format(formatter);
    }

    public String getWeatherkey()
    {
        Response res = getDataFromAPI("users","keys");
        restUtil = new RESTUtility();
        return restUtil.extractValueFromResponse(res,"weather");
    }

    public Response getWeatherForecast(String Key,String lat,String longitude)
    {
        restUtil = new RESTUtility();
        String url = BaseUrl+"weather/v2";
        System.out.println(url);
        Response res = RestAssured.given()
                .param("key",Key).param("lat",lat).param("long",longitude)
                .contentType(ContentType.JSON)
                .get(url);
//        System.out.println("response   "+ res.prettyPrint());
        System.out.println("Response Code for Get Daily Data Request : "+res.statusCode());
        return res;
    }

    public Response getWeatherForecast(String Key,String City)
    {
        restUtil = new RESTUtility();
        String url = BaseUrl+"weather/v2";
        System.out.println(url);
        Response res = RestAssured.given()
                .param("key",Key).param("city",City)
                .contentType(ContentType.JSON)
                .get(url);
//        System.out.println("response   "+ res.prettyPrint());
        System.out.println("Response Code for Get Daily Data Request : "+res.statusCode());
        return res;
    }

}

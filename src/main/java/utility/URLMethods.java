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

public class URLMethods extends BaseClass{

    RESTUtility restUtil;
    public String BaseUrl = (String) urls.get("Base");

    public Response getDataFromAPI(String URI) {
        restUtil = new RESTUtility();
        String url = BaseUrl+URI;
        System.out.println(url);
        Response res = RestAssured.given()
                .headers(headers).
                contentType(ContentType.JSON)
                .get(url);
//        System.out.println("response   "+ res.prettyPrint());
        System.out.println("Response Code for Get Daily Data Request : "+res.statusCode());
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
        return res;
    }
    public Response putDataAPI(String URI,String URIEndpoint)
    {
        String url = BaseUrl+URI+"/"+URIEndpoint;
        System.out.println(url);
        Response res;
        List<JSONObject> data = DataGenerationUtility.jsonGenerator(URI,URIEndpoint);
        res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON)
                .body(data.toString())
                .put(url);
        System.out.println("Response Code for Put Data Request : "+res.statusCode());
        return res;
    }

    public Response putDataAPI(String URI)
    {
        String url = BaseUrl+URI;
        System.out.println(url);
        Response res;
        List<JSONObject> data = DataGenerationUtility.jsonGenerator(URI);
        res = RestAssured.given().headers(headers)
                .contentType(ContentType.JSON)
                .body(data.toString())
                .put(url);
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
}

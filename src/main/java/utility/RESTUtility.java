package utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import utility.BaseClass;
import utility.JSONUtility;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RESTUtility {

    public int getStatusCode(Response res) {
        return res.getStatusCode();
    }

    public String getResBody(Response res) {
        return res.body().asString();
    }

    public static Response getRequest(String url) {
        Response response = RestAssured.given().contentType(ContentType.JSON).get(url);
        return response;
    }

//    public Response getReqWithParamsJSON(String JsonPath)  {
//        Map<String,Object>map;
//        try {
//            map= JSONUtility.getJsonDataInMap(JsonPath);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(map);
//        Response response = RestAssured.given().contentType(ContentType.JSON).queryParams(map).get(CurrentURL);
//        return response;
//    }

//    public Response getReqWithHeadersJSON(String JsonPath)  {
//        Map<String,Object>map;
//        try {
//            map=JSONUtility.getJsonDataInMap(JsonPath);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(map);
//        Response response = RestAssured.given().contentType(ContentType.JSON).headers(map).get(CurrentURL);
//        return response;
//    }
//    public static Response postRequest(String url)
//    {
//
//        return null;
//    }

    public static String getAccessToken() {
        String otp1 = "https://dev-wearables.titan.in/api/registry/users/mobiles";
        String otp2 = "https://dev-wearables.titan.in/api/registry/otp";
        //HashMap<String,Object>body=new HashMap<>(JSONUtility.getJsonDataInMap("./src/main/resources/SignUP.json"));
        String body = null;
        try {
            body = JSONUtility.getJSONString("./src/main/resources/SignUP.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(body);
        Response response1 = RestAssured.given().headers("titan-context-group-code", "FASTRACK").body(body).contentType(ContentType.JSON).post(otp1);
        System.out.println(response1.statusCode());
        //HashMap<String,Object>otpbody=new HashMap<>(JSONUtility.getJsonDataInMap("./src/main/resources/OTP.json"));
        String otpbody = null;
        try {
            otpbody = JSONUtility.getJSONString("./src/main/resources/OTP.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(otpbody);
        Response response = RestAssured.given().header("titan-context-group-code", "FASTRACK").body(otpbody).contentType(ContentType.JSON).post(otp2);
        String token = response.getHeader("access-token");
        System.out.println(response.statusCode());
        System.out.println(token);
        return token;
    }

    public static boolean CompareTwoResponses(Response res1,Response res2)
    {
        String st1 = res1.toString();
        String st2 = res2.toString();
        return (Objects.equals(st1, st2));
    }
}





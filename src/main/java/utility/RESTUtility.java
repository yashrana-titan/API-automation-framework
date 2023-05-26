package utility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;
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

    public static Map<String,Object> getAccessToken() {
        Map<String,Object>map = new HashMap<>();
        String otp1 = "https://dev-wearables.titan.in/api/registry/users/mobiles";
        String otp2 = "https://dev-wearables.titan.in/api/registry/otp";
        //HashMap<String,Object>body=new HashMap<>(JSONUtility.getJsonDataInMap("./src/main/resources/SignUP.json"));
        String body = null;
        try {
            body = JSONUtility.getJSONString("./src/main/resources/SignUP.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("JSON input for User Reg : "+body);
        Response response1 = RestAssured.given().headers("titan-context-group-code", "FASTRACK").body(body).contentType(ContentType.JSON).post(otp1);
        System.out.println("Response code of User Reg : "+response1.statusCode());
        //HashMap<String,Object>otpbody=new HashMap<>(JSONUtility.getJsonDataInMap("./src/main/resources/OTP.json"));
        String otpbody = null;
        try {
            otpbody = JSONUtility.getJSONString("./src/main/resources/OTP.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("JSON input for OTP verification : "+otpbody);
        Response response = RestAssured.given().header("titan-context-group-code", "FASTRACK").body(otpbody).contentType(ContentType.JSON).post(otp2);
        String AccessToken = response.getHeader("access-token");
        String RefreshToken = response.getHeader("refresh-token");
        System.out.println("Response Code of OTP verification : "+response.statusCode());
        System.out.println("Access token : "+AccessToken);
        map.put("Refresh-Token",RefreshToken);
        return map;
    }

    public static Map<String,Object> refreshAccessToken(String RefreshToken) {
        String refreshURL = "https://dev-wearables.titan.in/api/registry/users/renew";
        Map<String,Object>map = new HashMap<>();
        //HashMap<String,Object>body=new HashMap<>(JSONUtility.getJsonDataInMap("./src/main/resources/SignUP.json"));
        String body = null;
        Response response1 = RestAssured.given().headers("titan-context-group-code", "FASTRACK").body(body).contentType(ContentType.JSON).post(refreshURL);
        System.out.println("Response code of Access Token Refresh : "+response1.statusCode());
        //HashMap<String,Object>otpbody=new HashMap<>(JSONUtility.getJsonDataInMap("./src/main/resources/OTP.json"));
        String AccessToken = response1.getHeader("access-token");
        String NewRefreshToken = response1.getHeader("refresh-token");
        System.out.println("Access token : "+AccessToken);
        System.out.println("Refresh token : "+NewRefreshToken);
        //map.put("Access-Token",AccessToken);
        map.put("Refresh-token",RefreshToken);
        return map;
    }

    public static boolean CompareTwoResponses(Response res1,Response res2)
    {
        String st1 = res1.toString();
        String st2 = res2.toString();
        return (Objects.equals(st1, st2));
    }
}





package utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RESTUtility extends JSONUtility{
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

    public static Map<String,Object> getAccessToken() {
        Map<String,Object>map = new HashMap<>();
        String otp1 = BaseUrl+"registry/users/mobiles";
        String otp2 = BaseUrl+"registry/otp";
        //HashMap<String,Object>body=new HashMap<>(JSONUtility.getJsonDataInMap("./src/main/resources/SignUP.json"));
        String body = null;
        try {
            body = JSONUtility.getJSONString("./src/main/resources/prerequisites/SignUP.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("JSON input for User Reg : "+body);
        Response response1 = RestAssured.given().headers("titan-context-group-code", "FASTRACK").body(body).contentType(ContentType.JSON).post(otp1);
        System.out.println("Response code of User Reg : "+response1.statusCode());
        //HashMap<String,Object>otpbody=new HashMap<>(JSONUtility.getJsonDataInMap("./src/main/resources/OTP.json"));
        String otpbody = null;
        try {
            otpbody = JSONUtility.getJSONString("./src/main/resources/prerequisites/OTP.json");
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
        map.put("Access-Token",AccessToken);
        return map;
    }

    public static boolean CompareTwoResponses(Response res1,Response res2)
    {
        String st1 = res1.toString();
        String st2 = res2.toString();
        return (Objects.equals(st1, st2));
    }

    public static boolean compareResponses(Response response1, Response response2, String key) {
        try {


            // Get the response bodies as strings
            String responseString1 = response1.getBody().asString();
            String responseString2 = response2.getBody().asString();

            System.out.println("response1 "+responseString1);
            System.out.println("response2 "+responseString2);

            ObjectMapper objectMapper = new ObjectMapper();

            // Parse JSON responses into JsonNode objects
            JsonNode jsonNode1 = objectMapper.readTree(responseString1);
            JsonNode jsonNode2 = objectMapper.readTree(responseString2);

            // Get the values from the JSON responses for the given key
            String value1 = jsonNode1.get(key).asText();
            String value2 = jsonNode2.get(key).asText();

            // Compare the values
            return value1.equals(value2);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}





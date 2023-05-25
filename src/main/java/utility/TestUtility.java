package utility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TestUtility {
    public static void setBaseURI(String URL) {
        RestAssured.baseURI = "URL.json";
    }
    public static void setBasePath(String basePathTerm) {
        RestAssured.basePath = basePathTerm;
    }
    public static void resetBaseURI() {
        RestAssured.baseURI = null;
    }
    public static void resetBasePath() {
        RestAssured.basePath = null;
    }
    //Sets ContentType
    public static void setContentType(ContentType Type) {
        given().contentType(Type);
    }
    //Returns response by given path
    public static Response getResponse(String path) {
        return given().get(path);
    }
    //Returns response
    public static Response getResponse() {
        return given().get();
    }
    //Returns JsonPath object
    public static JsonPath getJsonPath(Response res) {
        String json = res.asString();
        return new JsonPath(json);
    }
}

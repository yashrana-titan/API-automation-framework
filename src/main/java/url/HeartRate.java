package url;

import io.restassured.response.Response;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.RESTUtility;

public class HeartRate extends BaseClass {
    public RESTUtility restUtil;
    public String url = (String) urls.get("Health")+"hr";
    public HealthURLMethods healthURLMethods = new HealthURLMethods();

    public Response getDailyHR(String date) {
        Response res = healthURLMethods.getDailyData(url, date, headers);
        return res;
    }

    public Response getWeeklyHR(String date) {
        return healthURLMethods.getWeeklyData(url, date, headers);
    }

    public Response getMonthlyHR(String date) {
        return healthURLMethods.getMonthlyData(url, date, headers);
    }

    public Response putHr() {
        String FilePath = "./src/main/java/putData/PutHRData.json";
        return healthURLMethods.putData(url, FilePath, headers);
    }
}



//    public Response getDailyhr(String date) throws IOException {
//        System.out.println("url   "+url);
//        System.out.println("headers   "+headers);
//        restUtil = new RESTUtility();
//        //JSONArray res = new JSONArray();
//        Response res = RestAssured.given().headers(headers).contentType(ContentType.JSON).param("date", date).get(url);
//        System.out.println("response   "+ res.asString());
//        return res;
//    }
//
//    public Response putHR()
//    {
//        // Read the JSON file contents
//        String jsonFilePath="./src/main/java/putData/PutHRData.json";
//        // Send the PUT request with the JSON data
//        Response res = RestAssured.given().headers(headers)
//                .contentType(ContentType.JSON)
//                .body(new File(jsonFilePath))
//                .put(url);
//        System.out.println(res.body().asString());
//        System.out.println(res.statusCode());
//        return res;
//    }
//
//   public Response getHRforInterval(String date,String interval)
//   {
//       restUtil = new RESTUtility();
//       //JSONArray res = new JSONArray();
//       Response res = RestAssured.given().headers(headers).contentType(ContentType.JSON).param("date", date).param("scope",interval).get(url);
//       System.out.println("response   "+ res.prettyPrint());
//       return res;
//   }


package validators.health;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;

public class ValidatorPeriodTracker extends BaseClass {
    public HealthURLMethods healthURLMethods;

    @Test
    public void ValidatePutPT() {
        healthURLMethods = new HealthURLMethods();
        String filePath = "./src/main/java/putdata/PutPTData.json";
        Response res1 = healthURLMethods.putDataHealthAPI(filePath, headers, "pt");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test
    public void ValidateUpdatePT() {
        healthURLMethods = new HealthURLMethods();
        String filePath = "./src/main/java/putdata/PutPTData.json";
        Response res1 = healthURLMethods.updateDataUsingIDHealthAPI(filePath, headers, "pt", "id");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test
    public void ValidateGetPT() {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(headers, "pt", "id");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test
    public void ValidateGetPTHistory() {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(headers, "pt/history", "id");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test
    public void ValidateGetPTAverageAndCycleLength() {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(headers, "pt/avgs");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test
    public void ValidateGetPeriodCycle() {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-11", headers, "pt/avgs", "id");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test
    public void ValidateGetPeriodConfig() {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(headers, "pt/config", "id");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test
    public void ValidatePutPeriodLogs() {
        String filePath = "./src/main/java/putdata/PutPeriodLogs.json";
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.putDataHealthAPI(filePath, headers, "pt");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }
}




//    public Response SavePT()
//    {
//        String FilePath = "./src/main/java/putdata/PutPTData.json";
//        return healthURLMethods.putData(url, FilePath, headers);
//    }
//    public Response UpdatePT(String id) {
//        String tempURL = url+"/"+id;
//        String FilePath = "./src/main/java/putdata/PutPTData.json";
//        return healthURLMethods.putData(tempURL, FilePath, headers);
//    }
//    public Response GetPT(String id) {
//        String tempURL = url + "/" + id;
//        String FilePath = "./src/main/java/putdata/PutPTData.json";
//        return healthURLMethods.getDataWithoutDate(tempURL, headers);
//    }
//
//    public Response GetPTHistory() {
//        String tempURL = url + "/history";
//        return healthURLMethods.getDataWithoutDate(tempURL, headers);
//    }
//    public Response GetAvgPeriodAndCycleLength()
//    {
//        String tempURL = url+"/pt/avgs";
//        return healthURLMethods.getDataWithoutDate(tempURL,headers);
//    }
//    public Response GetPeriodCycle(String date)
//    {
//        String tempURL = url+"/pt/cycle";
//        return healthURLMethods.getDailyData(tempURL,date,headers);
//    }
//    public Response GetPeriodConfig(String date)
//    {
//        String tempURL = url+"/pt/config";
//        return healthURLMethods.getDataWithoutDate(tempURL,headers);
//    }
//    public Response ValidatePutPeriodLogsById(String id)
//    {
//        String tempURL = url+"/pt/"+id+"/logs";
//        String filePath = "./src/main/java/putdata/PutPeriodLogs.json";
//        return healthURLMethods.putData(tempURL,filePath,headers);
//    }
//    public Response PutPeriodLogs()
//    {
//        String tempURL = url+"/pt/logs";
//        String filePath = "./src/main/java/putdata/PutPeriodLogs.json";
//        return healthURLMethods.putData(tempURL,filePath,headers);
//    }
//    public Response GetPeriodLogs(String id)
//    {
//        String tempURL = url+"/pt/"+id+"/logs";
//        return healthURLMethods.getDataWithoutDate(tempURL,headers);
//    }



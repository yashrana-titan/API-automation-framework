package validators.health;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.URLMethods;
import utility.WomenHealthURLMethods;

public class ValidatorWomenHealth extends BaseClass {
    public String id = "11";
    WomenHealthURLMethods womenHealthURLMethods;
    String todayDate = URLMethods.todayDateString();

    @Test(priority = 0)
    public void ValidatePutPT()
    {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 =womenHealthURLMethods.postDataAPI("health","pt");
        Assert.assertEquals(res1.statusCode(),201,"Status Code not 201");
    }

    @Test(priority = 0)
    public void ValidateUpdatePT() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.updateDataUsingIDHealthAPI("pt", id);
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test(priority = 2)
    public void ValidateGetPT() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.getDataHealthAPI( "pt/history", id);
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test(priority = 2)
    public void ValidateGetPTHistory() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.getDataFromAPI("health","pt/history");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test(priority = 2)
    public void ValidateGetPeriodTracker() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.getDataFromAPI("health","pt/tracker");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }


    @Test(priority = 2)
    public void ValidateGetPTAverageAndCycleLength() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.getDataFromAPI("health","pt/avgs");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test(priority = 2)
    public void ValidateGetPeriodCycle() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.getDataFromAPI("health","pt/cycle","2023-08-10");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test(priority = 2)
    public void ValidateGetPeriodConfig() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.getDataFromAPI("health","pt/config");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test(priority = 1)
    public void ValidatePutPeriodLogs() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.putDataAPI("health","pt/logs");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test(priority = 1)
    public void ValidatePutPeriodLogsWithId() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.putPeriodLogsWithId("pt/logs",id);
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test(priority = 2)
    public void ValidateGetPeriodLogs()
    {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res = womenHealthURLMethods.getLogsHealthAPIWithId("pt",id);
        Assert.assertEquals(res.statusCode(), 200, "Status Code not 200");

    }

    @Test(priority = 2)
    public void ValidatePutAndGetGetPeriodLogs()
    {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Boolean result = womenHealthURLMethods.verifyPutAndGetPeriodLogs("pt","11");
        Assert.assertTrue(result);

    }
}

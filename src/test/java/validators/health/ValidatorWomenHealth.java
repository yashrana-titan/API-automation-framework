package validators.health;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.WomenHealthURLMethods;

public class ValidatorWomenHealth {

    WomenHealthURLMethods womenHealthURLMethods;

    @Test
    public void ValidatePutPT()
    {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 =womenHealthURLMethods.putDataHealthAPI("pt");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }

    @Test
    public void ValidateUpdatePT() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.updateDataUsingIDHealthAPI("pt", "id");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test
    public void ValidateGetPT() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.getDataHealthAPI( "pt/history", "id");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test
    public void ValidateGetPTHistory() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.getDataHealthAPI("pt/history");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test
    public void ValidateGetPeriodTracker() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.getDataHealthAPI( "pt/tracker");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }


    @Test
    public void ValidateGetPTAverageAndCycleLength() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.getDataHealthAPI("pt/avgs");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test
    public void ValidateGetPeriodCycle() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.getDataHealthAPI("2023-05-11", "pt/cycle");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test
    public void ValidateGetPeriodConfig() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.getDataHealthAPI("pt/config");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test
    public void ValidatePutPeriodLogs() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.putDataHealthAPIWithFilename("pt/logs","logs");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test
    public void ValidatePutPeriodLogsWithId() {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res1 = womenHealthURLMethods.putPeriodLogsWithId("pt/logs","id");
        Assert.assertEquals(res1.statusCode(), 200, "Status Code not 200");
    }

    @Test
    public void ValidateGetPeriodLogs()
    {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Response res = womenHealthURLMethods.getLogsHealthAPIWithId("pt","id");
        Assert.assertEquals(res.statusCode(), 200, "Status Code not 200");

    }
    
    @Test
    public void ValidatePutAndGetGetPeriodLogs()
    {
        womenHealthURLMethods = new WomenHealthURLMethods();
        Boolean result = womenHealthURLMethods.verifyPutAndGetPeriodLogs("pt","id");
        Assert.assertTrue(result);

    }
}

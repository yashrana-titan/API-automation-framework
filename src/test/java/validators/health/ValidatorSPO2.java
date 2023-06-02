package validators.health;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;

import java.io.IOException;

public class ValidatorSPO2 extends BaseClass {
    public HealthURLMethods healthURLMethods;
    @Test
    public void ValidatePutSpO2()
    {
        healthURLMethods = new HealthURLMethods();
        String filePath = "./src/main/java/putdata/PutSpO2Data.json";
        Response res1 = healthURLMethods.putDataHealthAPI(filePath,headers,"spo2");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetDailySpO2()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-12",headers,"spo2","DAILY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetWeeklySpO2()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-12",headers,"spo2","WEEKLY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetMonthlySpO2()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-12",headers,"spo2","MONTHLY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidatePutAndGetBloodPressure()
    {
        String filePath = "./src/main/java/putdata/PutBloodPressureData.json";
        healthURLMethods = new HealthURLMethods();
        boolean result;
        try {
            result = healthURLMethods.verifyPutAndGetHealthAPI(filePath,headers,"spo2");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(result,"Put and Get JSONs do not match");
    }
}

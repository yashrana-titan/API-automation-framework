import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import url.health.SpO2;
import utility.AssertUtility;
import utility.BaseClass;

import java.io.IOException;

public class TestSpO2 extends BaseClass {
    SpO2 spO2;
    AssertUtility assertUtility;

    @Test(priority = 1)
    public void testGetDailySpO2()
    {
        spO2=new SpO2();
        assertUtility=new AssertUtility();
        Response dailySpO2= spO2.getDailySpO2("2023-05-09");
        //JSONUtility.saveResponseInFile(dailySpO2);
        assertUtility.checkStatusIs200(dailySpO2);
    }
    @Test(priority = 2)
    public void testGetWeeklySpO2(){
        spO2=new SpO2();
        assertUtility=new AssertUtility();
        Response weeklySpO2= spO2.getWeeklySpO2("2023-05-09");
        //JSONUtility.saveResponseInFile(weeklySpO2);
        assertUtility.checkStatusIs200(weeklySpO2);
    }
    @Test(priority = 3)
    public void testGetMonthlySpO2(){
        spO2=new SpO2();
        assertUtility=new AssertUtility();
        Response monthlySpO2= spO2.getMonthlySpO2("2023-05-09");
        assertUtility.checkStatusIs200(monthlySpO2);
    }
    @Test(priority = 4)
    public void testPutSpO2() {
        spO2=new SpO2();
        assertUtility=new AssertUtility();
        Response putSpO2= spO2.putSpO2();
        assertUtility.checkStatusIs200(putSpO2);
    }
    @Test(priority = 5)
    public void testGetAndPut() throws IOException {
        spO2=new SpO2();
        assertUtility=new AssertUtility();
        Boolean check = spO2.getAndPutSpO2();
        System.out.println(check);
        Assert.assertTrue(check,"Put and Get return different JSONs");
    }
}

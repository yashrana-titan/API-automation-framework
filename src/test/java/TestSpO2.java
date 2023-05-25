import io.restassured.response.Response;
import org.testng.annotations.Test;
import url.HeartRate;
import url.SpO2;
import utility.AssertUtility;
import utility.JSONUtility;

import java.io.IOException;

public class TestSpO2{
    SpO2 spo2;
    AssertUtility assertUtility;

    @Test
    public void testGETDailySpO2(){
        spo2=new SpO2();
        assertUtility=new AssertUtility();
        Response dailySpO2 = spo2.getDailySpO2("2023-05-09");
        assertUtility.checkStatusIs200(dailySpO2);
    }
    @Test
    public void testGETWeeklySpO2(){
        spo2=new SpO2();
        assertUtility=new AssertUtility();
        Response weeklySpo2 = spo2.getWeeklySpO2("2023-05-09");
        assertUtility.checkStatusIs200(weeklySpo2);
    }
    @Test
    public void testGETMonthlySpO2(){
        spo2=new SpO2();
        assertUtility=new AssertUtility();
        Response monthlySpo2 = spo2.getMonthlySpO2("2023-02-09");
        JSONUtility.saveResponseInFile(monthlySpo2);
        assertUtility.checkStatusIs200(monthlySpo2);
    }
    @Test
    public void testPUTSpO2() {
        spo2=new SpO2();
        assertUtility=new AssertUtility();
        Response putSpO2= spo2.putSpO2();
        assertUtility.checkStatusIs200(putSpO2);
    }
}

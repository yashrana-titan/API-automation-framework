import io.restassured.response.Response;
import org.testng.annotations.Test;
import url.HeartRate;
import utility.AssertUtility;
import utility.JSONUtility;

import java.io.IOException;

public class TestHeartRate {
    HeartRate hr;
    AssertUtility assertUtility;

    @Test
    public void testGetDailyHR(){
        hr=new HeartRate();
        assertUtility=new AssertUtility();
        Response dailyHR= hr.getDailyHR("2023-05-09");
        assertUtility.checkStatusIs200(dailyHR);
    }
    @Test
    public void testGetWeeklyHR(){
        hr=new HeartRate();
        assertUtility=new AssertUtility();
        Response dailyHR= hr.getWeeklyHR("2023-05-09");
        JSONUtility.saveResponseInFile(dailyHR);
        assertUtility.checkStatusIs200(dailyHR);
    }
    @Test
    public void testGetMonthlyHR(){
        hr=new HeartRate();
        assertUtility=new AssertUtility();
        Response dailyHR= hr.getMonthlyHR("2023-05-09");
        assertUtility.checkStatusIs200(dailyHR);
    }
    @Test
    public void testPutHR() {
        hr=new HeartRate();
        assertUtility=new AssertUtility();
        Response putHr= hr.putHr();
       // assertUtility.checkStatusIs200(putHr);
    }
}

import io.restassured.response.Response;
import org.testng.annotations.Test;
import url.health.HeartRate;
import utility.AssertUtility;
import utility.BaseClass;
import utility.JSONUtility;

public class TestHeartRate extends BaseClass {
    HeartRate heartRate;
    AssertUtility assertUtility;

    @Test
    public void testGetDailyHeartRate()
    {
        heartRate=new HeartRate();
        assertUtility=new AssertUtility();
        Response dailyHeartRate= heartRate.getDailyHeartRate("2023-05-09");
        //JSONUtility.saveResponseInFile(dailyHeartRate);
        assertUtility.checkStatusIs200(dailyHeartRate);
    }
    @Test
    public void testGetWeeklyHeartRate(){
        heartRate=new HeartRate();
        assertUtility=new AssertUtility();
        Response weeklyHeartRate= heartRate.getWeeklyHeartRate("2023-05-09");
        //JSONUtility.saveResponseInFile(weeklyHeartRate);
        assertUtility.checkStatusIs200(weeklyHeartRate);
    }
    @Test
    public void testGetMonthlyHeartRate(){
        heartRate=new HeartRate();
        assertUtility=new AssertUtility();
        Response monthlyHeartRate= heartRate.getMonthlyHeartRate("2023-05-09");
        assertUtility.checkStatusIs200(monthlyHeartRate);
    }
    @Test
    public void testPutHeartRate() {
        heartRate=new HeartRate();
        assertUtility=new AssertUtility();
        Response putHeartRate= heartRate.putHeartRate();
        assertUtility.checkStatusIs200(putHeartRate);
    }
}

import io.restassured.response.Response;
import org.testng.annotations.Test;
import url.health.Temperature;
import utility.AssertUtility;
import utility.BaseClass;
import utility.JSONUtility;

public class TestTemperature extends BaseClass {
    Temperature temperature;
    AssertUtility assertUtility;

    @Test
    public void testGetDailyTemperature()
    {
        temperature=new Temperature();
        assertUtility=new AssertUtility();
        Response dailyTemperature= temperature.getDailyTemperature("2023-05-09");
        //JSONUtility.saveResponseInFile(dailyTemperature);
        assertUtility.checkStatusIs200(dailyTemperature);
    }
    @Test
    public void testGetWeeklyTemperature(){
        temperature=new Temperature();
        assertUtility=new AssertUtility();
        Response weeklyTemperature= temperature.getWeeklyTemperature("2023-05-09");
        //JSONUtility.saveResponseInFile(weeklyTemperature);
        assertUtility.checkStatusIs200(weeklyTemperature);
    }
    @Test
    public void testGetMonthlyTemperature(){
        temperature=new Temperature();
        assertUtility=new AssertUtility();
        Response monthlyTemperature= temperature.getMonthlyTemperature("2023-05-09");
        assertUtility.checkStatusIs200(monthlyTemperature);
    }
    @Test
    public void testPutTemperature() {
        temperature=new Temperature();
        assertUtility=new AssertUtility();
        Response putTemperature= temperature.putTemperature();
        assertUtility.checkStatusIs200(putTemperature);
    }
}

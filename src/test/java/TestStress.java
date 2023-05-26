import io.restassured.response.Response;
import org.testng.annotations.Test;
import url.health.Stress;
import utility.AssertUtility;
import utility.BaseClass;
import utility.JSONUtility;

public class TestStress extends BaseClass {
    Stress stress;
    AssertUtility assertUtility;

    @Test
    public void testGetDailyStress()
    {
        stress=new Stress();
        assertUtility=new AssertUtility();
        Response dailyStress= stress.getDailyStress("2023-05-09");
        //JSONUtility.saveResponseInFile(dailyStress);
        assertUtility.checkStatusIs200(dailyStress);
    }
    @Test
    public void testGetWeeklyStress(){
        stress=new Stress();
        assertUtility=new AssertUtility();
        Response weeklyStress= stress.getWeeklyStress("2023-05-09");
        //JSONUtility.saveResponseInFile(weeklyStress);
        assertUtility.checkStatusIs200(weeklyStress);
    }
    @Test
    public void testGetMonthlyStress(){
        stress=new Stress();
        assertUtility=new AssertUtility();
        Response monthlyStress= stress.getMonthlyStress("2023-05-09");
        assertUtility.checkStatusIs200(monthlyStress);
    }
    @Test
    public void testPutStress() {
        stress=new Stress();
        assertUtility=new AssertUtility();
        Response putStress= stress.putStress();
        assertUtility.checkStatusIs200(putStress);
    }
}

import io.restassured.response.Response;
import org.testng.annotations.Test;
import url.Stress;
import utility.AssertUtility;

public class TestStress{
    Stress Stress;
    AssertUtility assertUtility;

    @Test
    public void testGETDailyStress(){
        Stress=new Stress();
        assertUtility=new AssertUtility();
        Response dailyStress = Stress.getDailyStress("2023-05-11");
        assertUtility.checkStatusIs200(dailyStress);
    }
    @Test
    public void testGETWeeklyStress(){
        Stress=new Stress();
        assertUtility=new AssertUtility();
        Response weeklyStress = Stress.getDailyStress("2023-05-11");
        assertUtility.checkStatusIs200(weeklyStress);
    }
    @Test
    public void testGETMonthlyStress(){
        Stress=new Stress();
        assertUtility=new AssertUtility();
        Response monthlyStress = Stress.getDailyStress("2023-05-11");
        assertUtility.checkStatusIs200(monthlyStress);
    }
    @Test
    public void testPUTStress() {
        Stress=new Stress();
        assertUtility=new AssertUtility();
        Response putHr= Stress.putStress();
        assertUtility.checkStatusIs200(putHr);
    }
}

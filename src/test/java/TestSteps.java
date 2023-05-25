import io.restassured.response.Response;
import org.testng.annotations.Test;
import url.Steps;
import utility.AssertUtility;
import utility.JSONUtility;

public class TestSteps{
    Steps steps;
    AssertUtility assertUtility;

    @Test
    public void testGETDailySteps(){
        steps=new Steps();
        assertUtility=new AssertUtility();
        Response dailySteps = steps.getDailySteps("2023-05-11");
        assertUtility.checkStatusIs200(dailySteps);
    }
    @Test
    public void testGETWeeklySteps(){
        steps=new Steps();
        assertUtility=new AssertUtility();
        Response weeklySteps = steps.getDailySteps("2023-05-11");
        assertUtility.checkStatusIs200(weeklySteps);
    }
    @Test
    public void testGETMonthlySteps(){
        steps=new Steps();
        assertUtility=new AssertUtility();
        Response monthlySteps = steps.getMonthlySteps("2023-05-11");
        JSONUtility.saveResponseInFile(monthlySteps);
        assertUtility.checkStatusIs200(monthlySteps);
    }
    @Test
    public void testPUTSteps() {
        steps=new Steps();
        assertUtility=new AssertUtility();
        Response putHr= steps.putSteps();
        assertUtility.checkStatusIs200(putHr);
    }
}

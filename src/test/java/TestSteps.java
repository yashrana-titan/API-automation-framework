import io.restassured.response.Response;
import org.testng.annotations.Test;
import url.health.Steps;
import utility.AssertUtility;
import utility.JSONUtility;

public class TestSteps{
    Steps steps;
    AssertUtility assertUtility;

    @Test
    public void testGetDailySteps()
    {
        steps=new Steps();
        assertUtility=new AssertUtility();
        Response dailySteps= steps.getDailySteps("2023-05-09");
        //JSONUtility.saveResponseInFile(dailySteps);
        assertUtility.checkStatusIs200(dailySteps);
    }
    @Test
    public void testGetWeeklySteps(){
        steps=new Steps();
        assertUtility=new AssertUtility();
        Response weeklySteps= steps.getWeeklySteps("2023-05-09");
        //JSONUtility.saveResponseInFile(weeklySteps);
        assertUtility.checkStatusIs200(weeklySteps);
    }
    @Test
    public void testGetMonthlySteps(){
        steps=new Steps();
        assertUtility=new AssertUtility();
        Response monthlySteps= steps.getMonthlySteps("2023-05-09");
        assertUtility.checkStatusIs200(monthlySteps);
    }
    @Test
    public void testPutSteps() {
        steps=new Steps();
        assertUtility=new AssertUtility();
        Response putSteps= steps.putSteps();
        assertUtility.checkStatusIs200(putSteps);
    }
}

import io.restassured.response.Response;
import org.testng.annotations.Test;
import url.health.Sleep;
import utility.AssertUtility;
import utility.BaseClass;
import utility.JSONUtility;

public class TestSleep extends BaseClass {
    Sleep sleep;
    AssertUtility assertUtility;

    @Test
    public void testGetDailySleep()
    {
        sleep=new Sleep();
        assertUtility=new AssertUtility();
        Response dailySleep= sleep.getDailySleep("2023-05-09");
        //JSONUtility.saveResponseInFile(dailySleep);
        assertUtility.checkStatusIs200(dailySleep);
    }
    @Test
    public void testGetWeeklySleep(){
        sleep=new Sleep();
        assertUtility=new AssertUtility();
        Response weeklySleep= sleep.getWeeklySleep("2023-05-09");
        //JSONUtility.saveResponseInFile(weeklySleep);
        assertUtility.checkStatusIs200(weeklySleep);
    }
    @Test
    public void testGetMonthlySleep(){
        sleep=new Sleep();
        assertUtility=new AssertUtility();
        Response monthlySleep= sleep.getMonthlySleep("2023-05-09");
        assertUtility.checkStatusIs200(monthlySleep);
    }
    @Test
    public void testPutSleep() {
        sleep=new Sleep();
        assertUtility=new AssertUtility();
        Response putSleep= sleep.putSleep();
        assertUtility.checkStatusIs200(putSleep);
    }
}

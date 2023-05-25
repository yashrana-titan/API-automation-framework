import io.restassured.response.Response;
import org.testng.annotations.Test;
import url.Sleep;
import utility.AssertUtility;
import utility.JSONUtility;

public class TestSleep{
    Sleep Sleep;
    AssertUtility assertUtility;

    @Test
    public void testGETDailySleep(){
        Sleep=new Sleep();
        assertUtility=new AssertUtility();
        Response dailySleep = Sleep.getDailySleep("2023-05-11");
        assertUtility.checkStatusIs200(dailySleep);
    }
    @Test
    public void testGETWeeklySleep(){
        Sleep=new Sleep();
        assertUtility=new AssertUtility();
        Response weeklySleep = Sleep.getDailySleep("2023-05-11");
        assertUtility.checkStatusIs200(weeklySleep);
    }
    @Test
    public void testGETMonthlySleep(){
        Sleep=new Sleep();
        assertUtility=new AssertUtility();
        Response monthlySleep = Sleep.getMonthlySleep("2023-03-11");
        JSONUtility.saveResponseInFile(monthlySleep);
        assertUtility.checkStatusIs200(monthlySleep);
    }
    @Test
    public void testPUTSleep() {
        Sleep=new Sleep();
        assertUtility=new AssertUtility();
        Response putHr= Sleep.putSleep();
        assertUtility.checkStatusIs200(putHr);
    }
}

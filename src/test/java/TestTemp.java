import io.restassured.response.Response;
import org.testng.annotations.Test;
import url.Temperature;
import utility.AssertUtility;

public class TestTemp{
    Temperature Temp;
    AssertUtility assertUtility;

    @Test
    public void testGETDailyTemp(){
        Temp=new Temperature();
        assertUtility=new AssertUtility();
        Response dailyTemp = Temp.getDailyTemp("2023-05-11");
        assertUtility.checkStatusIs200(dailyTemp);
    }
    @Test
    public void testGETWeeklyTemp(){
        Temp=new Temperature();
        assertUtility=new AssertUtility();
        Response weeklyTemp = Temp.getDailyTemp("2023-05-11");
        assertUtility.checkStatusIs200(weeklyTemp);
    }
    @Test
    public void testGETMonthlyTemp(){
        Temp=new Temperature();
        assertUtility=new AssertUtility();
        Response monthlyTemp = Temp.getDailyTemp("2023-05-11");
        assertUtility.checkStatusIs200(monthlyTemp);
    }
    @Test
    public void testPUTTemp() {
        Temp=new Temperature();
        assertUtility=new AssertUtility();
        Response putHr= Temp.putTemp();
        assertUtility.checkStatusIs200(putHr);
    }
}

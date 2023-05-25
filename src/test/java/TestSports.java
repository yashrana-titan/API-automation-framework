import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import url.health.Sports;
import utility.AssertUtility;
import utility.JSONUtility;
import utility.RESTUtility;

public class TestSports {
    Sports sports;
    AssertUtility assertUtility;

    @Test
    public void testGetDailySports()
    {
        sports=new Sports();
        assertUtility=new AssertUtility();
        Response dailySports= sports.getDailySports("2023-05-09");
        //JSONUtility.saveResponseInFile(dailySports);
        assertUtility.checkStatusIs200(dailySports);
    }
    @Test
    public void testGetWeeklySports(){
        sports=new Sports();
        assertUtility=new AssertUtility();
        Response weeklySports= sports.getWeeklySports("2023-05-09");
        //JSONUtility.saveResponseInFile(weeklySports);
        assertUtility.checkStatusIs200(weeklySports);
    }
    @Test
    public void testGetMonthlySports(){
        sports=new Sports();
        assertUtility=new AssertUtility();
        Response monthlySports= sports.getMonthlySports("2023-05-09");
        assertUtility.checkStatusIs200(monthlySports);
    }
    @Test
    public void testPutSports() {
        sports=new Sports();
        assertUtility=new AssertUtility();
        Response putSports= sports.putSports();
        assertUtility.checkStatusIs200(putSports);
    }
//    @Test
//    public void testPutAndGet()
//    {
//        sports = new Sports();
//        assertUtility=new AssertUtility();
//        sports.get
//
//    }
}

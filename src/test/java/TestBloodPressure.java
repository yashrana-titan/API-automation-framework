import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import url.health.BloodPressure;
import url.health.HeartRate;
import url.health.SpO2;
import utility.AssertUtility;
import utility.BaseClass;
import utility.JSONUtility;

import java.io.IOException;

public class TestBloodPressure extends BaseClass {
    BloodPressure bloodPressure;
    AssertUtility assertUtility;

    @Test
    public void testGetDailyBloodPressure()
    {
        bloodPressure=new BloodPressure();
        assertUtility=new AssertUtility();
        Response dailyBloodPressure= bloodPressure.getDailyBloodPressure("2023-05-09");
        //JSONUtility.saveResponseInFile(dailyBloodPressure);
        assertUtility.checkStatusIs200(dailyBloodPressure);
    }
    @Test
    public void testGetWeeklyBloodPressure(){
        bloodPressure=new BloodPressure();
        assertUtility=new AssertUtility();
        Response weeklyBloodPressure= bloodPressure.getWeeklyBloodPressure("2023-05-09");
        //JSONUtility.saveResponseInFile(weeklyBloodPressure);
        assertUtility.checkStatusIs200(weeklyBloodPressure);
    }
    @Test
    public void testGetMonthlyBloodPressure(){
        bloodPressure=new BloodPressure();
        assertUtility=new AssertUtility();
        Response monthlyBloodPressure= bloodPressure.getMonthlyBloodPressure("2023-05-09");
        assertUtility.checkStatusIs200(monthlyBloodPressure);
    }
    @Test
    public void testPutBloodPressure() {
        bloodPressure=new BloodPressure();
        assertUtility=new AssertUtility();
        Response putBloodPressure= bloodPressure.putBloodPressure();
        assertUtility.checkStatusIs200(putBloodPressure);
    }
//    @Test
//    public void testGetAndPut() throws IOException {
//        bloodPressure=new BloodPressure();
//        assertUtility=new AssertUtility();
//        Boolean check = bloodPressure.getAndPu();
//        System.out.println(check);
//        Assert.assertTrue(check,"Put and Get return different JSONs");
//    }
}

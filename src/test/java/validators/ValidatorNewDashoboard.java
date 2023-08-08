package validators;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utility.HealthURLMethods;
import utility.JSONUtility;
import utility.RESTUtility;
import utility.URLMethods;
import validators.DataInFile.HealthItems;

public class ValidatorNewDashoboard {
    HealthURLMethods healthURLMethods;
    String todayDate = URLMethods.todayDateString();
    @Test
    public void ValidateDailyAllHealthData()
    {
        healthURLMethods = new HealthURLMethods();
        Response res = healthURLMethods.getDataHealthAPI(todayDate,"","DAY");
        Assert.assertEquals(res.getStatusCode(),200,"Status code not equal to 200");
    }

    @Test
    public void ValidateWeeklyAllHealthData()
    {
        healthURLMethods = new HealthURLMethods();
        Response res = healthURLMethods.getDataHealthAPI(todayDate,"","WEEK");
        Assert.assertEquals(res.getStatusCode(),200,"Status code not equal to 200");
    }

    @Test
    public void ValidateMonthlyAllHealthData()
    {
        healthURLMethods = new HealthURLMethods();
        Response res = healthURLMethods.getDataHealthAPI(todayDate,"","MONTH");
        Assert.assertEquals(res.getStatusCode(),200,"Status code not equal to 200");
    }

    @Test
    public void ValidateGetTotalsFromMultiSports()
    {
        healthURLMethods = new HealthURLMethods();
        Response resDaily = healthURLMethods.getDataHealthAPI(todayDate,"sports/totals","DAY");
        Response resWeekly = healthURLMethods.getDataHealthAPI(todayDate,"sports/totals","WEEK");
        Response resMonthly = healthURLMethods.getDataHealthAPI(todayDate,"sports/totals","MONTH");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(resDaily.getStatusCode(),200,"Status code for daily data not equal to 200");
        softAssert.assertEquals(resWeekly.getStatusCode(),200,"Status code for weekly data not equal to 200");
        softAssert.assertEquals(resMonthly.getStatusCode(),200,"Status code for monthly data not equal to 200");
        softAssert.assertAll();
    }
    //Modification required
    @Test
    public void ValidateGetPaginatedMultisports()
    {
        healthURLMethods = new HealthURLMethods();
        Response res = healthURLMethods.getDataCustomParameter("health","sports/next","fromDate",todayDate);
        Assert.assertEquals(res.getStatusCode(),200,"Status code not equal to 200");
    }
}

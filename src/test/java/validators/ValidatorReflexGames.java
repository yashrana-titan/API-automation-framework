package validators;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.RESTUtility;
import utility.URLMethods;

public class ValidatorReflexGames extends BaseClass {
    URLMethods urlMethods;
    String todayDate = URLMethods.todayDateString();

    @Test
    public void ValidateGetGames()
    {
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataFromAPI("users","games",todayDate);
        System.out.println(res.getBody().prettyPrint());
        Assert.assertEquals(res.getStatusCode(),200,"Response code not 200");
    }

    @Test
    public void ValidateGetGameEvents()
    {
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataFromAPI("users","games/events",todayDate);
        System.out.println(res.getBody().prettyPrint());
        Assert.assertEquals(res.getStatusCode(),200,"Response code not 200");
    }

    @Test
    public void ValidateGetGameStatistics()
    {
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataFromAPI("leaderboards","games/statistics");
        System.out.println(res.getBody().prettyPrint());
        Assert.assertEquals(res.getStatusCode(),200,"Response code not 200");
    }

    @Test
    public void ValidatePutEnrollment()
    {
        urlMethods = new URLMethods();
        Response res = urlMethods.putDataAPIWithEmptyBody("users","games/enroll",todayDate);
        System.out.println(res.getBody().prettyPrint());
        Assert.assertEquals(res.getStatusCode(),200,"Response code not 200");
    }
}

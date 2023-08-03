package validators;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.URLMethods;

public class ValidatorLeaderboard extends BaseClass {
    URLMethods urlMethods;
    @Test
    public void ValidateGetAllTimeGlobal()
    {
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataFromAPI("leaderboards","global/all");
        System.out.println(res.getBody().prettyPrint());
        Assert.assertEquals(res.getStatusCode(),200,"Response code not 200");
    }

    @Test
    public void ValidateGetAllTimeGeo()
    {
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataFromAPI("leaderboards","geo/all");
        System.out.println(res.getBody().prettyPrint());
        Assert.assertEquals(res.getStatusCode(),200,"Response code not 200");
    }
}

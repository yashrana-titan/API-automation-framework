package validators;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.URLMethods;

public class ValidatorGamification extends BaseClass {
    URLMethods urlMethods;
    @Test
    public void ValidatePostLocation(){
        urlMethods = new URLMethods();
        Response res = urlMethods.postDataAPI("locations");
        Assert.assertEquals(res.getStatusCode(),200,"Response code not equal to 200");
    }

    @Test
    public void ValidateGetGamingProfile()
    {
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataFromAPI("users","gamingprofiles");
        Assert.assertEquals(res.getStatusCode(),200,"Response code not equal to 200");
    }

    @Test
    public void ValidateGetBadges()
    {
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataFromAPI("badges");
        Assert.assertEquals(res.getStatusCode(),200,"Response code not equal to 200");
    }

    @Test
    public void ValidateGetLevels()
    {
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataFromAPI("levels");
        Assert.assertEquals(res.getStatusCode(),200,"Response code not equal to 200");
    }
}

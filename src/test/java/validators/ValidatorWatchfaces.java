package validators;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.URLMethods;

public class ValidatorWatchfaces extends BaseClass {

    URLMethods urlMethods;

    @Test
    public void ValidateSaveRecentWatchFaces()
    {
        urlMethods = new URLMethods();
        Response res = urlMethods.putDataAPI("users","watchfaces");
        Assert.assertEquals(res.getStatusCode(),200,"Status Code not equal to 200");
    }

    @Test
    public void ValidateGetRecentWatchFaces()
    {
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataFromAPI("users","watchfaces");
        Assert.assertEquals(res.getStatusCode(),200,"Status Code not equal to 200");
    }

    @Test
    public void ValidatePutAndGetWatchFace()
    {
        urlMethods = new URLMethods();
        boolean result  = urlMethods.verifyPutAndGetDataAPI("users","watchfaces");
        Assert.assertTrue(result,"Put and Get data not equal");
    }

//    @Test
//    public void ValidateGetRecentWatchFaces()
//    {
//        urlMethods = new URLMethods();
//        Response res = urlMethods.getDataFromAPI("users","watchfaces");
//        Assert.assertEquals(res.getStatusCode(),200,"Status Code not equal to 200");
//    }

    @Test
    public void ValidateGetRecentWatchFacesByCategory()
    {
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataFromAPI("users","watchfaces/categories");
        Assert.assertEquals(res.getStatusCode(),200,"Status Code not equal to 200");
    }

    @Test
    public void ValidateGetAllWatchFaces()
    {
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataFromAPI("watchfaces");
        Assert.assertEquals(res.getStatusCode(),200,"Status Code not equal to 200");
    }

    @Test
    public void ValidateGetAllWatchFacesV2()
    {
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataFromAPI("watchfaces/v2");
        Assert.assertEquals(res.getStatusCode(),200,"Status Code not equal to 200");
    }
}

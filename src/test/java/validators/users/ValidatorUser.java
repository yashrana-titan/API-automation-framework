//package validators.users;
//
//import io.restassured.response.Response;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import utility.BaseClass;
//import utility.HealthURLMethods;
//import utility.URLMethods;
//
//public class ValidatorUser extends BaseClass {
//
//    URLMethods urlMethods;
//    @Test
//    public void ValidateUpdateUser()
//    {
//        urlMethods = new HealthURLMethods();
//        Response res = urlMethods.putDataAPI("users");
//        Assert.assertEquals(res.statusCode(),200,"Status Code not 200");
//    }
//
//    @Test
//    public void ValidateGetUser()
//    {
//        urlMethods = new URLMethods();
//        Response res1 = urlMethods.getDataUsingURI("users");
//        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
//    }
//}

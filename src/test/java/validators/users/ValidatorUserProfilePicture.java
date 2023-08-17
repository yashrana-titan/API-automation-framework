package validators.users;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.RESTUtility;
import utility.URLMethods;

public class ValidatorUserProfilePicture extends BaseClass {
    URLMethods urlMethods;
    @Test(priority = 0)
    public void ValidateUpdateUserProfilePicture()
    {
        urlMethods = new HealthURLMethods();
        Response res1 = urlMethods.putFileInFormData("users","images","./src/main/resources/profilepicture/profile_pic_goku.jpeg");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 1)
    public void ValidateGetUserProfilePicture()
    {
        urlMethods = new HealthURLMethods();
        Response res1 = urlMethods.getDataFromAPI("users","images");
        System.out.println(res1.getBody().prettyPrint());
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }

    @Test(priority = 2)
    public void ValidateSameImageId()
    {
        urlMethods = new HealthURLMethods();
        Response resPutPicture = urlMethods.putFileInFormData("users","images","./src/main/resources/profilepicture/profile_pic_goku.jpeg");
        Response getPicture = urlMethods.getDataFromAPI("users","images");
        boolean result = RESTUtility.compareResponses(resPutPicture,getPicture,"imageId");
        Assert.assertTrue(result);
    }
    
//    @Test(priority = 3)
//    public void ValidateDeleteProfilePicture()
//    {
//        urlMethods = new HealthURLMethods();
//        Response res1 = urlMethods.deleteDataAPI("users","images");
//        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
//    }
}

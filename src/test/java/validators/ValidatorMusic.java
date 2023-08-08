package validators;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.URLMethods;

public class ValidatorMusic extends BaseClass {

    URLMethods urlMethods;
    @Test
    public void ValidateGetAllMusic(){
        urlMethods = new URLMethods();
        Response res1 = urlMethods.getDataFromAPI("music");
        Assert.assertEquals(res1.getStatusCode(),200,"Status Code not equal to 200");
    }
}

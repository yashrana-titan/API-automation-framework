package validators;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;

import java.util.prefs.BackingStoreException;

public class ValidatorProfile extends BaseClass {
    public HealthURLMethods healthURLMethods;

    @Test
    public void ValidatePutProfile(){
        healthURLMethods = new HealthURLMethods();
        Response res = healthURLMethods.putDataAPI("health","profiles");
        Assert.assertEquals(res.getStatusCode(),200);
    }

    @Test
    public void ValidateGetProfile(){
        healthURLMethods = new HealthURLMethods();
        Response res = healthURLMethods.getDataFromAPI("health","profiles");
        Assert.assertEquals(res.getStatusCode(),200);
    }
}

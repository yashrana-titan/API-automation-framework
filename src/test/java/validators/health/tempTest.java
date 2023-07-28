package validators.health;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.DataGenerationUtility;
import utility.HealthURLMethods;

import java.io.IOException;

public class tempTest extends BaseClass {
    public HealthURLMethods healthURLMethods;
    @Test
    public void testSleep()
    {
        healthURLMethods = new HealthURLMethods();
        DataGenerationUtility.csvGenerator("sleep");
        Boolean result= null;
        try {
            result = healthURLMethods.verifyPutAndGetHealthAPI("sleep");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(result.booleanValue(),true);

    }
}

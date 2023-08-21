package validators;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.URLMethods;

public class ValidatorOTA extends BaseClass {
    public URLMethods urlMethods;

    @Test
    public void ValidateGetOTAPackage(){
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataCustomParameter("ota","","version","V1.0.09");
        Assert.assertEquals(res.getStatusCode(),200);
        //"version": "V1.0.09",
    }

    @Test
    public void ValidateGetOTAMetadata(){
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataFromAPI("ota","meta");
        Assert.assertEquals(res.getStatusCode(),200);
    }
}

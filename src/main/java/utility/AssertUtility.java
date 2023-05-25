package utility;

import io.restassured.response.Response;
import org.testng.Assert;

public class AssertUtility {
    public void checkStatusIs200(Response res) {
        Assert.assertEquals(res.getStatusCode(), 200, "Status Check Failed!");
    }
    public void checkStatusIs201(Response res) {
        Assert.assertEquals(res.getStatusCode(), 201, "Status Check Failed!");
    }
}

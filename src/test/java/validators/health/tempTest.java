package validators.health;

import csvgenerator.CSVGenerator;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.JSONPlaceholderReplacer;

public class tempTest extends BaseClass {
    public HealthURLMethods healthURLMethods;
    @Test
    public void testPushSleep()
    {
        healthURLMethods = new HealthURLMethods();
        CSVGenerator.csvGenerator("sleep");
        Response res=healthURLMethods.putDataHealthAPIFromCSV(headers,"sleep");
        System.out.println(res.statusCode());

    }
}

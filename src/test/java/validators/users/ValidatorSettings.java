package validators.users;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.*;

public class ValidatorSettings extends BaseClass {
    public URLMethods urlMethods;
//    @Test
//    public void validatePutSettings() {
//        settingsURLMethods = new SettingsURLMethods();
//        String CSVfilePath = "src/main/java/csvdata/BPData.csv";
//        String TemplateFilePath = "src/main/java/jsontemplates/bpTemplate.json";
//        Response res=settingsURLMethods.putDataHealthAPIFromCSV(CSVfilePath,TemplateFilePath,headers);
//        Assert.assertEquals(res.statusCode(),200);
//    }

    @Test
    public void ValidateGetSettings()
    {
        urlMethods = new HealthURLMethods();
        Response res1 = urlMethods.getDataFromAPI("users","settings");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");

    }
}
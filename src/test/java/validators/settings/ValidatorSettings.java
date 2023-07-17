package validators.settings;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.JSONUtility;
import utility.SettingsURLMethods;

public class ValidatorSettings extends BaseClass {
    public SettingsURLMethods settingsURLMethods;

    @Test
    public void validatePutSettings() {
        settingsURLMethods = new SettingsURLMethods();
        String CSVfilePath = "src/main/java/csvdata/BPData.csv";
        String TemplateFilePath = "src/main/java/jsontemplates/BPTemplate.json";
        Response res=settingsURLMethods.putDataHealthAPIFromCSV(CSVfilePath,TemplateFilePath,headers);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void validateGetSettings()
    {
        settingsURLMethods = new SettingsURLMethods();
        Response res1 = settingsURLMethods.getData(headers);
        JSONUtility.saveResponseInFile(res1,"./src/main/resources/SavedData/settingsdata.json");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");

    }
}
package url.settings;

import io.restassured.response.Response;
import utility.BaseClass;
import utility.RESTUtility;
import utility.SettingsURLMethods;

public class Reflex extends BaseClass {
    public RESTUtility restUtil;
    public String url = (String) urls.get("Settings");
    public SettingsURLMethods settingsURLMethods = new SettingsURLMethods();

    public Response getSettings() {
        Response res = settingsURLMethods.getData(url,headers);
        return res;
    }
    public Response putReflexSettings() {
        String FilePath = "./src/main/java/putData/PutReflexSettingsData.json";
        return settingsURLMethods.putData(url, FilePath, headers);
    }
    public Response putTitanSettings() {
        String FilePath = "./src/main/java/putData/PutTitanSettingsData.json";
        return settingsURLMethods.putData(url, FilePath, headers);
    }
}
package url.health;

import io.restassured.response.Response;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.RESTUtility;

public class PeriodTracking extends BaseClass {
    public RESTUtility restUtil;
    public String url = (String) urls.get("Health")+"pt";
    public HealthURLMethods healthURLMethods = new HealthURLMethods();

    public Response SavePT()
    {
        String FilePath = "./src/main/java/putdata/PutPTData.json";
        return healthURLMethods.putData(url, FilePath, headers);
    }
    public Response UpdatePT(String id) {
        String tempURL = url+"/"+id;
        String FilePath = "./src/main/java/putdata/PutPTData.json";
        return healthURLMethods.putData(tempURL, FilePath, headers);
    }
    public Response GetPT(String id) {
        String tempURL = url + "/" + id;
        String FilePath = "./src/main/java/putdata/PutPTData.json";
        return healthURLMethods.getDataWithoutDate(tempURL, headers);
    }

    public Response GetPTHistory() {
        String tempURL = url + "/history";
        return healthURLMethods.getDataWithoutDate(tempURL, headers);
    }
    public Response GetAvgPeriodAndCycleLength()
    {
        String tempURL = url+"/pt/avgs";
        return healthURLMethods.getDataWithoutDate(tempURL,headers);
    }
    public Response GetPeriodCycle(String date)
    {
        String tempURL = url+"/pt/cycle";
        return healthURLMethods.getDailyData(tempURL,date,headers);
    }
    public Response GetPeriodConfig(String date)
    {
        String tempURL = url+"/pt/config";
        return healthURLMethods.getDataWithoutDate(tempURL,headers);
    }
    public Response PutPeriodLogsById(String id)
    {
        String tempURL = url+"/pt/"+id+"/logs";
        String filePath = "./src/main/java/putdata/PutPeriodLogs.json";
        return healthURLMethods.putData(tempURL,filePath,headers);
    }
    public Response PutPeriodLogs()
    {
        String tempURL = url+"/pt/logs";
        String filePath = "./src/main/java/putdata/PutPeriodLogs.json";
        return healthURLMethods.putData(tempURL,filePath,headers);
    }
    public Response GetPeriodLogs(String id)
    {
        String tempURL = url+"/pt/"+id+"/logs";
        return healthURLMethods.getDataWithoutDate(tempURL,headers);
    }

}

package validators;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.URLMethods;

public class ValidatorTasks extends BaseClass {

    String todayDate;
    URLMethods urlMethods;
    @Test
    public void ValidateGetTaskForTheDay(){
        todayDate = URLMethods.todayDateString();
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataFromAPI("tasks","",todayDate);
        Assert.assertEquals(res.getStatusCode(),200);
    }

    @Test
    public void ValidateGetTaskHistory(){
        todayDate = URLMethods.todayDateString();
        urlMethods = new URLMethods();
        Response res = urlMethods.getDataFromAPIWithScope("tasks",todayDate,"MONTH");
        Assert.assertEquals(res.getStatusCode(),200);
    }
}

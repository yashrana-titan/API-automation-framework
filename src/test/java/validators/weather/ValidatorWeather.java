package validators.weather;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.URLMethods;

public class ValidatorWeather extends BaseClass {
    URLMethods urlMethods;
    String weatherKey;
    @Test
    public void ValidateGetKey(){
        urlMethods = new URLMethods();
        weatherKey = urlMethods.getWeatherkey();
        Assert.assertNotEquals(weatherKey,null);
    }

    @Test
    public void ValidateGetWeatherForecast()
    {
        urlMethods = new URLMethods();
        weatherKey = urlMethods.getWeatherkey();
        Response res = urlMethods.getWeatherForecast(weatherKey,"17.3850","77.1025");
        res.prettyPrint();
        Assert.assertEquals(res.getStatusCode(),200,"status code not 200");
    }

    @Test
    public void ValidateGetWeatherForecastByCity()
    {
        urlMethods = new URLMethods();
        weatherKey = urlMethods.getWeatherkey();
        Response res = urlMethods.getWeatherForecast(weatherKey,"Delhi");
        res.prettyPrint();
        Assert.assertEquals(res.getStatusCode(),200,"status code not 200");
    }
}

package validators.DataInFile;

import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.RESTUtility;

import java.util.Arrays;
import java.util.List;

public class HealthItems extends BaseClass {
    RESTUtility restUtility;

    @Test
    public void saveDataInFile () {
        List<String> items = Arrays.asList("bp", "hr", "steps", "sleep", "spo2");
        for (String item : items) {
            HealthURLMethods healthURLMethods = new HealthURLMethods();
            healthURLMethods.getDataHealthAPIinFile(item, "DAY", "2023-05-11");
        }
    }
}

//package utility;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class test {
//        // Sample JSON response as a string
//        public static void main (String[]args){
//            // Sample JSON response as a string
////            String jsonResponse = "[{\"start\": \"2023-05-22\", \"pl\": 6, \"cl\": 28, \"id\": 1545, \"dates\": []},\n" +
////                    "{\"start\": \"2023-06-19\", \"pl\": 6, \"cl\": 28, \"id\": 1674, \"dates\": []},\n" +
////                    "{\"start\": \"2023-07-17\", \"pl\": 6, \"cl\": 28, \"id\": 1877, \"dates\": []},\n" +
////                    "{\"start\": \"2023-08-01\", \"pl\": 5, \"cl\": 28, \"id\": 1878, \"dates\": []},\n" +
////                    "{\"start\": \"2023-08-03\", \"pl\": 5, \"cl\": 28, \"id\": 1879, \"dates\": []},\n" +
////                    "{\"start\": \"2023-08-09\", \"pl\": 5, \"cl\": 28, \"id\": 1913, \"dates\": []},\n" +
////                    "{\"start\": \"2023-08-10\", \"pl\": 6, \"cl\": 28, \"id\": 1841, \"dates\": [\n" +
////                    "\"2023-08-10\", \"2023-08-11\", \"2023-08-12\", \"2023-08-13\", \"2023-08-14\", \"2023-08-15\"\n" +
////                    "]}]";
//            WomenHealthURLMethods.
//            String jsonResponse =
//
//            // Parse the JSON response into a JSONArray
//            JSONArray jsonArray = new JSONArray(jsonResponse);
//
//            // Extract and store the "id" values into a list of integers
//            List<Integer> idList = new ArrayList<>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                int id = jsonObject.getInt("id");
//                idList.add(id);
//            }
//
//            // Print the list of id values
//            System.out.println(idList);
//        }
//    }
//
//

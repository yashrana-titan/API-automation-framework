package utility;

public class StringTrimmer {

    public static String trimStringToLastDigit(String input) {
        int lastIndex = -1;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                lastIndex = i;
            }
        }

        if (lastIndex != -1) {
            return input.substring(0, lastIndex + 1).trim();
        }

        return input;
    }

    public static void main(String[] args) {
        String input1 = "97%";
        String input2 = "120bpm";
        String input3 = "115/70 mmhg";

        String trimmed1 = trimStringToLastDigit(input1);
        String trimmed2 = trimStringToLastDigit(input2);
        String trimmed3 = trimStringToLastDigit(input3);

        System.out.println(trimmed1); // Output: 97
        System.out.println(trimmed2); // Output: 120
        System.out.println(trimmed3); // Output: 115/70
    }
}

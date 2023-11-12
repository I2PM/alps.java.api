package alps.java.api.util;

public class StringExtractor {
    public static String extractString(String input) {
        int startIndex = input.indexOf("\"") + 1;
        int endIndex = input.indexOf("\"^^");

        if (startIndex >= 0 && endIndex > startIndex) {
            return input.substring(startIndex, endIndex);
        } else {
            return "No valid String found";
        }
    }

}

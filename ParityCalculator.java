package calculators;

public class ParityCalculator {

    

    
    public static String calculateEvenParityAuto(String input) {
        String binary = toBinaryIfNeeded(input);
        return calculateEvenParity(binary);
    }

    public static String appendEvenParityAuto(String input) {
        String binary = toBinaryIfNeeded(input);
        return binary + calculateEvenParity(binary);
    }

    public static boolean hasEvenParityAuto(String input) {
        String binary = toBinaryIfNeeded(input);
        return countOnes(binary) % 2 == 0;
    }

    

   
    public static String calculateEvenParity(String data) {
        requireBinary(data);
        int ones = countOnes(data);
        return (ones % 2 == 0) ? "0" : "1";
    }

    

    
    private static String toBinaryIfNeeded(String input) {
        if (input.matches("[01]+")) {
            return input; // already binary
        }
        return textToBinary(input);
    }

    
    private static String textToBinary(String text) {
        StringBuilder binary = new StringBuilder();
        for (char c : text.toCharArray()) {
            String b = String.format("%8s", Integer.toBinaryString(c))
                    .replace(' ', '0');
            binary.append(b);
        }
        return binary.toString();
    }

    
    private static void requireBinary(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Input must be a non-empty binary string");
        }
        for (char c : s.toCharArray()) {
            if (c != '0' && c != '1') {
                throw new IllegalArgumentException(
                        "Input must contain only '0' or '1': found '" + c + "'");
            }
        }
    }

    
    private static int countOnes(String s) {
        int ones = 0;
        for (char c : s.toCharArray()) {
            if (c == '1') ones++;
        }
        return ones;
    }

}

package calculators;

public class HammingCalculatorEncoder {

    public static String HammingEncoder(String data) {
        StringBuilder encoded = new StringBuilder();

        for (char c : data.toCharArray()) {
            String binary = String.format("%8s",
                    Integer.toBinaryString(c)).replace(' ', '0');


            encoded.append(encodeBlock(binary.substring(0, 4)));
            encoded.append(encodeBlock(binary.substring(4, 8)));
        }
        return encoded.toString();
    }


    private static String encodeBlock(String block) {

        int d1 = block.charAt(0) - '0';
        int d2 = block.charAt(1) - '0';
        int d3 = block.charAt(2) - '0';
        int d4 = block.charAt(3) - '0';

        int p1 = d1 ^ d2 ^ d4;
        int p2 = d1 ^ d3 ^ d4;
        int p3 = d2 ^ d3 ^ d4;


        return "" + p1 + p2 + d1 + p3 + d2 + d3 + d4;
    }
}

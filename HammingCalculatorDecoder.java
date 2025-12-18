package calculators;

public class HammingCalculatorDecoder {

    public static String decodeAndCorrect(String encodedData) {

    StringBuilder binaryData = new StringBuilder();

    for (int i = 0; i < encodedData.length(); i += 7) {

        String block = encodedData.substring(i, i + 7);
        char[] bits = block.toCharArray();

        int p1 = bits[0] - '0';
        int p2 = bits[1] - '0';
        int d1 = bits[2] - '0';
        int p3 = bits[3] - '0';
        int d2 = bits[4] - '0';
        int d3 = bits[5] - '0';
        int d4 = bits[6] - '0';

        int s1 = p1 ^ d1 ^ d2 ^ d4;
        int s2 = p2 ^ d1 ^ d3 ^ d4;
        int s3 = p3 ^ d2 ^ d3 ^ d4;

        int errorPosition = (s3 << 2) | (s2 << 1) | s1;


        if (errorPosition != 0) {
            int index = errorPosition - 1;
            bits[index] = bits[index] == '0' ? '1' : '0';
        }


        binaryData.append(bits[2])
                .append(bits[4])
                .append(bits[5])
                .append(bits[6]);
    }


    StringBuilder text = new StringBuilder();
    for (int i = 0; i < binaryData.length(); i += 8) {
        int ascii = Integer.parseInt(binaryData.substring(i, i + 8), 2);
        text.append((char) ascii);
    }

    return text.toString();
}
}

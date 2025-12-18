package calculators;

public class ParityCalculator {

    public static String calculateEvenParity(String data) {
        int oneCount = 0;
        for (char c : data.toCharArray()) {
            oneCount += Integer.bitCount(c);
        }
        return (oneCount % 2 == 0) ? "0" : "1";
    }
}

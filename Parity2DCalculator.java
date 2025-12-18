package calculators;

public class Parity2DCalculator {
    public static String calculate2DParity(String data) {

       int rows = data.length();
       int columns = 8;

       int[][] matrix =  new int[rows][columns];
       for (int i = 0; i < rows; i++) {
           int ascii = data.charAt(i);
           for (int j = 7; j >= 0; j--) {
               matrix[i][j] = ascii & 1;
               ascii >>= 1;
           }
       }

       StringBuilder rowParity = new StringBuilder();
       StringBuilder columnParity = new StringBuilder();

       for (int i = 0; i < rows; i++) {
           int count = 0;
           for (int j = 0; j < columns; j++) {
               count += matrix[i][j];
           }
           rowParity.append(count % 2 == 0 ? "0" : "1");
       }
       for (int j = 0; j < columns; j++) {
           int count = 0;
           for (int i = 0; i < rows; i++) {
               count += matrix[i][j];
           }
           columnParity.append(count % 2 == 0 ? "0" : "1");
       }
       return rowParity + "," + columnParity;
    }
}

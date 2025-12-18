package calculators;

import java.nio.charset.StandardCharsets;

public class CRC16Calculator {

    public static String calculateCRC(String data) {
        int polynomial = 0x1021;
        int crc = 0xFFFF;

        for (byte b : data.getBytes(StandardCharsets.US_ASCII)) {
            crc ^= (b << 8);
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0) {
                    crc = (crc << 1) ^ polynomial;
                } else  {
                    crc <<= 1;
                }
                crc &= 0xFFFF;
            }
        }
        return String.format("%04X", crc);
    }
}

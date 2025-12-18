package client2;
import calculators.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class Client2 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(5555);
        byte[] receiveData = new byte[1024];

        while (true) {

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            String packet = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.US_ASCII);

            System.out.println("Packet received: " + packet);

            String[] parts = packet.split("\\|");
            if (parts.length < 3) {
                System.out.println("Invalid packet format");
                continue;
            }
            String data = parts[0];
            String method = parts[1];
            String incomingControl = parts[2];


            String computedControl = "";
            boolean status = false;


            switch (method) {
                case "PARITY":
                    computedControl = ParityCalculator.calculateEvenParity(data);
                    status = incomingControl.equals(computedControl);
                    break;

                case "2D_PARITY":
                    computedControl = Parity2DCalculator.calculate2DParity(data);
                    status = incomingControl.equals(computedControl);
                    break;

                case "CRC16":
                    computedControl = CRC16Calculator.calculateCRC(data);
                    status = incomingControl.equals(computedControl);
                    break;

                case "HAMMING":
                    String correctedText = HammingCalculatorDecoder.decodeAndCorrect(incomingControl);
                    System.out.println("Corrected Text: " + correctedText);
                    socket.close();
                    return;

                case "INTERNET CHECKSUM":
                    computedControl = InternetChecksumCalculator.calculateInternetChecksum(data);
                    status = incomingControl.equals(computedControl);
                    break;

            }

            System.out.println("Received Data: " + data);
            System.out.println("Method: " + method);
            System.out.println("Sent Check Bits: " + incomingControl);
            System.out.println("Computed Check Bits: " + computedControl);

            if (status) {
                System.out.println(" Status : Data correct");
            } else {
                System.out.println(" Status : Data Corrupted");
            }

        }
    }
}


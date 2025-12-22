package client1;
import calculators.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter text: ");
            String data = scanner.nextLine();

            System.out.println("Choose the error detection method:");
            System.out.println("1-Parity ");
            System.out.println("2-2D parity ");
            System.out.println("3-CRC16 ");
            System.out.println("4-Hamming code ");
            System.out.println("5-Internet checksum ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice");
                continue;
            }

            String method = "";
            String controlInfo = "";
            switch (choice) {
                case 1:
                    method = "PARITY";
                    controlInfo = ParityCalculator.calculateEvenParityAuto(data);
                    break;
                case 2:
                    method = "2D_PARITY";
                    controlInfo = Parity2DCalculator.calculate2DParity(data);
                    break;
                case 3:
                    method = "CRC16";
                    controlInfo = CRC16Calculator.calculateCRC(data);
                    break;
                case 4:
                    method = "HAMMING";
                    controlInfo = HammingCalculatorEncoder.HammingEncoder(data);
                    break;
                case 5:
                    method = "INTERNET CHECKSUM";
                    controlInfo = InternetChecksumCalculator.calculateInternetChecksum(data);
                    break;
            }

            String packet = data + "|" + method + "|" + controlInfo;
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");


            DatagramPacket sendPacket = new DatagramPacket(packet.getBytes(StandardCharsets.US_ASCII), packet.length(), serverAddress, 4444);
            socket.send(sendPacket);
            socket.close();
            System.out.println("UDP Packet sent" + packet);

            System.out.print("/Send another message (Y/N) : ");
            String again = scanner.nextLine();
            if (!again.equalsIgnoreCase("Y")) {
                System.out.println("END PROGRAM");
                break;
            }

        }
        scanner.close();
    }
}









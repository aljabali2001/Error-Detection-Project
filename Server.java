package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Random;

    public class Server {
        private static final Random R = new Random();
        public static void main(String[] args) throws Exception {
            DatagramSocket socket = new DatagramSocket(4444);
            byte[] receiveData = new byte[1024];
            System.out.println("Server started....");


            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String packet = new String(receivePacket.getData(), 0, receivePacket.getLength(),StandardCharsets.US_ASCII);
                System.out.println("Packet Received: " + packet);

                String corruptedPacket = injectError(packet);
                System.out.println("Corrupted Packet Sent: " + corruptedPacket);

                InetAddress clientIP = receivePacket.getAddress();

                DatagramPacket sendPacket =
                        new DatagramPacket(corruptedPacket.getBytes(StandardCharsets.US_ASCII), corruptedPacket.length(), clientIP, 5555);

                socket.send(sendPacket);
            }

        }
        private static String injectError(String packet) {

            String[] parts = packet.split("\\|");
            if (parts.length < 3) return packet;

            String data = parts[0];
            String method = parts[1];
            String control = parts[2];


            if (method.equals("HAMMING")) {
                if (control.isEmpty()) return packet;

                char[] bits = control.toCharArray();
                int index = R.nextInt(bits.length);
                bits[index] = (bits[index] == '0') ? '1' : '0';

                control = new String(bits);
                return data + "|" + method + "|" + control;
            }


            if (data.isEmpty()) return packet;

            int type = R.nextInt(2); // ONLY bit flip or substitute

            switch (type) {
                case 0: {
                    char[] arr = data.toCharArray();
                    int charIndex = R.nextInt(arr.length);
                    int bitIndex = R.nextInt(8);
                    arr[charIndex] ^= (1 << bitIndex);
                    data = new String(arr);
                    break;
                }
                case 1: {
                    char[] arr = data.toCharArray();
                    int i = R.nextInt(arr.length);
                    arr[i] = (char) ('A' + R.nextInt(26));
                    data = new String(arr);
                    break;
                }
            }

            return data + "|" + method + "|" + control;
        }

    }


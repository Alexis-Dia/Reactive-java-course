package example11;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Alexey Druzik on 4/6/2020
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 45000);
        Scanner scanner = new Scanner(System.in);
        String next = scanner.next();
        socket.getOutputStream().write(next.getBytes());
        socket.close();
        //OutputStream out = socket.getOutputStream();
        //InputStream in = socket.getInputStream();

        //out.write("hello".getBytes()); //Blocking call

        //byte[] data = new byte[1024];
        //while (in.read(data) != -1) { //Blocking call
        //System.out.println("Client:" + data);
        //handle(data);
    }

}

package example11;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Alexey Druzik on 4/6/2020
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 45000);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();

        out.write("hello".getBytes()); //Blocking call

        byte[] data = new byte[1024];
        while (in.read(data) != -1) { //Blocking call
        System.out.println("Client:" + data);
        //handle(data);
    }
}
}

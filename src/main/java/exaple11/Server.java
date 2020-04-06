package exaple11;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Alexey Druzik on 4/6/2020
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        Socket socket = serverSocket.accept();
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();

        byte[] data = new byte[1024];
        while (in.read(data) != -1) {
            out.write(data);
        }
    }
}

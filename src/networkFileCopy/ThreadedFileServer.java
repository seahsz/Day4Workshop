package networkFileCopy;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class ThreadedFileServer {

    public static void main(String[] args) throws IOException {

        // Initialize the port
        int port = 3000;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        // Create a server port
        ServerSocket server = new ServerSocket(port);

        // Create the workers
        ExecutorService thrPool = Executors.newFixedThreadPool(2);

        // While loop to keep the server running
        while (true) {

            System.out.println("Waiting for connection");

            Socket sock = server.accept();
    
            System.out.println("connected");

            ClientHandler worker = new ClientHandler(sock);

            thrPool.submit(worker);
        }

    }
}

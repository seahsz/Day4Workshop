package networkFileCopy;

import java.net.*;
import java.io.*;

public class FileServer {

    public static void main(String[] args) throws IOException {

        // Initialize the port
        int port = 3000;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        // Create a server port
        ServerSocket server = new ServerSocket(port);

        // While loop to keep the server running
        while (true) {

            System.out.println("Waiting for connection");

            Socket sock = server.accept();
    
            System.out.println("connected");

            // Get the input stream
            InputStream is = sock.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            // Read file
            String fileName = dis.readUTF();
            long fileSize = dis.readLong();

            // Get the output stream
            OutputStream os = new FileOutputStream("copy of " + fileName); // 1) use FILEoutputstream because writing to file; 2) write to this file name
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            // Read the file from client and write the file to a newFIle simultaneously
            byte[] buffer = new byte[(int) fileSize];

            int size = 0;

            while ((size = dis.read(buffer)) != -1) { // if -1 means reach EOF
                dos.write(buffer, 0, size); // Avoids writing uninitialized data -- does not matter in this case since we set buffer to filesize. but good to know
            }

            // Close reader & writer
            dos.flush();
            bos.flush();
            os.flush();

            dos.close();
            bos.close();
            os.close();

            dis.close();
            bis.close();
            is.close();

            sock.close();

        }

    }
}

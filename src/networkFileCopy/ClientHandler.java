package networkFileCopy;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

    private final Socket sock;

    public ClientHandler(Socket s) {
        this.sock = s;
    }

    @Override
    public void run() {

        try {
            // Get the input stream
            InputStream is = this.sock.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            // Read file
            String fileName = dis.readUTF();
            long fileSize = dis.readLong();

            // Get the output stream
            OutputStream os = new FileOutputStream("copy of " + fileName); // 1) use FILEoutputstream because writing to
                                                                           // file; 2) write to this file name
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}

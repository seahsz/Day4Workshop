package networkFileCopy;

import java.net.*;
import java.io.*;

public class FileClient {

    public static void main(String[] args) throws IOException {
        
        // Initialize the port
        int port = 3000;
        String filePath = "";
        if (args.length > 1) {
            port = Integer.parseInt(args[0]);
            filePath = args[1];
        } else if (args.length > 0) {
            filePath = args[0];
        } else {
            System.err.println("Missing file name");
            System.exit(1);
        }

        // Create connection to server
        System.out.println("Connecting to server");
        Socket sock = new Socket("localhost", port);

        System.out.println("connected");

        // Get the input stream
        InputStream is = new FileInputStream(filePath);     // use FILEinputstream because reading from a FILE
        BufferedInputStream bis = new BufferedInputStream(is);

        // Get the output stream
        OutputStream os = sock.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bos);

        // Retrieve file info from File
        File file = new File(filePath);

        if (file.exists() && file.isFile()) {
            String fileName = file.getName();
            long fileSize = file.length();

        // Write the file info
            dos.writeUTF(fileName);
            dos.writeLong(fileSize);

        // Read and write file content
            byte[] buffer = new byte[(int) fileSize];

            int size = 0;
    
            while((size = bis.read(buffer)) != -1) {
                dos.write(buffer, 0, size);
            }
        }

        // Close reader & writer
        dos.flush();
        bos.flush();
        os.flush();

        Console cons = System.console();
        cons.readLine("end?");

        bis.close();
        is.close();

        os.close();
        sock.close();
    }
    
}

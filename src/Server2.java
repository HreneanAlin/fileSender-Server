import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server2 {

    public final static int SOCKET_PORT = 5501;


    public static void main (String [] args ) throws IOException {
        FileInputStream fis;
        BufferedInputStream bis = null;
        OutputStream os = null;
        ServerSocket servsock = null;
        Socket sock = null;
        System.out.println("path to file:");
        Scanner scanner = new Scanner(System.in);
        String FILE_TO_SEND = scanner.nextLine();
        try {
            ServerSocket socket =new ServerSocket(5555);
            servsock = new ServerSocket(SOCKET_PORT);

                System.out.println("Waiting...");
                try {
                   Socket nameFileSocket=socket.accept();

                    File myFile = sendFileName(FILE_TO_SEND, socket, nameFileSocket);
                    ServerSocket lenSocket = new ServerSocket(7777);
                    Socket lenghtSocket= lenSocket.accept();
                    System.out.println("Accepted connection : " + lenghtSocket);
                    PrintWriter output = new PrintWriter(lenghtSocket.getOutputStream(),true);
                    output.print(myFile.length());
                    output.close();
                    lenghtSocket.close();
                    lenSocket.close();
                    sock = servsock.accept();
                    System.out.println("Accepted connection : " + sock);
                    byte [] mybytearray  = new byte [(int)myFile.length()];
                    fis = new FileInputStream(myFile);
                    bis = new BufferedInputStream(fis);
                    bis.read(mybytearray,0,mybytearray.length);
                    os = sock.getOutputStream();
                    System.out.println("Sending " + myFile.getName()+ "(" + mybytearray.length + " bytes)");
                    os.write(mybytearray,0,mybytearray.length);
                    os.flush();
                    System.out.println("Done.");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage()+": An Inbound Connection Was Not Resolved");
                } finally {
                if (bis != null) bis.close();
                if (os != null) os.close();
                if (sock!=null) sock.close();
            }

    }
    finally {
        if (servsock != null)
            servsock.close();
    }
}

    private static File sendFileName(String FILE_TO_SEND, ServerSocket socket, Socket nameFileSocket) throws IOException {
        System.out.println("Accepted connection : " + socket);
        PrintWriter output = new PrintWriter(nameFileSocket.getOutputStream(),true);
        File myFile = new File (FILE_TO_SEND);
        output.print(myFile.getName());
        output.close();
        nameFileSocket.close();
        socket.close();
        return myFile;
    }
}
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        System.out.println("Server is runnig");
        try(ServerSocket serverSocket =new ServerSocket(5555)){
            Socket socket = serverSocket.accept();
            System.out.println("Client Connected");
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(),true);
            Scanner scanner = new Scanner(System.in);
            String echoBack;
            while (true){
                String echoString=input.readLine();
                if(echoString.equals("exit")){
                    break;
                }
                System.out.println(echoString);
                System.out.print("Send messeage to Client: ");
                echoBack=scanner.nextLine();
                output.println(echoBack);

            }
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
    }
}

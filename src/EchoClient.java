import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoClient {


    private final  int port;
    private final  String host;

    private EchoClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public static EchoClient connectTo (int port) {
        String localhost = "127.0.0.1";
        return new EchoClient (localhost, port);
    }

    public void run (){
        System.out.println ("напиши 'bye' что бы выйти %n%n%n" );
        try (ServerSocket server = new ServerSocket (  port )) {
            try (Socket clientSocket = server.accept ( )){
                handle (clientSocket);
            }

        }catch (IOException e) {
            String formatMsg = "Вероятнеее всего порт %s занят. %n";
            System.out.printf (formatMsg, port);
            e.printStackTrace ();
        }





        try (Socket socket = new Socket ( host, port )) {

            Scanner scanner = new Scanner (System.in, "UTF-8");
            OutputStream output = socket.getOutputStream ();
            PrintWriter writer = new PrintWriter (output);

            try (scanner; writer){
                while (true) {

                    String message = scanner.nextLine ().trim ();
                    writer.write (message);
                    writer.write (System.lineSeparator ());
                    writer.flush ();

                    if ("bye".equals (message.toLowerCase ( ))) {

                        return;
                    }
                }

            }

        }catch (NoSuchElementException ex) {
            System.out.printf ("Connection dropped! %n");
        } catch (IOException e){
            String msg = "Can't connect to %s:%s!%n";
            System.out.printf (msg, host, port);
            e.printStackTrace ();
        }
    }
    private void handle (Socket socket) throws IOException {
        InputStream input = socket.getInputStream ();
        InputStreamReader isr = new InputStreamReader (input, "UTF-8");

        try (Scanner scanner = new Scanner (isr)) {
            while (true){
                String message = scanner.nextLine ().trim ();


                System.out.printf ("Got: %s%n", message );

            }
        }
    }


}
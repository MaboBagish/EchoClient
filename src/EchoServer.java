import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoServer {

    private final  String host;
    private final  int port;
    private final  String END_WORLD = "bye";
    private EchoServer(String host, int port) {
        this.host = host;
        this.port = port;
    }



    static EchoServer bindToPort ( int port) {
        String localhost = "127.0.0.1";
        return new EchoServer (localhost, port);
    }


    public void run (){

        try (ServerSocket server = new ServerSocket (  port )) {
            try (Socket clientSocket = server.accept ( )){
                handle (clientSocket);
            }

        }catch (IOException e) {
            String formatMsg = "Вероятнеее всего порт %s занят. %n";
            System.out.printf (formatMsg, port);
            e.printStackTrace ();
        }
    }

    private void handle (Socket socket) throws IOException {
        InputStream input = socket.getInputStream ();
        InputStreamReader isr = new InputStreamReader (input, "UTF-8");

        try (Scanner scanner = new Scanner (isr)) {
            while (true){
                String message = scanner.nextLine ().trim ();
                String messageReverce = new StringBuffer(message).reverse().toString();
                System.out.printf ("Got: %s%n", message );

                OutputStream output = socket.getOutputStream ();
                PrintWriter writer = new PrintWriter (output);




                writer.write (messageReverce);
                writer.write (System.lineSeparator ());
                writer.flush ();







                if (END_WORLD.equals (message.toLowerCase ( ))) {
                    System.out.printf ( "Bye Bye!%n" );
                    return;
                }
            }
        }
        catch (NoSuchElementException ex) {
            System.out.printf ("Clientdropped the connection! ");
        }

    }
}

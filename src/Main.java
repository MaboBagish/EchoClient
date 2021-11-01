import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static  Integer PORT = 8788;
    public static void main(String[] args) throws IOException {
        Scanner sc =new Scanner (System.in);
//        final EchoClient echoClient = EchoClient.connectTo ( PORT);
//
//
//        echoClient.run ();



        final EchoServer echoServer = EchoServer.bindToPort  ( PORT);

        echoServer.run ();




    }
}

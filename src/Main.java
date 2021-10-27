import java.util.Scanner;

public class Main {
    private static  Integer PORT = 5555;
    public static void main(String[] args) {
        Scanner sc =new Scanner (System.in);
        final EchoClient echoClient = EchoClient.connectTo ( PORT);
        final EchoServer echoServer = EchoServer.bindToPort (PORT);
        System.out.println ("Для отправки сообщения нажмите 1\n" +
                "Для према сообщения нажмите 2" );

        int mess = sc.nextInt ();





	if(mess ==1){
        echoClient.run ();
	    EchoClient.connectTo (8788).run ();
	}else if(mess ==2) {
	    echoServer.run();
        EchoServer.bindToPort (8788).run ( );
    }else System.out.println ("Неправильный набор" );
    }
}

public class Main {
    private static  Integer PORT = 5555;
    public static void main(String[] args) {
	final EchoClient echoClient = EchoClient.connectTo ( PORT);
	echoClient.run ();
        EchoClient.connectTo (8788).run ();
    }
}

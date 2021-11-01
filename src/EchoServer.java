import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {

 EchoSms echoSms = new EchoSms ();


    private final String host;
    private final int port;
    private final String END_WORLD = "bye";
    private final String VREMYA = "date";
    private final String HHMMSS = "time";
    private final String REVERCE = "reverce";
    private final String UP = "upper";
    private final String TEXT = "sms";
    private final ExecutorService pool = Executors.newCachedThreadPool ( );

    private EchoServer(String host, int port) {
        this.host = host;
        this.port = port;
    }


    static EchoServer bindToPort(int port) {
        String localhost = "127.0.0.1";
        return new EchoServer (localhost, port);
    }


    public void run() {

        try (ServerSocket server = new ServerSocket (port)) {

            while (!server.isClosed ( )) {
                Socket clientSocket = server.accept ( );
                pool.submit (() -> echoSms.handle (clientSocket));
                pool.submit (()->echoSms.handleX (clientSocket));
            }

        } catch (IOException e) {
            String formatMsg = "Вероятнеее всего порт %s занят. %n";
            System.out.printf (formatMsg, port);
            e.printStackTrace ( );
        }
    }




}

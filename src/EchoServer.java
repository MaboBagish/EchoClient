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
                pool.submit (() -> handle (clientSocket));
                pool.submit (()->handleX (clientSocket));
            }

        } catch (IOException e) {
            String formatMsg = "Вероятнеее всего порт %s занят. %n";
            System.out.printf (formatMsg, port);
            e.printStackTrace ( );
        }
    }

    private void handle(Socket socket) {
        System.out.printf ("Client connected: %s\n", socket);
        try (socket;
             Scanner reader = getReader (socket);
             PrintWriter writer = getWriter (socket))
        {
            sendResponse ("Привет" + socket, writer);
            while (true) {
                String message = reader.nextLine ( );
                System.out.println (message );
                if (isEmptyMsg (message) || isQuitMsg (message)) {
                    break;
                }
            }

        } catch (NoSuchElementException ex) {
        System.out.printf ("Client closed: %s\n", socket);
    }catch(
    IOException e)
    {
        e.printStackTrace ( );
    }
System.out.printf ("Client disconnected: %s\n", socket);
}

    private void handleX(Socket socket) {
        System.out.printf ("Client connected: %s\n", socket);
        try (socket;
             Scanner reader = getReader (socket);
             PrintWriter writer = getWriter (socket))
        {
            sendResponse ("Привет" + socket, writer);
            while (true) {
                Scanner scanner = new Scanner (System.in);
                String sms = scanner.nextLine ();
                sendResponse (sms, writer);
                if (isEmptyMsg (sms) || isQuitMsg (sms)) {
                    break;
                }
            }

        } catch (NoSuchElementException ex) {
            System.out.printf ("Client closed: %s\n", socket);
        }catch(
                IOException e)
        {
            e.printStackTrace ( );
        }
        System.out.printf ("Client disconnected: %s\n", socket);
    }






    private void sendResponse(String response, PrintWriter writer) {
        writer.write (response);
        writer.write (System.lineSeparator ());
        writer.flush ();

    }

    private boolean isQuitMsg(String message) {
        return "bye".equalsIgnoreCase (message);
    }

    private boolean isEmptyMsg(String message) {
        return  message == null || message.isBlank ();
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream stream = socket.getOutputStream ();
        return new PrintWriter (stream);
    }

    private Scanner getReader(Socket socket) throws IOException {
        InputStream stream = socket.getInputStream ();
        InputStreamReader input = new InputStreamReader (stream,"UTF-8");
        return  new Scanner (input);
    }

}

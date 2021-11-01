import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoSms {









    public void handle(Socket socket) {
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

    public void handleX(Socket socket) {
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

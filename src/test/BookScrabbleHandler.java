package test;
import java.io.*;
import java.util.Scanner;
public class BookScrabbleHandler implements ClientHandler {
    private PrintWriter out;
    private Scanner in;

    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        Boolean answer = null;
        try {
            in = new Scanner(new BufferedReader(new InputStreamReader(inFromClient)));
            out = new PrintWriter(outToClient, true);

            String line = in.next();
            String[] books = line.substring(2).split(",");

            if (line.startsWith("Q")) {
                answer = DictionaryManager.get().query(books);
            } else if (line.startsWith("C")) {
                answer = DictionaryManager.get().query(books);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.println(answer != null ? answer.toString() : "null" + "\n");
            }
        }
    }

    public void close() {
        try {
            if (in != null) {
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
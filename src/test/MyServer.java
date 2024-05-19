package test;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {

    private int port;
    private ClientHandler ch;
    private volatile boolean stop;


    public MyServer(int p, ClientHandler c){
    this.port = p;
    this.ch = c;
    this.stop=false;
    }

    public void start(){
    new Thread(()->runServer()).start();
    }

	private void runServer(){
        try(ServerSocket server = new ServerSocket(this.port)) {
            server.setSoTimeout(1000);
            while(!stop){
                try(Socket aClient = server.accept()){
                ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                }catch (SocketTimeoutException e){
                    System.out.println("Timed out");
                }catch(IOException e) {
                    e.printStackTrace();
                }finally {
                    ch.close();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            ch.close();
        }
    }
    public void close(){
        this.stop=true;
    }
}

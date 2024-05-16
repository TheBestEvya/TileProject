package test;


import com.sun.security.ntlm.Server;

import java.net.ServerSocket;

public class MyServer {

    private int port;
    private ClientHandler ch;
    private boolean stop;


    public MyServer(int p, ClientHandler c){
    this.port = p;
    this.ch = c;
    this.stop=false;
    }

    public void start(){
//    new Thread(()->runServer()).start();
    runServer();
    }

	private void runServer() throws Exception{
        ServerSocket server = new ServerSocket(this.port);
        server.setSoTimeout(1000);
        while(!stop){
            try{



            }catch (Exception e){

            }



        }

    }
    public void stop(){
        this.stop=true;
    }
}

package pl.pawc.Remote;

import java.util.Scanner;
import java.io.IOException;
import pl.pawc.Remote.ServerListener;

public class Server extends Thread{
    
    private ServerListener serverListener;
        
    public Server(ServerListener serverListener){
        this.serverListener = serverListener;
    }

    public static void main(String args[]){

        ServerListener serverListener = null;   
   
        try{
            int port = Integer.parseInt(args[0]);
            serverListener = new ServerListener(port);
            new Thread(serverListener).start();
        }
        catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
            System.out.println("Wrong port format");
            System.exit(-1);
        }
        catch(IOException e){
            System.out.println("Can't start server on this port");
            System.exit(-1);
        }
        
        Server server = new Server(serverListener);
        new Thread(server).start();

    }
    
    public void run(){

        Scanner scanner = new Scanner(System.in);
        while(true){
            String line = scanner.nextLine();
            switch(line){
                case "clients" :
                    serverListener.showClients();
                    break;
            }
        }
    }

    
}

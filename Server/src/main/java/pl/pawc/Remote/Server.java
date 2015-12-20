package pl.pawc.Remote;

import java.io.IOException;
import pl.pawc.Remote.ServerListener;

public class Server{
    public static void main(String[] args){
        
        try{
            int port = Integer.parseInt(args[0]);
            ServerListener serverListener = new ServerListener(port);
            new Thread(serverListener).run();
        }
        catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
            System.out.println("Wrong port format");
            System.exit(-1);
        }
        catch(IOException e){
            System.out.println("Can't start server on this port");
            System.exit(-1);
        }

        while(true){

        }

    }
}

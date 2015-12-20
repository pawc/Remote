package pl.pawc.Remote;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import pl.pawc.Remote.ClientConnection;
import pl.pawc.Remote.Command;
import java.util.Vector;

public class ServerListener extends Thread{

    private ServerSocket serverSocket;
    private Vector<ClientConnection> list;

    public ServerListener(int port) throws IOException{
        list = new Vector<ClientConnection>();
        System.out.println("Starting server on port "+port);
        serverSocket = new ServerSocket(port);
        System.out.println("Server started");
    }

    public void add(ClientConnection clientConnection){
        list.add(clientConnection);
    }

    public void remove(ClientConnection clientConnection){
        list.remove(clientConnection);
    }

    public void sendToAll(Command command) throws IOException{
        for(ClientConnection clientConnection : list){
            clientConnection.getObjectOutputStream().writeObject(command);
            clientConnection.getObjectOutputStream().flush();
        }
        System.out.println("Command has been send to all");        
    }

    public void run(){
        while(true){
            try{    
                System.out.println("Waiting for incoming connections...");
                Socket socket = serverSocket.accept();
                System.out.println("New connection from "+socket.getInetAddress().toString());
                ClientConnection clientConnection = new ClientConnection(socket, this);
                new Thread(clientConnection).run();
                System.out.println("Thread for the new connection started");
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}

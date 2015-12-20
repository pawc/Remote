package pl.pawc.Remote;

import java.net.Socket;
import java.net.SocketException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import pl.pawc.Remote.ServerListener;
import pl.pawc.Remote.Command;

public class ClientConnection extends Thread{

    private ServerListener serverListener;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    
    public ClientConnection(Socket socket, ServerListener serverListener) throws IOException{
        this.serverListener = serverListener;
        System.out.println("Creating streams...");        
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.flush();
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        System.out.println("Streams created");
        serverListener.add(this);
    }

    public ObjectOutputStream getObjectOutputStream(){
        return objectOutputStream;
    }

    public void run(){
        try{
            while(true){
                Command command = (Command) objectInputStream.readObject();
                System.out.println("Command received: ");
                System.out.println(command.getCommand());
                serverListener.sendToAll(command);
            }            
        }
        catch(IOException | NullPointerException | ClassNotFoundException e){
            e.printStackTrace();
            try{close();}catch(IOException f){f.printStackTrace();}
            System.out.println("Client disconnected: "+socket.getInetAddress().toString());
        }                   
    }

    private void close() throws IOException{
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
        serverListener.remove(this);
    }

}

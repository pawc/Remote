package pl.pawc.Remote;

import java.net.Socket;
import java.net.SocketException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.EOFException;
import pl.pawc.Remote.ServerListener;
import pl.pawc.Remote.Command;

public class ClientConnection extends Thread{

    private ServerListener serverListener;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    
    public ClientConnection(Socket socket, ServerListener serverListener) throws IOException{
        this.socket = socket;
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

    public String toString(){
        return socket.getInetAddress().toString();
    }

    public void run(){
        while(true){
            try{
                Command command = (Command) objectInputStream.readObject();
                System.out.println("Command received: ");
                System.out.println(command.getCommand());
                serverListener.sendToAll(command);
            }   
            catch(IOException | NullPointerException | ClassNotFoundException e){
                //e.printStackTrace();
                serverListener.remove(this);
                System.out.println("Client disconnected");
                break;
            }                   
        }
    }

}

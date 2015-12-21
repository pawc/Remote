package pl.pawc.Remote;

import pl.pawc.Remote.Command;
import pl.pawc.Remote.Execution;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client{
    
    private String author;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    
    public Client(){
        super();
    }

    public Client(Socket socket, String author) throws IOException{
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.flush();
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.author = author;
    }

    public static void main(String[] args){

        //args[0] - host
        //args[1] - port
        //args[2] - sender/receiver

        Socket socket = null;
        try{
            int port = Integer.parseInt(args[1]);
            System.out.print("Connecting to host... ");
            socket = new Socket(args[0], port);
            System.out.println("connected.");
            System.out.print("Creating streams... ");
            Client client = new Client(socket, args[2]);
            System.out.println("created.");
            System.out.print("Starting a listener thread...");
            Listener listener = new Listener(client);
            new Thread(listener).start();
            System.out.println("started.");
            if(client.author.equals("sender")){                    
                Scanner scanner = new Scanner(System.in);
                while(true){
                    String line = scanner.nextLine();
                    Command command = new Command("sender", line);
                    client.getObjectOutputStream().writeObject(command);
                    client.getObjectOutputStream().flush();              
                }
            }
            if(client.author.equals("receiver")){
                while(true){
                }
            }
               
        }
        catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
            System.out.println("Wrong port format");
        }
        catch(IOException e){
            e.printStackTrace();
        }       

    }

    public String execute(String command){
        return Execution.executeCommand(command);
    }

    public String getAuthor(){
        return author;
    }

    public Object read() throws ClassNotFoundException, IOException{
        return objectInputStream.readObject();
    }
    
    public ObjectOutputStream getObjectOutputStream(){
        return objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream(){
        return objectInputStream;
    }

}

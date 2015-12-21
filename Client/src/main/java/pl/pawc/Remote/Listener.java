package pl.pawc.Remote;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import pl.pawc.Remote.Command;
import pl.pawc.Remote.Client;

public class Listener extends Thread{

    private Client client;    

    public Listener(Client client){
        this.client = client;
    }

    public void run(){
        try{
            while(true){
                Command command = (Command) client.read();
                switch(client.getAuthor()){
                    case "sender" :
                        switch(command.getAuthor()){
                            case "sender" :
                                System.out.print(">");
                                System.out.println(command.getCommand()); 
                            break;
                            case "receiver" :
                                System.out.print(">>");
                                System.out.println(command.getCommand());
                            break;
                        }
                    break;
                    case "receiver" :
                        switch(command.getAuthor()){
                            case "sender" :
                                System.out.println(">"+command.getCommand());
                                String result = client.execute(command.getCommand());
                                Command reply = new Command("receiver", result);
                                client.getObjectOutputStream().writeObject(reply);
                                client.getObjectOutputStream().flush();
                            break;        
                            case "receiver" :
                                System.out.print(">>");
                                System.out.println(command.getCommand());
                            break;
                        }
                    break;
                }
            }           
        }
        catch(ClassNotFoundException | IOException e){
            e.printStackTrace();
            System.exit(-1);
        }       
        
    }
}

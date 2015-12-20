package pl.pawc.Remote;

import java.io.Serializable;

public class Command implements Serializable{

    private String author;
    private String command;

    public Command(String author, String command){
        this.author = author;
        this.command = command;
    }

    public String getAuthor(){
        return author;
    }

    public String getCommand(){
        return command;
    }

}

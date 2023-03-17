package com.example.battleships_client_gui;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client{

    private final String hostname;
    private final int port;
    private boolean isLogged;
    private PrintWriter writer;
    private BufferedReader reader;
    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void runClient() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to Battleships Game server");
            try {
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }
            catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        catch (UnknownHostException unknownHostException) {
            System.out.println("Server not found");
            unknownHostException.printStackTrace();
        }
        catch (IOException ioException) {
            System.out.println("I/O Error");
            ioException.printStackTrace();
        }
    }

    public String logInClient (String l, String p){
        String message = "brak wiadmosci";
            writer.println(l);
            writer.println(p);
            try {
                message = reader.readLine();
                //System.out.println(message);
                if (compare(message, "Nie poprawne dane logowania! Spróbuj ponownie")) {
                    this.isLogged = false;
                }
                if (compare(message, "Zalogowano poprawnie.")) {
                   this.isLogged = true;
                }
            }
            catch (IOException ioException)
            {
                if( ioException.getMessage().equals("Socket closed"))
                {
                    System.out.println("Have a good day!");
                }
                ioException.printStackTrace();
            }
        return message;
    }

    public String getInfo(){
        try {
            return reader.readLine();
        } catch (IOException e) {
            return "emptye exep";
        }
    }

    public void sendToServer(String message){
            writer.println(message);
    }

    public boolean isLogged() {
        return isLogged;
    }

    public static boolean compare(String str1, String str2) {
        return (str1 == null ? str2 == null : str1.equals(str2));
    }

    public String shoot(String coord) {
        writer.println(coord);
        return getInfo();
    }
}
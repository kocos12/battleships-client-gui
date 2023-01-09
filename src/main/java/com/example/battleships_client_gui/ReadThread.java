package com.example.battleships_client_gui;

import com.example.battleships_client_gui.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread
{
    private BufferedReader reader;
    private Socket socket;
    private Client client;

    public ReadThread(Socket socket, Client client)
    {
        this.socket = socket;
        this.client = client;
        try
        {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    public void run()
    {
        while (true)
        {
            try
            {
                String message = reader.readLine();
                System.out.println(message);
                if(compare(message,"Nie poprawne dane logowania! Spróbuj ponownie")){
                    client.setLogged(false);
                }
                if(compare(message,"Zalogowano poprawnie.")){
                    client.setLogged(true);
                }
                //if because first message with userName question looks following:
                //null: Enter your name:
//                if(main.client.getUserName() != null)
//                    System.out.print(main.client.getUserName() + ": ");
            }
            catch (IOException ioException)
            {
                if( ioException.getMessage().equals("Socket closed"))
                {
                    System.out.println("Have a good day!");
                    break;
                }
                ioException.printStackTrace();
                break;
            }
        }
    }
    public static boolean compare(String str1, String str2) {
        return (str1 == null ? str2 == null : str1.equals(str2));
    }
}

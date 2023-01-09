package com.example.battleships_client_gui;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private Client client;

    public WriteThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String text;
        do {
//            System.out.print("Enter your login: ");
//            String userLogin = scanner.nextLine();
//            while (userLogin == "") {
//                System.out.print("Enter your login: ");
//                userLogin = scanner.nextLine();
//            }
//
//            System.out.print("Enter your password: ");
//            String userPasswd = scanner.nextLine();
//            while (userPasswd == "") {
//                System.out.print("Enter your password: ");
//                userPasswd = scanner.nextLine();
//            }

            writer.println("jacek");
            writer.println("tajne");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }while(!client.isLogged());


        do {
            System.out.print(" ~");
            text = scanner.nextLine();
            writer.println(text);
        }while (!text.equals("bye"));

        try {
            scanner.close();
            socket.close();
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

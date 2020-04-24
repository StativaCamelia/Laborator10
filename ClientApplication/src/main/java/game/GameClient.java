package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {
    public static void main (String[] args) throws IOException {
        String serverAddress = "127.0.0.1";
        int PORT = 8100;
        Socket socket = new Socket(serverAddress, PORT);
        try {

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            View gamePanel = new View();

            System.out.println("Introduceti un nume:");
            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();
            out.println(username);
            boolean inGame = false;
            while(true) {
                gamePanel.getView(inGame, username);
                String command = scanner.nextLine();
                if(command.toLowerCase().equals("create") || command.toLowerCase().equals("join")){
                    inGame = true;
                }

                if(command.equals("exit")){
                    out.println(command);
                    break;
                }
                if(command.equals("stop")){
                    out.println(command);
                    String response = in.readLine ();
                    System.out.println(response);
                    break;
                }

                out.println(command);
                String response = in.readLine ();
                System.out.println(response);

            }
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }
        finally {
            try{
                socket.close();
            }
            catch (IOException e){
                System.out.println(e);
            }
        }
    }
}
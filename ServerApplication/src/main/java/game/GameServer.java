package game;

import gameComponents.Game;
import gameComponents.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {

    public static final int PORT = 8100;
    public boolean stopServer = false;

    private String username;

    List<Game> availableGames = new ArrayList<Game>();
    List<ClientThread> players = new ArrayList<ClientThread>();



    public GameServer() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);

            while (true) {
                System.out.println ("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                ClientThread newClient = new ClientThread(socket, this);
                players.add(newClient);
                newClient.start();
                if(this.stopServer){
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println ("Eroare" + e);
        } finally {
            serverSocket.close();
        }
    }

    public List<Game> getAvailableGames() {
        return availableGames;
    }

    public void setAvailableGames(List<Game> availableGames) {
        this.availableGames = availableGames;
    }

    public void addGame(Game newGame){
        this.availableGames.add(newGame);
    }

    public void deleteGame(Game completeGame){
        this.availableGames.remove(completeGame);
    }


    public List<ClientThread> getClients() {
        return players;
    }

    public void addPlayer(ClientThread newPlayer){
        this.players.add(newPlayer);
    }
    public void setClients(List<ClientThread> clients) {
        this.players = clients;
    }

    public static void main (String [] args ) throws IOException {
        GameServer server = new GameServer();
    }

    public boolean isStopServer() {
        return stopServer;
    }

    public void setStopServer(boolean stopServer) {
        this.stopServer = stopServer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

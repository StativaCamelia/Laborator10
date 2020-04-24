package game;

import gameComponents.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {

    public static final int PORT = 8100;
    public boolean stopServer = false;
    List<Game> availableGames = new ArrayList<Game>();
    List<ClientThread> clients = new ArrayList<ClientThread>();


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


    public GameServer() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);

            while (true) {
                System.out.println ("Waiting for a client ...");
                Socket socket = serverSocket.accept();

                ClientThread newClient = new ClientThread(socket, this);
                this.clients.add(newClient);
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

    public List<ClientThread> getClients() {
        return clients;
    }

    public void setClients(List<ClientThread> clients) {
        this.clients = clients;
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
}
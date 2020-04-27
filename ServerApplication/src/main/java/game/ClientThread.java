package game;

import com.sun.deploy.nativesandbox.comm.Request;
import gameComponents.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientThread extends Thread {
    enum Requests {
        CREATE, JOIN, MOVE, STOP, EXIT, NULL
    }

    private boolean continueThread = true;
    private Socket socket;
    private GameServer gameServer;
    private String username;
    private boolean waiting = true;
    private boolean inAGame = false;
    private Game actualGame = null;


    public static Requests asMyRequest(String str) {
        for (Requests req : Requests.values()) {
            if (req.name().equalsIgnoreCase(str))
                return req;
        }
        return Requests.NULL;
    }

    public ClientThread(Socket socket, GameServer gameServer) {
        this.socket = socket ;
        this.gameServer = gameServer;
    }

    public Game getActualGame() {
        return actualGame;
    }

    public void setActualGame(Game actualGame) {
        this.actualGame = actualGame;
    }

    public void run () {
        try {
            String raspuns = new String();
            String request = new String();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            GameController gameController = new GameController(this.gameServer);
            this.username = in.readLine();


            while(continueThread) {

                if(!this.waiting){
                    raspuns = "[GAME]A aparut un adversar pentru jocul creat anterior veti fi numarul 1 pe table:";
                    out.print(raspuns);
                    out.flush();
                    this.waiting = true;
                    continue;
                }

                request = in.readLine().toUpperCase();
                String[] reqSplited = request.split("\\s+");

                Requests req = asMyRequest(reqSplited[0]);

                switch (req) {
                    case CREATE:
                        raspuns = gameController.createController(this.inAGame, username);
                        break;
                    case JOIN:
                        raspuns = gameController.joinController(this.inAGame, username);
                        break;
                    case MOVE: {
                        int x = Integer.parseInt(reqSplited[1]);
                        int y = Integer.parseInt(reqSplited[2]);
                        raspuns = gameController.moveController(this.inAGame, actualGame, x, y, username);
                        break;
                    }
                    case STOP : {
                        try {
                            raspuns = "Server stopped...";
                            this.gameServer.setStopServer(true);
                            out.println(raspuns);
                            out.flush();
                            this.socket.close();
                        } catch (IOException e) {
                            System.err.println("Could not close port...");
                            System.exit(1);
                        } finally {
                            continueThread = false;
                            break;
                        }
                    }
                    case EXIT: {
                        try {
                            this.gameServer.getClients().remove(this);
                            this.socket.close();
                        } catch (IOException e) {
                            System.err.println("Could not close...");
                            System.exit(1);
                        } finally {
                            continueThread = false;
                            break;
                        }
                    }
                    case NULL: {
                        raspuns = "Comanda introdusa nu exista";
                        break;
                    }
                }
                out.println(raspuns);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) { System.err.println (e); }
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public boolean isInAGame() {
        return inAGame;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    public void setInAGame(boolean inAGame) {
        this.inAGame = inAGame;
    }
}

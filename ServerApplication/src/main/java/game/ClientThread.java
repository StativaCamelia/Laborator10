package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientThread extends Thread {
    private Socket socket = null ;
    private GameServer gameServer = null;
    private String username;
    private boolean waiting = true;
    private boolean inAGame = false;

    public ClientThread(Socket socket, GameServer gameServer) {
        this.socket = socket ;
        this.gameServer = gameServer;
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

            while(true) {

                if(!this.waiting){
                    raspuns = "Ati inceput in jocul creat";
                    out.println(raspuns);
                    out.flush();
                    this.waiting = true;
                    continue;
                }
                request = in.readLine();
                if (request.toUpperCase().equals("CREATE")) {
                    if(!this.inAGame) {
                        raspuns = gameController.createController(username);
                    }
                    else{
                        raspuns = "Sunteti deja intr-un joc";
                    }

                } else if (request.toUpperCase().equals("JOIN")) {
                    if(!this.inAGame) {
                        raspuns = gameController.joinController(username);
                    }
                    else{
                        raspuns = "Sunteti deja intr-un joc";
                    }
                } else if (request.toUpperCase().equals("MOVE")) {
                    if(inAGame) {
                        raspuns = gameController.moveController();
                    }
                    else{
                        raspuns = "Trebuie sa faceti parte dintr-un joc pentru a putea face o miscare";
                    }
                } else if (request.toUpperCase().equals("STOP")) {
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
                        break;
                    }
                }
                else if (request.toUpperCase().equals("EXIT")){
                    try {
                        this.gameServer.getClients().remove(this);
                        this.socket.close();
                    } catch (IOException e) {
                        System.err.println("Could not close...");
                        System.exit(1);
                    } finally {
                        break;
                    }
                }
                else {
                    raspuns = "Comanda introdusa nu exista";
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

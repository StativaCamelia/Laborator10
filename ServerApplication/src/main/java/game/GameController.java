package game;

import com.sun.security.ntlm.Client;
import gameComponents.Game;
import gameComponents.Player;

public class GameController {

    GameServer serverData;
    public GameController(GameServer serverData){
            this.serverData = serverData;
    }

    public String createController(String username){
        Player player = new Player(username);
        Game newGame = new Game(player);
        serverData.addGame(newGame);
        return "Un nou joc a fost creat asteptati pana este gasit un oponent... ";
    }

    public String joinController(String username){

        boolean succes = false;
        String otherPlayer = new String();
        Player player = new Player(username);
        for(Game game : this.serverData.getAvailableGames()) {
            otherPlayer = game.getPlayers().get(0).getUsername();
            if (!otherPlayer.equals(player.getUsername())) {
                game.addPlayer(player);
                this.serverData.deleteGame(game);
                succes = true;
                for (ClientThread client : this.serverData.getClients()) {
                    if (client.getName().equals(otherPlayer)) {
                        client.setInAGame(true);
                        client.setWaiting(false);
                    }
                    if (client.getName().equals(player.getUsername())) {
                        client.setInAGame(true);
                    }
                }
                break;
            }
        }
        if(succes) {
            return "Ati inceput un joc. Alaturi de jucatorul " + otherPlayer;
        }

        else{
            return "Nu exista nici un joc la care puteti participa in acest moment";
        }
    }

    public String moveController(){
        return "Server received the request ... ";
    }


}

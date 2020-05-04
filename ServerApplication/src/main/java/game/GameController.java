package game;

import com.sun.security.ntlm.Client;
import gameComponents.Game;
import gameComponents.Player;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class GameController {


    GameServer serverData;

    public GameController(GameServer serverData){
            this.serverData = serverData;
    }

    /***
     * Primeste un jucator pe care il adauga la adauga intr-un joc nou creat, apoi adauga jocul in lista de jocuri active pe server
     * @return
     */
    String createController(boolean inAGame, String username){
        if(inAGame){
            return "Sunteti deja intr-un joc nu puteti creea un joc nou";
        }
        Player player = new Player(username);
        Game newGame = new Game(player);
        serverData.addGame(newGame);
        return "Un nou joc a fost creat asteptati pana este gasit un oponent... ";
    }


    /**
     * Verifica in lista de jocuri daca exista un joc care are momentan doar un player, daca exista un astfel de joc adauga noul jucator la acest joc
     * si il avertizeaza pe celalalt jucator ca poate incepe sa joace
     * @param username
     * @return
     */

    public String joinController(boolean inAGame, String username){
        if(inAGame){
            return "Sunteti deja intr-un joc";
        }
        else {
            boolean succes = false;
            String otherPlayer = new String();
            Player player = new Player(username);
            for (Game game : this.serverData.getAvailableGames()) {
                otherPlayer = game.getPlayers().get(0).getUsername();
                if (!otherPlayer.equals(player.getUsername())) {
                    game.addPlayer(player);
                    this.serverData.deleteGame(game);
                    succes = true;
                    game.setReport(new HTMLReporter(player.getUsername(), otherPlayer));
                    for (ClientThread client : this.serverData.players) {
                        if (client.getUsername().equals(otherPlayer)) {
                            client.setInAGame(true);
                            client.setWaiting(false);
                            client.setActualGame(game);
                        }
                        if (client.getUsername().equals(player.getUsername())) {
                            client.setInAGame(true);
                            client.setActualGame(game);
                        }
                    }
                    break;
                }
            }
            if (succes) {

                return "[GAME]Ati inceput un joc. Alaturi de jucatorul " + otherPlayer + " veti fi numarul 2 pe tabla:";
            } else {
                return "Nu exista nici un joc la care puteti participa in acest moment";
            }
        }
    }



    public String moveController(ClientThread player, boolean inAGame, Game actualGame, int x, int y, String username){
        String raspuns;
        int winner;
        if(!inAGame){
            return "Trebuie sa fiti intr-un joc pentru aputea face o mutare";
        }
        else{
            System.out.println(actualGame.getTurn());
            if(!actualGame.isGameEnd()) {
                if (actualGame.getTurn() == 1) {
                    if (username.equals(actualGame.getPlayers().get(0).getUsername())) {
                        raspuns = actualGame.getGameBoard().addPiece(1, x, y);
                        actualGame.getReport().addToHtmlReport("Jucatorul: " + actualGame.getPlayers().get(1).getUsername() + " a adaugat o piese la cordonatele: " + x + " " + y);
                        if (raspuns.startsWith("[OK]")) {
                            winner = actualGame.checkWinner();
                            if (winner != 0) {
                                player.setInAGame(false);
                                actualGame.setGameEnd(true);
                                actualGame.getReport().addToHtmlReport("Jocul a fost castigat de: " + actualGame.getPlayers().get(0).getUsername());
                                actualGame.getReport().saveHtmlReport();
                                return "A CASTIGAT JOCUL: " + winner;

                            }
                            actualGame.setTurn(2);
                        }
                        System.out.println(actualGame.getGameBoard().toString());
                        return raspuns;
                    } else {
                        return "NU este randul tau";
                    }
                } else {
                    if (username.equals(actualGame.getPlayers().get(1).getUsername())) {
                        raspuns = actualGame.getGameBoard().addPiece(2, x, y);
                        actualGame.getReport().addToHtmlReport("Jucatorul: " + actualGame.getPlayers().get(0).getUsername() + " a adaugat o piese la cordonatele: " + x + " " + y);
                        if (raspuns.startsWith("[OK]")) {
                            winner = actualGame.checkWinner();
                            if (winner != 0) {
                                player.setInAGame(false);
                                actualGame.setGameEnd(true);
                                actualGame.getReport().addToHtmlReport("Jocul a fost castigat de: " + actualGame.getPlayers().get(1).getUsername());
                                actualGame.getReport().saveHtmlReport();
                                return "ATI CASTIGAT JOCUL: " + winner;

                            }
                            actualGame.setTurn(1);
                        }
                        System.out.println(actualGame.getGameBoard().toString());
                        return raspuns;
                    } else {
                        return "Nu este randul tau";
                    }
                }
            }
            else {
                player.setInAGame(false);
                return "JOCUL A FOST CASTIGAT DE ADVERSAR";
            }
            }
    }


}

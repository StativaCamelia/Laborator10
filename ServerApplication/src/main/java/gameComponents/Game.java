package gameComponents;

import java.util.ArrayList;
import java.util.List;

public class Game {
    Board gameBoard;
    List<Player> players= new ArrayList<Player>();

    public Game(Player player){
        this.gameBoard = new Board();
        players.add(player);
    }

    public int getNumberOfPlayers(){
        return players.size();
    }

    public void addPlayer(Player player){
        if(this.getNumberOfPlayers() < 2){
            this.players.add(player);
        }
    }

    public void addPiece(int x,int y){
        System.out.println("Piece added at " + x + " " + y);
    }

    public void setGameBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return this.players.toString() + "sunt intr-un joc";
    }
}

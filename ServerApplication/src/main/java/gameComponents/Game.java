package gameComponents;

import game.HTMLReporter;

import java.util.ArrayList;
import java.util.List;

public class Game {
    HTMLReporter report;
    public int turn = 1;
    public boolean gameEnd = false;
    Board gameBoard;
    List<Player> players= new ArrayList<Player>();


    public void setReport(HTMLReporter report) {
        this.report = report;
    }

    public HTMLReporter getReport() {
        return report;
    }

    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    public boolean isGameEnd() {
        return gameEnd;
    }

    public int checkDiagonal(){
        int type = 0;
        for(int i = 0, counter = 0; i < this.getGameBoard().table.length; i++) {
            for(int j = 0; j < this.getGameBoard().table[0].length; j++) {
                if(this.getGameBoard().table[i][j] == this.getGameBoard().table[i+1][j + 1]) {
                    type = this.getGameBoard().table[i][j];
                    counter++;
                }
                if(counter == 5)
                    return type;
            }
        }
        return  type;
    }


    public int checkHorizonal(){
        int type = 0;
        for(int i = 0, counter = 0; i < this.getGameBoard().table.length; i++) {
            for(int j = 0; j < this.getGameBoard().table[0].length; j++) {
                if(this.getGameBoard().table[i][j] == this.getGameBoard().table[i][j + 1]) {
                    type = this.getGameBoard().table[i][j];
                    counter++;
                }
                if(counter == 5)
                    return type;
            }
        }
    return  type;
    }

    public  int checkVerticaly(){
        int type = 0;
        for(int i = 0, counter = 0; i < this.getGameBoard().table.length; i++) {
            for(int j = 0; j < this.getGameBoard().table[0].length; j++) {
                if(this.getGameBoard().table[j][i] == this.getGameBoard().table[j + 1][i])
                    counter++;
                if(counter == 5)
                    return type;
            }
        }
        return type;
    }

    public int checkWinner(){
           if(checkHorizonal() != -1){
               return checkHorizonal();
           }
           else if(checkVerticaly() != -1){
               return checkHorizonal();
           }
           else if(checkDiagonal() != -1){
               return checkDiagonal();
           }
           return -1;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getTurn() {
        return turn;
    }

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

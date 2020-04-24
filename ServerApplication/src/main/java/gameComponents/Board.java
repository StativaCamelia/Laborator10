package gameComponents;

public class Board {
    public int[][] table;

    public Board(){
        table = new int[15][15];
    }

    public int[][] getTable() {
        return table;
    }

    public void setTable(int[][] table) {
        this.table = table;
    }

    public void addPiece(int playerNumber, int x, int y){
        table[x][y] = playerNumber;
    }
}

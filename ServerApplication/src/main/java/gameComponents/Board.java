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

    public String addPiece(int playerNumber, int x, int y){
         if(x-1 < 15 && y-1< 15 && x-1 >=0 && y-1 >=0){
             if(table[x-1][y-1] == 0){
                 table[x-1][y-1] = playerNumber;
                 return "[OK]Miscare realizata cu succes";
             }
             else{
                 return "Exista deja o piesa in acest loc";
             }
         }
         else return "Coordonatele primite nu sunt de pe table";
    }

    @Override
    public String toString() {
        String str = " ";
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                if (j == 14 || (i==14 && j==14))
                    str += (table[i][j] + "\n");
                else
                    str += (table[i][j] + "\t" );

            }
        }
        return str;
    }
}

package game;

public class View {

    public void getView(boolean inGame ,String username) {
        if (inGame) {
            System.out.println("1.Submit move(move + cordonate)");
            System.out.println("2.Stop (stop)");
            System.out.println("3. Exit game (exit)");
        } else {
            System.out.println("Bine ati venit " + username);
            System.out.println("Introduceti una din comenzile:");
            System.out.println("1.Create Game(create)");
            System.out.println("2.Join Game(join)");
            System.out.println("3.Submit move(move + cordonate)");
            System.out.println("4.Stop (stop)");
            System.out.println("5. Exit game (exit)");
        }
    }
}

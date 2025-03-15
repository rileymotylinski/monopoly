


public class Main {
    public static void main(String[] args) {
        String[] players = {"a", "b", "c"};
        Monopoly g = new Monopoly(players);
        for(int i = 0; i<10;i++){
            g.simulateTurn();
        }






    }


}
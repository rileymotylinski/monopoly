


public class Main {
    public static void main(String[] args) {
        String[] players = {"a", "b", "c"};
        Monopoly g = new Monopoly(players, "properties.csv");
        while(g.bankruptPlayers.isEmpty()){
            g.simulateTurn();
        }
        g.printGameState();






    }


}
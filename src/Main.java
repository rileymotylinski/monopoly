


public class Main {
    public static void main(String[] args) {
        String[] players = {"a", "b", "c"};
        Monopoly g = new Monopoly(players, "properties.csv");
        for(int i = 0; i<5000;i++){
            g.simulateTurn();
        }






    }


}
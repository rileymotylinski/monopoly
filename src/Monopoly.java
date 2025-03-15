import java.util.HashMap;
import java.util.Map;

public class Monopoly {
    final Space[] board = {
            new Property("riley's house", 500, 100),
            new Property("the school", 500, 100),
            new Property("the restaurant", 500, 100),
            new Property("the pool", 500, 100),
            new Property("the park", 500, 100),
            new Property("the haunted house", 500, 100),
            new Property("the boardwalk", 500, 100)
    };

    Map<String, Player> players;

    public Monopoly(String[] playerList){
        this.players = new HashMap<>();

        for(String player: playerList){
            this.players.put(player, new Player(player));
        }
    }

    public void simulateTurn(){
        for(Map.Entry<String, Player> player : this.players.entrySet()){
            Player currentPlayer = player.getValue();

            currentPlayer.movePlayer(this.board);


            Space currentSpace = this.board[currentPlayer.getPos()];

            if (currentSpace.getType().equals("property")) {
                Property prop = (Property) currentSpace;
                // could implement a buy method in Space class to avoid casting?
                if (!prop.isAvaliable()) {
                    System.out.println("You have to pay rent!");
                    // update balances of both players
                    players.put(prop.getOwner().getName(), players.get("riley").payRent(prop,prop.getOwner()));
                } else {
                    System.out.println("would you like to buy this property? (y/n) : ");
                    test_board[riley.getPos()] = riley.buy(prop);

                }

            }
        }
    }
}

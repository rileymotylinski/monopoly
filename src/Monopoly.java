import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Monopoly {
    //public static Map<String, Space> board;

    public static Space[] board = {new Property("riley's house", 500, 100),
            new Property("the school", 500, 100),
            new Property("the restaurant", 500, 100),
            new Property("the pool", 500, 100),
            new Property("the park", 500, 100),
            new Jail(),
            new Property("the haunted house", 500, 100),
            new Property("the boardwalk", 500, 100)};

    public Map<String, Player> players;
    public ArrayList<String> jailPlayers;

    public Monopoly(String[] playerList){
        this.players = new HashMap<>();
        this.jailPlayers = new ArrayList<>();
        for(String player: playerList){
            this.players.put(player, new Player(player));
        }
    }


    public boolean updatePlayer(Player p){
        players.put(p.getName(),p);
        return true;
    }

    public void simulateTurn(){
        for(Map.Entry<String, Player> player : this.players.entrySet()){
            Player currentPlayer = player.getValue();
            // skip turn in jail
            if (this.isInJail(currentPlayer)){
                continue
            }
            currentPlayer.movePlayer(Monopoly.board);

            Space currentSpace = Monopoly.board[currentPlayer.getPos()];
            currentSpace.onLand(currentPlayer, this);

            System.out.println("\n");
        }

    }

    public boolean jail(Player p){
        jailPlayers.add(p.getName());
        return true;
    }

    public boolean isInJail(Player p){
        if (p == null) { return false; }
        return jailPlayers.contains(p.getName());
    }

    public String getPropertyOwner(Property p){
        return p.getOwner().getName();
    }
}

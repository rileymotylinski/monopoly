import java.util.HashMap;
import java.util.Map;


public class Monopoly {

    public Space[] board = {new Property("riley's house", 500, 100),
            new Property("the school", 500, 100),
            new Property("the restaurant", 500, 100),
            new Property("the pool", 500, 100),
            new Property("the park", 500, 100),
            new Jail(),
            new Property("the haunted house", 500, 100),
            new Property("the boardwalk", 500, 100)};

    public Map<String, Player> players;
    public Map<String, Integer> jailPlayers;

    public Monopoly(String[] playerList){
        this.players = new HashMap<>();
        this.jailPlayers = new HashMap<>();
        for(String player: playerList){
            this.players.put(player, new Player(player));
        }
    }


    public boolean updatePlayer(Player p){
        players.put(p.getName(),p);
        return true;
    }

    // extra methods just in case we need hash table
    public Map<String, Space> boardHash(){
        Map<String,Space> b = new HashMap<>();
        for(Space s : this.board){
            b.put(s.getName(), s);
        }
        return b;
    }

    public Space[] hashToBoard(Map<String, Space> hash) {
        return hash.values().toArray(new Space[0]);
    }

    public boolean updateBoardProperty(Property p){

        for (int i = 0; i < this.board.length; i++){
            if (this.board[i].getName().equals(p.getName())){
                this.board[i] = p;
            }
        }
        return true;
    }

    public void simulateTurn(){
        // updating players in jail
        Map<String, Integer> jp = new HashMap<>();
        jailPlayers.forEach( (play, turns) -> {
            if (turns > 0) {
                jp.put(play,turns - 1);
            }
        });
        this.jailPlayers = jp;

        for(Map.Entry<String, Player> player : this.players.entrySet()){

            Player currentPlayer = player.getValue();
            // skip turn in jail
            if (this.isInJail(currentPlayer)){
                System.out.println(currentPlayer.getName() + " is currently in jail; they can't take their turn.");
                System.out.println("\n");
                continue;
            }

            // moving
            currentPlayer.movePlayer(this.board);
            Space currentSpace = this.board[currentPlayer.getPos()];

            currentSpace.onLand(currentPlayer, this);


            System.out.println("\n");


        }

    }
    // put a player in jail
    public boolean jail(Player p){
        jailPlayers.put(p.getName(),3);
        return true;
    }
    // see if a player is in jail
    public boolean isInJail(Player p){
        if (p == null) { return false; }
        return jailPlayers.containsKey(p.getName());
    }

    public String getPropertyOwner(Property p){
        return p.getOwner().getName();
    }
}

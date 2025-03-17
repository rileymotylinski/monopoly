import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOError;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;


public class Monopoly {

    public Space[] board;

    public Map<String, Player> players;
    public Map<String, Integer> jailPlayers;

    public Monopoly(String[] playerList, String csvFilePath){
        this.players = new HashMap<>();
        this.jailPlayers = new HashMap<>();

        for(String player: playerList){
            this.players.put(player, new Player(player));
        }
        try {
            FileReader filereader = new FileReader(csvFilePath);
            this.board = Property.instantiateProperties(filereader);
        } catch (FileNotFoundException f) {
            System.out.println("There was no csv file provided!");
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

        // taking turns for each player
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

            // perform event when the player visits the space
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

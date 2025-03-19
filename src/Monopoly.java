import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOError;
import java.util.*;


public class Monopoly {

    public Space[] board;
    public Property[] boardProperties;

    public Map<String, Player> players;
    public Map<String, Integer> jailPlayers;
    public ArrayList<Player> bankruptPlayers;

    public Monopoly(String[] playerList, String csvFilePath){
        this.players = new HashMap<>();
        this.jailPlayers = new HashMap<>();
        this.bankruptPlayers = new ArrayList<>();

        for(String player: playerList){
            this.players.put(player, new Player(player));
        }

        //creating board
        try {
            ArrayList<Space> tempBoard = new ArrayList<>();

            //properties
            FileReader filereader = new FileReader(csvFilePath);
            // need to add jail/go to jail etc.
            Property[] parsedProperties = Property.instantiateProperties(filereader);
            //informational purposes; doesn't need to be touched
            this.boardProperties = parsedProperties;

            tempBoard.addAll(List.of(parsedProperties));

            // jail - insert 3/4's of the way around the board, roughly
            tempBoard.add((int) (tempBoard.size()*0.75), new Jail());
            // go to jail
            tempBoard.add((int) (tempBoard.size()*0.25), new ToJail());
            // go

            Space[] tb = new Space[tempBoard.size()];
            this.board = tempBoard.toArray(tb);

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

        // Cycle through each active player every turn
        for(Map.Entry<String, Player> player : this.players.entrySet()){

            Player currentPlayer = player.getValue();

            // skip turn in jail
            if (this.isInJail(currentPlayer)){
                System.out.println(currentPlayer.getName() + " is currently in jail; they can't take their turn.");
                System.out.println("\n");
                continue;
            }
            // remove from game if player is bankrupt
            // can't do it while the for loop is happening otherwise it throws a concurrent modification exception


            if (this.bankruptPlayers.contains(currentPlayer)){
                continue;
            }

            // moving
            currentPlayer.movePlayer(this.board);
            Space currentSpace = this.board[currentPlayer.getPos()];

            // perform event when the player visits the space
            currentSpace.onLand(currentPlayer, this);

            if(currentPlayer.isBankrupt() && !this.bankruptPlayers.contains(currentPlayer)){
                // returning properties
                for (Property p : currentPlayer.getOwnedProperties()){
                    this.updateBoardProperty(p.setOwner(null));
                }
                bankruptPlayers.add(currentPlayer);
            }

            System.out.println("\n");


        }

    }


    // GAMESTATE METHODS


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

    public int getJailPos(){
        for(int i = 0; i < this.board.length; i++){
            if (this.board[i].getType().equals("ToJail")){
                return i;
            }
        }
        return -1;
    }

    public void printGameState(){
        for(Map.Entry<String, Player> p : this.players.entrySet()){
            Player currentPlayer = p.getValue();
            System.out.println(currentPlayer.getName());
            System.out.println("\t" + currentPlayer.getBalance());
            System.out.println("\t" + currentPlayer.isBankrupt());
            System.out.println(currentPlayer.displayProperties());
        }

        System.out.println(this.bankruptPlayers.getFirst().getName());
    }




}

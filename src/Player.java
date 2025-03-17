import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.Map;

public class Player {
    private String name;
    private int pos;
    private int money;
    private boolean isBankrupt;
    private ArrayList<Property> ownedProperties;


    public Player(String name){
        this.name = name;
        this.pos = 0;
        this.money = 5000;
        this.isBankrupt = false;
        this.ownedProperties = new ArrayList<>();

    }

    public String getName() {
        return this.name;
    }
    public int getBalance() {
        return this.money;
    }
    public int getPos() {
        return this.pos;
    }


    public int movePlayer(Space[] board){
        int dieResult = rollDie();
        int newPlayerPos = this.pos + dieResult;
        System.out.println(this.name + " rolled a " + dieResult + "!");
        if (newPlayerPos >= board.length) {
            // doesn't work if board.length < 6
            this.pos = dieResult - (board.length - this.pos);

        } else if (newPlayerPos < board.length) {
            this.pos = newPlayerPos;

        } else {
            return -1;
        }
        System.out.println("You landed on " + board[this.pos]);

        return this.pos;

    }

    public static int rollDie(){
        Random rand = new Random();
        return rand.nextInt(6)+1;

    }

    public boolean triggerBankruptcy(Monopoly g){
        for (Property p : this.ownedProperties){
            Property updatedProperty = p.setOwner(null);
            g.updateBoardProperty(updatedProperty);
        }
        this.isBankrupt = true;
        return true;
    }



    // spend some money
    public boolean spend(int amount){
        this.money = this.money - amount;
        System.out.println("Spent " + amount + " dollars. You now have " + this.money + " dollars");
        return true;


    }

    public boolean haveMoney(int amount) {
        if (this.money < amount) {
            System.out.println("you don't have enough money");
            return false;
        }
        return true;
    }



    public Property buy(Property prop) {
        if (!this.haveMoney(prop.getPrice())){

            return prop;
        }

        // buying property
        // automatically buying if you have the money - is that really the best strategy?
        if (prop.isAvaliable()){
            this.ownedProperties.add(prop);
            this.spend(prop.getPrice());
            prop.setOwner(this);

        } else {
            System.out.println("This property already has an owner");
        }
        return prop;

    }

    private boolean giveMoney(int amount){
        this.money += amount;
        return true;
    }

    public boolean isBankrupt(){
        return this.isBankrupt;
    }

    public Player payRent(Property p, Player owner){
        if (!this.haveMoney(p.getPrice())){
            // bankruptcy!
            return owner;
        }
        this.spend(p.getRent());
        owner.giveMoney(p.getRent());
        return owner;
    }

}

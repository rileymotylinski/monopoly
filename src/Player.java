import java.util.Random;
import java.util.Map;

public class Player {
    private String name;
    private int pos;
    private int money;
    private boolean isBankrupt;


    public Player(String name){
        this.name = name;
        this.pos = 0;
        this.money = 5000;
        this.isBankrupt = false;

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



    // spend some money
    // remember to handle event were they don't have
    public boolean spend(int amount){
        this.money = this.money - amount;
        System.out.println("Spent " + amount + " dollars. You now have " + this.money + " dollars");
        if(this.money < 0){
            this.isBankrupt = true;
            System.out.println("You are bankrupt!");
        }
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

        if (prop.isAvaliable()){

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

    public Player payRent(Property p, Player owner){
        if (!this.haveMoney(p.getPrice())){
            return owner;
        }
        this.spend(p.getRent());
        owner.giveMoney(p.getRent());
        return owner;
    }

}

public class Property extends Space {
    private int price;
    private int rent;
    private boolean isSold;
    private Player owner;

    public Property(String name, int price, int rent){
        super("property",name);
        this.price = price;
        this.rent = rent;
        this.isSold = false;
        this.owner = null;
    }
    @Override
    public boolean onLand(Player p, Monopoly g){
        if (this.owner == null) {
            System.out.println("would you like to buy this property? (y/n) : ");
            g.updateBoardProperty(p.buy(this));
        } else if (g.isInJail(this.owner)) {
            System.out.println(this.owner.getName() + " is currently in jail. You don't have to pay them rent.");
            return true;
        } else if (!this.isAvaliable() && this.owner != p) {
            System.out.println("You have to pay rent to " + g.getPropertyOwner(this));
            // update balances of both players
            g.updatePlayer(p.payRent(this,this.owner));
        } else {
            System.out.println("You own this property");
        }
        return true;
    }

    public int getPrice() {
        return this.price;
    }
    public Player getOwner(){
        return this.owner;
    }

    public int getRent() { return this.rent; }
    public boolean isAvaliable() {
        return this.owner == null;
    }

    public boolean setOwner(Player p){
        this.owner = p;
        return true;
    }

}

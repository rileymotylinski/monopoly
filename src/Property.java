import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.List;

import java.util.ArrayList;

public class Property extends Space {
    private int price;
    private int rent;
    private boolean isSold;
    private Player owner;

    public Property(String name, int price, int rent){
        super("property", name);
        this.price = price;
        this.rent = rent;
        this.isSold = false;
        this.owner = null;
    }

    public Property() {
        super("property", "placeholder");
        // could probably figure out a way to call the constructor to stay DRY
        this.price = 0;
        this.rent = 0;
        this.isSold = false;
        this.owner = null;
    }

    // helper function for instantiating from the csv files
    public static Property[] instantiateProperties(FileReader f){
        ArrayList<Property> properties = new ArrayList<>();
        try {
            CSVReader csvReader = new CSVReaderBuilder(f)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();

            for(String[] sL : allData){
                Property p = new Property();

                String name = sL[0];
                String price = sL[1];
                String rent = sL[2];
                //CSV file MUST be formatted : Name, cost, rent
                p.setName(sL[0]);
                p.setPrice(Integer.parseInt(sL[1]));
                p.setRent(Integer.parseInt(sL[2]));

                properties.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Property[] p = new Property[properties.size()];
        return properties.toArray(p);

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
            if (p.getBalance() < this.rent){
                // bankruptcy
                // updating gamestate
                p.triggerBankruptcy(g);
                // mortgaging properties?
            } else {
                g.updatePlayer(p.payRent(this,this.owner));
            }

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

    public Property setOwner(Player p){
        this.owner = p;
        return this;
    }


    private boolean setPrice(int newPrice){
        this.price = newPrice;
        return true;
    }

    private boolean setRent(int newRent){
        this.rent = newRent;
        return true;
    }
}

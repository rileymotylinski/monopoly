public abstract class Space {
    private String type;
    private String title;


    public Space(String type, String title) {
        if (type.equals("property")) {
            this.type = "property";

        } else if (type.equals("go to jail")) {
            this.type = "go to jail";
        }
        else if (type.equals("jail")) {
            this.type = "jail";
        }
        this.title = title;
    }

    public Space(){
        this.type = "null";
        this.title = "empty";
    }

    abstract boolean onLand(Player p, Monopoly g);
    public String getName(){
        return this.title;
    }
    public String getType() { return this.type; }
    @Override
    public String toString() {
        return this.title;
    }
    protected boolean setName(String newName){
        this.title = newName;
        return true;
    }
}

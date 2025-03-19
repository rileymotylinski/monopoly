import java.util.ArrayList;

public class Jail extends Space {
    public Jail(){
        super("jail","jail");
    }

   @Override
    public boolean onLand(Player p, Monopoly g) {
        return true;
    }


}

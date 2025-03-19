public class ToJail extends Space {
    public ToJail(){
        super("ToJail", "To Jail");
    }
    @Override
    public boolean onLand(Player p, Monopoly g) {
        p.setPos(g.getJailPos());
        g.jail(p);
        return true;
    }
}

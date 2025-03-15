import java.util.HashMap;
import java.util.Map;

public class Money {
    public static int sum(Map<Integer,Integer> m){
        int total = 0;

        for (Map.Entry<Integer, Integer> money : m.entrySet()){
            total = total + money.getKey() * money.getValue();
        }
        return total;
    }
}

package lightgbm.boosters;

import java.util.Map;

public interface Condition {

    boolean isLeft(Map<String,String> map);

}

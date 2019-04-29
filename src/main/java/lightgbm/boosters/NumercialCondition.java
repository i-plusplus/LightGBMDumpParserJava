package lightgbm.boosters;

import java.util.List;
import java.util.Map;

public class NumercialCondition implements Condition {

    String feature;
    double value;
    public NumercialCondition(String feature, Integer index, List<List<String>> features, double value){
        this.feature = feature;
        this.value = value;
    }
    @Override
    public boolean isLeft(Map<String, String> map) {
        try {
            return Double.parseDouble(map.get(feature)) <= value;
        }catch (Exception e){
            System.out.println(feature + " " + map.toString());
            throw new RuntimeException(e);
        }

    }
}

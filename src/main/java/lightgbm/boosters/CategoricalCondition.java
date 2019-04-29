package lightgbm.boosters;

import java.util.*;

public class CategoricalCondition implements Condition {

    String feature;
    Set<String> values;
    List<Integer> match;

    public List<Integer> binaryOnces(List<Long> values){
        List<Integer> list = new LinkedList<>();
        int k = 0;
        for(long value : values){
            for(int i = 0;i<32;i++){
                if(value%2 == 1){
                    list.add(k);
                }
                value = value>>1;
                k++;
            }
        }

        return list;
    }

    public CategoricalCondition(String feature, Integer index, List<List<String>> features, List<Long> value){
        this.feature = feature;
        values = new HashSet<>();
        match = binaryOnces(value);
        for(int k : binaryOnces(value)){
            values.add(features.get(index).get(k));
        }
    }

    @Override
    public boolean isLeft(Map<String, String> map) {
        return values.contains(map.get(feature));
    }
}

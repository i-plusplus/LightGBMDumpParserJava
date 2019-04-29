package lightgbm.boosters;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Booster {

    List<List<String>> features = new LinkedList<>();
    public Double value = null;
    public boolean isLeaf = false;
    public int index = -100;
    public Booster left = null;
    public Booster right = null;

    public Condition condition;

    public int getLeaf(Map<String,String> input){
        if(this.isLeaf){
            return index;
        }
        if(condition.isLeft(input)){
            return left.getLeaf(input);
        }
        return right.getLeaf(input);
    }

    public double getValue(Map<String,String>  input){

        if(this.isLeaf){
            return value;
        }
        if(condition.isLeft(input)){
            return left.getValue(input);
        }
        return right.getValue(input);

    }





}

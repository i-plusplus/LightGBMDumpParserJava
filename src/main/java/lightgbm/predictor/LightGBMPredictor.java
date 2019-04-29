package lightgbm.predictor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lightgbm.boosters.Booster;
import lightgbm.objective.ObjectiveFunction;

import java.util.List;
import java.util.Map;

public class LightGBMPredictor implements Predictor {

    List<Booster> boosters;
    ObjectiveFunction objectiveFunction;

    public LightGBMPredictor(List<Booster> boosters, ObjectiveFunction objectiveFunction){
        this.boosters = boosters;
        this.objectiveFunction = objectiveFunction;
    }

    @Override
    public Double predict(Map<String, String> input) {
        Double d = 0.0;
        int i = 1;
        for(Booster booster :boosters){
            d += booster.getValue(input);
         //   System.out.println(i + "\t" + d);
            i++;
        }
        return objectiveFunction.apply(d);
    }

    public void print(){
        Booster booster = boosters.get(8);
        print(booster);

    }
    boolean print(Booster b){
        if(b == null){
            return false;
        }
        if(b.value!=null && Math.abs(b.value - 0.00147059857429) < .0000001) {
            return true;
        }

        if(print(b.right)){
            System.out.print("->right");
            return true;
        }else if(print(b.left)){
            System.out.print("->left");
            return true;
        }
        return false;
    }

}

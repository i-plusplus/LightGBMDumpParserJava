package lightgbm.test;

import lightgbm.predictor.Predictor;

import java.util.List;
import java.util.Map;

/**
 * Created by paras.mal on 29/4/19.
 */
public class PredictOnList implements Runnable {

    List<Map<String,String>> maps = null;

    Predictor predictor;

    public PredictOnList(List<Map<String, String>> maps, Predictor predictor){
        this.maps  = maps;
        this.predictor = predictor;
    }

    @Override
    public void run() {
        long t3 = System.currentTimeMillis();
        for (Map<String, String> m : maps) {
            predictor.predict(m);
//            writer.newLine();
        }
        System.out.println((System.currentTimeMillis() - t3));
    }
}

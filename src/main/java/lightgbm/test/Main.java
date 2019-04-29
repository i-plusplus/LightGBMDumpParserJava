package lightgbm.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lightgbm.factory.LightGBM;
import lightgbm.predictor.Predictor;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Main {
    private String inputFilePath = null;
    private String modelFilePath = null;


    public static void main(String args[]) throws Exception {
        long t1 = System.currentTimeMillis();
        Main main = new Main();

       main.inputFilePath = "/home/datashare/input2.json";
       main.modelFilePath = "/home/datashare/modelStr.json";


        main.execute();
        System.out.println((System.currentTimeMillis() - t1));
    }

    public List<Map<String,String>> getJson(String fileName) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));

        return new Gson().fromJson(reader, new TypeToken<List<Map<String,String>>>(){}.getType());
    }

    private void execute() throws Exception {

        Predictor predictor = new LightGBM().load(new File(this.modelFilePath));
        List<Map<String, String>> maps = getJson(this.inputFilePath);
        Thread t = new Thread(new PredictOnList(maps, predictor));
        Thread t2 = new Thread(new PredictOnList(maps, predictor));
        Thread t3 = new Thread(new PredictOnList(maps, predictor));
        Thread t4 = new Thread(new PredictOnList(maps, predictor));
        Thread t5 = new Thread(new PredictOnList(maps, predictor));
        Thread t6 = new Thread(new PredictOnList(maps, predictor));
        Thread t7 = new Thread(new PredictOnList(maps, predictor));
        Thread t8 = new Thread(new PredictOnList(maps, predictor));
        Thread t9 = new Thread(new PredictOnList(maps, predictor));
        t.start();t2.start();t3.start();t4.start();t5.start();t6.start();t7.start();t8.start();t9.start();
        t.join();t2.join();t3.join();t4.join();t5.join();t6.join();t7.join();t8.join();t9.join();

    }

}

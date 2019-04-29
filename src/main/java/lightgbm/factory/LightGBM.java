package lightgbm.factory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lightgbm.boosters.Booster;
import lightgbm.objective.ObjectiveFunction;
import lightgbm.objective.RegressionObjectiveFunction;
import lightgbm.objective.SigmoidObjectiveFunction;
import lightgbm.predictor.LightGBMPredictor;
import lightgbm.predictor.Predictor;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class LightGBM {

    ObjectiveFunction objectiveFunction;
    List<List<Integer>> featureInfo = null;
    List<List<String>> pandasCategories = null;
    List<String> featureName = null;

    public Predictor load(File modelFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(modelFile));
        loadMetaData(reader);
        reader = new BufferedReader(new FileReader(modelFile));
        return new LightGBMPredictor(getBoosters(reader), objectiveFunction);
    }

    public Predictor load(String modelDump) throws IOException {
        Reader stringReader = new StringReader(modelDump);
        BufferedReader reader = new BufferedReader(stringReader);
        loadMetaData(reader);
        stringReader = new StringReader(modelDump);
        reader = new BufferedReader(stringReader);
        return new LightGBMPredictor(getBoosters(reader), objectiveFunction);
    }

    private void loadMetaData(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        while (line != null) {

            String k[] = line.split("=");
            switch (k[0]) {
                case "feature_infos":
                    featureInfo = new LinkedList<>();
                    for (String sub : k[1].split(" ")) {
                        sub = sub.replace("none", "0");
                        if (sub.contains("[")) {
                            continue;
                        }
                        featureInfo.add(Arrays.asList(sub.split(":")).stream().map(i -> Integer.parseInt((String) i)).collect(Collectors.toList()));
                    }
                    break;
                case "feature_names":
                    featureName = Arrays.asList(k[1].split(" "));
                    break;
                case "objective":
                    if (k[1].equals("binary sigmoid:1")) {
                        objectiveFunction = new SigmoidObjectiveFunction();
                    } else if (k[1].equals("regression")) {
                        objectiveFunction = new RegressionObjectiveFunction();
                    }

            }
            if (line.startsWith("pandas_categorical")) {
                line = line.split(":", 2)[1];
                pandasCategories = new Gson().fromJson(line, new TypeToken<List<List<String>>>() {
                }.getType());
            }
            line = reader.readLine();
        }
    }

    private List<Booster> getBoosters(BufferedReader reader) throws IOException {
        List<Booster> boosters = new LinkedList<>();
        String line = reader.readLine();
        while (!line.startsWith("Tree=")) {
            line = reader.readLine();
        }

        while (line != null) {
            List<String> lines = new LinkedList<>();
            line = reader.readLine();
            while (line != null && !line.startsWith("Tree=")) {
                lines.add(line);
                line = reader.readLine();
            }
            if (lines.size() > 0) {
                boosters.add(new BuildTree().set(lines, featureName, featureInfo, pandasCategories));
            }
        }
        return boosters;
    }


}

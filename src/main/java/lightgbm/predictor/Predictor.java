package lightgbm.predictor;

import com.google.gson.JsonObject;

import java.util.Map;

public interface Predictor {

    Double predict(Map<String,String> input);
}

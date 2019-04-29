package lightgbm.factory;

import javafx.util.Pair;
import lightgbm.boosters.Booster;
import lightgbm.boosters.CategoricalCondition;
import lightgbm.boosters.Condition;
import lightgbm.boosters.NumercialCondition;

import java.util.*;
import java.util.stream.Collectors;

public class BuildTree {

    int numLeaves;
    int numCat;
    List<String> featureName;
    List<Integer> splitFeature;
    List<Integer> decisionType;
    List<Pair<Integer,Integer>> childList;
    List<Integer> leftChild;
    List<Integer> rightchild;
    List<Double> leaf_values;
    List<Integer> catBoundery;
    List<Long> catThreshold;
    List<List<Integer>> featureInfo;
    List<List<String>> pandasCategories;
    List<Double> threshold;


    public Booster build(){

        Map<Integer,Booster> boosters = new HashMap<>();
        for(int i = 0 ; i<decisionType.size();i++){


            Condition condition = null;

            if(decisionType.get(i) == 5 || decisionType.get(i) == 1){
                List<Long> values = new LinkedList<>();
                int k = threshold.get(i).intValue();
                for(int d = catBoundery.get(k);d<catBoundery.get(k+1);d++){
                    values.add(catThreshold.get(d));
                }
                condition = new CategoricalCondition(featureName.get(splitFeature.get(i)), splitFeature.get(i),pandasCategories,values);
            }
            if(decisionType.get(i) == 2){
                condition = new NumercialCondition(featureName.get(splitFeature.get(i)), splitFeature.get(i), pandasCategories, threshold.get(i));
            }


            boosters.put(i,new Booster());
            boosters.get(i).condition = condition;

        }
        for(int i = 0 ; i<decisionType.size();i++){
            Booster left = boosters.get(childList.get(i).getKey());
            if(childList.get(i).getKey() < 0){
                left = new Booster();
                left.value = leaf_values.get(childList.get(i).getKey()*-1 -1);
                left.isLeaf = true;
                left.index = childList.get(i).getKey();
            }
            Booster right = boosters.get(childList.get(i).getValue());
            if(childList.get(i).getValue() < 0){
                right = new Booster();
                right.value = leaf_values.get(childList.get(i).getValue()*-1 -1);
                right.isLeaf = true;
                right.index = childList.get(i).getValue();
            }
            boosters.get(i).left = left;
            boosters.get(i).right = right;
        }
        return boosters.get(0);
    }


    public Booster set(List<String> lines, List<String> featureName, List<List<Integer>> featureInfo, List<List<String>> pandasCategories) {
        this.featureInfo = featureInfo;
        this.featureName = featureName;
        this.pandasCategories = pandasCategories;

        for(String line : lines) {
            String k[] = line.split("=");
            switch (k[0]) {
                case "num_leaves":
                    numLeaves = Integer.parseInt(k[1]);
                    break;
                case "num_cat":
                    numCat = Integer.parseInt(k[1]);
                    break;
                case "split_feature":
                    splitFeature = Arrays.asList(k[1].split(" ")).stream().map(i -> Integer.parseInt((String) i)).collect(Collectors.toList());
                    break;
                case "decision_type":
                    decisionType = Arrays.asList(k[1].split(" ")).stream().map(i -> Integer.parseInt((String) i)).collect(Collectors.toList());
                    break;
                case "left_child":
                    leftChild = Arrays.asList(k[1].split(" ")).stream().map(i -> Integer.parseInt((String) i)).collect(Collectors.toList());
                    break;
                case "right_child":
                    rightchild = Arrays.asList(k[1].split(" ")).stream().map(i -> Integer.parseInt((String) i)).collect(Collectors.toList());
                    break;
                case "leaf_value":
                    leaf_values = Arrays.asList(k[1].split(" ")).stream().map(i -> Double.parseDouble((String) i)).collect(Collectors.toList());
                    break;
                case "threshold":
                    threshold = Arrays.asList(k[1].split(" ")).stream().map(i -> Double.parseDouble((String) i)).collect(Collectors.toList());
                    break;
                case "cat_boundaries":
                    catBoundery = Arrays.asList(k[1].split(" ")).stream().map(i -> Integer.parseInt((String) i)).collect(Collectors.toList());
                    break;
                case "cat_threshold":
                    catThreshold = Arrays.asList(k[1].split(" ")).stream().map(i -> Long.parseLong((String) i)).collect(Collectors.toList());
                    break;
            }

        }
        childList = new LinkedList<>();
        for(int i = 0;i<leftChild.size();i++){
            childList.add(new Pair<>(leftChild.get(i),rightchild.get(i)));
        }
        return build();
    }




}

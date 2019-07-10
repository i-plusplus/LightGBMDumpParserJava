# LightGBM4j


Java library and command-line application is for doing predictions for LightGBM model.

# Prerequisites
LightGBM 2.2.0 or newer.
Java 1.8 or newer.

# How to use ?

This library can used to do predictions for models trained by LightGBM library in python or any other language
# step 1:
 Train the model with LightGBM and store the model in a text file. 
 Sample code :
  from sklearn.datasets import load_boston
  from lightgbm import LGBMRegressor
  boston = load_boston()
  lgbm = LGBMRegressor()
  lgbm.fit(boston.data, boston.target, feature_name = boston.feature_names)
  lgbm.booster_.save_model("lightgbm.txt")







import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.tuning.{ParamGridBuilder, TrainValidationSplit}

val data = spark.read.format("libsvm").load("sample_linear_regression_data.txt")

// Creating subsets for training and test
val Array(training, test) = data.randomSplit(Array(0.9, 0.1), seed=12345)

data.printSchema

// Create the Data Mining Structure
val lr = new LinearRegression()


// Creating the parameter grid
/*
*
* Here we create a parameter grid using the parameter grid builder to
* construct a grid of a combination of all the different parameter values
* that we want to search over.
* We do this by adding a grid of parameters for the linear regression
* object.
* After that, when we train the validation split, it is going to try all the
* possible combinations of values and determine the best model using the
* evaluator that we imported.
*
* */
val paramGrid = (new ParamGridBuilder()
    .addGrid(lr.regParam, Array(0.1, 0.01))
    .addGrid(lr.fitIntercept)
    .addGrid(lr.elasticNetParam, Array(0.0, 0.5, 1.0))
    .build()
  )

val trainValidationSplit = (new TrainValidationSplit()
    .setEstimator(lr) // set the actual model
    .setEvaluator(new RegressionEvaluator()) // the evaluator must line up with the type of model we are using (Regression, Classification, etc)
    .setEstimatorParamMaps(paramGrid) // our grid of parameters
    .setTrainRatio(0.8) // what we are going to use for training and testing versus what we are going to use for the hold out set
  )

/*
*
* By doing this, our estimator (the data mining structure "lr") will be
* trained several times with all possible combinations of parameters we
* defined in our "paramGrid" object and the "RegressionEvaluator" object
* will choose the best set of parameters.
*
* */


// Training the model
val model = trainValidationSplit.fit(training)

/*
*
* This is similar to what we have already done so far, except that
* here the model was trained with a parameter grid and the result is
* the actual best fit for all possible combination of the selected
* values for the parameters.
*
* */
model.transform(test).select("features","label","prediction").show()


model.bestModel
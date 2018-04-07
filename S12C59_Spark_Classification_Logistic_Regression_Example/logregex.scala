// Import libraries
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession

// Set log level to show only Errors
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

// Create a spark session
val spark = SparkSession.builder().getOrCreate()

// Create a data frame from a single file
val data_frame = (spark.read
    .option("header","true")
    .option("inferSchema","true")
    .format("csv")
    .load("titanic.csv")
  )

// Print out the schema (SisSp stands for "Siblings and Spouses" and Parch stands for "Parents and Children")
data_frame.printSchema()

// Print one line using field delimitator " and field separator ,
val first_row = data_frame.head(1)(0)
data_frame.columns.foreach(x => print("$\""+x + "\", "))
println()
for(i <- Range(0,first_row.length)){
  print("\""+first_row(i) + "\",\t")
}
println()


//# Preparing a data frame appropriate for machine learning
/*
* In this section we are going to select useful features that
* might help in our task that is to predict if a person
* would survive the Titanic crash.
*
* */

// First: selection of useful features
val logregdataall = (data_frame
    .select(data_frame("Survived").as("label")
    , $"Pclass", $"Name", $"Sex", $"Age", $"SibSp", $"Parch"
    //  , $"Ticket"
    , $"Fare"
    //, $"Cabin"
    , $"Embarked"
    )
  )


// Second: Dropping Mising Values
val logregdata = logregdataall.na.drop()

logregdata.printSchema()

// # Dealing with Categorical Data
/*
* Now that we selected useful features and dropped missing values
* we still need to perform some transformations in our data set
* before we train the data with machine learning algorithms.
*
* Machine learning algorithms work only with numerical data.
* So, in order to use a Logistic Regression algorithm as a classifier,
* we need to transform all categorical data into some sort of numerical
* data.
*
* For this task, we are going to use Spark ML libraries designed for that.
* The libraries are:
* Vectors, VectorAssembler, StringIndexers and OneHotEncoders.
* Those libraries will help us to deal with Categorical Data.
*
* */

// Import transformation libraries
import org.apache.spark.ml.feature.{VectorAssembler,StringIndexer,VectorIndexer,OneHotEncoder}
import org.apache.spark.ml.linalg.Vectors


// Transforming Categorical Data into numerical data
// Step 1: Using StringIndexer to convert Strings into numerical values
val genderIndexer = new StringIndexer().setInputCol("Sex").setOutputCol("SexIdx")
val embarkIndexer = new StringIndexer().setInputCol("Embarked").setOutputCol("EmbarkedIdx")

// Step 2:
// REQUIRED FOR LOGISTIC REGRESSION: Convert Numerical values into "One Hot Encoding" (binary representation for that numbers)
val genderEncoder = new OneHotEncoder().setInputCol("SexIdx").setOutputCol("SexVector")
val embarkEncoder = new OneHotEncoder().setInputCol("EmbarkedIdx").setOutputCol("EmbarkedVector")

// # Spark Machine Learning Data Layout ("label","features (an array of values)")
/*
*
* 1) Select the "label" column (the feature that we are going to predict). Also known as output feature.
* 2) Select the numerical columns. Also known as input features.
*
* */
println("Column names:")
logregdata.columns.foreach(x => print("\""+x+ "\", "))
println()


/*
* Assembling the vectors for machine learning (it converts the input values to a single vector)
* (this step is mandatory in order to use MLlib to train the models)
* "For this, we will set the input columns from which we are supposed to read the values, and then we set the name
* of the column or the vector will be stored"
*/

// The VectorAssembler object needs two parameters: a set of input columns and a name for the output feature that
// will contain all the arrays of features


val assembler = (new VectorAssembler()
    .setInputCols(Array(
    "Pclass", "SexVector", "Age", "SibSp"
      , "Parch", "Fare", "EmbarkedVector"
    ))
    .setOutputCol("features")
  )


/*
*
* Up until now, we have not done anything yet.
* Instead, we are just creating all data structures that we need.
* Next, we are going to call them in order to actually
* perform all the transformations that we have to do.
*
* */

// # Prepare test and train subset of data
/*
*
* We are going to split the filtered data (remember that we removed NAs)
* into "training" and "test" subsets. And we are going to perform
* this based on a fixed seed value for reproducibility purposes.
* For our "training" subset we are going to use 70%.
* For our "test" subset, 30%.
* For this task, we are going to use the "randomSplit" method of the
* data frame created called "logregdata".
*
* */
val Array(training, test) = logregdata.randomSplit(Array(0.7,0.3), seed=12345)

// # Setting up the pipeline data
import org.apache.spark.ml.Pipeline

val lr = new LogisticRegression()

/*
*
* Now we can setup a pipeline of stages for all of our data structures
* and then we can train our pipeline as if it was a normal Machine Learning
* model.
*
* */
val pipeline = (
    new Pipeline().setStages(
      Array(genderIndexer, embarkIndexer
          , genderEncoder, embarkEncoder
          , assembler, lr)
    )
  )

// This is where we actually train our model
val model = pipeline.fit(training)

val results = model.transform(test)

results.printSchema()

// # Evaluating Stage: How well our model performed on the test data
// # Using RDD (Resilient Distributed Datasets) to evaluate the training results:
import org.apache.spark.mllib.evaluation.MulticlassMetrics

// Converting data frames into RDDs
val predictionAndLabels = results.select($"Prediction",$"label").as[(Double, Double)].rdd

val metrics = new MulticlassMetrics(predictionAndLabels)

println("Confusion Matrix:")
println(metrics.confusionMatrix)





// Load essential libraries
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.regression.LinearRegression

// Restrict logging to show only errors
import org.apache.log4j._
Logger.getLogger("org").setLevel( Level.ERROR) // to avoid WARNINGS

// Start an essential spark session
val spark = SparkSession.builder().getOrCreate()

// Load data
// Set options in order to infer the data types that we expect
val data = (
    spark.read
      .option("header","true")
      .option("inferSchema","true")
      .format("csv")
      .load("Clean-USA-Housing.csv")
  )

// Print schema for an overview
data.printSchema

// Pretty row print for a more human readable format
val colnames = data.columns
val firstrow = data.head(1)(0)
println("\n")
println("Example Data Row:")
for(ind <- Range(1, colnames.length)){
  println(colnames(ind) + ": " + firstrow(ind))
}

// machine learning algorithms data file layout: ("label","features (an array of values)")
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

// 1) Select the "label" column (the feature that we are going to predict). Also known as output feature.
// 2) Select the numerical columns. Also known as input features.

data.columns

val df = (data
    .select(data("Price").as("label") // this is a convention name
      , $"Avg Area Income"
      , $"Avg Area House Age"
      , $"Avg Area Number of Rooms"
      , $"Avg Area Number of Bedrooms"
      , $"Area Population"
    )
  )

// Assembling the vectors for machine learning (it converts the input values to a single vector)
// [this step is mandatory in order to use MLlib to train the models]
// "For this, we will set the input columns from which we are supposed to read the values, and then we set the name
// of the column or the vector will be stored"

// The VectorAssembler object needs two parameters: a set of input columns and a name for the output feature that
// will contain all the arrays of features
val assembler = (new VectorAssembler()
    .setInputCols(
      Array(
          "Avg Area Income"
        , "Avg Area House Age"
        , "Avg Area Number of Rooms"
        , "Avg Area Number of Bedrooms"
        , "Area Population"
      )
    )
    .setOutputCol(
      "features"
    )
  )

// Select only the columns that we need
val output = assembler.transform(df).select($"label",$"features")

// This is the format that we need in order to work with the MLlib in Spark
output.show()

// Prepare test and train data




////////////////////////////////////////////
//// LINEAR REGRESSION EXERCISE ///////////
/// Complete the commented tasks below ///
/////////////////////////////////////////

// Import LinearRegression
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.regression.LinearRegression

// Optional: Use the following code below to set the Error reporting
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)


// Start a simple Spark Session
val spark = SparkSession.builder().getOrCreate()

// Use Spark to read in the Ecommerce Customers csv file.
val data = (spark.read
    .option("header","true")
    .option("inferSchema","true")
    .format("csv")
    .load("Clean-Ecommerce.csv")
  )

// Print the Schema of the DataFrame
data.printSchema

// Print out an example Row
// Various ways to do this, just
// choose whichever way you prefer
val colnames = data.columns
val firstrow = data.head(1)(0)
println("\n")
println("Example Data Row:")
for(ind <- Range(1, colnames.length)){
  println(colnames(ind) + ": " + firstrow(ind))
}

////////////////////////////////////////////////////
//// Setting Up DataFrame for Machine Learning ////
//////////////////////////////////////////////////

// A few things we need to do before Spark can accept the data!
// It needs to be in the form of two columns
// ("label","features")

// Import VectorAssembler and Vectors

// Rename the Yearly Amount Spent Column as "label"
// Also grab only the numerical columns from the data
// Set all of this as a new dataframe called df

// An assembler converts the input values to a vector
// A vector is what the ML algorithm reads to train a model

// Use VectorAssembler to convert the input columns of df
// to a single output column of an array called "features"
// Set the input columns from which we are supposed to read the values.
// Call this new object assembler

// Use the assembler to transform our DataFrame to the two columns: label and features


// Create a Linear Regression Model object


// Fit the model to the data and call this model lrModel


// Print the coefficients and intercept for linear regression

// Summarize the model over the training set and print out some metrics!
// Use the .summary method off your model to create an object
// called trainingSummary

// Show the residuals, the RMSE, the MSE, and the R^2 Values.


// Great Job!

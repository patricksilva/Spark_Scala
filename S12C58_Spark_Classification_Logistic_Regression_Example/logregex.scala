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


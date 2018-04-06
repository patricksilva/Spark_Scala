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

// Print out the schema (SisSp: "Siblings and Spouses"; Parch: "Parents and Children")
data_frame.printSchema()

// Print one line using field delimitator " and field separator ,
val first_row = data_frame.head(1)(0)
data_frame.columns.foreach(x => print("$\""+x + "\", "))
println()
for(i <- Range(0,first_row.length)){
  print("\""+first_row(i) + "\",\t")
}


// Preparing a data frame appropriate for machine learning
val logregdataall = (data_frame
    .select(data_frame("Survived").as("label")
    , $"Pclass", $"Name", $"Sex", $"Age", $"SibSp", $"Parch"
    //  , $"Ticket"
    , $"Fare"
    //, $"Cabin"
    , $"Embarked"
    )
  )


// Dropping Mising Values
val logregdata = logregdataall.na.drop()



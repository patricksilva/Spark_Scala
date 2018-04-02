// DATAFRAME PROJECT
// Use the Netflix_2011_2016.csv file to Answer and complete
// the commented tasks below!

// Start a simple Spark Session
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()

// Load the Netflix Stock CSV File, have Spark infer the data types.
val df = spark.read.option("header","true").option("inferSchema","true").csv("./Netflix_2011_2016.csv")

// What are the column names?
df.columns.foreach(x => println(x))

// What does the Schema look like?
df.printSchema()

// Print out the first 5 columns.
// Method 1: using foreach method
df.head(5).foreach(x => println(x))
// Method 2: using for loop structure
for(row <- df.head(5)){
  println(row)
}

// Use describe() to learn about the DataFrame.
df.describe().show()

// How to select columns?
// df.select($"column_name"[, $"column_name"])

// Create a new dataframe with a column called HV Ratio that
// is the ratio of the High Price versus volume of stock traded
// for a day.
// To create new columns we use the method "withColumn("new_column_name", )"
val df2 = df.withColumn("HV Ratio", df("High") / df("Volume"))

// To rename a column_name:
//df2.select(df2("HV Ratio").as("HVR"), df2("Volume")).show()

// What day had the Peak High in Price?
println("What day had the Peak High in Price?")
df2.orderBy($"High".desc).show(1)
// What is the mean of the Close column?
df2.select(mean("Close")).show()
// What is the max and min of the Volume column?
df2.select(max("Volume").as("Max Volume"), min("Volume").as("Min Volume")).show()

// Filtering data (this import is needed to use the $-notation)
import spark.implicits._


// For Scala/Spark $ Syntax


// How many days was the Close lower than $ 600?
println("Number of days where \"Close\" was lower than $ 600: " + df2.filter("Close < 600").count())
println("Number of days where \"Close\" was lower than $ 600: " + df2.filter($"Close" < 600).count())

// What percentage of the time was the High greater than $500 ?
( df2.filter("High > 500").count().toFloat / df2.count() ) * 100
( df2.filter($"High" > 500).count().toFloat / df2.count() ) * 100

// What is the Pearson correlation between High and Volume?
df2.select(corr("High","Volume")).show()

// What is the max High per year?
// Method 1:
val df3 = df2.withColumn("Year", year(df2("date")))
val df3max = df3.groupBy("Year").max()
df3max.select($"Year", $"max(High)").show()
// Method 2 (sorting the result set):
val yearMaxs = df3.select($"Year",$"High").groupBy("Year").max()
val result = yearMaxs.select($"Year", $"max(High)")
result.orderBy($"Year".desc).show()

// What is the average Close for each Calender Month?
val df4 = df3.withColumn("Month", month(df3("Date")))
//val df5 = df4.withColumn("Year-Month", df4.select(concat($"Year", lit("-"), $"Month").as("Year-Month")))
val monthavgs = df4.select($"Month", $"Close").groupBy("Month").mean()
monthavgs.select($"Month", $"avg(Close)").orderBy($"Month").show()

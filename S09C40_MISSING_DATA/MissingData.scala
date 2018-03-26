import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header","true").option("inferSchema","true").csv("../S09C37_Data_Frames/Spark DataFrames/ContainsNull.csv")

df.printSchema()

df.show()

// 1. Ignore nulls up to a certain percentage
// 2. Drop all nulls
df.na.drop().show()
// 3. Fill in null with some inputation technique
df.na.fill(100).show() // this will look for all the columns that match that data type and fill it in.
df.na.fill("Missing Name").show() // this will look for all the columns that match that data type and fill it in.
df.na.fill("New Name", Array("Name")).show() // this will look for all the columns specified in the Array() and fill it in.
df.na.fill(200, Array("Sales")).show() // this will look for all the columns specified in the Array() and fill it in.
// There is no correct answer here.
// You just have to adapt the way you are thinking to the data you have.

// Drop any rows that have less than a minimum Number
// of NON-NULL values ( < Int )
df.na.drop(2).show()


// How to fill in the "Sales" with the average value?
df.describe().show()
df.na.fill(400.5, Array("Sales")).show()
df.na.fill("missing name", Array("Name")).show()

val df2 = df.na.fill(400.5, Array("Sales"))
df2.na.fill("missing name", Array("Name")).show()

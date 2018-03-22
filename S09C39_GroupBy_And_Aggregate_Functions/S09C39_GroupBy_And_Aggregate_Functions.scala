// DataFrame Operations
// GroupBy And Aggregate Functions

import org.apache.spark.sql.SparkSession

// Start a simple Spark Session
val spark = SparkSession.builder().getOrCreate()

// Create a DataFrame from Spark Session read csv
val df = spark.read.option("header","true").option("inferSchema","true").csv("../S09C37_Data_Frames/Spark DataFrames/Sales.csv")

df.printSchema()

df.show()

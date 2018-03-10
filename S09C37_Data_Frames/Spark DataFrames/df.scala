/*
A "SparkSession" is what you are going to use when you are working if Sparkle

*/
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header","true").option("inferSchema","true").csv("CitiGroup2006_2008")
//val df = spark.read.csv("CitiGroup2006_2008")

//df.head(5)

for(row <- df.head(5)){
  println(row)
}

df.columns

df.describe().show()

df.select($"Volume",$"Date",$"Close").show()

println("Creating new columns")
val df2 = df.withColumn("HighPlusLow", df("High") + df("Low"))
val df3 = df.withColumn("HighPlusLow", df($"High",$"Low"))

println("Printing out the schema")
df2.printSchema()

println("Renaming a column")
df2.select(df2("HighPlusLow").as("HPL"), df2("Close")).show()

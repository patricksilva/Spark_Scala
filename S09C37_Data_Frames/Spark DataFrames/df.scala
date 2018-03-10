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

//df.describe().show()

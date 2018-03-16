// DataFrame Operations

import org.apache.spark.sql.SparkSession

// Start a simple Spark Session
val spark = SparkSession.builder().getOrCreate()

// Create a DataFrame from Spark Session read csv
val df = spark.read.option("header","true").option("inferSchema","true").csv("CitiGroup2006_2008")

//df.printSchema()




// There is two main ways of filtering out data:

// Way 1: using Spark SQL Syntax
//df.filter("Close > 480").show()

// Way 2: using Scala Syntax
import spark.implicits._
//df.filter($"Close" < 480 && $"High" < 480).show()

// Scala is based on Transformations and Actions
// Transformations are lazily evaluated. So then can be stacked until an action
// is performed on them.
// In this example, "filter" is the transformation; and "show" is the action.
df.filter($"High"===484.40).show()
df.filter("High = 484.40").show()

// Another operations and functions that we can perform on DataFrames with
// Scala on a DataFrame
// Ex 1: [PEARSON] CORRELATION
df.select(corr("High","Low")).show() // notice that here no $ nor 

//df.filter("Close < 480 AND High < 480").show()

// Another action is "collect"
val CH_low = df.filter("Close < 480 AND High < 480").collect()

val CH_low = df.filter("Close < 480 AND High < 480").count()

/*
println("This is how we get the result set into a df")
println("scala> val identifier_name = df.filter(...l).collect()")
val CH_low = df.filter("Close < 480 AND High < 480").collect()
*/

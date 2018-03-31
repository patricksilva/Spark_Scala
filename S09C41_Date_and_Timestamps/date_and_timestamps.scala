import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header","true").option("inferSchema","true").csv("./S09C37_Data_Frames/Spark DataFrames/CitiGroup2006_2008")

df.printSchema()
df.show()

println("Getting one timestamp column")
df.select(year(df("Date"))).show()

val df2 = df.withColumn("Year", year(df("Date")))

val dfavgs = df2.groupBy("Year").mean()
dfavgs.select($"Year", $"avg(Close)").show()

val dfstddev = df2.groupBy("Year").agg(stddev($"Close"))
dfstddev.select($"Year", $"stddev_samp(Close)").show()

val dfmax = df2.groupBy("Year").max()
dfmax.select($"Year", $"max(Close)").show()

val dfmin = df2.groupBy("Year").min()
dfmin.select($"Year", $"min(Close)").show()

df2.describe().show()

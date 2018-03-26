import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header","true").option("inferSchema","true").csv("../S09C37_Data_Frames/Spark DataFrames/CitiGroup2006_2008")

df.printSchema()
df.show()

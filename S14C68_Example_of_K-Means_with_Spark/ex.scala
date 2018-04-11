// Set Error reporting
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

// Import Spark Session
import org.apache.spark.sql.SparkSession

// Start Spark Session
val spark = SparkSession.builder().getOrCreate()

// Import Clustering KMeans Algorithm Package
import org.apache.spark.ml.clustering.KMeans

// Load some data (for unsupervised learning we do not have the "label" column)
val dataset = spark.read.format("libsvm").load("sample_kmeans_data.txt")

// Create the Data Mining Structure
val kmeans = (new KMeans()
    .setK(2) // the number of K clusters/centroids (use all domain knowledge/experience available to help to choose this parameter)
    .setSeed(1L) // for reproducibility and comparison purposes
  )

// Train the model
val model = kmeans.fit(dataset)

// Evaluation: The objective of the KMeans is to minimize the Within Set Sum of Squared Errors
val WSSSE = model.computeCost(dataset)
println(s"Within Set Sum of Squared Errors: $WSSSE")

// Print Results
println("Cluster Centers:")
model.clusterCenters.foreach(println)

// References
/*

http://spark.apache.org/docs/latest/ml-clustering.html
http://spark.apache.org/docs/latest/ml-clustering.html#input-columns
http://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.ml.clustering.KMeans

*/
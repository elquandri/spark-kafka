
import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.{DataFrame, Dataset}

object sparkConsumer extends App with configuration {

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val ds1: DataFrame = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", kafkaCon)
      .option("subscribe", topic)
      .load()

    val data: Dataset[(String, String)] = ds1.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
      .as[(String, String)]
    ds1.printSchema()

    val query: StreamingQuery = data.writeStream
      .outputMode("append")
      .format("console")
        .start()
    query.awaitTermination()



}


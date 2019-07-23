import java.io.BufferedOutputStream
import java.util.Properties
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.kafka.clients.consumer.KafkaConsumer
import resultcheck.generate

import scala.collection.JavaConverters._


object kafkaConsumer extends App  with configuration {

              initifile()
  val props = createProp()
              checkresult(props)




  def createProp(): Properties  ={

    val props:Properties = new Properties()
    props.put("group.id", "test-consumer-group")
    props.put(server,kafkaCon)
    props.put("key.deserializer",
      "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer",
      "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("auto.offset.reset", "earliest")

    return props

  }


  def checkresult(props : Properties): Unit =
  {
    var c=""

    val consumer = new KafkaConsumer(props)
    try {
      consumer.subscribe(topics.asJava)
      var count = 0
      while (count < 151) {
        val records = consumer.poll(100)
        for (record <- records.asScala) {
          println("Topic: " + record.topic() +
            ",Key: " + record.key() +
            ",Value: " + record.value() +
            ", Offset: " + record.offset() +
            ", Partition: " + record.partition())
          count +=1
        }
      }
      c = generate("passed").toString

    }catch{
      case e:Exception => e.printStackTrace()
        c = generate("failed").toString

    }finally {
      consumer.close()
    }

    saveResultFile(c)

  }

  def initifile(): Unit =
  {
    val c =generate("failed").toString
    saveResultFile(c)
  }

  def saveResultFile(content: String) = {
    val conf = new Configuration()
    conf.set("fs.defaultFS", pathHdfs)

    val date = java.time.LocalDate.now.toString
    val filePath = pathResult + resultPrefix + "_" + date + ".json"

    val fs = FileSystem.get(conf)
    val output = fs.create(new Path(filePath), true)

    val writer = new BufferedOutputStream(output)

    try {
      writer.write(content.getBytes("UTF-8"))
    }
    finally {
      writer.close()
    }
  }




}

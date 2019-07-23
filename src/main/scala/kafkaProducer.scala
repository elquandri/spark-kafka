import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}


object kafkaProducer extends App with  configuration {

    val props = createProp()
                createProducer(props)



  def createProp(): Properties ={

    val props:Properties = new Properties()
    props.put(server,kafkaProd)
    props.put("key.serializer",
      "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer",
      "org.apache.kafka.common.serialization.StringSerializer")
    props.put("acks","all")
    return props

  }

  def createProducer(props : Properties): Unit =
  {
    val producer = new KafkaProducer[String, String](props)

    try {
      for (i <- 0 to 150) {
        val record = new ProducerRecord[String, String](topic, i.toString, " Message envoyer avec succee " + i)
        val metadata = producer.send(record)
        printf(s"sent record(key=%s value=%s) " +
          "meta(partition=%d, offset=%d)\n",
          record.key(), record.value(),
          metadata.get().partition(),
          metadata.get().offset())
      }
    }catch{
      case e:Exception => e.printStackTrace()
    }finally {
      producer.close()
    }

  }
}

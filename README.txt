#pour exécuter le kafkaConsumer

spark-submit  --class "kafkaConsumer"  "chemin du Jar"

#pour exécuter le sparkConsumer 

spark-submit  --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.2.0 --class "sparkConsumer"  "chemin du Jar"

#pour exécuter le kafkaProducer

spark-submit  --class "kafkaProducer" "chemin"


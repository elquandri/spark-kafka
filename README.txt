#	Application Interaction Spark/Kafka

Cette application a pour but tester les interactions entre Apache Spark et Apache Kafka, elle permet :
-	La production des diffusions de données Kafka. 
-	La consommation de ces diffusions de données.
#	Composants concernés


Composant	    	Version
- Spark	      	2.3.2
- Kafka		      1.1.1
- DC-OS		      1.12
- Scala		      2.11.8
- Assembly	  	0.14.9
- Gitlab		    11.3.0



# Prérequis 

Avant de lancer l’application vous devez la configurer, cela se fait au niveau du fichier de configuration de l’application, qui est dans le chemin (/src/main/resources/kafka.conf).

# Traitements 
Lancer les deux classes de l’application sur le bootstrap en utilisant les commandes suivantes : 
-	Le consommateur :   dcos spark --name="spark-2-3" run --submit-args="--conf spark.mesos.containerizer=docker --conf spark.driver.memory=4G --conf spark.cores.max=3 --conf spark.executor.cores=1 --conf spark.executor.memory=4G --conf spark.mesos.executor.docker.forcePullImage=false --conf spark.eventLog.enabled=true --conf spark.eventLog.dir=hdfs:///spark_history  --class kafkaConsumer hdfs:///jars/Spark-Kafka -assembly-0.1.jar"
-	Le producteur : dcos spark --name="spark-2-3" run --submit-args="--conf spark.mesos.containerizer=docker --conf spark.driver.memory=4G --conf spark.cores.max=3 --conf spark.executor.cores=1 --conf spark.executor.memory=4G --conf spark.mesos.executor.docker.forcePullImage=false --conf spark.eventLog.enabled=true --conf spark.eventLog.dir=hdfs:///spark_history  --class kafkaProducer hdfs:///jars/Spark-Kafka -assembly-0.1.jar"
	
# Validation du test 
Vérifier le statut du test dans le fichier résultat. 

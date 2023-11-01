# Getting Started
./gradlew build -s
./gradlew build --exclude-task test
java -jar .\build\libs\demo-0.0.1-SNAPSHOT.jar

# Kafka Command for test
## List Topic
$KAFKA_HOME/bin/kafka-topics.sh --list --bootstrap-server 192.168.122.17:19092


## Delete Topic
$KAFKA_HOME/bin/kafka-topics.sh --delete --bootstrap-server 192.168.122.17:19092 --topic delivery
$KAFKA_HOME/bin/kafka-topics.sh --delete --bootstrap-server 192.168.122.17:19092 --topic pay

## Produce
$KAFKA_HOME/bin/kafka-console-producer.sh --broker-list 192.168.122.17:19092 --topic pay
{"eventType":"Paid", "orderId":1}
{"eventType":"Paid", "orderId":2}
{"eventType":"PaymentCancelled", "orderId":1}

$KAFKA_HOME/bin/kafka-console-producer.sh --broker-list 192.168.122.17:19092 --topic delivery
{"eventType":"DeliveryOrdered", "orderId":10}
{"eventType":"DeliveryCancelled", "orderId":10}





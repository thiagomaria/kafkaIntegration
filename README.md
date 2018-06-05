# kafkaIntegration
Springboot kafka integration

**Steps for running the application:**
You need to run a kafka broker first, and then
run the consumers assigning then in one or more topics.
For example:
```
mvn spring-boot:run -Dspring.kafka.consumer.group-id="group-one" -Drun.arguments="ERROR,WARNING"

mvn spring-boot:run -Dspring.kafka.consumer.group-id="group-two" -Drun.arguments="ERROR,WARNING,INFORMATION"
```
in this case, we are running two consumers, one assigned to "ERROR,WARNING" topics and another to
"ERROR,WARNING,INFORMATION" topics.

after that, we just need to run the producer:
```
mvn spring-boot:run
```

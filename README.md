# Transactional Order API with Rabbit MQ Event Notification 

This code tries to show a real life enterprise example. It receives and order from an external system, saves the order it in a database, then using outbox pattern
saves the event to be sent in the same database as a transaction. Another thread will then send out these events and make sure we have retries in case Rabbit MQ is down.

Goto

http://localhost:8080/h2-console

Enter this In 
jdbc:h2:mem:jonasingvar



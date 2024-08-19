# Transactional Order API using Outbox Pattern 

This code tries to show a real life enterprise example. It receives and order from an external system, saves the order it in a database, then using outbox pattern
saves the event to be sent in the same database as a transaction. Another thread will then send out these events (not implemented yet) with retries.

## Design Decisions

### Directory Layout to Support DDD

### Anti Corruption Layers

### Request Validation

### Outbox Pattern



### Run Unit and Integration Test

This runs all unit test + 
an end 2 end integration test that uses the API to submit an order
and retrieves the order through the API 

```bash
 ./gradlew clean test
```

### Run App

```bash
 ./gradlew bootRun
```


### Run manual script

```bash
 ./send_request.sh
```

#### Response
```json
{
  "id": "b2eb3512-f440-4ab9-8f13-f9a737c2712d",
  "products": [
    {
      "productKey": "BACKPACK-FR-14",
      "quantity": 2,
      "price": 149.99,
      "discount": 5,
      "name": "Fjallraven Backpack"
    },
    {
      "productKey": "HAT-FR-3",
      "quantity": 3,
      "price": 34.99,
      "discount": 0,
      "name": "Fjallraven Backpack"
    }
  ],
  "totalGross": 404.95,
  "totalNet": 394.95
}
```



### H2 Database

This runs with an embedded H2 in-memory database. This could easily be changed to a postgres database or similar for
production.

Goto: http://localhost:8080/h2-console and enter URL: `jdbc:h2:mem:jonasingvar`




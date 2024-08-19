#!/bin/bash

curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{ "products" : [
  {"id": "BACKPACK-FR-14", "quantity": 2, "name" : "Fjallraven Backpack", "price": 149.99, "discount": 5.00},
  {"id": "HAT-FR-3", "quantity": 3, "price": 34.99, "name" : "Fjallraven Hat", "discount": 0 }
]}'

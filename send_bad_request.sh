#!/bin/bash

echo
echo Invalid product values

curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{ "products" : [
  {"productKey": "BACKPACK-FR-14", "quantity": 2, "name" : "Fjallraven Backpack", "price": 149.99, "discount": 5.00},
  {"productKey": "H3", "quantity": -3, "price": -34.99, "name" : "FB", "discount": -5 }
]}'  | jq

echo
echo
echo Empty product list
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{ "products" : []}'  | jq

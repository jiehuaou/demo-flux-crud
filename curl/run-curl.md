
# get stream of all

curl -v http://localhost:8080/item-stream

# get item TLG-SKU-0082

curl -v http://localhost:8080/item/TLG-SKU-0082

# create NEW item 

curl -H "Content-Type: application/json" --data @item1.json -X POST http://localhost:8080/item-x

# update item 
   
curl -H "Content-Type: application/json" --data @item2.json -X PUT http://localhost:8080/item-x/TLG-SKU-0082a   
   
# delete item 
   
curl -X DELETE http://localhost:8080/item-x/1001


   
   
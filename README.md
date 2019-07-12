A Simple Rest API server 

To compile
```bash
$ mvn clean package
```

To Run ```
java -cp target/dependency/*:target/rs-1.0-SNAPSHOT.jar  org.ys.tutorial.AppServer
```

To try it
```bash

$ curl -X GET http://localhost:8080/todo

$ curl -X PUT http://localhost:8080/todo -H 'content-type:Application/json' -d '{"description":"walk the dog"}'

$ curl -X PUT http://localhost:8080/todo -H 'content-type:Application/json' -d '{"description":"feed the cat"}'

$ curl -X GET http://localhost:8080/todo

$ curl -X POST http://localhost:8080/todo -H 'content-type:Application/json' -d '{"description":"feed the cat", "id":2, "done":true}'

$ curl -X GET http://localhost:8080/todo

$ curl -X DELETE http://localhost:8080/todo/1 

$ curl -X GET http://localhost:8080/todo


```


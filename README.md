**api-demo overview** \
Features 
1. It's done using contract first approach. The contract is made available \
 in resources/static  directory and Rest Interface & model are generated using maven plugin.
2. It uses oneToMany in hibernate to map Recipe with list of ingredients
3. Have used controllerAdvice to capture and customize error msg.
An example of it can be executed can as below.
curl --location --request POST 'http://localhost:8080/v1/recipes' --header 'Content-Type: application/json' --data-raw '{}'
4. The swagger documentation can be accessed from here http://localhost:8080/v1/swagger-ui.html
5. Have used undertow instead of tomcat for lighter footprint.

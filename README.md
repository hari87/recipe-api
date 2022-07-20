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
6. The update over ingredients is not implemented as it is at a different table and logic would also be a little different.
7. I prefer to use meaningful and descriptive method and variable names so the functionality in a method is concise, and easier without comments.  So they tend to be a bit longer, but helps during reading of code.
8. Extended with API filter
9. Added document generator
10. Included slf4j2 logging (avoiding vulnerability) 
11. Included dockerfile and docker-compose. And following commands help to run locally with docker 
    1. From classpath execute  docker build -f .\src\assembly\docker\Dockerfile -t recipe-api:0.0.1 .
    2. From classpath execute docker-compose -f .\src\assembly\docker\docker-compose.yaml up -d
12. Documentation are generated in target/static dir
13. Contract was designed using apicur.io studio
14. Would have preferred liquibase for db table creation and MapStruct for POJO mapping/transformation
15. Did not focus on Ingredients much while creating filters for app.
16. Would have used actuators for health and readiness

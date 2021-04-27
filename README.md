# usage-tool
A hotel usage tool, for calculating 
usage based on room categories rules. 

--------------------
prereqs for running
-------------------
java version 11
maven 3.6+




-----------------------
running the application
-----------------------
Navigate inside the directory of the project 
and use the following commands:

mvn clean install (clean, build & run unit tests)
mvn verify (run unit tests)
mvn spring-boot:run (run)

alternatively you can run with:
java -jar target/usage-tool-0.0.1-SNAPSHOT.jar


-------------
API end-point
-------------
exposed on port: 8080

POST    /hotel-usage/calculate
body: 
{
    "freePremiumRooms": 7,
    "freeEconomyRooms":5,
    "potentialGuestPrices": [ 23, 95.50, 45, 155, 374, 22, 99.99, 90.75, 100, 101, 115, 209 ]
}

responses:
200 success
{
    "premium": {
        "rooms": 5,
        "reservedRooms": 5,
        "totalUsage": 954.0
    },
    "economy": {
        "rooms": 5,
        "reservedRooms": 5,
        "totalUsage": 354.24
        }
}

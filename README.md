# indy-oracle
Spring Boot app for organizing cosplay safety.

# Intro
Demonstrates knowledge of:
* Java
* Spring Boot
* Spring Security
* [Stormpath](https://stormpath.com/) (User Management API)
* GMail API
* Heroku

# Production
This app is hosted on Heroku and can be viewed here: 
[https://indy-oracle.herokuapp.com/](https://indy-oracle.herokuapp.com/)

# Running a Local Instance of indy-oracle
* First, you will need a Stormpath API key. Put this in ```Users\account\.stormpath\apiKey.properties```.
* Next, clone this repository and navigate to the ```rootdirectory\indy-oracle\``` directory.
* Then run ```mvn clean package``` then ```java -jar target\the-indy-oracle-0.0.1-SNAPSHOT.jar```.

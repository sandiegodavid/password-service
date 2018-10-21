Please run the following steps to setup for execution:

- Build jar file:

			mvn clean install

- Start RESTful Web service:

			java  -Dspring.profiles.active=local -jar target/passwordService-0.0.1-SNAPSHOT.jar
	
- Request from Browser(Chrome, Firefox, Safari):

			https://localhost:8100/users
	
Notes:

- resources/selfsigned.jks expires on Fri Jan 18 14:39:20 PST 2019
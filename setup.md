For detailed description on functionality, please checkout `function_description.md`

Please run the following steps to setup for execution:

* Build jar file:

			./mvnw clean install

* Run test coverage:

			./mvnw clean verify

* Start RESTful Web service:

			java  -Dspring.profiles.active=local -jar target/passwordService-0.0.1-SNAPSHOT.jar
	
* Request from Browser(Chrome, Firefox, Safari). Please be sure to use `https`. The very first time will take you to OAuth page with Facebook:

			https://localhost:8100/users
			https://localhost:8100/users/query?uid=1001
			
	
* Request from CURL or [Postman](https://www.getpostman.com/ "Postman")
  1. Get OAuth token from OAuth Provider
  2. Add token in `Authorization` header in the format of `Bearer <your token>`
  
  
  			curl -H "Authorization: Bearer $TOKEN" https:/localhost:8100/users
  

			




Notes:

- `resources/selfsigned.jks`(self signed site certificate) expires on Fri Jan 18 14:39:20 PST 2019
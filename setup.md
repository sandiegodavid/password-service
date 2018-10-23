For detailed description on functionality, please checkout `function_description.md`

Please run the following steps to setup for execution:

* Build jar file:

			./mvnw clean install

* Run test coverage:

			./mvnw clean verify

* Start RESTful Web service:

			java  -Dspring.profiles.active=local -jar target/passwordService-0.0.1-SNAPSHOT.jar
	
* Request from Browser(Chrome, Firefox, Safari). Please be sure to use `https`:

			https://localhost:8100/users
			https://localhost:8100/users/query?uid=1001
			https://localhost:8100/users/1001
			https://localhost:8100/users/1001/groups
			https://localhost:8100/groups
			https://localhost:8100/groups/query?gid=502&member=shelley&members=juan
			https://localhost:8100/groups/250						

Notes:

- `resources/selfsigned.jks`(self signed site certificate) expires on Fri Jan 18 14:39:20 PST 2019
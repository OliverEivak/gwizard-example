# A modified GWizard Example
A WIP to add more useful basic functionalities to the gwizard example project.

## Running

```
$ mvn install
$ java -jar target/gwizard-example-1.0-SNAPSHOT.jar properties.yml
```

**Note:** this project needs gwizard-hibernate 0.7-SNAPSHOT

Stopping
```
java -jar target/gwizard-example-1.0-SNAPSHOT.jar properties.yml stop
```

# Changes compared to the original example
* Can build fat jar
* Use reflection to find services and resources
* Mysql support
* import.sql for tests
* Switched to Junit and EasyMock
* Tests for DAO, Service and Resource layer
* Shut down application from command line
* Configured logging to file
* BaseDAO, BaseResource
* Configurable initParameters for ServletContextHolder
    * RestEasy @RolesAllowed enabled
    * Custom authentication filter
    * Tests for authentication
* Custom exception mapping
* Registration (using bcrypt for passwords)
* Log in and log out
* Cobertura configuration in pom

## TODO
* Make a BaseEntity
* Add more hibernate configuration
* Providers as separate classes maybe

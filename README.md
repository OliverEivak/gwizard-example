# A modified GWizard project base
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
        * Returns 401 when authentication is not valid
        * Returns 403 in case of role based restrictions
    * Tests for authentication
    * Params are also used to list RestEasy providers
* Custom exception mapping
* Registration (using bcrypt for passwords)
* Log in and log out
* Cobertura configuration in pom
* PersistFilter to start/end a UnitOfWork for every request, this creates and closes an EntityManager

## TODO
* Make a BaseEntity
* Providers as separate classes maybe

# A modified GWizard Example
A WIP to add more useful basic functionalities to the gwizard example project.

## Running

```
$ mvn install
$ java -jar target/gwizard-example-1.0-SNAPSHOT.jar properties.yml
```

# Changelog
* Can build fat jar
* Use reflection to find Resources
* Mysql support
* import.sql for tests
* Switched to Junit and EasyMock
* Example tests for DAO and Service layer too
* Shut down application from command line
* Configured logging to file
* Made BaseDAO, BaseResource
* Allow configuring initParameters for ServeletContextHolder
    * Enabled RestEasy @RolesAllowed
    * Added custom authentication filter
    * Added FullWebStackTest for authentication
* Added custom exception mapping

# TODO
* Add more tests
* Make a BaseEntity
* Providers as separate classes maybe
* Add more hibernate configuration
* Find a good place where to register providers for ResteasyProviderFactory
* Add an update method, test it
* Create a testdata.sql

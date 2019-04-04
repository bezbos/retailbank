#### A demo project I've made using Spring Boot.

### MySQL
* Database based off a retail bank schema(http://www.databaseanswers.org/data_models/retail_banks/)
* Tables for user authentication and authorization.
* Stored Procedures all necessary operations.
* After INSERT or UPDATE the new record is returned to the user(made use of LAST_INSERT_ID() for this).
* Before DELETE we check if the record exists. If not, we throw an exception using SIGNAL SQLSTATE.
* Triggers are used for all domain INSERT, UPDATE and DELETE operations. Records are saved in the `audit_log` table.
* Events are used to clean stale audit logs on a yearly interval.
* For the `create_payment` stored procedure I've used SET SESSION TRANSACTION ISOLATION LEVEL SERIALIZABLE; to ensure greater reliability and prevent "phantom reads" from occuring(default isolation level for all SQL Transactions is REPEATABLE READ which is probably good enough, but I wanted to show what I know).
* Used String and Date functions here and there(NOW(), CONCAT(), INTERVAL..., etc.)
* Some SQL features I haven't used are Views, IFNULL(), COALESCE(), REGEX(), CASE, ALL, ANY, Subqueries(well I did use JOINS instead), ROLLUP, HAVING, GROUP BY and so on. I am aware of these features but just haven't found place in my project for them.

### Models
* Created domain models and DTOs.
* I've done a poor job of converting domain models to DTOs and vice versa. Basically all domain models have methods like `getDto()` and DTOs have `getDBModel()`. Domain models and DTOs should not be aware of each other, instead I should've created a Mapper using Java Reflection APIs but I didn't want to spend anymore time than necessary on this project so this remains done poorly...

### Repositories
* Connected the application to DB with Java platform JDBC(not the Spring one).
* Created a repository interface `JDBCRepositoryBase` containing CRUD method signatures.
* Created repository implementations for all domain models.
* Created repositories for user auth operations.
* Created some helper classes for configuration, closing connections and pagination.
* Annotated implementation classes with `@Repository` annotation.

### Services
* Created a service interface and it's implementation for each domain model.
* Used C# naming convention for naming interfaces here. Ex. Interface: `IBankService`, Implementation: `BankService`. I am aware that in Java the convention is to suffix your implementations with `Impl`.
* Annotated implementation classes with `@Service` annotation.

### Security
* I'm using JWT for authorization and authentication as they work so nicely with React.
* Normal users can only read data. Only admins are authorized for all APIs.
* OAUTH2 single sign in via Google is implemented, although it works only when I run my React project in development. It doesn't work in the production build of the React App because of the lack of Server Side Rendering implementation.

### REST Controllers
* I version my API using the popular `/api{versionNumber}/` convention.
* I use nouns instead of verbs in my API names `/api/v1/customers` returns all customers, `/api/v1/customer/1` returns a single customer with ID of 1,  `/api/v1/customers/3` returns the third page containing multiple customers.
* `ResponseEntity<>` is used to format the HTTP responses.
* I've taken care to return proper HTTP status codes.
* Implemented pagination from scratch.

### Tests
* Used Mockito, Hamcrest and JUnit for testing.
* Written Integration tests for Repository layer.
* Written Mock tests for Service layer.
* Written Mock tests for REST APIs.

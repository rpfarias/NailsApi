# Nails Application

Application for order management.

### Dependencies

- [Java 11 Development Kit](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [MySQL](https://www.mysql.com/)

### Built With

- [Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Springboot](https://spring.io/projects/spring-boot/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa/)
- [Maven](https://maven.apache.org/)
- [Swagger](https://swagger.io/)
- [MapStruct](https://mapstruct.org/)
- [Lombok](https://projectlombok.org/)
- [Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)

### Getting Started

The application can be started using Maven.

> `Access the file application.properties and change database access data, like the example below`
>

```
spring.datasource.url=YOUR_JDBC_URL
spring.datasource.username=YOUR_USER
spring.datasource.password=YOUR_PASSWORD
```

**Example of jdbc url**: jdbc:mysql://localhost:3306/db_nexti

> `mvn clean install`
>
> `mvn spring-boot:run`

### Facilities

- Port - http://localhost:8080/
- Swagger - http://localhost:8080/swagger-ui/index.html
- Actuator - http://localhost:8080/actuator/health

### If you want to add fake data, go to

- Clients
- http://localhost:8080/swagger-ui/index.html#/fake-data-controller/createClientsUsingPOST

```
curl -X POST "http://localhost:8080/fake/clients" -H "accept: application/json" -d ""
```

- Products
- http://localhost:8080/swagger-ui/index.html#/fake-data-controller/createProductsUsingPOST

```
  curl -X POST "http://localhost:8080/fake/products" -H "accept: application/json" -d ""
```

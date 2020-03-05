
# Shopping Cart Application

This application is a simple design of Shopping Cart for an e-commerce system.

## Requirements

For building and running the application you need:

- [JDK 1.10](https://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html)
- [Maven 4](https://maven.apache.org)
- [Lombok](https://projectlombok.org/)
    * For IntelliJ [Lombok IntelliJ Plugin] (https://plugins.jetbrains.com/plugin/6317-lombok)
    * For Eclipse [Lombok Eclipse] (https://projectlombok.org/downloads/lombok.jar)

## Running the application locally

There are several ways to run a Spring application on your local machine. One way is to execute the `main` method in the `com.canemreayar.bookshop.BookShopApplication` class from your IDE.

Alternatively you can use the maven spring-boot run command like:

For calling below commands,firstly you have to go the project folder directory.

```shell
mvn clean install
mvn spring-boot:run

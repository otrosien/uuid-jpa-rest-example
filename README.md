# JPA/EclipseLink UUID primary key example

[![Build Status](https://travis-ci.org/otrosien/uuid-jpa-rest-example.svg)](https://travis-ci.org/otrosien/uuid-jpa-rest-example)

Example project for demonstrating the use of UUIDs as primary keys in JPA.

## Special Features

### UUID persistence

The UUID is not naïvely serialized as String, but into a 16-byte array.
It is using the [Java Uuid Generator](https://github.com/cowtowncoder/java-uuid-generator)
library for generating Version-1 UUIDs, hiding the actual implementation behind a functional interface.

### JPA auditing and optimistic locking

The `Mouse` entity has support for last-modified-based and ETag-based optimistic locking.
These properties are hidden from the business model and REST API (i.e. private fields without getters/setters)

### EclipseLink load-time weaving

Although not directly related to UUID persistence, this project also applies EclipseLinks load-time
weaving, but only when spring-instrument.jar is present on the classpath.

## How to run

On the command-line run `./gradlew bootRun` and open a browser at [http://localhost:8080/](http://localhost:8080/).

## Further references:

* https://en.wikipedia.org/wiki/Universally_unique_identifier
* https://github.com/cowtowncoder/java-uuid-generator
* http://mysql.rjweb.org/doc.php/uuid
* http://mysqlserverteam.com/storing-uuid-values-in-mysql-tables/
* https://www.clever-cloud.com/blog/engineering/2015/05/20/why-auto-increment-is-a-terrible-idea/

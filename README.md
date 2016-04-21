# JPA/EclipseLink UUID primary key example

[![Build Status](https://travis-ci.org/otrosien/uuid-jpa-rest-example.svg)](https://travis-ci.org/otrosien/uuid-jpa-rest-example)

Example project for demonstrating the use of UUIDs as primary keys in JPA and Spring Data REST.

## Special Features

### UUID as primary key

This service is using UUIDs as primary keys. The UUID is not naïvely persisted into the
DB as `CHAR(36)`, but shifted into a `BYTE(16)` structure.

It is using the [Java Uuid Generator](https://github.com/cowtowncoder/java-uuid-generator)
library for generating Version-1 UUIDs, still hiding the actual implementation behind a
functional interface ( `UUIDGenerator` ).

### Business vs. technical identifiers

In addition to the `id`, which is immutable, there is the `name` property as business identifier of a resource.
Most business identifiers in the wild are not immutable, unfortunately.
Still, by default the URI is composed using the business identifier, creating human-readable URIs.
For the case of "renamed mice", there is still the possibility of a look-up via the finder resource
`/search/findById`.

### JPA auditing and optimistic locking

The `Mouse` entity has support for last-modified-based and `ETag`-based optimistic locking.
The extra properties ( `optLock`, `createdAt`, `lastModifiedAt` ) are hidden from the business model
and resource representations (i.e. private fields without getters/setters)

### EclipseLink load-time weaving

Although not directly related to UUID persistence, this project also applies EclipseLink's load-time
weaving, but only when `spring-instrument.jar` is present on the classpath.

## How to run

On the command-line run `./gradlew bootRun` and open a browser at [http://localhost:8080/](http://localhost:8080/).

## Further references:

* https://en.wikipedia.org/wiki/Universally_unique_identifier
* https://github.com/cowtowncoder/java-uuid-generator
* http://mysql.rjweb.org/doc.php/uuid
* http://mysqlserverteam.com/storing-uuid-values-in-mysql-tables/
* https://www.clever-cloud.com/blog/engineering/2015/05/20/why-auto-increment-is-a-terrible-idea/

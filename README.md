# JPA/EclipseLink UUID primary key example

Example project for demoing the use of UUIDs as primary keys in JPA, using 
Version 1 (time-based) UUIDs by default.

## Special Features

The UUID is not, naively serialized as String, but into a two-byte array.
It is using the [Java Uuid Generator](https://github.com/cowtowncoder/java-uuid-generator)
library for generating Version-1 UUIDs.

## How to run

On the command-line run `./gradlew bootRun` and open a browser at http://localhost:8080/ .

## Further references:

* https://en.wikipedia.org/wiki/Universally_unique_identifier
* https://github.com/cowtowncoder/java-uuid-generator
* http://mysql.rjweb.org/doc.php/uuid
* http://mysqlserverteam.com/storing-uuid-values-in-mysql-tables/
* https://www.clever-cloud.com/blog/engineering/2015/05/20/why-auto-increment-is-a-terrible-idea/

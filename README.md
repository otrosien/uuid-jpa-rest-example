# JPA/Eclipselink UUID primary key sample

Example project for demonstrating an enhanced usage of UUIDs as primary keys in JPA,
using Version 1 (time-based) UUIDs.

## Special Features

The UUID is not, naively serialized as String, but converted into a 16-byte array
for optimized storage. The UUIDs are generated transparently while persisting the
entity. This is achieved by registering a custom UUID sequence generator in EclipseLink.

The JUG [Java Uuid Generator](https://github.com/cowtowncoder/java-uuid-generator) library
serves as source for generation of (Version-1) UUIDs.

## Further references:

* https://en.wikipedia.org/wiki/Universally_unique_identifier
* https://github.com/cowtowncoder/java-uuid-generator
* http://mysql.rjweb.org/doc.php/uuid
* http://mysqlserverteam.com/storing-uuid-values-in-mysql-tables/
* https://www.clever-cloud.com/blog/engineering/2015/05/20/why-auto-increment-is-a-terrible-idea/
* https://wiki.eclipse.org/EclipseLink/Examples/JPA/CustomSequencing
* http://stackoverflow.com/questions/10867405/generating-v5-uuid-what-is-name-and-namespace
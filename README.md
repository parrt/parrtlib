# parrtlib

Parrt's Java library with useful functions

## Releasing

```bash
mvn deploy
mvn release:prepare
mvn release:perform
```

Maven will use git to push `pom.xml` changes.

Now, go here:

    https://oss.sonatype.org/#welcome

and on the left click "Staging Repositories". Click the staging repo and close it, then you refresh, click it and release it. It's done when you see it here:

    http://repo1.maven.org/maven2/us/parr/parrtlib
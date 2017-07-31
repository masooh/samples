# Jacoco

Generate Coverage Report: `mvn package`
- Report is in `target/site/jacoco/index.html`
- execution data: `target/jacoco.exec` 

`mvn test`
- only produces `target/jacoco.exec`
 

## How does it work?

Modification of `argLine` Parameter which is used by 
[surefire:test](http://maven.apache.org/surefire/maven-surefire-plugin/test-mojo.html).

```
[DEBUG] Configuring mojo 'org.jacoco:jacoco-maven-plugin:0.7.9:prepare-agent'
...
[INFO] argLine set to -javaagent:/home/masooh/.m2/repository/org/jacoco/org.jacoco.agent/0.7.9/org.jacoco.agent-0.7.9-runtime
.jar=destfile=/home/masooh/github/samples/jacoco/target/jacoco.exec
...
[DEBUG] Configuring mojo 'org.apache.maven.plugins:maven-surefire-plugin:2.12.4:test' with basic configurator -->
[DEBUG]   (s) argLine = -javaagent:/home/masooh/.m2/repository/org/jacoco/org.jacoco.agent/0.7.9/org.jacoco.agent-0.7.9-runtime.jar=destfile=/home/masooh/github/samples/jacoco/target/jacoco.exec
...
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Forking command line: /bin/sh -c cd /home/masooh/github/samples/jacoco && /opt/jdk1.8.0_121/jre/bin/java -javaagent:/home/masooh/.m2/repository/org/jacoco/org.jacoco.agent/0.7.9/org.jacoco.agent-0.7.9-runtime.jar=destfile=/home/masooh/github/samples/jacoco/target/jacoco.exec -jar /home/masooh/github/samples/jacoco/target/surefire/surefirebooter8204652475694082862.jar /home/masooh/github/samples/jacoco/target/surefire/surefire6361610322700484510tmp /home/masooh/github/samples/jacoco/target/surefire/surefire_06669626637539293301tmp
Running com.github.masooh.samples.jacoco.CalcTest
...
[INFO] --- jacoco-maven-plugin:0.7.9:report (default-report) @ jacoco ---
 [DEBUG] Configuring mojo 'org.jacoco:jacoco-maven-plugin:0.7.9:report' with basic configurator -->
[DEBUG]   (f) dataFile = /home/masooh/github/samples/jacoco/target/jacoco.exec
[DEBUG]   (f) outputDirectory = /home/masooh/github/samples/jacoco/target/site/jacoco
[DEBUG]   (f) outputEncoding = UTF-8
[DEBUG]   (f) project = MavenProject: com.github.masooh.samples:jacoco:1.0-SNAPSHOT @ /home/masooh/github/samples/jacoco/pom.xml
[DEBUG]   (f) skip = false
[DEBUG]   (f) sourceEncoding = UTF-8
[DEBUG]   (f) title = jacoco
[DEBUG] -- end configuration --
[INFO] Loading execution data file /home/masooh/github/samples/jacoco/target/jacoco.exec
[INFO] Analyzed bundle 'jacoco' with 1 classes

```

# Sonar Integration

Start SonarQube in Docker: `docker run -d -p 9000:9000 -p 9092:9092 sonarqube`.

Download Sonar Scanner: [Analyzing with SonarQube Scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner)

Minimal working Sonar Scanner `sonar-scanner -Dsonar.projectKey=jacoco -Dsonar.sources=src/main/java`

More properties are passed via [sonar-project.properties](sonar-project.properties). Start Scanner without parameters `sonar-scanner`.

## Hint for mixed Java and Groovy test
While analysing the source files the SurefireSensor only looks for Java files and the GroovySurefireSensor only for Groovy 
files. As the surefire report for both is in the same directory (`target/surefire-reports`) both sensor get also the reports of
 tests in the other language. You can see in the log:
 
```
17:29:45.768 INFO: Sensor GroovySurefireSensor [groovy]
17:29:45.769 INFO: parsing /home/masooh/github/samples/jacoco/target/surefire-reports
...
17:29:45.828 WARN: Resource not found: com.github.masooh.samples.jacoco.CalcPlusJavaTest
...
17:29:45.849 INFO: Sensor SurefireSensor [java]
17:29:45.851 INFO: parsing [/home/masooh/github/samples/jacoco/target/surefire-reports]
17:29:45.858 DEBUG: Class not found in resource cache : com.github.masooh.samples.jacoco.CalcMinusGroovyTest
17:29:45.858 WARN: Resource not found: com.github.masooh.samples.jacoco.CalcMinusGroovyTest
``` 

Nevertheless the result is correct as both tests are added by their Sensor to Sonar.
# Checkerframework

See https://checkerframework.org/.

This project shows simple maven usage.
- `mvn clean test` fails with `NullPointerException` in tests.
```
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ checkerframework ---
[INFO] Surefire report directory: /home/masooh/github/samples/checkerframework/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running NullableTest
Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 0.063 sec <<< FAILURE!
nullableTest(NullableTest)  Time elapsed: 0.007 sec  <<< ERROR!
java.lang.NullPointerException
    at Caller.printer(Caller.java:10)
    at Caller.callOfNullable(Caller.java:6)
    at NullableTest.nullableTest(NullableTest.java:7)
```
- With the checker profile activated `mvn -Pchecker clean compile` fails in compile step.

```
 [INFO] Compiling 1 source file to /home/masooh/github/samples/checkerframework/target/classes
 [INFO] -------------------------------------------------------------
 [ERROR] COMPILATION ERROR :
 [INFO] -------------------------------------------------------------
 [ERROR] /home/masooh/github/samples/checkerframework/src/main/java/Caller.java:[6,17] [argument.type.incompatible] incompatible types in argument.
  found   : @Initialized @Nullable String
  required: @Initialized @NonNull String
 [INFO] 1 error

```

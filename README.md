# SpringBoot AWS Kitchensink

```shell
$ ./mvnw test

# this will start postgres and LocalStack containers
$ ./run.sh start 

# start the application using local profile
$ ./mvnw spring-boot:run -Dspring-boot.run.profiles=local 
```
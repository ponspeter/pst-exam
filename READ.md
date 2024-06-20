# Pedro Pons Exam

## Local setup

How to get the project running in your local development machine

* Checkout the project into your favorite IDE
* Install [JDK 17](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)
* Install [Docker](https://www.docker.com/products/docker-desktop)
* Run required docker containers (e.g. postgres. Open "exam/docker" directory in a terminal & run

if creating the containers for the first time use
```shell
docker-compose up -d
```

if you are recreating the containers run
```shell
docker compose up -d --force-recreate --renew-anon-volumes
```

Run the project from your IDE or Maven
```shell
./mvnw spring-boot:run "-Dspring-boot.run.profiles=local"
```

* navigate to http://localhost:8080/index to view all the records
* add records to http://localhost:8080/add
* update records to http://localhost:8080/edit/{uuid}
* search records to http://localhost:8080/search
* download records http://localhost:8080/download 


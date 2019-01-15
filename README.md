IWS Scrapping BookStore-Test
===

with great honor I present an api service to manage a bookstore.

# Using Environment
Run this environment is a quite simple,you'll need to follow these steps.

## #First Step
Install Docker and Docker Compose

```bash
$ curl -sSL https://get.docker.com/ | sh
$ sudo pip install docker-compose
```

## #Second Step
Clone environment from github.

```bash
$ sudo git clone https://github.com/Yuri-Bartochevis/BookStore.git
```
and after that, you'll need to execute MAVEN "../interview"

```bash
$ mvn clean install -U
```

## #Third and Last Step
Execute Enviroment with docker compose  "../bookStore"

```bash
$ sudo docker-compose build
$ sudo docker-compose up
```
How to test application 
===
while docker-compose is running, you can test API using the following Requests.

## Find a Book
```bash
$ curl -X GET \
  http://localhost:8080/book/1 \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: ff162f31-58e5-86bb-39ce-b38d99749493' \
  -d 8881153
```

## get all books
```bash
$ curl -X GET \
  'http://localhost:8080/books' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: cc48df4f-995c-896b-aed6-db1c1565d6e5'
```


OBS: To run integration tests, will be necessary add jvm environment "integration" = true. 

```bash
-Dintegration=true
```


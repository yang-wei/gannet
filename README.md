# Gannet 

Help you detech 404 links on your website

## Usage

### development
Start everything
```
lein run
lein repl
lein figwheel 
```

### build for production

```
make build
java -jar target/gannet-{version}.jar
```

### test

```
lein test
```

### migration

Migration folder is located in `resources/migrations`. To create new migration:

```
touch resources/migrations/`date "+%Y%m%d%H%M%S"`-xxx.{up,down}.sql
lein run migrate
```

where xxx is your file name.

## API

```
curl -d '{"url": "https://github.com"}' -H "Content-Type: application/json" http://localhost:3000/analyse
curl -d '{"urls": ["https://yang-wei.github.io"]}' -H "Content-Type: application/json" http://localhost:3000/crawler
```

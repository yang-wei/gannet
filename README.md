# Gannet 

Help you detech 404 links on your website

## Usage

Start everything
```
lein run
lein repl
lein figwheel 

(if you have tmux install)
bash ./scripts/lein.sh
```

## API

```
curl -d '{"url": "https://github.com"}' -H "Content-Type: application/json" http://localhost:3000/analyse
 curl -d '{"urls": ["https://yang-wei.github.io"]}' -H "Content-Type: application/json" http://localhost:3000/crawler
```
## License

Copyright © 2016 Gannet

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

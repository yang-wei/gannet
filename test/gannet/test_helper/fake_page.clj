(ns gannet.test-helper.fake-page
  (:require [net.cgrand.enlive-html :as html]))

(def fake-body
  "<html>
    <head>Test page</head> 
    <body>
      <div id='app'>
        <a href='http://ok-link.com'>OK LINK</a>
        <a href='http://not-found-link.com'>NOT FOUND LINK</a>
        <a href='http://error-link.com'>ERROR LINK</a>
        <a href='/relative-link'>ERROR LINK</a>
      </div>
    </body>
  </html>")

(defn fake-scrap-webpage [_]
  (html/html-resource (java.io.StringReader. fake-body)))

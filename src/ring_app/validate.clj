(ns ring-app.validate
  (:import org.apache.commons.validator.UrlValidator))

(defn valid-url? [url-str]
    (let [validator (UrlValidator.)]
          (.isValid validator url-str)))

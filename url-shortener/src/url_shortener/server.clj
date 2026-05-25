(ns url-shortener.server
  (:require [clojure.string :as str]
            [compojure.core :refer [defroutes GET POST PUT DELETE]]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.util.response :refer [response status]]
            [url-shortener.db :as db])
  (:import (java.net URLDecoder)
           (java.nio.charset StandardCharsets)))

(defn- decode [s]
  (URLDecoder/decode s (StandardCharsets/UTF_8)))

(defn- ok [body]
  (-> (response body)
      (assoc :headers {"Content-Type" "text/plain; charset=utf-8"})))

(defn- not-found []
  (-> (response "Not Found")
      (status 404)
      (assoc :headers {"Content-Type" "text/plain; charset=utf-8"})))

(defn- no-content []
  (-> (response "")
      (status 204)
      (assoc :headers {"Content-Type" "text/plain; charset=utf-8"})))

(defroutes app-routes
  (POST "/normal-url" request
    (let [normal-url (str/trim (slurp (:body request)))]
      (if (empty? normal-url)
        (not-found)
        (ok (db/create-url! normal-url)))))

  (GET "/:short-url" [short-url]
    (if-let [normal (db/get-normal-url short-url)]
      (ok normal)
      (not-found)))

  (PUT "/:short-url/:normal-url" [short-url normal-url]
    (let [short (decode short-url)
          normal (decode normal-url)]
      (if (db/update-url! short normal)
        (no-content)
        (not-found))))

  (DELETE "/:short-url" [short-url]
    (if (db/delete-url! (decode short-url))
      (no-content)
      (not-found)))

  (route/not-found (not-found)))

(def app app-routes)

(defn start
  "Запускает Ring-сервер на указанном порту."
  [port]
  (db/init!)
  (println (str "Сервер URL Shortener запущен: http://127.0.0.1:" port))
  (run-jetty app {:port port :join true}))

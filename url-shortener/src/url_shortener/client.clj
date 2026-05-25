(ns url-shortener.client
  (:require [clj-http.client :as http])
  (:import (java.net URLEncoder)
           (java.nio.charset StandardCharsets)))

(def default-base-url "http://127.0.0.1:8080")

(defn- encode [s]
  (URLEncoder/encode s (StandardCharsets/UTF_8)))

(defn- request-opts []
  {:throw-exceptions false
   :as :text})

(defn create-url
  "POST /normal-url — создать короткий url."
  [normal-url & {:keys [base-url] :or {base-url default-base-url}}]
  (let [resp (http/post (str base-url "/normal-url")
                 (merge (request-opts)
                        {:body normal-url
                         :content-type "text/plain; charset=utf-8"}))]
    (if (= 200 (:status resp))
      {:ok true :body (:body resp)}
      {:ok false :body nil})))

(defn show-url
  "GET /short-url — получить обычный url."
  [short-url & {:keys [base-url] :or {base-url default-base-url}}]
  (let [resp (http/get (str base-url "/" (encode short-url))
               (request-opts))]
    (if (= 200 (:status resp))
      {:ok true :body (:body resp)}
      {:ok false :body nil})))

(defn update-url
  "PUT /short-url/normal-url — изменить запись."
  [short-url normal-url & {:keys [base-url] :or {base-url default-base-url}}]
  (let [resp (http/put (str base-url "/" (encode short-url) "/" (encode normal-url))
               (request-opts))]
    (if (#{200 204} (:status resp))
      {:ok true}
      {:ok false})))

(defn delete-url
  "DELETE /short-url — удалить запись."
  [short-url & {:keys [base-url] :or {base-url default-base-url}}]
  (let [resp (http/delete (str base-url "/" (encode short-url))
                 (request-opts))]
    (if (#{200 204} (:status resp))
      {:ok true}
      {:ok false})))

(ns url-shortener.db
  (:require [clojure.java.jdbc :as jdbc]
            [korma.db :refer [defdb]]
            [korma.core :refer [defentity select insert delete where limit table pk values]
             :as k])
  (:import (java.util Random)))

(def db-file "url_shortener.db")

(def db-spec
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     db-file})

(defdb db db-spec)

(defentity urlentry
  (table :urls)
  (pk :id))

(def ^:private short-chars
  "abcdefghijklmnopqrstuvwxyz0123456789")

(defn- random-short-id
  "Генерирует короткий идентификатор из 8 символов."
  []
  (let [rnd (Random.)]
    (apply str (repeatedly 8 #(nth short-chars (.nextInt rnd (count short-chars)))))))

(defn init!
  "Создаёт таблицу urls, если её ещё нет."
  []
  (jdbc/execute!
    db-spec
    [(str "CREATE TABLE IF NOT EXISTS urls ("
           "id INTEGER PRIMARY KEY AUTOINCREMENT, "
           "short_url TEXT NOT NULL UNIQUE, "
           "normal_url TEXT NOT NULL)")]))

(defn find-by-short
  [short-url]
  (select urlentry
    (where {:short_url short-url})
    (limit 1)))

(defn find-by-normal
  [normal-url]
  (select urlentry
    (where {:normal_url normal-url})
    (limit 1)))

(defn- short-exists?
  [short-url]
  (boolean (seq (find-by-short short-url))))

(defn generate-unique-short-url
  "Возвращает уникальный короткий url."
  []
  (loop [candidate (random-short-id)]
    (if (short-exists? candidate)
      (recur (random-short-id))
      candidate)))

(defn create-url!
  "Сохраняет пару короткий/обычный url. Возвращает короткий url."
  [normal-url]
  (if-let [existing (first (find-by-normal normal-url))]
    (:short_url existing)
    (let [short-url (generate-unique-short-url)]
      (insert urlentry
        (values [{:short_url short-url :normal_url normal-url}]))
      short-url)))

(defn get-normal-url
  [short-url]
  (some-> (first (find-by-short short-url)) :normal_url))

(defn update-url!
  "Обновляет обычный url для короткого. Возвращает true при успехе."
  [short-url normal-url]
  (if-let [row (first (find-by-short short-url))]
    (do
      (k/update urlentry
        (k/set-fields {:normal_url normal-url})
        (where {:id (:id row)}))
      true)
    false))

(defn delete-url!
  "Удаляет запись по короткому url. Возвращает true при успехе."
  [short-url]
  (if-let [row (first (find-by-short short-url))]
    (do
      (delete urlentry (where {:id (:id row)}))
      true)
    false))

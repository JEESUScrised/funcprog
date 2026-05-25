(defproject url-shortener "0.1.0"
  :description "REST API для сокращения URL (Ring + Korma + SQLite)"
  :url "https://github.com/JEESUScrised/funcprog"
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring-core "1.11.0"]
                 [ring/ring-jetty-adapter "1.11.0"]
                 [compojure "1.7.1"]
                 [korma "0.4.3"]
                 [org.xerial/sqlite-jdbc "3.45.1.0"]
                 [clj-http "3.12.3"]]
  :main url-shortener.core
  :aot [url-shortener.core]
  :uberjar-name "url-shortener-0.1.0-standalone.jar"
  :profiles {:uberjar {:aot [url-shortener.core]}}
  :jvm-opts ["-Xmx256m"])

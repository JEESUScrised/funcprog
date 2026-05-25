(ns url-shortener.core
  (:gen-class)
  (:require [url-shortener.server :as server]
            [url-shortener.cli :as cli]))

(defn -main
  "Точка входа: с аргументом порта — сервер, без аргументов — клиент."
  [& args]
  (if (seq args)
    (server/start (Integer/parseInt (first args)))
    (cli/run-client)))

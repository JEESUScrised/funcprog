(ns url-shortener.cli
  (:require [clojure.string :as str]
            [url-shortener.client :as client]))

(defn- read-line-prompt [prompt]
  (print prompt)
  (flush)
  (read-line))

(defn- print-menu []
  (println)
  (println "=== URL Shortener ===")
  (println "1. Создать")
  (println "2. Показать")
  (println "3. Изменить")
  (println "4. Удалить")
  (println "5. Выйти"))

(defn- handle-create []
  (println "Введите обычный URL для сокращения:")
  (let [url (read-line-prompt "  > ")]
    (when (seq (str/trim url))
      (println "\nОтправка запроса...")
      (if-let [result (:body (client/create-url (str/trim url)))]
        (println (str "Ответ: " result))
        (println "Ответ: Ошибка")))))

(defn- handle-show []
  (println "Введите короткий URL:")
  (let [short (read-line-prompt "  > ")]
    (when (seq (str/trim short))
      (println "\nОтправка запроса...")
      (let [resp (client/show-url (str/trim short))]
        (println (if (:ok resp)
                   (str "Ответ: " (:body resp))
                   "Ответ: Ошибка"))))))

(defn- handle-update []
  (println "Введите короткий URL и обычный URL через пробел:")
  (let [input (read-line-prompt "  > ")
        parts (clojure.string/split (str/trim input) #"\s+" 2)]
    (when (= 2 (count parts))
      (println "\nОтправка запроса...")
      (let [resp (client/update-url (first parts) (second parts))]
        (println (if (:ok resp) "Ответ: OK" "Ответ: Ошибка"))))))

(defn- handle-delete []
  (println "Введите короткий URL:")
  (let [short (read-line-prompt "  > ")]
    (when (seq (str/trim short))
      (println "\nОтправка запроса...")
      (let [resp (client/delete-url (str/trim short))]
        (println (if (:ok resp) "Ответ: OK" "Ответ: Ошибка"))))))

(defn run-client
  "Запускает интерактивное меню клиента."
  []
  (println "Клиент URL Shortener")
  (loop []
    (print-menu)
    (let [choice (read-line-prompt "Выберите действие: ")]
      (case choice
        "1" (handle-create)
        "2" (handle-show)
        "3" (handle-update)
        "4" (handle-delete)
        "5" (println "Выход.")
        (println "Неизвестное действие. Выберите 1–5."))
      (when (not= choice "5")
        (recur)))))

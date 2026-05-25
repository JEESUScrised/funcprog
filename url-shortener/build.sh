#!/bin/bash

# Скачивание зависимостей и сборка проекта
lein deps
lein uberjar

echo "Сборка завершена!"
echo ""
echo "Запуск сервера: java -jar target/url-shortener-0.1.0-standalone.jar 8080"
echo "Запуск клиента: java -jar target/url-shortener-0.1.0-standalone.jar"

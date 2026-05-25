#!/bin/bash

# Скачивание зависимостей и сборка проекта
LEIN_CMD="lein"
if [ -f "./lein" ]; then
  LEIN_CMD="./lein"
elif [ -f "./lein.bat" ]; then
  LEIN_CMD="./lein.bat"
fi

$LEIN_CMD deps
$LEIN_CMD uberjar

echo "Сборка завершена!"
echo ""
echo "Запуск сервера: java -jar target/url-shortener-0.1.0-standalone.jar 8080"
echo "Запуск клиента: java -jar target/url-shortener-0.1.0-standalone.jar"

# REST API для сокращения URL

Служба сокращения ссылок на **Ring** + **Korma** + **SQLite**.  
Клиент — консольное меню, сервер — REST API.

## Сборка

**Linux / macOS / Git Bash:**

```bash
chmod +x build.sh
./build.sh
```

**Windows** (если `lein` не в PATH — сначала скачайте `lein.bat`):

```powershell
Invoke-WebRequest -Uri "https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein.bat" -OutFile lein.bat
.\lein.bat self-install
.\build.bat
```

Требуется [Leiningen](https://leiningen.org/) (или локальный `lein.bat`) и Java 11+.

## Запуск

**Сервер** (отдельный терминал):

```bash
java -jar target/url-shortener-0.1.0-standalone.jar 8080
```

**Клиент:**

```bash
java -jar target/url-shortener-0.1.0-standalone.jar
```

## REST API

| Метод | Путь | Действие |
|-------|------|----------|
| `POST` | `/normal-url` | Создать короткий URL (тело запроса — обычный URL) |
| `GET` | `/:short-url` | Получить обычный URL по короткому |
| `PUT` | `/:short-url/:normal-url` | Изменить соответствие |
| `DELETE` | `/:short-url` | Удалить запись |

База данных: `url_shortener.db` (SQLite, создаётся при старте сервера).

## Структура

```
url-shortener/
├── project.clj
├── build.sh / build.bat
└── src/url_shortener/
    ├── core.clj    — точка входа (сервер / клиент)
    ├── db.clj      — Korma + SQLite
    ├── server.clj  — Ring HTTP API
    ├── client.clj  — HTTP-клиент
    └── cli.clj     — меню в терминале
```

## Ссылки на GitHub

- Папка на `main`: [funcprog/tree/main/url-shortener](https://github.com/JEESUScrised/funcprog/tree/main/url-shortener)
- Отдельная ветка: [funcprog/tree/url-shortener](https://github.com/JEESUScrised/funcprog/tree/url-shortener)

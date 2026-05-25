# funcprog

Задания по функциональному программированию (Clojure).

Репозиторий: [https://github.com/JEESUScrised/funcprog](https://github.com/JEESUScrised/funcprog)

## Структура

| Папка | Задание | Ссылка |
|-------|---------|--------|
| [`guess-number/`](guess-number/) | Игра «Угадай число» наоборот | [открыть на GitHub](https://github.com/JEESUScrised/funcprog/tree/main/guess-number) |
| [`plotter/`](plotter/) | Виртуальный плоттер (Clojure + TypeScript) | [открыть на GitHub](https://github.com/JEESUScrised/funcprog/tree/main/plotter) |
| [`url-shortener/`](url-shortener/) | REST API сокращения URL (Ring + Korma) | [открыть на GitHub](https://github.com/JEESUScrised/funcprog/tree/main/url-shortener) |

Каждое задание в отдельной папке — удобно сдавать ссылку на конкретную директорию.

## Ветки (опционально)

На `main` лежат все задания. Отдельные ветки — только одно задание:

| Ветка | Задание | Ссылка |
|-------|---------|--------|
| `guess-number` | Угадай число | [github.com/JEESUScrised/funcprog/tree/guess-number](https://github.com/JEESUScrised/funcprog/tree/guess-number) |
| `plotter` | Плоттер | [github.com/JEESUScrised/funcprog/tree/plotter](https://github.com/JEESUScrised/funcprog/tree/plotter) |
| `url-shortener` | Сокращение URL | [github.com/JEESUScrised/funcprog/tree/url-shortener](https://github.com/JEESUScrised/funcprog/tree/url-shortener) |

## Leiningen (Windows)

Если `lein` не в PATH, в `url-shortener/` скачайте обёртку:

```powershell
cd url-shortener
Invoke-WebRequest -Uri "https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein.bat" -OutFile lein.bat
.\lein.bat self-install
.\build.bat
```

;; Плоттер поддерживает пять команд:
;;
;; 1. Переместить каретку на некоторое расстояние в текущем направлении.
;; 2. Повернуть на определённое количество градусов по или против часовой стрелки.
;; 3. Опустить или поднять каретку. Когда каретка опущена, при перемещении рисуется линия.
;; 4. Установить цвет линии (чёрный, красный или зелёный).
;; 5. Установить начальную позицию каретки.
;;
;; Состояние неизменяемо: каждая команда возвращает новое состояние (функциональный стиль).

;;; --- Константы и отображение цветов ---

(def line-colors
  "Соответствие ключей цвета строкам для вывода (как в plotter.ts)."
  {:black "чорный"
   :red   "красный"
   :green "зелёный"})

(defn color-name [color]
  (get line-colors color (str color)))

;;; --- Начальное состояние ---

(defn initial-plotter-state
  "Создаёт начальное состояние плоттера."
  ([] (initial-plotter-state {:x 0.0 :y 0.0} 0.0 :black :up))
  ([position angle color carriage-state]
   {:position position
    :angle angle
    :color color
    :carriage-state carriage-state}))

;;; --- Вспомогательные функции ---

(defn draw-line
  "Чертит линию от координат from к координатам to."
  [prt from to color]
  (prt (str "...Чертим линию из (" (:x from) ", " (:y from) ") в ("
            (:x to) ", " (:y to) ") используя " (color-name color) " цвет.")))

(defn calc-new-position
  "Вычисляет новую позицию при перемещении на distance под углом angle."
  [distance angle current]
  (let [angle-rads (* angle (/ Math/PI 180.0))
        x (+ (:x current) (* distance (Math/cos angle-rads)))
        y (+ (:y current) (* distance (Math/sin angle-rads)))]
    {:x (Math/round x) :y (Math/round y)}))

;;; --- Команды плоттера (возвращают новое состояние) ---

(defn move
  "Перемещает каретку на расстояние distance."
  [prt distance state]
  (let [new-position (calc-new-position distance (:angle state) (:position state))]
    (when (= :down (:carriage-state state))
      (draw-line prt (:position state) new-position (:color state)))
    (when (= :up (:carriage-state state))
      (prt (str "Передвигаем на " distance " от точки ("
                (:x (:position state)) ", " (:y (:position state)) ")")))
    (assoc state :position new-position)))

(defn turn
  "Поворачивает каретку на угол angle (градусы)."
  [prt angle state]
  (prt (str "Поворачиваем на " angle " градусов"))
  (assoc state :angle (mod (+ (:angle state) angle) 360.0)))

(defn carriage-up
  "Поднимает каретку."
  [prt state]
  (prt "Поднимаем каретку")
  (assoc state :carriage-state :up))

(defn carriage-down
  "Опускает каретку."
  [prt state]
  (prt "Опускаем каретку")
  (assoc state :carriage-state :down))

(defn set-color
  "Устанавливает цвет линии (:black, :red или :green)."
  [prt color state]
  (prt (str "Устанавливаем " (color-name color) " цвет линии."))
  (assoc state :color color))

(defn set-position
  "Устанавливает позицию каретки."
  [prt position state]
  (prt (str "Устанавливаем позицию каретки в (" (:x position) ", " (:y position) ")."))
  (assoc state :position position))

;;; --- Черчение фигур ---

(defn draw-triangle
  "Чертит треугольник со сторонами size."
  [prt size state]
  (loop [state (carriage-down prt state)
         step 0]
    (if (< step 3)
      (recur (-> state
                 (move prt size)
                 (turn prt 120.0))
             (inc step))
      (carriage-up prt state))))

(defn draw-square
  "Чертит квадрат со сторонами size."
  [prt size state]
  (loop [state (carriage-down prt state)
         step 0]
    (if (< step 4)
      (recur (-> state
                 (move prt size)
                 (turn prt 90.0))
             (inc step))
      (carriage-up prt state))))

;;; --- Демонстрация (как в plotter.ts) ---

(defn run-demo
  "Запускает тот же сценарий, что и plotter.ts: треугольник, затем красный квадрат."
  []
  (let [prt println]
    (-> (initial-plotter-state)
        (draw-triangle prt 100.0)
        (set-position prt {:x 10.0 :y 10.0})
        (set-color prt :red)
        (draw-square prt 80.0))))

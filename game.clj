(def game-state
  (atom {:low 1 :high 100 :guess nil}))

(defn start [n m]
  (reset! game-state {:low n :high m :guess nil})
  "Я готов...")

(defn guess-my-number []
  (let [{:keys [low high]} @game-state
        guess (quot (+ low high) 2)]
    (swap! game-state assoc :guess guess)
    guess))

(defn smaller []
  (let [{:keys [guess]} @game-state]
    (swap! game-state assoc :high (dec guess) :guess nil)
    (guess-my-number)))

(defn bigger []
  (let [{:keys [guess]} @game-state]
    (swap! game-state assoc :low (inc guess) :guess nil)
    (guess-my-number)))

(defn v+ [v1 v2]
  (mapv + v1 v2))

(defn v- [v1 v2]
  (mapv - v1 v2))

(defn v* [v1 v2]
  (mapv * v1 v2))

(defn vd [v1 v2]
  (mapv / v1 v2))

(defn scalar [v1 v2]
  (reduce + (map * v1 v2)))

(defn vect [v1 v2]
  (vector
    (- (* (v1 1) (v2 2)) (* (v2 1) (v1 2)))
    (- (* (v2 0) (v1 2)) (* (v1 0) (v2 2)))
    (- (* (v1 0) (v2 1)) (* (v2 0) (v1 1)))))

(defn v*s [v scalar]
  (mapv #( * scalar %) v))

(defn m+ [m1 m2]
  (mapv v+ m1 m2))

(defn m- [m1 m2]
  (mapv v- m1 m2))

(defn m* [m1 m2]
  (mapv v* m1 m2))

(defn md [m1 m2]
  (mapv vd m1 m2))

(defn m*s [matrix scalar]
  (mapv #(v*s % scalar) matrix))

(defn m*v [matrix vector]
  (mapv #(scalar % vector) matrix))

(defn transpose [matrix]
  (apply mapv vector matrix))

(defn m*m [m1 m2]
  (let [trans-m2 (transpose m2)]
    (mapv #(mapv (partial scalar %) trans-m2) m1)))

(defn c+ [c1 c2]
  (mapv #(mapv v+ %1 %2) c1 c2))

(defn c- [c1 c2]
  (mapv #(mapv v- %1 %2) c1 c2))

(defn c* [c1 c2]
  (mapv #(mapv v* %1 %2) c1 c2))

(defn cd [c1 c2]
  (mapv #(mapv vd %1 %2) c1 c2))
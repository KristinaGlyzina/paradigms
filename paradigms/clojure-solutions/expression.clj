(defn constant [c]
  (fn [_] c))

(defn variable [v]
  (fn [env] (get env v)))

(defn op [operation] (fn [a b]
                       (fn [env] (operation (a env) (b env)))))

(defn un_op [operation]
  (fn [a]
    (fn [env] (operation (a env)))))

(def add (op +))
(def subtract (op -))
(def multiply (op *))
(def subtract (op -))

(defn divide [a b]
  (fn [env] (/ (a env) (double (b env)))))

(def negate (un_op -))

(def sin (un_op (fn [x] (double (Math/sin x)))))

(def cos (un_op (fn [x] (double (Math/cos x)))))

(def operations {'+      add
                 '-      subtract
                 '*      multiply
                 '/      divide
                 'sin    sin
                 'cos    cos
                 'negate negate})
(def variables {'x (variable "x"), 'y (variable "y"), 'z (variable "z")})

(defn parseReadString [expr]
  (cond
    (number? expr) (constant expr)
    (contains? variables expr) (variables expr)
    :else (apply (get operations (first expr)) (map parseReadString (rest expr))))
  )
(defn parseFunction [expr] (parseReadString (read-string expr)))

(definterface Interface
  (toString [])
  (evaluate [maps])
  )

(deftype VariableClass [argument]
  Interface
  (toString [this] (.-argument this))
  (evaluate [this maps] (maps (.-argument this))))

(deftype AddClass [first second]
  Interface
  (toString [this] (str "(" '+ " " (.toString (.-first this)) " " (.toString (.-second this)) ")"))
  (evaluate [this maps] (+ (.evaluate (.-first this) maps) (.evaluate (.-second this) maps))))

(deftype SubtractClass [first second]
  Interface
  (toString [this] (str "(" '- " " (.toString (.-first this)) " " (.toString (.-second this)) ")"))
  (evaluate [this maps] (- (.evaluate (.-first this) maps) (.evaluate (.-second this) maps))))

(deftype DivideClass [first second]
  Interface
  (toString [this] (str "(" '/ " " (.toString (.-first this)) " " (.toString (.-second this)) ")"))
  (evaluate [this maps] (/ (.evaluate (.-first this) maps) (double (.evaluate (.-second this) maps)))))

(deftype MultiplyClass [first second]
  Interface
  (toString [this] (str "(" '* " " (.toString (.-first this)) " " (.toString (.-second this)) ")"))
  (evaluate [this maps] (* (.evaluate (.-first this) maps) (.evaluate (.-second this) maps))))

(deftype NegateClass [argument]
  Interface
  (toString [this] (str "(" 'negate " " (.toString (.-argument this)) ")"))
  (evaluate [this maps] (- (.evaluate (.-argument this) maps))))

(deftype ExpClass [argument]
  Interface
  (toString [this] (str "(" 'exp " " (.toString (.-argument this)) ")"))
  (evaluate [this maps] (Math/exp (.evaluate (.-argument this) maps))))

(deftype LnClass [argument]
  Interface
  (toString [this] (str "(" 'ln " " (.toString (.-argument this)) ")"))
  (evaluate [this maps] (Math/log (Math/abs (double (.evaluate (.-argument this) maps))))))

(deftype ConstClass [val]
  Interface
  (toString [this] (str (.-val this)))
  (evaluate [this _] (.-val this)))

(defn evaluate [arg maps]
  (.evaluate arg maps))
(defn toString [this]
  (.toString this))

(defn Variable [argument]
  (VariableClass. argument))

(defn Negate [argument]
  (NegateClass. argument))

(defn Constant [argument]
  (ConstClass. argument))

(defn Add [first second]
  (AddClass. first second))

(defn Subtract [first second]
  (SubtractClass. first second))

(defn Divide [first second]
  (DivideClass. first second))

(defn Multiply [first second]
  (MultiplyClass. first second))

(defn Exp [argument]
  (ExpClass. argument))

(defn Ln [argument]
  (LnClass. argument))

(def operations {'+      Add
                 '-      Subtract
                 '*      Multiply
                 '/      Divide
                 'exp    Exp
                 'ln     Ln
                 'negate Negate})

(defn parseObject [expr]
  (letfn [(parseExpression [expr]
            (cond
              (number? expr) (Constant expr)
              (symbol? expr) (Variable (str expr))
              :else (let [op (first expr)
                          args (rest expr)
                          operation (get operations op)]
                      (apply operation (map parseExpression args)))))]
    (parseExpression (read-string expr))))
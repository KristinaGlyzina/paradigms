"use strict";

const Const = function (value) {
    this.value = value;
}

Const.prototype.evaluate = function () {
    return this.value;
}

Const.prototype.toString = function () {
    return String(this.value);
}

Const.prototype.prefix = function () {
    return String(this.value);
}

const Variables = ["x", "y", "z"]
const Variable = function (variable) {
    this.variable = variable;
}

Variable.prototype.evaluate = function (...args) {
    return args[Variables.indexOf(this.variable)];
}

Variable.prototype.toString = function () {
    return this.variable;
}

Variable.prototype.prefix = function () {
    return this.variable;
}

function AbstractOperation(operation, sign, ...values) {
    this.operation = operation;
    this.sign = sign;
    this.values = values;
}

AbstractOperation.prototype.evaluate = function (...args) {
    return this.operation(...this.values.map(val => 
val.evaluate(...args)));
}

AbstractOperation.prototype.toString = function () {
    return this.values.map(val => val.toString()).join(' ') + ' ' + 
this.sign;
}

AbstractOperation.prototype.prefix = function () {
    return '(' + this.sign + ' ' + this.values.map(val => 
val.prefix()).join(' ') + ')';
}

const Operations = new Map([]);

function createOperation(operation, sign) {
    const Function = function (...values) {
        return new AbstractOperation(operation, sign, ...values)
    }
    Operations.set(sign, [operation.length, Function])
    return Function
}

const Add = createOperation((left, right) => left + right, "+");
const Subtract = createOperation((left, right) => left - right, "-");
const Multiply = createOperation((left, right) => left * right, "*");
const Divide = createOperation((left, right) => left / right, "/");
const Negate = createOperation(argument => -argument, "negate");
const Sin = createOperation(Math.sin, "sin");
const Cos = createOperation(Math.cos, "cos");

const Mean = createOperation((...args) => {
    const sum = args.reduce((acc, val) => acc + val, 0);
    return sum / args.length;
}, "mean");
const Var = createOperation((...args) => {
    function mean(...args) {
        const sum = args.reduce((acc, val) => acc + val, 0);
        return sum / args.length;
    }

    const meanVal = mean(...args);
    const sum = args.reduce((acc, val) => acc + (val - meanVal) ** 2, 0);
    return sum / args.length;
}, "var");

const parse = function (str) {
    const stack = [];
    const elements = str.trim().split(/\s+/g);

    for (const el of elements) {
        if (!isNaN(el)) {
            stack.push(new Const(Number(el)));
        } else if (Variables.includes(el)) {
            stack.push(new Variable(el));
        } else if (Operations.has(el) && Operations.get(el)[0] === 2) {
            const right = stack.pop();
            const left = stack.pop();
            stack.push(new (Operations.get(el)[1])(left, right));
        } else if (Operations.has(el) && Operations.get(el)[0] === 1) {
            const right = stack.pop();
            stack.push(new (Operations.get(el)[1])(right));
        }
    }
    return stack.pop();
}

const parsePrefix = function (str) {
    const stack = [];
    let i = 0;
    let elements = [];
    let balance = 0;
    str = str.replaceAll("(", " ( ")
    str = str.replaceAll(")", " ) ")
    elements = str.match(/(\-?\d+|\(|\)|[*+\-/|]|\S+)/g);

    const getExpression = function () {
        while (i < elements.length) {
            let el = elements[i];
            i++;
            if (el === '(') {
                balance++;
                let signs = elements[i++];
                if (!Operations.has(signs)) {
                    throw new Error("Expression hasn't the sign.")
                }
                const arity = Operations.get(signs)[0];
                const operands = [];
                if (arity === 0) {
                    while (el !== ')') {
                        let getVal = getExpression();
                        if (getVal === undefined) {
                            throw new Error("Undefined element.");
                        }
                        operands.push(getVal);
                        el = elements[i];
                    }
                }
                for (let j = 0; j < arity; j++) {
                    let getVal = getExpression();
                    if (getVal === undefined) {
                        throw new Error("Undefined element.");
                    }
                    operands.push(getVal);
                }
                el = elements[i++];
                if (el !== ")") {
                    throw new Error("Wrong brackets sequence.");
                }
                balance--;
                if (balance < 0) {
                    throw new Error("Count of closing brackets is more than opening brackets.");
                }
                return (Operations.get(signs)[1](...operands))
            } else if (!isNaN(parseInt(el))) {
                if (parseInt(el).toString() !== el) {
                    throw new Error("Element isn't a number.")
                }
                return new Const(Number(el));
            } else if (Variables.includes(el)) {
                return new Variable(el);
            } else {
                throw new Error("Unknown symbol.")
            }
        }
        return stack.pop();
    }
    const result = getExpression(str);
    if (balance !== 0) {
        throw new Error("Error count of brackets");
    }
    if (i !== elements.length) {
        throw new Error("Invalid count of arguments");
    }
    return result;
}

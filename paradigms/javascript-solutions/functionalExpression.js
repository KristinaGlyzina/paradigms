"use strict";

// ниже все еще неисправленные замечания

// :NOTE: функции в стрелочной нотации
// :NOTE: x,y,z -> args

function variable(variable) {
    return (x, y, z) => {
        switch (variable) {
            case 'x':
                return x;
            case 'y':
                return y;
            case 'z':
                return z;
        }
    };
}

function binaryOperation(operation) {
    return function (l, r) {
        return function (x, y, z) {
            return operation(l(x, y, z), r(x, y, z));
        }
    }
}

const unaryOperation = (operation) => (argument) => (x, y, z) => operation(argument(x, y, z));

const cnst = (value) => () => value;
const add = binaryOperation((left, right) => left + right);
const subtract = binaryOperation((left, right) => left - right);
const multiply = binaryOperation((left, right) => left * right);
const divide = binaryOperation((left, right) => left / right);
const negate = unaryOperation(argument => -argument);
const pi = cnst(Math.PI);
const e = cnst(Math.E);
const square = unaryOperation(argument => argument * argument);
const sqrt = unaryOperation(argument => Math.sqrt(Math.abs(argument)));

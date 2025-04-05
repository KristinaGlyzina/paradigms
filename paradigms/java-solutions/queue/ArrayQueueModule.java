package queue;

import java.util.Objects;
import java.util.function.Predicate;

/*
Model : a[0]...a[n]
Invariant: for i=0...n: a[i] != null;

Let immutable(n): for i=0...n: a'[i] == a[i]

Pred: element != null;
Post: n' = n + 1 && a[n'] == element && immutable(n);
enqueue(element);

Pred: n > 0;
Post: n' = a[0] && immutable(n') && R = a[n];
dequeue();

Pred: n > 0;
Post: immutable(n') && R = a[n] && n' == n;
element();

Pred: true;
Post: immutable(n) && R == n && n' == n;
size();

Pred: true;
Post: immutable(n) && R == (n == 0) && n' == n;
isEmpty();

Pred: true;
Post: R == (n = 0);
clear(element);

Pred: element != null;
Post: For all "i" at count += predicate.test(elements[i]);
countIf(predicate);
 */

public class ArrayQueueModule {
    private static Object[] elements = new Object[3];
    private static int head = 0;
    private static int tail = 0;
    private static int size = 0;


    public static Object enqueue(final Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(size);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
        size++;
        return element;
    }

    private static void ensureCapacity(int size) {
        if (elements.length < size + 1) {
            Object[] longerQueue = new Object[elements.length * 2];
            System.arraycopy(elements, head, longerQueue, 0, elements.length - head);
            if (head <= tail) {
                System.arraycopy(elements, 0, longerQueue, elements.length - head, tail);
            }
            elements = longerQueue;
            head = 0;
            tail = size;
        }
    }

    public static Object dequeue() {
        assert size > 0;
        Object element = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return element;
    }

    public static Object element() {
        return elements[head];
    }

    public static int size() {
        return size;
    }

    public static boolean isEmpty() {
        return size == 0;
    }

    public static void clear() {
        elements = new Object[3];
        head = 0;
        tail = 0;
        size = 0;
    }


    public static int countIf(Predicate<Object> predicate) {
        int count = 0;
        for (Object obj : elements) {
            if (predicate.test(obj)) {
                count++;
            }
        }
        return count;
    }

    public static void print() {
        System.out.print("[ ");
        for (Object obj : elements) {
            System.out.print(obj);
            System.out.print(" ");
        }
        System.out.println("]");
    }
}
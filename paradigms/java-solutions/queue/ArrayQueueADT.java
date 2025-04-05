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
clear();

Pred: element != null;
Post: For all "i" at count += predicate.test(elements[i]);
countIf(queue, predicate);
 */

public class ArrayQueueADT {
    private Object[] elements = new Object[3];
    private int head = 0;
    private int tail = 0;
    private int size = 0;

    public ArrayQueueADT() {
    }

    public static void enqueue(final ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(queue, queue.size);
        queue.elements[queue.tail] = element;
        queue.tail = (queue.tail + 1) % queue.elements.length;
        queue.size++;
    }

    private static void ensureCapacity(final ArrayQueueADT queue, int size) {
        Objects.requireNonNull(queue);
        if (queue.elements.length < size + 1) {
            Object[] longerQueue = new Object[queue.elements.length * 2];
            System.arraycopy(queue.elements, queue.head, longerQueue, 0, queue.elements.length - queue.head);
            if (queue.head <= queue.tail) {
                System.arraycopy(queue.elements, 0, longerQueue, queue.elements.length - queue.head, queue.tail);
            }
            queue.elements = longerQueue;
            queue.head = 0;
            queue.tail = queue.size;
        }
    }

    public static Object dequeue(final ArrayQueueADT queue) {
        assert queue.size > 0;
        Object element = queue.elements[queue.head];
        queue.elements[queue.head] = null;

        queue.head = (queue.head + 1) % queue.elements.length;
        queue.size--;
        return element;
    }

    public static Object element(final ArrayQueueADT queue) {
        return queue.elements[queue.head];
    }

    public static int size(final ArrayQueueADT queue) {
        return queue.size;
    }

    public static boolean isEmpty(final ArrayQueueADT queue) {
        return queue.size == 0;
    }

    public static void clear(final ArrayQueueADT queue) {
        queue.elements = new Object[3];
        queue.head = 0;
        queue.tail = 0;
        queue.size = 0;
    }

    public static int countIf(final ArrayQueueADT queue, Predicate<Object> predicate) {
        int count = 0;
        for (Object obj : queue.elements) {
            if (predicate.test(obj)) {
                count++;
            }
        }
        return count;
    }

    public void print(final ArrayQueueADT queue) {
        System.out.print("[ ");
        for (Object obj : elements) {
            System.out.print(obj);
            System.out.print(" ");
        }
        System.out.println("]");
    }
}
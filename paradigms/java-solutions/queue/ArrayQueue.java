package queue;

import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[3];
    private int head = 0;
    private int tail = 0;

    public void enqueueImpl(Object element) {
        ensureCapacity(size);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
    }

    private void ensureCapacity(int size) {
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

    public Object dequeueImpl() {
        Object element = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        return element;
    }

    public Object element() {
        return elements[head];
    }

    public void clear() {
        elements = new Object[3];
        head = 0;
        tail = 0;
        size = 0;
    }

    public int countIf(Predicate<Object> predicate) {
        int count = 0;
        for (Object obj : elements) {
            if (predicate.test(obj)) {
                count++;
            }
        }
        return count;
    }

    public void dedup() {
        int constSize = size;
        Object element_last = dequeue();
        enqueue(element_last);
        for (int i = 0; i < constSize - 1; i++) {
            Object element = dequeue();
            if (!element_last.equals(element)) {
                enqueue(element);
            }
            element_last = element;
        }
    }

    public void print() {
        System.out.print("[ ");
        for (Object obj : elements) {
            System.out.print(obj);
            System.out.print(" ");
        }
        System.out.println("]");
    }
}
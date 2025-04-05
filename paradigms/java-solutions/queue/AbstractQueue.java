package queue;

public abstract class AbstractQueue implements Queue {
    protected int size;

    public void enqueue(Object element) {
        enqueueImpl(element);
        size++;
    }

    public abstract void enqueueImpl(Object element);

    public Object dequeue() {
        Object element = dequeueImpl();
        size--;
        return element;
    }

    public int size() {
        return size;
    }

    public abstract Object dequeueImpl();

    public abstract Object element();

    public boolean isEmpty() {
        return size == 0;
    }

    public abstract void clear();

    public abstract void print();

    public abstract void dedup();
}
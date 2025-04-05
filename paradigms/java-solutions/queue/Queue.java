package queue;

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

Pred: size > 0;
Post: For all: i=1...n - 1: a[i] != a[i+1] &&
a[i], a[i+1] -> a[i'] == a[i] && a[i'+1] == a[i+1] && size' <= size;
dedup();
 */
public interface Queue {
    void enqueue(Object element);

    Object dequeue();

    Object element();

    boolean isEmpty();

    void clear();

    void print();

    void dedup();
}
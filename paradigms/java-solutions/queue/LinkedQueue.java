package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head;

    public void enqueueImpl(Object element) {
        Node newNode = new Node(element);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public Object dequeueImpl() {
        Object element = head.element;
        head = head.next;
        return element;
    }

    public Object element() {
        return head.element;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    public void dedup() {
        if (head == null || head.next == null) {
            return;
        }
        Node current = head;
        while (current.next != null) {
            if (current.element.equals(current.next.element)) {
                current.next = current.next.next;
                size--;
            } else {
                current = current.next;
            }
        }
    }

    public void print() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node current = head;
        while (current != null) {
            sb.append(current.element);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    private static class Node {
        private final Object element;
        private Node next;

        public Node(Object element) {
            assert element != null;

            this.element = element;
            this.next = null;
        }
    }
}
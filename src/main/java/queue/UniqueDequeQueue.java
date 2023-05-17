package queue;

import java.util.ArrayDeque;

public class UniqueDequeQueue<E> extends ArrayDeque<E> {

    @Override
    public void addLast(E e) {
        if (!this.contains(e)) {
            super.addLast(e);
        }
    }

    @Override
    public boolean add(E e) {
        if (!this.contains(e)) {
            return super.add(e);
        }
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return this.add(e);
    }

    @Override
    public void addFirst(E e) {
        System.out.println("Can't add first element");
    }

    @Override
    public E pollLast() {
        System.out.println("Can't poll last element");
        return null;
    }
}

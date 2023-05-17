package queue;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

//We need to implement a queue with following property.
// If we are to add an object which is already in the queue (using equals semantic) nothing should happen.
// If there is still no such object in the queue (again, using equals semantic) then the object is
// added to the end of the queue. Rationale is that we do not want to have several same objects
// in the queue. This could be especially useful when objects are events to recalculate something
// and there is no sense to have several such events in the queue.

public class UniqueQueue<E> extends AbstractQueue<E> {

    private Object[] elements;
    private int head = 0;
    private int tail = 0;

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    public UniqueQueue() {
        elements = new Object[16];
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return tail - head;
    }

    public UniqueQueue(int numElements) {
        if (numElements < 1) {
            numElements = 1;
        } else if (numElements != Integer.MAX_VALUE) {
            numElements += 1;
        }

        elements = new Object[numElements];
    }

    public UniqueQueue(Collection<? extends E> c) {
        this(c.size());
        copyElements(c);
    }

    @Override
    public boolean add(E e) {
        return offer(e);
    }

    @Override
    public boolean offer(E e) {
        if (!Arrays.asList(elements).contains(e)) {
            if (e == null) {
                throw new NullPointerException();
            }

            final Object[] es = elements;
            es[tail] = e;
            if (++tail <= es.length) {
                grow(1);
            }

            return true;
        }

        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E poll() {
        final Object[] es = elements;
        final int h = head;
        E e = (E) es[h];
        if (e != null) {
            es[h] = null;
            head = inc(h, es.length);
        }
        return e;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peek() {
        return size() == 0 ? null : (E) elements[head];
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        final int s, needed;
        if ((needed = (s = size()) + c.size() + 1 - elements.length) > 0)
            grow(needed);
        copyElements(c);
        return size() > s;
    }

    static int inc(int i, int modulus) {
        if (++i >= modulus) i = 0;
        return i;
    }

    private void copyElements(Collection<? extends E> c) {
        c.forEach(this::offer);
    }

    private void grow(int needed) {
        // overflow-conscious code
        final int oldCapacity = elements.length;
        int newCapacity;
        // Double capacity if small; else grow by 50%
        int jump = (oldCapacity < 64) ? (oldCapacity + 2) : (oldCapacity >> 1);
        if (jump < needed
                || (newCapacity = (oldCapacity + jump)) - MAX_ARRAY_SIZE > 0)
            newCapacity = newCapacity(needed, jump);
        elements = Arrays.copyOf(elements, newCapacity);
    }

    /** Capacity calculation for edge conditions, especially overflow. */
    private int newCapacity(int needed, int jump) {
        final int oldCapacity = elements.length, minCapacity;
        if ((minCapacity = oldCapacity + needed) - MAX_ARRAY_SIZE > 0) {
            if (minCapacity < 0)
                throw new IllegalStateException("Sorry, queue too big");
            return Integer.MAX_VALUE;
        }
        if (needed > jump)
            return minCapacity;
        return (oldCapacity + jump - MAX_ARRAY_SIZE < 0)
                ? oldCapacity + jump
                : MAX_ARRAY_SIZE;
    }
}

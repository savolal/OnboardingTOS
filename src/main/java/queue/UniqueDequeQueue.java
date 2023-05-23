package queue;

import java.util.ArrayDeque;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UniqueDequeQueue<E> extends ArrayDeque<E> {
    private static final Logger LOGGER = Logger.getLogger(UniqueDequeQueue.class.getName());

    private boolean empty = true;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void addLast(E e) {
        try {
            lockWrite();
            if (!this.contains(e)) {
                synchronized (this) {
                    empty = false;
                    super.addLast(e);
                    notifyAll();
                }
            }
        } finally {
            unlockWrite();
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
    public E pollFirst() {
        synchronized (this) {
            if (this.size() == 0) {
                empty = true;
                while (empty) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        LOGGER.log(Level.WARNING, e.getMessage());
                    }
                }
            }
        }

        try {
            lockWrite();
            return super.pollFirst();
        } finally {
            unlockWrite();
        }
    }

    @Override
    public E peekLast() {
        try {
            lockRead();
            return super.peekLast();
        } finally {
            unlockRead();
        }
    }


    @Override
    public void addFirst(E e) {
        LOGGER.log(Level.INFO, "Can't add first element: " + e);
    }

    @Override
    public E pollLast() {
        LOGGER.log(Level.INFO, "Can't poll last element");
        return null;
    }

    public void lockWrite() {
        lock.writeLock().lock();
    }

    public void unlockWrite() {
        lock.writeLock().unlock();
    }

    public void lockRead() {
        lock.readLock().lock();
    }

    public void unlockRead() {
        lock.readLock().unlock();
    }
}

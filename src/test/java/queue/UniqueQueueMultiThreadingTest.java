package queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UniqueQueueMultiThreadingTest {

    UniqueDequeQueue<String> queue = new UniqueDequeQueue<>();

    @Test
    public void testUniqueQueue_multiThread() throws InterruptedException {
        Thread writer1 = new Thread(() -> {
            System.out.println("Writer1 started");
            assertTrue(queue.add("cool"));
            assertTrue(queue.add("fun"));
            assertTrue(queue.add("smile"));
            assertFalse(queue.add("cool"));
            assertTrue(queue.add("1"));
        });
        writer1.start();

        Thread.sleep(500);
        assertEquals(4, queue.size());

        Thread reader1 = new Thread(() -> {
            System.out.println("Reader1 started");
            assertEquals("cool", queue.poll());
            assertEquals("fun", queue.poll());
            assertEquals("smile", queue.poll());
            assertEquals("1", queue.poll());
        });
        reader1.start();

        Thread.sleep(500);
        assertEquals(0, queue.size());

        Thread reader2 = new Thread(() -> {
            System.out.println("Reader2 started");
            assertEquals("cool", queue.poll());
        });
        reader2.start();
        Thread.sleep(300);
        assertEquals(0, queue.size());

        Thread writer2 = new Thread(() -> {
            System.out.println("Writer2 started");
            assertTrue(queue.add("cool"));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertTrue(queue.add("fun"));
            assertTrue(queue.add("smile"));
        });


        Thread writer3 = new Thread(() -> {
            System.out.println("Writer3 started");
            try {
                Thread.sleep(200);
                assertTrue(queue.add("cool"));
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertTrue(queue.add("1"));
        });
        writer2.start();
        writer3.start();

        Thread.sleep(1500);
        assertEquals(4, queue.size());
        assertEquals("cool", queue.poll());
        assertEquals("fun", queue.poll());
        assertEquals("smile", queue.poll());
        assertEquals("1", queue.poll());

    }
}

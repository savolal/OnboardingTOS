package queue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UniqueQueueTest {
    @Test
    public void testUniqueQueue() {
        UniqueQueue<String> stringUniqueQueue = new UniqueQueue<>();
        assertEquals(0, stringUniqueQueue.size());

        assertTrue(stringUniqueQueue.add("cool"));
        assertTrue(stringUniqueQueue.add("fun"));
        assertTrue(stringUniqueQueue.add("smile"));
        assertFalse(stringUniqueQueue.add("cool"));
        assertTrue(stringUniqueQueue.add("1"));

        assertEquals(4, stringUniqueQueue.size());
        assertEquals("cool", stringUniqueQueue.poll());

        stringUniqueQueue.addAll(Arrays.asList("cool", "fun", "sun", "2"));

        assertEquals(6, stringUniqueQueue.size());
        assertEquals("fun", stringUniqueQueue.peek());

        assertEquals("fun", stringUniqueQueue.poll());
        assertEquals("smile", stringUniqueQueue.poll());
        assertEquals("1", stringUniqueQueue.poll());
        assertEquals("cool", stringUniqueQueue.poll());
        assertEquals("sun", stringUniqueQueue.poll());
        assertEquals("2", stringUniqueQueue.poll());

        assertNull(stringUniqueQueue.poll());
        assertEquals(0, stringUniqueQueue.size());
    }

    @Test
    public void testUniqueDequeQueue() {
        UniqueDequeQueue<String> stringUniqueQueue = new UniqueDequeQueue<>();
        assertEquals(0, stringUniqueQueue.size());

        assertTrue(stringUniqueQueue.add("cool"));
        assertTrue(stringUniqueQueue.add("fun"));
        assertTrue(stringUniqueQueue.add("smile"));
        assertFalse(stringUniqueQueue.add("cool"));
        assertTrue(stringUniqueQueue.add("1"));

        assertEquals(4, stringUniqueQueue.size());
        assertEquals("cool", stringUniqueQueue.poll());

        stringUniqueQueue.addAll(Arrays.asList("cool", "fun", "sun", "2"));

        assertEquals(6, stringUniqueQueue.size());
        assertEquals("fun", stringUniqueQueue.peek());

        assertEquals("fun", stringUniqueQueue.poll());
        stringUniqueQueue.pollLast();

        assertEquals("smile", stringUniqueQueue.poll());
        assertEquals("1", stringUniqueQueue.poll());
        assertEquals("cool", stringUniqueQueue.poll());
        assertEquals("sun", stringUniqueQueue.poll());
        assertEquals("2", stringUniqueQueue.poll());

        assertNull(stringUniqueQueue.poll());
        assertEquals(0, stringUniqueQueue.size());

        stringUniqueQueue.push("e");

    }
}

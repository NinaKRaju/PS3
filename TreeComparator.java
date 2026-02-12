import net.datastructures.EmptyPriorityQueueException;
import net.datastructures.Entry;
import net.datastructures.InvalidKeyException;
import net.datastructures.PriorityQueue;

import java.util.Comparator;

public class TreeComparator implements PriorityQueue, Comparator {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Entry min() throws EmptyPriorityQueueException {
        return null;
    }

    @Override
    public Entry insert(Object o, Object o2) throws InvalidKeyException {
        return null;
    }

    @Override
    public Entry removeMin() throws EmptyPriorityQueueException {
        return null;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}

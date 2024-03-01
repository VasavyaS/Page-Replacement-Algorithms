package assignment3;

import java.util.Queue;

public class LRUAlgorithm implements PageReplacementAlgorithm{
    @Override
    public String getName() {
        return "LRUAlgorithm";
    }

    @Override
    public FreePage evictPage(Queue<FreePage> freePages) {
        FreePage evictPage = null;
        long leastReferencedAt = Long.MAX_VALUE;

        for (FreePage page : freePages) {
            if ((page.getReferencedAt() >- 1) && page.getReferencedAt() < leastReferencedAt) {
                evictPage = page;
                leastReferencedAt = page.getReferencedAt();
            }

        }
        return evictPage;
    }
}

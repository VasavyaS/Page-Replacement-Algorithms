package assignment3;

import java.util.Queue;

public class FIFOAlgorithm  implements  PageReplacementAlgorithm{

    @Override
    public String getName() {
        return "FIFOAlgorithm";
    }

    @Override
    public FreePage evictPage(Queue<FreePage> freePages) {
        FreePage evictPage = null;
        long earliestArrivalTime = Long.MAX_VALUE;

        for (FreePage page : freePages) {
            if ((page.getAllocatedAt()>-1) && page.getAllocatedAt() < earliestArrivalTime) {
                evictPage = page;
                earliestArrivalTime = page.getAllocatedAt();
            }

        }
        return evictPage;
    }
}


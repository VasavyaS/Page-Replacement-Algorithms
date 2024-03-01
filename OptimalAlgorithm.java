package assignment3;

import java.util.*;

public class OptimalAlgorithm implements PageReplacementAlgorithm {
    @Override
    public String getName() {
        return "OptimalAlgorithm";
    }

    @Override
    public FreePage evictPage(Queue<FreePage> freePages) {
        FreePage evictPage = null;
        long leastFrequent = Long.MAX_VALUE;

        for (FreePage page : freePages) {
            if (page.getFrequency()>0 && page.getFrequency() < leastFrequent) {
                evictPage = page;
                leastFrequent = page.getFrequency();
            }

        }
        System.out.println("Evicting page is  " +  evictPage);
        return evictPage;
    }
    }

//    @Override
//    public FreePage evictPage(Queue<FreePage> freePages) {
//        FreePage evictPage = null;
//        int minFutureIndex = Integer.MAX_VALUE;
//
//        HashMap<Integer, Integer> futureIndexMap = new HashMap<>();
//        for (FreePage page : freePages) {
//            int futureIndex = getFutureIndex(page, freePages);
//            futureIndexMap.put(page.getPageNumber(), futureIndex);
//        }
//
//        for (FreePage page : freePages) {
//            int futureIndex = futureIndexMap.get(page.getPageNumber());
//            if (futureIndex == -1) {
//                return page;
//            }
//
//            if (futureIndex < minFutureIndex) {
//                minFutureIndex = futureIndex;
//                evictPage = page;
//            }
//        }
//
//        return evictPage;
//    }
//
//    private int getFutureIndex(FreePage page, Queue<FreePage> freePages) {
//        long currentReferenceTime = page.getReferencedAt();
//        List<Long> futureReferences = new ArrayList<>();
//
//        // Populate futureReferences with the remaining page reference times
//        for (FreePage p : freePages) {
//            if (p.getReferencedAt() > currentReferenceTime) {
//                futureReferences.add(p.getReferencedAt());
//            }
//        }
//
//        if (futureReferences.isEmpty()) {
//            return -1; // Page will not be referenced in the future
//        }
//
//        // Find the minimum future index among the remaining references
//        int minFutureIndex = Integer.MAX_VALUE;
//        long minReferenceTime = Long.MIN_VALUE;
//        for (int i = 0; i < futureReferences.size(); i++) {
//            if (futureReferences.get(i) > minReferenceTime) {
//                minReferenceTime = futureReferences.get(i);
//                minFutureIndex = i;
//            }
//        }
//        return minFutureIndex;
//    }



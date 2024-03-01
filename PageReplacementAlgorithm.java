package assignment3;

import java.util.Queue;

interface PageReplacementAlgorithm {

    String getName();
     FreePage evictPage(Queue<FreePage> freePages);
}

package assignment3;

import java.util.*;

import static assignment3.Simulator.timeStamp;

public class Process {
    FreePageHelper freePageHelper = new FreePageHelper();
    private final String name;
    private final int size;
    private final long duration;
    private final int arrivalTime;
    private final List<Integer> pageReferences;

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    private List<Page> pages;

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public void setMissCount(int missCount) {
        this.missCount = missCount;
    }

    private int hitCount;
    private int missCount;


    public Process(String name, int size, long duration, int arrivalTime) {
        this.name = name;
        this.size = size;
        this.duration = duration;
        this.arrivalTime = arrivalTime;
        this.pageReferences = new ArrayList<>();
        this.pages = new ArrayList<>();
        this.hitCount = 0;
        this.missCount = 0;
        for (int i = 0; i < size; i++) {
            pages.add(new Page(i));
        }
        Collections.shuffle(pages, new Random());
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public long getDuration() {
        return duration;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getMissCount() {
        return missCount;
    }

    public List<Integer> getPageReferences() {
        return pageReferences;
    }

    public Page getPage(int index) {
        return pages.get(index);
    }

    public void addPageReference(int pageNumber) {
        pageReferences.add(pageNumber);
    }


    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    synchronized void pageIn(Page page,Process process,Queue<FreePage> freePages, int pageEvicted) {
        List<Page> pages = process.getPages();
        for (Page p : pages) {
            if (page.getPageNumber() == p.getPageNumber()) {

                FreePage nextFreePage = freePageHelper.getNextFreePage(freePages);
                if (nextFreePage != null) {
                    p.setInMemory(true);
                    p.setPageNumberOfMainMemory(nextFreePage.getPageNumber());
                    p.setPageAssignedTime(System.currentTimeMillis() + 1000);
//                    time-stamp in seconds, process Name, page-referenced, Page-in-memory, which process/page numbe
                    System.out.println(" Page In : TimeStamp: " + timeStamp + " Process: " + process.getName() +
                            " Page-referenced: " + page.getPageNumber() + " Page-in-memory: " + nextFreePage.getPageNumber() +
                            " Page evicted : " + pageEvicted);
                    System.out.println(freePageHelper.printMemoryMap(freePages));
                    nextFreePage.setProcessPageNumber(page.getPageNumber());
                    nextFreePage.setProcessAllocated(process.getName());
                    nextFreePage.setMemorySlotFree(false);
                    nextFreePage.setAllocatedAt(System.currentTimeMillis() + 1000);
                    nextFreePage.setFrequency(nextFreePage.getFrequency() + 1);
                    break;
                }
            }
        }
        process.setPages(pages);
    }

    synchronized void pageOut(FreePage freePage,Process process) {
        List<Page> pages = process.getPages();
        for(Page page : pages)
        {
            if(page.getPageNumberOfMainMemory() == freePage.getPageNumber() || page.getPageNumber() == freePage.getProcessPageNumber()){
                System.out.println(" Page Out :  TimeStamp: " + timeStamp + " Process: " + process.getName() +
                        " Page-referenced: " + page.getPageNumber() + " Page-in-memory: " + page.getPageNumberOfMainMemory() +
                        " Page evicted : " + page.getPageNumber());
                page.setInMemory(false);
                page.setPageNumberOfMainMemory(-1);
                page.setPageAssignedTime(-1);
                freePage.setAllocatedAt(-1);
                freePage.setProcessAllocated(".");
                freePage.setMemorySlotFree(true);
                freePage.setProcessPageNumber(-1);
                freePage.setFrequency(-1);
            }
        }
    }

    public void assignPages(List<FreePage> freePages,Process process) {
        List<Page> pages = process.getPages();
        int sizeOfFreePages = freePages.size();
        int index = 0;
        for (Page page : pages) {
            if ((index < sizeOfFreePages) && !page.isInMemory()) {
                page.setInMemory(true);
                page.setPageAssignedTime(System.currentTimeMillis() + 1000);
                FreePage freePage = freePages.get(index);
                freePage.setProcessPageNumber(page.getPageNumber());
                freePage.setReferencedAt(System.currentTimeMillis() + 1000);
                page.setPageNumberOfMainMemory(freePage.getPageNumber());
                index++;
            }
        }
    }

//    @Override
//    public String toString() {
//        return "Process [name = " + name +
//                ", size = " + size +
//                ", duration = " + duration +
//                ", arrivalTime = " + arrivalTime +
//                ", pageReferences = " + pageReferences +
//                ", pages = " + pages +
//                ", hitCount = " + hitCount +
//                ", missCount = " + missCount +
//                "]";
//    }

    @Override
    public String toString() {
        return "Process [name = " + name +
                ", size = " + size +
                ", duration = " + duration +
                ", arrivalTime = " + arrivalTime +
                "]";
    }

}



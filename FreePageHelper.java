package assignment3;

import java.util.Queue;

public class FreePageHelper {
    Simulator simulator = new Simulator();

    public  int getFreePagesLength(Queue<FreePage> freePageList){
        int count = 0;
        for ( FreePage freePage : freePageList){
            if(freePage.getProcessAllocated().equals(".") && freePage.isMemorySlotFree()){
                count++;
            }
        }
        return count;
    }


    public  FreePage getNextFreePage(Queue<FreePage> freePageList){
        for ( FreePage freePage : freePageList){
            if(freePage.getProcessAllocated().equals(".") && freePage.isMemorySlotFree()){
                freePage.setMemorySlotFree(false);
                return freePage;
            }
        }
        return null;
    }

    public void deallocateMemory(int pageNumber, Queue<FreePage> freePages){
        for(FreePage freePage : freePages){
            if(freePage.getPageNumber() == pageNumber){
                freePage.setProcessAllocated(".");
                freePage.setProcessPageNumber(-1);
                freePage.setAllocatedAt(-1);
                freePage.setMemorySlotFree(true);
                freePage.setFrequency(0);
                simulator.setFreePages(freePages);
            }
        }
    }

    public FreePage getPageByNumber(int pageNumber, Queue<FreePage> freePages){
        FreePage page = null ;
        for(FreePage freePage : freePages){
            if(freePage.getPageNumber() == pageNumber){
                page = freePage;
            }
        }
        return page;
    }

    public String printMemoryMap(Queue<FreePage> freePages) {
        String memoryMap = "";
            for (FreePage freePage : freePages) {
                memoryMap+=freePage.getProcessAllocated() + ":"+ freePage.getProcessPageNumber()+" ";
            }
        return memoryMap;
    }
}

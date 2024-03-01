package assignment3;

public class FreePage {
    String processAllocated;
    int processPageNumber;

    //check if particular memory slot is available
    boolean isMemorySlotFree;

    // process allocated at
    long allocatedAt;

    //pagenumber of the memory
    int pageNumber;

    long referencedAt;
    private int frequency;

    public FreePage(int pageNumber) {
        this.processAllocated = ".";
        this.processPageNumber = -1;
        this.isMemorySlotFree = true;
        this.allocatedAt = -1;
        this.pageNumber = pageNumber;
        this.referencedAt = -1;
        this.frequency = 0;
    }

    public String getProcessAllocated() {
        return processAllocated;
    }

    public void setProcessAllocated(String processAllocated) {
        this.processAllocated = processAllocated;
    }

    public int getProcessPageNumber() {
        return processPageNumber;
    }

    public void setProcessPageNumber(int processPageNumber) {
        this.processPageNumber = processPageNumber;
    }

    public boolean isMemorySlotFree() {
        return isMemorySlotFree;
    }

    public void setMemorySlotFree(boolean memorySlotFree) {
        isMemorySlotFree = memorySlotFree;
    }

    public long getAllocatedAt() {
        return allocatedAt;
    }

    public void setAllocatedAt(long allocatedAt) {
        this.allocatedAt = allocatedAt;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getReferencedAt() {
        return referencedAt;
    }

    public void setReferencedAt(long referencedAt) {
        this.referencedAt = referencedAt;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "FreePage{" +
                "processAllocated='" + processAllocated + '\'' +
                ", processPageNumber=" + processPageNumber +
                ", isMemorySlotFree=" + isMemorySlotFree +
                ", allocatedAt=" + allocatedAt +
                ", pageNumber=" + pageNumber +
                ", referencedAt=" +referencedAt +
                ", frequency=" + frequency +
                '}';
    }

}

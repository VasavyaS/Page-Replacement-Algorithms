package assignment3;

public class Page {
    private int pageNumber;
    private boolean inMemory;

    //page number of memory
    private int pageNumberOfMainMemory;




    //declare and store the processname in the freepage

    //time at which particular page got memory slot
    private long pageAssignedTime;

    public Page(int pageNumber) {
        this.pageNumber = pageNumber;
        this.inMemory = false;
        this.pageNumberOfMainMemory = -1;
        this.pageAssignedTime = -1;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public boolean isInMemory() {
        return inMemory;
    }

    public void setInMemory(boolean inMemory) {
        this.inMemory = inMemory;
    }


    public int getPageNumberOfMainMemory() {
        return pageNumberOfMainMemory;
    }

    public void setPageNumberOfMainMemory(int pageNumberOfMainMemory) {
        this.pageNumberOfMainMemory = pageNumberOfMainMemory;
    }

    public long getPageAssignedTime() {
        return pageAssignedTime;
    }

    public void setPageAssignedTime(long pageAssignedTime) {
        this.pageAssignedTime = pageAssignedTime;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNumber=" + pageNumber +
                ", inMemory=" + inMemory +
                ", processPageNumberInMemory=" + pageNumberOfMainMemory +
                ", pageAssignedTime=" + pageAssignedTime +
                '}';
    }

}




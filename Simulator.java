package assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Simulator {
    static int timeStamp;
    static List<Process> processes;
    static Queue<Process> jobQueue;
    static Queue<FreePage> freePages;
    static Random random;
    static int numberOfProcess;
    FreePageHelper freePageHelper = null;

    public static void main(String[] args) throws FileNotFoundException {
        PrintStream o = new PrintStream(new File("output.txt"));
        PrintStream console = System.out;

        // Assign o to output stream
        // using setOut() method
        System.setOut(o);

        Scanner scanner = new Scanner(System.in);
        List<PageReplacementAlgorithm> pageReplacementAlgorithmList = new ArrayList<>();
        pageReplacementAlgorithmList.add(new FIFOAlgorithm());
        pageReplacementAlgorithmList.add(new LRUAlgorithm());
        pageReplacementAlgorithmList.add(new RandomPickAlgorithm());
        pageReplacementAlgorithmList.add(new OptimalAlgorithm());
//        System.out.println(" Enter number of times each algorithm should run");
        int numberOfRuns = 5;
//        System.out.println(" Enter number of processes ");
        int numberOfProcess = 100;
//        System.out.println(" Enter size of memory ");
        int sizeOfMemory = 100;
        Simulator simulator = new Simulator();
        FreePageHelper freePageHelper = new FreePageHelper();
        String[] statistics = new String[pageReplacementAlgorithmList.size()];

        simulator.initialize(freePageHelper,sizeOfMemory,numberOfProcess);
        List<Process> initialProcessSet = simulator.generateProcesses();
        for (int k = 0; k < pageReplacementAlgorithmList.size(); k++) {
            String algorithmName = pageReplacementAlgorithmList.get(k).getName();
            System.out.println("######################################## Going to Run " + algorithmName + " PageReplacement algorithm ##########################");
            AtomicInteger hitCount = new AtomicInteger();
            AtomicInteger missCount = new AtomicInteger();
            int hits = 0 ;
            int misses = 0 ;
            List<Thread> threads = new ArrayList<>();
            for (int i = 0; i < numberOfRuns ; i++) {
                System.out.println("Size of Job queue " + jobQueue.size());
                while (!jobQueue.isEmpty()) {
                    Process process = jobQueue.peek();
                    if (simulator.assignMemory(process, simulator.getFreePages())) {
                        System.out.println(" Process " + process.getName() + " started Executing " + freePageHelper.printMemoryMap(freePages));
                        jobQueue.poll();
                        PageReplacementAlgorithm pageReplacementAlgorithm = pageReplacementAlgorithmList.get(k);
                        Thread thread = new Thread(() -> {
                            process.run();
                            simulator.simulateLocalityOfReference(process, pageReplacementAlgorithm);
                            hitCount.addAndGet(process.getHitCount());
                            missCount.addAndGet(process.getMissCount());
                        });
                        threads.add(thread);
                        thread.start();
                    }
                }
                // Wait for all threads to finish
                for (Thread thread : threads) {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Memory map after execution of run :"  + (i+1) + "  " +freePageHelper.printMemoryMap(simulator.getFreePages()));
//                System.out.printf(" Hit/Miss count " + hitCount + " /" + missCount);
                System.out.println(" \n" +"------------------------------------------------------------------------------------------------------------------");
//                System.out.println("Setting up the data for each run");
                jobQueue = new LinkedList<>(initialProcessSet);
                jobQueue = jobQueue.stream().sorted(Comparator.comparingInt(Process::getArrivalTime)).collect(Collectors.toCollection(LinkedList::new));

            }
           hits = hitCount.addAndGet(0);
           misses = missCount. addAndGet( 0 );
            System.out.println(" Execution of " + algorithmName + " is done ");
            System.out.println(" ************************************************** Statistics of " + algorithmName + " *********************************************");
            System.out.println("Hits and misses of all the runs " + hits + " " + misses);
            float missRatio = (float) misses/ (float)(hits+misses);
            float hitRatio = (float) hits/ (float)(hits+misses);
            System.out.println(" Average Hit ratio : " + hitRatio);
            System.out.println(" Average Miss ratio : " + missRatio);
            statistics[k]= algorithmName + " = " + " HitRatio : " +  hitRatio + " , MissRatio: " + missRatio;
        }
        System.out.println(" *************************************************************************************************************************************");
        System.out.println(" *************************************************************************************************************************************");
        System.out.println(" ******************************************** FINAL RESULTS **************************************************************");

        System.out.println(Arrays.toString(statistics));
        System.setOut(console);

        // Display message only
        System.out.println(
                "This will be written on the console!");
    }


    public Queue<FreePage> getFreePages() {
        return freePages;
    }

    public static void setFreePages(Queue<FreePage> freePages) {
        Simulator.freePages = freePages;
    }

    void initialize(FreePageHelper freePageHelper,int sizeOfMainMemory, int processCount) {
        this.freePageHelper = freePageHelper;
        random = new Random();
        timeStamp = 0;
        processes = new ArrayList<>();
        jobQueue = new LinkedList<>();
        freePages = new LinkedList<>();
        for (int i = 0; i < sizeOfMainMemory; i++) {
            FreePage page = new FreePage(i);
            freePages.offer(page);
        }
        numberOfProcess = processCount;
    }

    List<Process> generateProcesses() {
        int[] processSizes = {5, 11, 17, 31};
        for (int i = 0; i < numberOfProcess; i++) {
            int size = processSizes[random.nextInt(processSizes.length)];
            String name = "P" + i;
            int duration = 1 + random.nextInt(5);
            int arrivalTime = random.nextInt(numberOfProcess);
            processes.add(new Process(name, size, duration, arrivalTime));
            jobQueue.offer(processes.get(processes.size() - 1));
        }
        jobQueue = jobQueue.stream().sorted(Comparator.comparingInt(Process::getArrivalTime)).collect(Collectors.toCollection(LinkedList::new));
        System.out.println("#################################### Printing process ######################################### ");
        for( Process process : processes){
            System.out.println(process);
        }
        return processes;
    }

    boolean assignMemory(Process process, Queue<FreePage> freePages) {
        if (freePageHelper.getFreePagesLength(freePages) < 4) {
            return false;
        }
        List<FreePage> freePageList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            FreePage freePage = freePageHelper.getNextFreePage(getFreePages());
            if (freePage != null) {
                freePage.setMemorySlotFree(false);
                freePage.setProcessAllocated(process.getName());
                freePage.setAllocatedAt(System.currentTimeMillis() + 1000);
                freePage.setFrequency(freePage.getFrequency()+1);
                freePageList.add(freePage);
            }
        }
        process.assignPages(freePageList, process);
        return true;
    }

    void regainMemory(Process process) {
        for (Page page : process.getPages()) {
            int pageNumberOfMainMemory = page.getPageNumberOfMainMemory();
            page.setInMemory(false);
            page.setPageNumberOfMainMemory(-1);
            page.setPageAssignedTime(-1);
            freePageHelper.deallocateMemory(pageNumberOfMainMemory, getFreePages());
        }
    }

    void simulateLocalityOfReference(Process process, PageReplacementAlgorithm algorithm) {
        int i = 0;
        long durationRan = 0;
        long startOfExecution = 0;
        synchronized (this) {
            while (true) {
                if (startOfExecution + durationRan < process.getDuration()) {
                    int j = random.nextInt(process.getSize());
                    if (j < 7) {
                        i += random.nextInt(3) - 1;
                        i = (i + process.getSize()) % process.getSize();
                    } else {
                        j = (i <= 2 || i >= 8) ? random.nextInt(6) : random.nextInt(10) - 6;
                        i = (i + j + process.getSize()) % process.getSize();
                    }
                    if (process.getPages().size() <= i) {
                        i = process.getPages().size() - 1;
                    }
                    Page page = process.getPage(i);
                    process.addPageReference(page.getPageNumber());
                    System.out.println(" Process " + process.getName() + " referenced page " + page.getPageNumber() + " ");
                    System.out.println(freePageHelper.printMemoryMap(getFreePages()));

                    if (!page.isInMemory()) {
                         System.out.println(" Its a page fault");
                        pageFaultHandler(process, page, algorithm);
                        FreePage pageByNumber = freePageHelper.getPageByNumber(page.getPageNumberOfMainMemory(), getFreePages());
                        if(pageByNumber == null){
                            return ;
                        }
                        pageByNumber.setReferencedAt(System.currentTimeMillis()+1000);
                        pageByNumber.setFrequency(pageByNumber.getFrequency()+1);
                        int missCount = process.getMissCount();
                        missCount++;
                        process.setMissCount(missCount);
                    } else {
                         System.out.println(" Its a page hit");
                        FreePage pageByNumber = freePageHelper.getPageByNumber(page.getPageNumberOfMainMemory(), getFreePages());
                        if(pageByNumber == null){
                            return ;
                        }
                        pageByNumber.setReferencedAt(System.currentTimeMillis()+1000);
                        pageByNumber.setFrequency(pageByNumber.getFrequency()+1);
                        int hitCount = process.getHitCount();
                        hitCount++;
                        process.setHitCount(hitCount);
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    timeStamp++;
                } else {
                    System.out.println(" Timestamp: " + timeStamp + " " + " Process "  + process.getName() + " "  + " Status : Exit " + "sizes :" + process.getSize());
                    regainMemory(process);
                    break;
                }
                durationRan++;
            }
        }
    }

    synchronized void pageFaultHandler(Process process, Page page, PageReplacementAlgorithm algorithm) {
        synchronized (this) {
            if (freePageHelper.getFreePagesLength(getFreePages()) <= 0) {
                System.out.println(" Evicting page using " + algorithm.getName() + " as there are no free memory slots ");
                //get the evict page based on Page replacement algorithm
                FreePage evictPage = algorithm.evictPage(getFreePages());
                if(evictPage == null ){
                    System.out.println(" Memory is full");
                    return ;
                }
                System.out.println(" Evicting process " + process.getName() + "`s page number " + evictPage.getProcessPageNumber() + " from memory location : " + evictPage.getPageNumber()) ;
                int evictPageNumber = evictPage.getPageNumber();
                process.pageOut(evictPage, process);
                process.pageIn(page, process, getFreePages(),evictPageNumber);
            } else {
                process.pageIn(page, process, getFreePages(),-1);
            }
            freePageHelper.printMemoryMap(getFreePages());
        }
    }
}
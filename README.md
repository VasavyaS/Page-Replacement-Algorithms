Title: Implementation of various page replacement algorithms. Description:
● Starting with the process simulation, we generate 150 processes with all the needed details like Process Name, Process size in pages, arrival time, service duration using random number generator. All generated processes have evenly and randomly distributed sizes of 5, 11, 17, and 31 MB. Processes generated have randomly and evenly distributed service durations of 1, 2, 3, 4, or 5 seconds.
● We then generate the workload by creating a list of 100 slots representing the free memory. Each slot represents a size of 1 MB.
● The 150 processes generated are assigned to the job queue and sorted based on the arrival time. These processes then get serviced.
● A job at the head of the Job Queue is taken out and assigned memory to run if there are at least 4 free pages available. When a job runs, we need initially one page and then the process will make a memory reference to another page in that process and we need to page-in the referenced page from disk if the page is not already in memory.
● Each page uses locality of reference to reference the next page. If a page i references another page, there is a 70% probability that the next page will be i-1 or i+1.
● Each process currently in the memory runs for 100 msec and then pauses and makes another page reference using locality of reference. For each memory reference we collect statistics like time-stamp in seconds, process Name, page-referenced, Page-in-memory, process/page number that gets evicted.
● We run all the algorithms FIFO, LRU, Optimal page replacement and Random pick 5 times for each algorithm and print the hit ratios and miss ratios.

Process to run:
● Execute the following command to compile– Javac Simulator.java
● Torun–
Javac Simulator

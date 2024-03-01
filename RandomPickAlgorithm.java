package assignment3;

import java.util.Queue;
import java.util.Random;

public class RandomPickAlgorithm implements PageReplacementAlgorithm {

    @Override
    public String getName() {
        return "RandomPickAlgorithm";
    }
    public FreePage evictPage(Queue<FreePage> freePages) {

        Random random = new Random();
        FreePageHelper freePageHelper = new FreePageHelper();

        //get Nextfreepage number gives the first free page in freepages list
        FreePage nextFreePage = freePageHelper.getNextFreePage(freePages);
        if(nextFreePage != null) {
            int pageNumber = nextFreePage.getPageNumber();
            //In random.nextInt() parameter is the page number -1 ,
            // limiting the value generated by the following function will be less than first free page number location
            pageNumber = random.nextInt(pageNumber - 1);

            for (FreePage page : freePages) {
                if(page.getPageNumber() == pageNumber){
                    return page;
                }
            }
        }
       return freePages.element();
        }
    }


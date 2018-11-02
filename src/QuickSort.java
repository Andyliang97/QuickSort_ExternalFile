//import java.util.Stack;

/**
 * Quick Sort class
 * this is where the sort happens.
 * The quick sort algorithm is optimized.
 * It checks the duplicate before start sorting
 * the record.
 * @author Junjie Liang
 * @version 2018 Oct
 *
 */
public class QuickSort {
    
    private BufferPool bufferpool;
    /**
     * Constructor
     * @param poolSize the buffer pool size
     * @param filename the file needed to read from
     */
    public QuickSort(int poolSize, String filename) {
        bufferpool = new BufferPool(poolSize, filename);
    }
    
    /**
     * Process the sort and write the data back to file
     */
    public void sort() {
        this.quicksort(0, bufferpool.getRecordSize() - 1);
        this.putback();
    }
    
    /**
     * Get the statistics 
     * @return a container includes all the statistics
     */
    public int[] getStat() {
        int[] stat = new int[5];
        stat[0] = bufferpool.getFileSize();
        stat[1] = bufferpool.getBufferPoolSize();
        stat[2] = bufferpool.getCacheHit();
        stat[3] = bufferpool.getDiskRead();
        stat[4] = bufferpool.getDiskWrite();
        return stat;
        
    }
    
    /**
     * the quick sort algorithm
     * @param i position i
     * @param j position j
     */
    private void quicksort(int i, int j) { // Quicksort
        if (isSorted(i, j)) {
            return;
        }
        int pivotindex = findpivot(i, j);  // Pick a pivot
        bufferpool.swap(pivotindex, j);               // Stick pivot at end
        // k will be the first position in the right subarray
        int k = partition(i, j - 1, bufferpool.getKey(j));
        bufferpool.swap(k, j);                        // Put pivot in place
        if ((k - i) > 1) {
            quicksort(i, k - 1);  // Sort left partition
        }
        if ((j - k) > 1) {
            quicksort(k + 1, j);  // Sort right partition
        }
    }
    
    /**
     * check if the given range is sorted
     * @param i the starting position
     * @param j the ending position
     * @return true if the list was sorted, else return false
     */
    private boolean isSorted(int i, int j) {
        for (int n = i; n < j; n++) {
            if (bufferpool.getKey(n) > bufferpool.getKey(n + 1)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * perform partition on the given range
     * @param left the start position
     * @param right the end position
     * @param pivot the pivot key
     * @return the next start position
     */
    private int partition(int left, int right, short pivot) {
        while (left <= right) { // Move bounds inward until they meet
            while (bufferpool.getKey(left) < pivot) {
                left++;
            }
            while ((right >= left) && (bufferpool.getKey(right) >= pivot)) {
                right--;
            }
            if (right > left) {
                bufferpool.swap(left, right); // Swap out-of-place values
            }
        }
        return left;            // Return first position in right partition
    }
    
    /**
     * write the data back to file
     */
    private void putback() {
        bufferpool.clearBufferPool();
    }
    
    /**
     * file the pivot position
     * @param i the start position
     * @param j the end position
     * @return the pivot position
     */
    private int findpivot(int i, int j) { 
        return (i + j) / 2; 
    }
    
    
    /*
    public void quicksortloop(int i, int j) {
        Stack<int[]>  sortStack = new Stack<int[]>();
        int currentsort[] = new int[] {i, j, 0};
        sortStack.push(currentsort);
        
        while(!sortStack.isEmpty()) {
            currentsort = sortStack.pop();
            if (isSorted(currentsort[0], currentsort[1])) {
                continue;
            }
            int pivotindex = findpivot(currentsort[0], currentsort[1]);  
            bufferpool.swap(pivotindex, currentsort[1]);               
            int k = partition(currentsort[0], 
            currentsort[1]-1, bufferpool.getKey(currentsort[1]));
            bufferpool.swap(k, currentsort[1]);
            if ((currentsort[1] - k) > 1) {
                int newsort[] = new int[] {k+1, currentsort[1]};
                sortStack.push(newsort);    
            }
            if ((k-currentsort[0]) > 1) {
                int newsort[] = new int[] {currentsort[0], k-1};
                sortStack.push(newsort);
            }
        }
    }
    */
    
    
    
    /*
    
    private void insertionsort() {
        for (int i=1; i<bufferpool.getRecordSize(); i++){ // Insert i'th record
            for (int j=i; (j>0) && 
            (bufferpool.getKey(j)<bufferpool.getKey(j-1)); j--){
                bufferpool.swap(j, j-1);
            }
        }
    }
        
    private int findpivot(int i, int j)
    { 
        short first = bufferpool.getKey(i);
        short last = bufferpool.getKey(j);
        short med = bufferpool.getKey((i+j)/2);
        short[] tempArray = new short[] {first, last, med};
        this.inssort(tempArray);
        if (tempArray[1] == first) {
            return i;
        }
        else if (tempArray[1] == med) {
            return (i+j)/2;
        }
        else {
            return j;
        }
    }
    
    private void inssort(short[] tempArray) {
        for (int i=1; i<tempArray.length; i++) // Insert i'th record
          for (int j=i; (j>0) && (tempArray[j] < tempArray[j-1]); j--) {
            swap(tempArray, j, j-1);
          }
    }
    
    private void swap(short[] A, int i, int j) {
        short temp = A[j];
        A[j] = A[i];
        A[i] = temp;
    }
    */
    
}

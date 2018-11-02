import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Buffer pool class
 * It contains multiple buffer inside a buffer pool.
 * It handles all the request from outside
 * @author Junjie Liang
 * @version 2018 October
 *
 */
public class BufferPool {
    private RandomAccessFile raf;
    private int poolSize;
    private Buffer[] bytebufferPool;
    private int cachehit;
    private int diskread;
    private int diskwrite;
    private int length;
    private static final int BLOCK_SIZE = 4096;
    private static final int RECORD_SIZE = 4;
    
    /**
     * Bufferpool Constructor
     * It initialize the buffer pool size and the file
     * that that buffer pool can read from
     * @param poolSize the buffer pool size
     * @param filename the read-in file
     */
    public BufferPool(int poolSize, String filename) {
        cachehit = 0;
        diskread = 0;
        diskwrite = 0;
        this.poolSize = poolSize;
        bytebufferPool = new Buffer[poolSize];
        for (int i = 0; i < poolSize; i++) {
            bytebufferPool[i] = new Buffer(raf);
        }
        try {
            
            raf = new RandomAccessFile(filename, "rw");
            this.length = (int) raf.length();
            
        } 
        catch (Exception e) {
            System.out.println("Exception thrown  :" + e);
        }
        
        
    }
    
    /**
     * get the file size(how many bytes)
     * @return the byte size of the file
     */
    public int getLength() {
        return this.length;
    }
    
    /**
     * get the file size(how many blocks)
     * @return the block size of the file
     */
    public int getRecordSize() {
        return this.length / RECORD_SIZE;
    }
    
    /**
     * swap two records in the file
     * @param pos1 the position of record 1
     * @param pos2 the position of record 2
     */
    public void swap(int pos1, int pos2) {
        int blockNum1 = pos1 * RECORD_SIZE / BLOCK_SIZE;
        int blockNum2 = pos2 * RECORD_SIZE / BLOCK_SIZE;
        this.lru(blockNum1);
        byte[] tempByteArray = new byte[RECORD_SIZE];
        if (poolSize == 1 &&  blockNum1 != blockNum2) {
            byte[] tempByteArray2 = new byte[RECORD_SIZE];
            tempByteArray = bytebufferPool[0].getRecord(pos1 
                    * RECORD_SIZE - blockNum1 * BLOCK_SIZE, RECORD_SIZE);
            this.lru(blockNum2);
            tempByteArray2 = bytebufferPool[0].getRecord(pos2 
                    * RECORD_SIZE - blockNum2 * BLOCK_SIZE, RECORD_SIZE);
            bytebufferPool[0].writeRecord(tempByteArray, pos2 
                    * RECORD_SIZE - blockNum2 * BLOCK_SIZE, RECORD_SIZE);
            this.lru(blockNum1);
            bytebufferPool[0].writeRecord(tempByteArray2, pos1 
                    * RECORD_SIZE - blockNum1 * BLOCK_SIZE, RECORD_SIZE);
        }
        else if (blockNum1 == blockNum2) {
            tempByteArray = bytebufferPool[0].getRecord(pos1 
                    * RECORD_SIZE - blockNum1 * BLOCK_SIZE, RECORD_SIZE);
            bytebufferPool[0].writeRecord(bytebufferPool[0].getRecord(pos2 
                    * RECORD_SIZE - blockNum2 * BLOCK_SIZE, RECORD_SIZE), 
                    pos1 * RECORD_SIZE - blockNum1 * BLOCK_SIZE, RECORD_SIZE);
            bytebufferPool[0].writeRecord(tempByteArray, pos2 
                    * RECORD_SIZE - blockNum2 * BLOCK_SIZE, RECORD_SIZE);
        }
        else {
            this.lru(blockNum2);
            tempByteArray = bytebufferPool[1].getRecord(pos1 
                    * RECORD_SIZE - blockNum1 * BLOCK_SIZE, RECORD_SIZE);
            bytebufferPool[1].writeRecord(bytebufferPool[0].getRecord(pos2 
                    * RECORD_SIZE - blockNum2 * BLOCK_SIZE, RECORD_SIZE), 
                    pos1 * RECORD_SIZE - blockNum1 * BLOCK_SIZE, RECORD_SIZE);
            bytebufferPool[0].writeRecord(tempByteArray, pos2 
                    * RECORD_SIZE - blockNum2 * BLOCK_SIZE, RECORD_SIZE);
        }
            
        
    }
    
    /**
     * least recently used method
     * it reads in a parameter called blocknum,
     * which is the number of the block you need to use.
     * if the block exist, then it will bring it to the top
     * of the array, which is the 0 spot, and shift everything 
     * above it one block down. If the block cant be found and it
     * reaches to an empty spot,shift everything one block down
     * and bring up the block to the first spot.
     * If the for loop reaches the end of the array, discard the 
     * last buffer in the array and write everything back to file.
     * Then shift everything one block down and insert the new buffer
     * to the first spot.
     * @param blocknum
     */
    private void lru(int blocknum) {
        for (int i = 0; i < poolSize; i++) {
            if (bytebufferPool[i].getblockNum() == blocknum) {
                Buffer tempbuffer = bytebufferPool[i];
                for (int j = i; j > 0; j--) {
                    bytebufferPool[j] = bytebufferPool[j - 1];
                }
                bytebufferPool[0] = tempbuffer;
                cachehit++;
                return;
            }
            else if (bytebufferPool[i].isEmpty()) {
                if (i != 0) {
                    for (int j = i; j > 0; j--) {
                        bytebufferPool[j] = bytebufferPool[j - 1];
                    }
                }
                bytebufferPool[0] = new Buffer(this.raf, blocknum);
                diskread++;
                return;
            }
        }
        bytebufferPool[poolSize - 1].writetoFile();
        diskwrite++;
        for (int j = poolSize - 1; j > 0; j--) {
            bytebufferPool[j] = bytebufferPool[j - 1];
        }
        bytebufferPool[0] = new Buffer(this.raf, blocknum);
        diskread++;
    }
    
    /**
     * get the key of the record based on the give position
     * @param pos the position
     * @return the key value
     */
    public short getKey(int pos) {
        byte[] tempByteArray = new byte[RECORD_SIZE];
        byte[] tempArray = new byte[2];
        int blockNum = pos * RECORD_SIZE / BLOCK_SIZE; 
        this.lru(blockNum);
        tempByteArray = bytebufferPool[0].getRecord(pos * RECORD_SIZE 
                - blockNum * BLOCK_SIZE, RECORD_SIZE);
        tempArray[0] = tempByteArray[0];
        tempArray[1] = tempByteArray[1];
        return byteToShort(tempByteArray);
    }
    
    /**
     * Only used when buffer is no longer needed.
     * write everything in the buffer back to the file
     */
    public void clearBufferPool() {
        for (int i = 0; i < poolSize; i++) {
            if (!bytebufferPool[i].isEmpty()) {
                bytebufferPool[i].writetoFile();
                diskwrite++;
            }
        }
        try {
            raf.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * get number of cache hit
     * @return the number of cache hit
     */
    public int getCacheHit() {
        return cachehit;
    }
    
    /**
     * get number of disk write
     * @return the number of disk write
     */
    public int getDiskWrite() {
        return diskwrite;
    }
    
    /**
     * get number of disk read
     * @return the number of disk read
     */
    public int getDiskRead() {
        return diskread;
    }
    
    /**
     * get the file size in block
     * @return the block size of the file
     */
    public int getFileSize() {
        return this.getLength() / BLOCK_SIZE;
    }
    
    /**
     * get the buffer pool size
     * @return the buffer pool size
     */
    public int getBufferPoolSize() {
        return poolSize;
    }
    
    /**
     * convert byte number into short
     * @param b the byte number 
     * @return the given number in short
     */
    private static short byteToShort(byte[] b) { 
        short s = 0; 
        short s1 = (short) (b[1] & 0xff); // smallest digit
        short s0 = (short) (b[0] & 0xff); 
        s0 <<= 8; 
        s = (short) (s0 | s1); 
        return s; 
    }

}

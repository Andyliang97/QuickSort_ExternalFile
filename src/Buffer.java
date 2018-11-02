import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Buffer class
 * it stands for a single buffer in the memory.
 * It also has some info about the buffer including
 * the which block of information it store and 
 * the dirty bit.
 * @author Junjie Liang
 * @version 2018 Oct
 *
 */
public class Buffer {

    private RandomAccessFile raf;
    private ByteBuffer bytebuffer;
    private int blockNum;
    private int dirtybit;
    private static final int BLOCK_SIZE = 4096;
    private static final int RECORD_SIZE = 4;
    
    /**
     * Constructor
     * @param tempraf the given file that it 
     *                 needs to read from
     */
    public Buffer(RandomAccessFile tempraf) {
        blockNum = -1;
        dirtybit = 0;
        bytebuffer = ByteBuffer.allocate(BLOCK_SIZE);
        raf = tempraf;
    }
    
    /**
     * Constructor 
     * @param tempraf the given file that it needs
     *                     to read from
     * @param num the block it stands for
     */
    public Buffer(RandomAccessFile tempraf, int num) {
        blockNum = -1;
        dirtybit = 0;
        bytebuffer = ByteBuffer.allocate(BLOCK_SIZE);
        raf = tempraf;
        this.readfromFile(num);
    }
    
    /**
     * It reads certain block from the file based on the
     * given block number 
     * @param num the block it needs to read
     */
    public void readfromFile(int num) {
        byte[] b = new byte[BLOCK_SIZE];
        this.blockNum = num;
        try {
            raf.seek(num * BLOCK_SIZE);
            raf.read(b);
            bytebuffer = ByteBuffer.wrap(b);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * check if the buffer is empty
     * @return true if it's empty, else false
     */
    public boolean isEmpty() {
        return (blockNum == -1);
    }
    
    /**
     * get the block number it stands for
     * @return the block number
     */
    public int getblockNum() {
        return blockNum;
    }
    
    /**
     * get the whole buffer
     * @return the current byte buffer
     */
    public ByteBuffer getBuffer() {
        return bytebuffer;
    }
    
    /**
     * set the dirty bit to 1
     * meaning that something needs to
     * be wrote back to the file
     */
    private void getDirty() {
        dirtybit = 1;
    }
    
    /**
     * write the content in the buffer
     * back to the file
     */
    public void writetoFile() {
        if (dirtybit == 0) {
            return;
        }
        else {
            try {
                raf.seek(this.blockNum * BLOCK_SIZE);
                raf.write(bytebuffer.array());
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * get the record basd on the given position in
     * the buffer.
     * @param offset the starting postion
     * @param length the length of the record
     * @return the record, 4 byte data
     */
    public byte[] getRecord(int offset, int length) {
        byte[] tempArray = new byte[RECORD_SIZE];
        for (int i = 0; i < length; i++) {
            tempArray[i] = bytebuffer.get(offset + i);
        }
        return tempArray;
    }
    
    /**
     * modify the record in the buffer
     * @param src the new data
     * @param offset the starting position
     * @param length the length of the record
     */
    public void writeRecord(byte[] src, int offset, int length) {
        for (int i = 0; i < length; i++) {
            bytebuffer.put(offset + i, src[i]);
        }
        getDirty();
    }
}

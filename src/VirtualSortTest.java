import student.TestCase;
import java.io.IOException;
/**
 * @author Junjie Liang
 * @version 2018 Oct
 */

public class VirtualSortTest extends TestCase {
    private CheckFile fileChecker;

    /**
     * This method sets up the tests that follow.
     */
    public void setUp() {
        fileChecker = new CheckFile();
    }

    // ----------------------------------------------------------
    /**
     * Test initialization
     * @throws IOException 
     */
    public void testInit() throws IOException {
        VirtualSort mysort = new VirtualSort();
        assertNotNull(mysort);
        //VirtualSort.main(null);
        //assertFuzzyEquals("Hello, World", systemOut().getHistory());
    }
    
    /**
     * This method tests the main functionality of Quicksort on an "ascii" file
     *
     * @throws Exception
     *             either a IOException or FileNotFoundException
     */
    public void testQuicksortAscii()
        throws Exception
    {
        String[] args = new String[3];
        args[0] = "input.txt";
        args[1] = "4";
        args[2] = "statFileA.txt";

        VirtualSort.generateFile("input.txt", "1", 'a');
        VirtualSort.generateFile("OneBlockascOneBuff.txt", "1", 'c');
        //VirtualSort.generateFile("input2.txt", "1", 'b');
        VirtualSort.main(args);
        assertTrue(fileChecker.checkFile("input.txt"));
        
    }
    
    /**
     * test binary file with 1 block and 1 buffer
     * @throws Exception
     */
    public void test1block1bufBINARY() throws Exception {
        VirtualSort.generateFile("OneBlockbinaryOneBuff.txt", "1", 'b');
        assertFalse(fileChecker.checkFile("OneBlockbinaryOneBuff.txt"));
        String[] args = new String[3];
        args[0] = "OneBlockbinaryOneBuff.txt";
        args[1] = "1";
        args[2] = "statFileA.txt";
        VirtualSort.main(args);
        assertTrue(fileChecker.checkFile("OneBlockbinaryOneBuff.txt"));
    }
    
    /**
     * test ascii file with 10 block and 10 buffer
     * @throws Exception
     */
    public void test10block10bufASC() throws Exception {
        VirtualSort.generateFile("TenBlockascTenBuff.txt", "10", 'a');
        String[] args = new String[3];
        args[0] = "TenBlockascTenBuff.txt";
        args[1] = "10";
        args[2] = "statFileA.txt";
        VirtualSort.main(args);
        assertTrue(fileChecker.checkFile("TenBlockascTenBuff.txt"));
    }
    
    /**
     * test ascii file with 10 block and 1 buffer
     * @throws Exception
     */
    public void test10block1bufASC() throws Exception {
        VirtualSort.generateFile("TenBlockascOneBuff.txt", "10", 'a');
        String[] args = new String[3];
        args[0] = "TenBlockascOneBuff.txt";
        args[1] = "1";
        args[2] = "statFileA.txt";
        VirtualSort.main(args);
        assertTrue(fileChecker.checkFile("TenBlockascOneBuff.txt"));
    }
    
    /**
     * test binary file with 10 block and 10 buffer
     * @throws Exception
     */
    public void test10block10bufBINARY() throws Exception {
        VirtualSort.generateFile("TenBlockbinaryTenBuff.txt", "10", 'b');
        String[] args = new String[3];
        args[0] = "TenBlockbinaryTenBuff.txt";
        args[1] = "10";
        args[2] = "statFileA.txt";
        VirtualSort.main(args);
        assertTrue(fileChecker.checkFile("TenBlockbinaryTenBuff.txt"));
    }
    
    /**
     * test ascii file with 10 block and 4 buffer
     * @throws Exception
     */
    public void test10block4bufASC() throws Exception {
        VirtualSort.generateFile("TenBlockascFourBuff.txt", "10", 'a');
        String[] args = new String[3];
        args[0] = "TenBlockascFourBuff.txt";
        args[1] = "4";
        args[2] = "statFileA.txt";
        VirtualSort.main(args);
        assertTrue(fileChecker.checkFile("TenBlockascFourBuff.txt"));
    }
    
    /**
     * test ascii file with 15 block and 4 buffer
     * @throws Exception
     */
    public void test15block4bufASC() throws Exception {
        VirtualSort.generateFile("TenBlockasc15Buff.txt", "15", 'a');
        String[] args = new String[3];
        args[0] = "TenBlockasc15Buff.txt";
        args[1] = "4";
        args[2] = "statFileA.txt";
        VirtualSort.main(args);
        assertTrue(fileChecker.checkFile("TenBlockasc15Buff.txt"));
    }
    
    /**
     * test ascii file with 100 block and 10 buffer
     * @throws Exception
     */
    public void test100block10bufASC() throws Exception {
        String filename = "HundredBlockasc10Buff.txt";
        VirtualSort.generateFile(filename, "100", 'a');
        String[] args = new String[3];
        args[0] = filename;
        args[1] = "10";
        args[2] = "statFileA.txt";
        VirtualSort.main(args);
        assertTrue(fileChecker.checkFile(filename));
    }
    
    /**
     * test ascii file with 200 block and 10 buffer
     * @throws Exception
     */
    public void test200block10bufASC() throws Exception {
        String filename = "TwoHundredBlockasc10Buff.txt";
        VirtualSort.generateFile(filename, "200", 'a');
        String[] args = new String[3];
        args[0] = filename;
        args[1] = "10";
        args[2] = "statFileA.txt";
        VirtualSort.main(args);
        assertTrue(fileChecker.checkFile(filename));
    }
    
    /**
     * test ascii file with 500 block and 10 buffer
     * @throws Exception
     */
    public void test500block10bufASC() throws Exception {
        String filename = "FiveHundredBlockasc10Buff.txt";
        VirtualSort.generateFile(filename, "500", 'a');
        String[] args = new String[3];
        args[0] = filename;
        args[1] = "10";
        args[2] = "statFileA.txt";
        VirtualSort.main(args);
        assertTrue(fileChecker.checkFile(filename));
    }
    
    /**
     * test ascii file with 1000 block and 10 buffer
     * @throws Exception
     */
    public void test1000block10bufASC() throws Exception {
        VirtualSort.generateFile("ThousandBlockascTenBuff.txt", "1000", 'a');
        String[] args = new String[3];
        args[0] = "ThousandBlockascTenBuff.txt";
        args[1] = "10";
        args[2] = "statFileA.txt";
        VirtualSort.main(args);
        assertTrue(fileChecker.checkFile("ThousandBlockascTenBuff.txt"));
    }
    
    /*
    public void test5000block10bufASC() throws Exception {
        String filename = "FiveThousandBlockasc10Buff.txt";
        VirtualSort.generateFile(filename, "5000", 'a');
        String[] args = new String[3];
        args[0] = filename;
        args[1] = "10";
        args[2] = "statFileA.txt";
        VirtualSort.main(args);
        assertTrue(fileChecker.checkFile(filename));
    }
    
    public void test10000block10bufASC() throws Exception {
        String filename = "TenThousandBlockasc10Buff.txt";
        VirtualSort.generateFile(filename, "10000", 'a');
        String[] args = new String[3];
        args[0] = filename;
        args[1] = "10";
        args[2] = "statFileA.txt";
        VirtualSort.main(args);
        assertTrue(fileChecker.checkFile(filename));
    }
    
    public void test50000block10bufASC() throws Exception {
        String filename = "FiftyThousandBlockasc10Buff.txt";
        VirtualSort.generateFile(filename, "50000", 'a');
        String[] args = new String[3];
        args[0] = filename;
        args[1] = "10";
        args[2] = "statFileA.txt";
        VirtualSort.main(args);
        assertTrue(fileChecker.checkFile(filename));
    }
    
    */
    
    
    
    

}

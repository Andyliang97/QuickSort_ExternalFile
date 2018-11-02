import java.io.FileWriter;
import java.io.IOException;

/**
 * The main class
 * It contains a file generator and a main
 * It will sort the given file based on the given block number
 * and print out the statistic data to certain file.
 */

//On my honor:
//
//- I have not used source code obtained from another student,
//or any other unauthorized source, either modified or
//unmodified.
//
//- All source code and documentation used in my program is
//either my original work, or was derived by me from the
//source code published in the textbook for this course.
//
//- I have not discussed coding details about this project with
//anyone other than my partner (in the case of a joint
//submission), instructor, ACM/UPE tutors or the TAs assigned
//to this course. I understand that I may discuss the concepts
//of this program with other students, and that another student
//may help me debug my program so long as neither of us writes
//anything during the discussion or modifies any computer file
//during the discussion. I have violated neither the spirit nor
//letter of this restriction.


/**
 * The class containing the main method, the entry point of the application.
 * 
 * @author Junjie Liang
 * @version 2018 Oct
 */
public class VirtualSort {
    
    /**
     * This method is used to generate a file of a certain size, containing a
     * specified number of records.
     *
     * @param filename the name of the file to create/write to
     * @param blockSize the size of the file to generate
     * @param format the format of file to create
     * @throws IOException throw if the file is not open and proper
     */
    public static void generateFile(String filename, String blockSize,
        char format) throws IOException {
        FileGenerator generator = new FileGenerator();
        String[] inputs = new String[3];
        inputs[0] = "-" + format;
        inputs[1] = filename;
        inputs[2] = blockSize;
        generator.generateFile(inputs);
    }

    /**
     * The entry point of the application
     * @param args the argument
     */
    public static void main(String[] args) {
        QuickSort quicksort = new QuickSort(Integer.valueOf(args[1]), args[0]);
        quicksort.sort();
        int[] stat = quicksort.getStat();
        try {
            FileWriter filewrite = new FileWriter(args[2]);
            filewrite.write(args[2] + ", " + stat[0] +
                    " blocks, " + stat[1] + " buffers:\n");
            filewrite.write("Cache Hits: " + stat[2] + "\n");
            filewrite.write("Disk Reads: " + stat[3] + "\n");
            filewrite.write("Disk Writes: " + stat[4] + "\n");
            filewrite.close();
        } 
        catch (IOException e) {
            
            e.printStackTrace();
        }
        
    }
    
    
}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
    public ArrayList<String> readFile(String fileName){
        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            ArrayList<String> allSequences = new ArrayList<>();
            // Loop that sequentially reads lines to the end of the file and adds them to the ArrayList.
            while (fileScanner.hasNextLine()) {
                allSequences.add(fileScanner.nextLine().trim());
            }
            return allSequences;
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found!");
            return null;
        }
    }
}

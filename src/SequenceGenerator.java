import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SequenceGenerator {

    public static void main(String[] args) {
        SequenceGenerator generator = new SequenceGenerator();
        // Call methods that create random sequences for the first and second rows and write them to a file
        String sequence1 = generator.generateSequences(129);
        String sequence2 = generator.generateSequences(119);

        writeSequencesToFile("input.txt", sequence1, sequence2);
    }
    public String generateSequences(int length) {
        // Method that generates a random sequence of the specified length.
        StringBuilder sequence = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            char nucleotide = "ACGT".charAt(random.nextInt(4));
            sequence.append(nucleotide);
        }

        return sequence.toString();
    }

    public static void writeSequencesToFile(String filename, String sequence1, String sequence2) {
        // Method that writes the created sequences to a file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(sequence1 + "\n");
            writer.write(sequence2);
            System.out.println("Sequences are written to " + filename);
        } catch (IOException e) {
            System.err.println("An error occurred while writing sequences to file: " + e.getMessage());
        }
    }


}

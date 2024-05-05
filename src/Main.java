import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        FileReader reader= new FileReader();
        ArrayList<String> sequences;
        ArrayList<String> alignmentResult;
        sequences = reader.readFile("input5.txt"); // ArrayList to store sequences read from file

        sequences= checkInputOrder(sequences);

        final double MATCH_SCORE = 7;
        final double MISMATCH_SCORE = -5;
        final double GAP_OPENING_PENALTY = -3;
        final double GAP_EXTENSION_PENALTY = -1;

        SequenceAlignment sequenceAlignment = new SequenceAlignment();
        // Aligning arrays and getting results
        alignmentResult = sequenceAlignment.alignSequences(sequences.get(0),sequences.get(1),MATCH_SCORE,MISMATCH_SCORE,GAP_OPENING_PENALTY,GAP_EXTENSION_PENALTY);

        // Outputs
        System.out.println("Sequence 1: "+alignmentResult.get((sequences.size()>2 && sequences.get(2)=="Yes") ? 1 : 0));
        System.out.println("Sequence 2: "+alignmentResult.get((sequences.size()>2 && sequences.get(2)=="Yes") ? 0 : 1));
        System.out.println("Score: "+alignmentResult.get(2));

    }

    public static ArrayList<String> checkInputOrder(ArrayList<String> sequences){
        // Check and sort inputs
        int sl=sequences.get(0).length();
        int sl2=sequences.get(1).length();
        if (sl>sl2){
            String temp = sequences.get(0);
            sequences.set(0, sequences.get(1));
            sequences.set(1,temp);
            sequences.add("Yes");
        }
        return sequences;
    }

}


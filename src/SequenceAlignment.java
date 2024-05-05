import java.util.ArrayList;

public class SequenceAlignment {

    public double[][] dpMatrix; // Dynamic Programming Matrix
    public ArrayList<String> alignSequences(String sequence1, String sequence2,double match_score,double mismatch_score,double gap_opening_penalty, double gap_extension_penalty) {

        dpMatrix = new double[sequence1.length() + 1][sequence2.length() + 1];
        ArrayList<String> resultAlignment = new ArrayList<>();
        StringBuilder alignment1 = new StringBuilder();
        StringBuilder alignment2 = new StringBuilder();

        // A method that fills the matrix with initial values such that there are no matches in rows and columns.
        initialFillMatrix(sequence1,sequence2,gap_opening_penalty,gap_extension_penalty);
        // Function that finds the optimal alignment in the matrix.
        findPathScoresInMatrix(sequence1,sequence2,match_score,mismatch_score,gap_opening_penalty,gap_extension_penalty);


        int i = sequence1.length();
        int j = sequence2.length();
        double lastMatchOrMismatchScore=0;
        double currentMatchOrMismatchScoreDifference=0;
        double upGapScore=0;
        double currentUpGapScoreDifference=0;

        // Loop for alignment on sequences
        while (i > 0 || j > 0) {

            if(i > 0 && j > 0){
                lastMatchOrMismatchScore = (sequence1.charAt(i - 1) == sequence2.charAt(j - 1)) ? match_score : mismatch_score;
                currentMatchOrMismatchScoreDifference = dpMatrix[i][j] - dpMatrix[i-1][j-1];
            }
            if(i > 0){
                upGapScore=(j == sequence2.length()) ? gap_opening_penalty : gap_extension_penalty;
                currentUpGapScoreDifference= dpMatrix[i][j] - dpMatrix[i - 1][j];
            }
            if (i > 0 && j > 0 && lastMatchOrMismatchScore==currentMatchOrMismatchScoreDifference) {

                alignment1.insert(0, sequence1.charAt(i - 1));
                alignment2.insert(0, sequence2.charAt(j - 1));
                i--;
                j--;

            } else if (i > 0 && upGapScore==currentUpGapScoreDifference) {
                alignment1.insert(0, sequence1.charAt(i - 1));
                alignment2.insert(0, '-');
                i--;
            } else if(j > 0){
                alignment1.insert(0, '-');
                alignment2.insert(0, sequence2.charAt(j - 1));
                j--;
            }
        }

        // Sequence Alignment and Score Results
        resultAlignment.add(alignment1.toString());
        resultAlignment.add(alignment2.toString());
        resultAlignment.add(calculateScoreWithGapExtension(alignment1,alignment2,match_score,mismatch_score,gap_opening_penalty,gap_extension_penalty));

        return resultAlignment;
    }

    public void initialFillMatrix(String sequence1, String sequence2,double gap_opening_penalty, double gap_extension_penalty){
        // A method that fills the matrix with initial values such that there are no matches in rows and columns.
        int seq1Length=sequence1.length();
        int seq2Length=sequence2.length();
        for (int i = 1; i <= seq1Length; i++) {
            dpMatrix[i][0] = gap_opening_penalty + i * gap_extension_penalty;
        }
        for (int j = 1; j <= seq2Length; j++) {
            dpMatrix[0][j] = gap_opening_penalty + j * gap_extension_penalty;
        }

    }


    public void findPathScoresInMatrix(String sequence1, String sequence2,double match_score,double mismatch_score,double gap_opening_penalty, double gap_extension_penalty){
        // Function that finds the optimal alignment in the matrix.
        int seq1Length=sequence1.length();
        int seq2Length=sequence2.length();
        for (int i = 1; i <= seq1Length; i++) {
            for (int j = 1; j <= seq2Length; j++) {
                double matchScore = (sequence1.charAt(i - 1) == sequence2.charAt(j - 1)) ? match_score : mismatch_score;
                double diagonalScore = dpMatrix[i - 1][j - 1] + matchScore;
                double upScore = dpMatrix[i - 1][j] + ((j == sequence2.length()) ? gap_opening_penalty : gap_extension_penalty);
                double leftScore = dpMatrix[i][j - 1] + ((i == sequence1.length()) ? gap_opening_penalty : gap_extension_penalty);

                dpMatrix[i][j] = Math.max(diagonalScore, Math.max(upScore, leftScore));
            }
        }

    }

    public String calculateScoreWithGapExtension(StringBuilder alignment1, StringBuilder alignment2, double match_score, double mismatch_score, double gap_opening_penalty, double gap_extension_penalty){

        // Method to calculate alignment results and scores.
        int alignmentLength=alignment1.length();
        double score=0;
        for(int i=0;i < alignmentLength;i++){
            if((alignment1.charAt(i)==alignment2.charAt(i)) && (alignment1.charAt(i)!='-' || alignment2.charAt(i)!='-')){
                score+=match_score;
            }else if ((alignment1.charAt(i)!=alignment2.charAt(i)) && (alignment1.charAt(i)!='-' && alignment2.charAt(i)!='-')) {
                score+=mismatch_score;
            }else{

                if(i!=0){
                    if(alignment1.charAt(i-1)!='-' && alignment1.charAt(i)=='-'){
                        score+=gap_opening_penalty;
                    }else if(alignment1.charAt(i-1)=='-' && alignment1.charAt(i)=='-'){
                        score+=gap_extension_penalty;
                    }

                    if(alignment2.charAt(i-1)!='-' && alignment2.charAt(i)=='-'){
                        score+=gap_opening_penalty;
                    }else if(alignment2.charAt(i-1)=='-' && alignment2.charAt(i)=='-'){
                        score+=gap_extension_penalty;
                    }
                }
            }
        }

        return String.valueOf(score);
    }
}

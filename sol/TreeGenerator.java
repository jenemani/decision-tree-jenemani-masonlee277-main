package sol;

import src.ITreeGenerator;
import src.Row;

/**
 * A class that implements the ITreeGenerator interface
 * used to generate a tree
 */
public class TreeGenerator implements ITreeGenerator<Dataset> {
    @Override
    public void generateTree(Dataset trainingData, String targetAttribute) {

    }

    @Override
    public String getDecision(Row datum) {
        return null;
    }
    // TODO: Implement methods declared in ITreeGenerator interface!

}

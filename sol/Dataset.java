package sol;

import src.DecisionTreeCSVParser;
import src.IDataset;
import src.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A class that implements the IDataset interface,
 * representing a training data set.
 */
public class Dataset implements IDataset {
    List<String> attList;
    List<Row> dataList;
    String filePath;
    Dataset(List<String> attList, List<Row> dataList, String filePath){
        this.attList = attList;
        this.dataList = dataList;
        this.filePath = filePath;
    }

    @Override
    public List<String> getAttributeList() {
        this.attList.addAll(this.dataList.get(0).getAttributes());
        return this.attList;
    }

    @Override
    public List<Row> getDataObjects() {
       return DecisionTreeCSVParser.parse(this.filePath);
    }

    @Override
    public int size() {
        return 0;
    }
}

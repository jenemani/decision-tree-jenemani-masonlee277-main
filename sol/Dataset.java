package sol;

import src.IDataset;
import src.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A class that implements the IDataset interface,
 * representing a training data set.
 */
public class Dataset implements IDataset {
    public List<String> attList;
    private List<String> allAttributes;
    public List<Row> dataList;
    public static String TARGET_ATT;

    /**
     * Our constructor for the Dataset class
     * @param attributeList the list of attributes with the targetAttribute removed
     * @param dataObjects the rows of data within our CSV
     */
    public Dataset(List<String> attributeList, List<Row> dataObjects){
        this.dataList = dataObjects;
        this.attList = attributeList; // We remove the target attribute from the attList and from here only access the
    }

    public static void setTargetAtt(String string){
        TARGET_ATT = string;
    }

    @Override
    public int size() {
       return this.dataList.size();
    }

    // checks if all the values of the atts are the same
    // if the targetAtt values are different in subset then return false
    public Boolean isUniform(){
        if(this.dataList.size() == 0 || this.dataList.size() == 1){
            return true;
        }

        String string = this.dataList.get(0).getAttributeValue(TARGET_ATT);
        for (Row row : this.dataList) {
            if (!row.getAttributeValue(TARGET_ATT).equals(string)){
                return false;
            }
        }

        return true;
    }

    @Override
    public List<String> getAttributeList() {
        return this.attList;
    }

    @Override
    public List<Row> getDataObjects() {
        return this.dataList;
    }

    public ArrayList<String> getEdges(String attValue){
        ArrayList<String> valueList = new ArrayList<>();
        for(Row r : this.dataList){
            String att = r.getAttributeValue(attValue);
            if (!valueList.contains(att)){
                valueList.add(att);
            }
        }
        return valueList;
    }

    public Dataset getSubset(String attribute, String attValue) {
        List<Row> d = new ArrayList<>(this.dataList);
        List<String> newAttList = new ArrayList<>(this.attList);
        newAttList.remove(attribute);
        List<Row> subset = new ArrayList<>();

        for (Row r: d) {
            if (r.getAttributeValue(attribute).equals(attValue)) {
                subset.add(r);
            }
        }
        this.attList = newAttList;
        return new Dataset(newAttList, subset);
    } //This splits the data given a specific attValue

    public String getDefault() {
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
            for (int i = 0; i < this.dataList.size(); i++) {
                String attVal = this.dataList.get(i).getAttributeValue(TARGET_ATT);
                hm.merge(attVal, 1, Integer::sum);
            }

            List<String> posAttVals = new ArrayList<>(hm.keySet());
            String largest = posAttVals.get(0);
                for (int i = 1; i < posAttVals.size(); i++) {
                    if (hm.get(largest) < hm.get(posAttVals.get(i))) {
                        largest = posAttVals.get(i);
                    }
                }

            return largest;


    } //This gets the default targetAttribute value of the given dataset

    public String getSplit(){
        int min = 0;
        int max = this.attList.size();
        int random = (int) ((Math.random() * (max - min)) + min);
        return this.attList.get(random);
    } //This chooses a random attribute to split the data on
} // end of class

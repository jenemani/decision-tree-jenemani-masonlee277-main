package sol;

import src.IDataset;
import src.Row;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * A class that implements the IDataset interface,
 * representing a training data set.
 */
public class Dataset implements IDataset {
    public List<String> attList;
    public List<Row> dataList;
    public static String TARGET_ATT;

    /**
     * Our constructor for the Dataset class
     * @param attributeList the list of attributes with the targetAttribute removed
     * @param dataObjects the rows of data within our CSV
     */
    public Dataset(List<String> attributeList, List<Row> dataObjects){
        if(dataObjects.size() == 0){
            throw new NullPointerException("You've got no data!");
        }else {
            this.dataList = dataObjects;
            this.attList = attributeList;
        }// We remove the target attribute from the attList and from here only access the
    }

    /**
     * @param string the targetAttribute for this decisionTree
     * Creating a static variable allows us to hold the target attribute throughout the
     * run and not continually pass it, this also means that our node doesn't have to know
     * about the Target Attribute at all.
     */
    public static void setTargetAtt(String string){
        TARGET_ATT = string;
    }

    /**
     * @return the size of the datalist
     */
    @Override
    public int size() {
       return this.dataList.size();
    }

    /**
     * @return true if target attribute for all data is the same
     */
    public Boolean isUniform(){
        if(TARGET_ATT == null ){
            throw new NullPointerException("No designated target attribute");
        }

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

    /**
     * @return the current attribute list
     */
    @Override
    public List<String> getAttributeList() {
        return this.attList;
    }

    /**
     * @return the current dataList
     */
    @Override
    public List<Row> getDataObjects() {
        return this.dataList;
    }

    /**
     * @param attValue the attribute to find edge attribute values for
     * @return a list of all possible attList values
     */
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

    /**
     * @param attribute the attribute or key being used to split
     * @param attValue the particular attribute value of the attribute
     * @return returns a dataset of data with rows whose attribute equal the attValue
     */
    public Dataset getSubset(String attribute, String attValue) {
        List<Row> d = new ArrayList<>(this.dataList);
        List<String> newAttList = new ArrayList<>(this.attList);
        newAttList.remove(attribute); //Removes the attribute so that we can't split on it again
        List<Row> subset = new ArrayList<>();

        for (Row r: d) {
            if (r.getAttributeValue(attribute).equals(attValue)) {
                subset.add(r);
            }
        }
        this.attList = newAttList;
        return new Dataset(newAttList, subset);
    }

    /**
     * @return the default value or mode of the dataset
     */
    public String getDefault() {
        if(TARGET_ATT == null ){
            throw new NullPointerException("No designated target attribute");
        }
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        //we use a hashmap with keys that are unique target attributes and keys that are the tally of their occurence
        for (Row row : this.dataList) {
            String attVal = row.getAttributeValue(TARGET_ATT);
            hm.merge(attVal, 1, Integer::sum);
        }
        //we then loop through this map to find the largest
        List<String> posAttVals = new ArrayList<>(hm.keySet());
        //this randomizes the order of our list so the last largest attribute is random
        Collections.shuffle(posAttVals);
        String largest = posAttVals.get(0);
        for (int i = 1; i < posAttVals.size(); i++) {
            if (hm.get(largest) < hm.get(posAttVals.get(i))) {
                largest = posAttVals.get(i);
            }
        }

        return largest;

    } //This gets the default targetAttribute value of the given dataset

    /**
     * @return a random attribute to split the data on
     */
    public String getSplit(){
        int min = 0;
        int max = this.attList.size();
        int random = (int) ((Math.random() * (max - min)) + min);
        return this.attList.get(random);
    }
} // end of class

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
    public List<Row> dataList;

    Dataset(List<Row> dataObjects, List<String> attributeList){
        this.dataList = dataObjects;
        this.attList = attributeList;
    }

    @Override
    public List<String> getAttributeList() {
        return this.attList;
    }

    @Override
    public List<Row> getDataObjects() {
        return this.dataList;
    }

    public ArrayList<String> getPosAttVals(String attValue){

        ArrayList<String> valueList = new ArrayList<>();
        for(Row r : this.dataList){
            String att = r.getAttributeValue(attValue);
            if (!valueList.contains(att)){
                valueList.add(att);
            }
        }
        return valueList;
    }

    @Override
    public int size() {
       return this.dataList.size();
    }

    public void remove(String str){
        this.dataList.remove(str);
    }

    // checks if all the values of the atts are the same
    // if the targetAtt values are different in subset then return false
    public Boolean sameValue(String targetAtt){
        String capShape  = this.dataList.get(5998).getAttributeValue("capShape");
        String capSurface = this.dataList.get(100).getAttributeValue("capSurface");
        String capColor= this.dataList.get(1000).getAttributeValue("capColor");
        String string = this.dataList.get(2).getAttributeValue(targetAtt);

        String str = this.dataList.get(6).getAttributeValue(targetAtt);
        boolean bool = true;
        for (Row row : this.dataList) {
            if (row.getAttributeValue(targetAtt) != string){
                bool = false;
                break;
            }

        }
        return bool;
    }

    public String leafValue(String targetAtt){
        return this.dataList.get(1).getAttributeValue(targetAtt);
    }

    public Dataset getSubset(String att, String attVal) {
        List<Row> d = this.dataList;
        List<String> newAttList = this.attList;
        newAttList.remove(att);
        List<Row> subset = new ArrayList<Row>();
        for (Row r: d) {
            if (r.getAttributeValue(att) == attVal) {
                subset.add(r);
            }

        }
        return new Dataset(subset, newAttList);
    } // end of subset

    public String getDefault(String tarAtt) {
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
            for (int i = 0; i < this.dataList.size()-1; i++) {
                String attVal = this.dataList.get(i).getAttributeValue(tarAtt);
                hm.put(attVal, hm.get(attVal) + 1);
            }

            List<String> posAttVals = new ArrayList<>(hm.keySet());
            String largest = posAttVals.get(0);
                for (int i = 1; i < posAttVals.size(); i++) {
                    if (hm.get(largest) < hm.get(posAttVals.get(i))) {
                        largest = posAttVals.get(i);
                    }
                }

            return largest;


    }
} // end of class

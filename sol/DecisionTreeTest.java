package sol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.Before;
import src.DecisionTreeCSVParser;
import src.Row;

import java.util.ArrayList;
import java.util.List;

public class DecisionTreeTest {
    
    // Constructor for DecisionTreeTest tester class - don't need to add anything in here!
    public DecisionTreeTest() {
        
    }
    
    @Test
    public void testExample() {
        List<Row> rows = DecisionTreeCSVParser.parse("/Users/isaacjenemann/Desktop/cs200/projects/decision-tree-jenemani-masonlee277-main/data/mushrooms/training.csv");
        List<String> atts = new ArrayList<>(rows.get(0).getAttributes());
        Dataset villians = new Dataset(rows, atts);
        System.out.println(villians.dataList.get(100).getAttributeValue("capShape"));
        System.out.println(villians.dataList.get(0).getAttributeValue("capShape"));
        System.out.println(villians.dataList.get(10).getAttributeValue("capShape"));
        System.out.println(villians.dataList.get(55).getAttributeValue("capShape"));
    }

    // TODO: Add your tests here!
    
}

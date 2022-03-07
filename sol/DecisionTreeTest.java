package sol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.Before;
import src.DecisionTreeCSVParser;
import src.DecisionTreeTester;
import src.Row;

import java.util.ArrayList;
import java.util.List;

public class DecisionTreeTest {
    
    // Constructor for DecisionTreeTest tester class - don't need to add anything in here!
    public DecisionTreeTest() {
        
    }
    
    @Test
    public void testExample() {
        List<Row> rows = DecisionTreeCSVParser.parse("/Users/isaacjenemann/Desktop/cs200/projects/decision-tree-jenemani-masonlee277-main/data/smol.csv");
        List<String> atts = new ArrayList<>(rows.get(0).getAttributes());
        Dataset mushrooms = new Dataset(atts, rows);
        TreeGenerator generator = new TreeGenerator();
        generator.generateTree(mushrooms,"Animal");
        Row dog = new Row("dog");
/**
        dog.setAttributeValue("Size", "Big");
        dog.setAttributeValue("Fur", "Rough");
        dog.setAttributeValue("Ears", "Floppy");
        dog.setAttributeValue("Tail", "Long");
        generator.getDecision(dog);
        System.out.println(generator.getDecision(dog));
 **/
    }
    // TODO: Add your tests here
}

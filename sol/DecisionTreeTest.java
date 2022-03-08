package sol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Assert;
import org.junit.Test;
import src.DecisionTreeCSVParser;
import src.DecisionTreeTester;
import src.Row;

import java.util.ArrayList;
import java.util.List;

public class DecisionTreeTest {
    
    // Constructor for DecisionTreeTest tester class - don't need to add anything in here!
    public DecisionTreeTest() {
    }

    /**
     * This test, assembles a decision tree from Animal data and runs the test decision on itself this checks
     * that the tree has compiled as we intended and that getdecision is working as anticipated, this is a large
     * overarching test broken down into smaller components in the tests below
     */
    @Test
    public void Node() {
        List<Row> rows = DecisionTreeCSVParser.parse("/Users/isaacjenemann/Desktop/cs200/projects/decision-tree-jenemani-masonlee277-main/data/selfMade/animals.csv");
        List<String> atts = new ArrayList<>(rows.get(0).getAttributes());
        Dataset animals = new Dataset(atts, rows);
        TreeGenerator generator = new TreeGenerator();
        //This is not a test but no error methods here is a good sign!
        generator.generateTree(animals,"Animal");

        Row dog = new Row("Testing");
        dog.setAttributeValue("Size", "Big");
        dog.setAttributeValue("Fur", "Rough");
        dog.setAttributeValue("Ears", "Floppy");
        dog.setAttributeValue("Tail", "Long");
        //If this test ever fails it means that our accuracy using training data is less than 1.0
        assertEquals(generator.getDecision(dog),"Dog");
    }

    /**
     * this test class makes a dataset and tests a couple different get methods within the class
     */
    @Test
    public void dataSet() {
        List<Row> rows = DecisionTreeCSVParser.parse("/Users/isaacjenemann/Desktop/cs200/projects/decision-tree-jenemani-masonlee277-main/data/selfMade/animals.csv");
        List<String> atts = new ArrayList<>(rows.get(0).getAttributes());
        Dataset animals = new Dataset(atts, rows);

        //Tests that getEdges returns the appropriately sized list
        assertEquals(animals.getEdges("Size").size(), 2 );
        //Tests that getEdges returns a list with the correct contents
        assertEquals(animals.getEdges("Size").get(0), "Big");
        //Tests that getSubset returns a correctly partitioned list
        assertEquals(animals.getSubset("Size","Big").size(),2);
        //Tests that an error is thrown when trying to tabulate a default value for a dataset without a TARGET_ATTRIBUTE
        Exception e = assertThrows(RuntimeException.class,() -> animals.getDefault());
        Assert.assertEquals("No designated target attribute", e.getMessage());
        //Tests that getDefault returns the appropriate value
        Dataset.setTargetAtt("Animal");
        assertEquals(animals.getDefault(),"Dog");
    }

    /**
     * tests that isUniform returns true for data that is uniform.
     */
    @Test
    public void isTrue() {
        List<Row> rows = DecisionTreeCSVParser.parse("/Users/isaacjenemann/Desktop/cs200/projects/decision-tree-jenemani-masonlee277-main/data/selfMade/sameValue.csv");
        List<String> atts = new ArrayList<>(rows.get(0).getAttributes());
        Dataset allTrue = new Dataset(atts, rows);
        Dataset.setTargetAtt("Target");
        assertTrue(allTrue.isUniform());
    }

    /**
     * tests that isUniform returns false for data that is nonuniform.
     */
    @Test
    public void isFalse() {
        List<Row> rows = DecisionTreeCSVParser.parse("/Users/isaacjenemann/Desktop/cs200/projects/decision-tree-jenemani-masonlee277-main/data/selfMade/animals.csv");
        List<String> atts = new ArrayList<>(rows.get(0).getAttributes());
        Dataset animals = new Dataset(atts, rows);
        Dataset.setTargetAtt("Animal");
        assertFalse(animals.isUniform());
    }

    /**
     * tests that an error is thrown when a treeGenerator is called on an empty dataset
     */
    @Test
    public void onlyTargetAtt() {
        List<Row> rows = DecisionTreeCSVParser.parse("/Users/isaacjenemann/Desktop/cs200/projects/decision-tree-jenemani-masonlee277-main/data/selfMade/justTarget.csv");
        List<String> atts = new ArrayList<>(rows.get(0).getAttributes());
        Dataset justTarget = new Dataset(atts, rows);
        TreeGenerator justTar = new TreeGenerator();
        Exception e = assertThrows(RuntimeException.class,() -> justTar.generateTree(justTarget,"Target"));
        Assert.assertEquals("You've got no data!", e.getMessage());
    }

    /**
     * tests that a leaf is generated when the dataset is size 1
     */
    @Test
    public void justALeaf() {
        List<Row> rows = DecisionTreeCSVParser.parse("/Users/isaacjenemann/Desktop/cs200/projects/decision-tree-jenemani-masonlee277-main/data/selfMade/justALeaf.csv");
        List<String> atts = new ArrayList<>(rows.get(0).getAttributes());
        Dataset justLeaf = new Dataset(atts, rows);
        TreeGenerator generator = new TreeGenerator();
        generator.generateTree(justLeaf,"Target");
        Row row = new Row("Testing");
        row.setAttributeValue("Att","TRUE");
        assertEquals(generator.getDecision(row),"TRUE");
    }
}

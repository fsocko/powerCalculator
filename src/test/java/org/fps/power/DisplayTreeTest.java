package org.fps.power;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DisplayTreeTest {

    static TreeNode root;

    @BeforeAll
    static void setUp() throws Exception {

        root = new TreeNode("root1->",1);
        root.children.add(new TreeNode(2));
        root.children.add(new TreeNode(3));
        root.children.add(new TreeNode(4));
        //Node2 -> 2 + 5 + 6 + 7
        root.children.get(0).children.add(new TreeNode(5));
        root.children.get(0).children.add(new TreeNode(6));
        root.children.get(0).children.add(new TreeNode(7));
        //Node3 -> 3 + 8
        root.children.get(1).children.add(new TreeNode(8));
        //Node4 -> 4 + 9 + 10 + 11
        root.children.get(2).children.add(new TreeNode(9));
        root.children.get(2).children.add(new TreeNode(10));
        root.children.get(2).children.add(new TreeNode(11));
        System.out.println(DisplayTree.printNAryTree(root).toString());
    }

    @Test
    public void testReturnSum() throws Exception {

        assertEquals(20, DisplayTree.calculatePowerSum( this.root.children.get(0)));
        assertEquals(11, DisplayTree.calculatePowerSum( this.root.children.get(1)));
        assertEquals(34, DisplayTree.calculatePowerSum(this.root.children.get(2)));

        assertEquals(20, this.root.children.get(0).calculateThisPowerSum());
        assertEquals(11, this.root.children.get(1).calculateThisPowerSum());
        assertEquals(34, this.root.children.get(2).calculateThisPowerSum());
    }

}
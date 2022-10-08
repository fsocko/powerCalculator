package org.fps.power;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeNode {

    private static final double VOLT_CONSTANT = 240;

    static enum Unit {
        WATT, AMP;
    }

    double power;
    double powerLimit;
    String name;
    List<TreeNode> children = new LinkedList<>();

    TreeNode(double power) {
        this.power = power;
        this.powerLimit = -1;
        this.name = "unnamed_node_" + this.power;
    }

    TreeNode(String name, double power) {
        this.name = name;
        this.power = power;
        this.powerLimit = -1;
    }

    TreeNode(String name, double power, double powerLimit) {
        this.name = name;
        this.power = power;
        this.powerLimit = powerLimit;
    }

    TreeNode(String name, double power, Unit unit) {
        this.name = name;
        this.power = power;
        this.powerLimit = -1;
        //P = IV
        if(unit.equals(Unit.AMP)){
            this.power = power * VOLT_CONSTANT;
        }
    }

    //If for some reason, Watts are not available.
    TreeNode(String name, double power, double powerLimit, Unit unit) {
        this.name = name;
        this.power = power;
        this.powerLimit = powerLimit;
        //P = IV
        if(unit.equals(Unit.AMP)){
            this.power = power * VOLT_CONSTANT;
        }
    }


    public TreeNode addChild(final TreeNode n) {
        children.add(n);
        return this;
    }

    public double calculateThisPowerSum() {
        double sum = power;
        if (this == null) return -1;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(this);
        while (!queue.isEmpty()) {
            int len = queue.size();
            for (int i = 0; i < len; i++) { // so that we can reach each level
                TreeNode node = queue.poll();
                for (TreeNode item : node.children) { // for-Each loop to iterate over all childrens
                    queue.offer(item);
                }
            }
            for (TreeNode node : queue) {
                sum += node.power;
            }
        }
        return sum;
    }
}
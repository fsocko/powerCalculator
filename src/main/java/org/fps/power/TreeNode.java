package org.fps.power;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeNode {

    private static final double VOLT_CONSTANT = 240;
    double power;
    String name;
    List<TreeNode> children = new LinkedList<>();

    TreeNode(String name, int power){
        this.name = name;
        this.power = power;
    }
    TreeNode(int power){
        this.power = power;
        this.name = "unnamed_node_" + this.power;

    }

    TreeNode(int data,List<TreeNode> child){
        power = data;
        children = child;
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
            for(TreeNode node : queue){
                sum += node.power;
            }
        }
        return sum;
    }

}
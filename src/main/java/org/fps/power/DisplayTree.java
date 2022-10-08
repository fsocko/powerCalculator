package org.fps.power;

import java.util.*;

public class DisplayTree {

    private static final boolean USE_UNICODE = true;


    public static void main(final String[] args) throws Exception {

        TreeNode root = new TreeNode("ROOT", 1);
        root.children.add(new TreeNode(2));
        root.children.add(new TreeNode(3));
        root.children.add(new TreeNode(4));
        //Node2 -> 2R + 5 + 6 + 7
        root.children.get(0).children.add(new TreeNode(5));
        root.children.get(0).children.add(new TreeNode(6));
        root.children.get(0).children.add(new TreeNode(7));
        //Node3 -> 3R + 8
        root.children.get(1).children.add(new TreeNode(8));
        //Node4 -> 4R + 9 + 10 + 11
        root.children.get(2).children.add(new TreeNode(9));
        root.children.get(2).children.add(new TreeNode(10));
        root.children.get(2).children.add(new TreeNode(11));

        //System.out.println(printNAryTree(root).toString());
        printNAryTreeOld(root);
        //calculatePowerSum(root.children.get(1));
    }

    public static StringBuilder printNAryTree(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        if (root == null) return sb;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            for (int i = 0; i < len; i++) { // so that we can reach each level
                TreeNode node = queue.poll();
                sb.append(formatNode(node) + " ");
                for (TreeNode item : node.children) { // for-Each loop to iterate over all childrens
                    queue.offer(item);
                }
            }
            sb.append("\n");
        }
        return sb;
    }

    private static void printNAryTreeOld(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            for (int i = 0; i < len; i++) { // so that we can reach each level
                TreeNode node = queue.poll();
                System.out.print(node.name + " ");
                for (TreeNode item : node.children) { // for-Each loop to iterate over all childrens
                    queue.offer(item);
                }
            }
            System.out.println();
        }
    }

    public static String formatNode(TreeNode node) {
        return node.name + "\t-> P: " + node.power + " -> pΣ: " + node.calculateThisPowerSum() + "w \n ";
    }

    public static double calculatePowerSum(TreeNode root) {
        double sum = root.power;
        if (root == null) return -1;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
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
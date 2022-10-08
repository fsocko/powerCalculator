package org.fps.power;

import java.util.*;

public class DisplayTree {

    private static final boolean USE_UNICODE = true;


    public static void main(final String[] args) throws Exception {

        TreeNode root = new TreeNode("SOCKET", 0);
        root.children.add(new TreeNode("WALL_3SPLIT",0,3500 ));
            root.children.get(0).children.add(new TreeNode("PRJ",1.5, TreeNode.Unit.AMP));
            root.children.get(0).children.add(new TreeNode("FAN",40));
            root.children.get(0).children.add(new TreeNode("EXTENSION_1",0, 3500));
                root.children.get(0).children.get(2).children.add(new TreeNode("WIFI", 0.7, TreeNode.Unit.AMP));
                root.children.get(0).children.get(2).children.add(new TreeNode("CLOCK", 10));
                root.children.get(0).children.get(2).children.add(new TreeNode("USBx3", 0.8,TreeNode.Unit.AMP));
                root.children.get(0).children.get(2).children.add(new TreeNode("EXTENSION_MON", 0, 3500));
                    root.children.get(0).children.get(2).children.get(3).children.add(new TreeNode("MON1", 24));
                    root.children.get(0).children.get(2).children.get(3).children.add(new TreeNode("MON2", 24));
                root.children.get(0).children.get(2).children.add(new TreeNode("EXTENSION_2USB", 0.8,3500));
                    root.children.get(0).children.get(2).children.get(4).children.add(new TreeNode("INTEGRATED2xUSB", 24));
                    root.children.get(0).children.get(2).children.get(4).children.add(new TreeNode("PERS_PC", 1.7, TreeNode.Unit.AMP));
                    root.children.get(0).children.get(2).children.get(4).children.add(new TreeNode("WORK_PC", 55, TreeNode.Unit.AMP));
                    root.children.get(0).children.get(2).children.get(4).children.add(new TreeNode("MIXER", 0.2, TreeNode.Unit.AMP));

        System.out.println(printNAryTree(root).toString());
        printNAryTreeOld(root);
        //calculatePowerSum(root.children.get(1));
    }

    public static StringBuilder printNAryTree(TreeNode root) throws Exception {
        StringBuilder sb = new StringBuilder();
        if (root == null) return sb;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            int i;
            for (i = 0; i < len; i++) { // so that we can reach each level
                TreeNode node = queue.poll();
                for(int j=0 ; j<i ; j++ ) {
                    sb.append("\t");
                }
                sb.append(formatNode(node) + " ");
                for (TreeNode item : node.children) { // for-Each loop to iterate over all childrens
                    queue.offer(item);
                }
            }
            sb.append("\n"); //Between Levels:
            for(int j=0 ; j<i ; j++ ) {
                sb.append("\t");
            }
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
                System.out.print(node.name + "> " + node.power + "w > pΣ" + node.calculateThisPowerSum() + "w | " );
                for (TreeNode item : node.children) { // for-Each loop to iterate over all childrens
                    queue.offer(item);
                }
            }
            System.out.println("");
        }
    }

    public static String formatNode(TreeNode node) throws Exception {
        if(node.powerLimit > 0){
            if(node.calculateThisPowerSum() > node.powerLimit ){
                return "\n" + node.name + "\t-> P: " + node.power
                        + " -> pΣ: " + node.calculateThisPowerSum() + "w Limit:"
                        + ColouredJavaOutput.ANSI_BG_WHITE + ColouredJavaOutput.ANSI_RED
                        + node.powerLimit + "w" + ColouredJavaOutput.ANSI_RESET + " ";
            }

            return "\n" + node.name + "\t-> P: " + node.power + " -> pΣ: "
                    + node.calculateThisPowerSum() + "w Limit:"
                    + node.powerLimit + "w" + " ";
        }

        else{
            return node.name + "\t-> P: " + node.power + "w | ";
        }
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
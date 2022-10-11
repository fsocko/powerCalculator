package org.fps.power;

import hu.webarticum.treeprinter.SimpleTreeNode;
import hu.webarticum.treeprinter.TreeNode;

import java.util.*;

public class PowerNode extends SimpleTreeNode {
    private static final double VOLT_CONSTANT = 240;

    private final List<PowerNode> childrenPn = new LinkedList<>();

    @Override
    public String content() {
        return this.formatNode();
    }

    static enum Unit {
        WATT, AMP;
    }

    double power;
    double powerLimit;
    String name;

    boolean powerUnsure;


    PowerNode(String name) {
        super(name);
        this.name = name;
        this.power = -1;
        this.powerLimit = -1;
        this.powerUnsure = false;
    }

    PowerNode(double power) {
        super("unnamed_node_" + power);
        this.name = "unnamed_node_" + power;
        this.power = power;
        this.powerLimit = -1;
        this.powerUnsure = false;
    }

    PowerNode(String name, double power) {
        super(name);
        this.name = name;
        this.power = power;
        this.powerLimit = -1;
        this.powerUnsure = false;
    }

    PowerNode(String name, double power, double powerLimit) {
        super(name);
        this.name = name;
        this.power = power;
        this.powerLimit = powerLimit;
        this.powerUnsure = false;
    }

    PowerNode(String name, double power, Unit unit) {
        super(name);
        this.name = name;
        this.power = power;
        this.powerLimit = -1;
        this.powerUnsure = false;
        //P = IV
        if (unit.equals(Unit.AMP)) {
            this.power = power * VOLT_CONSTANT;
        }
    }

    //If for some reason, Watts are not available.
    PowerNode(String name, double power, double powerLimit, Unit unit) {
        super(name);
        this.name = name;
        this.power = power;
        this.powerLimit = powerLimit;
        this.powerUnsure = false;
        //P = IV
        if (unit.equals(Unit.AMP)) {
            this.power = power * VOLT_CONSTANT;
        }
    }

    @Override
    public List<TreeNode> children() {
        ArrayList treeList = new ArrayList<>(childrenPn);
        Collections.sort(treeList, new PowerComparatorDesc());
        return treeList;
    }

    public List<PowerNode> childrenAsPn() {
        ArrayList treeList = new ArrayList<>(childrenPn);
        Collections.sort(treeList, new PowerComparatorDesc());
        return treeList;
    }

    public void addChild(PowerNode childNode) {
        if (childNode == null) {
            throw new IllegalArgumentException("Child node must not be null, use placeholder instead");
        }
        childrenPn.add(childNode);
    }

    public double calculateThisPowerSum() {
        double sum = power;
        if (this == null) return -1;
        Queue<PowerNode> queue = new LinkedList<>();
        queue.offer(this);
        while (!queue.isEmpty()) {
            int len = queue.size();
            for (int i = 0; i < len; i++) { // so that we can reach each level
                PowerNode node = queue.poll();
                for (PowerNode item : node.childrenAsPn()) { // for-Each loop to iterate over all childrens
                    queue.offer(item);
                }
            }
            for (PowerNode node : queue) {
                sum += node.power;
            }
        }
        return sum;
    }

    public String formatNode() {
        //Limit check
        if (this.children().size() > 0 && powerLimit > 0) {
            if (this.calculateThisPowerSum() > this.powerLimit) {
                return limitSurpassedFormat(this);
            }
            return limitOKFormat(this);
            // Root node has children, no parent, no power and no power limit.
        } else if (this.childrenPn.size() > 0 && this.powerLimit <= 0 && this.power <= 0 && this.calculateThisPowerSum() > 0) {
            return rootFormat(this);
        }
        //Normal device, leaf node.
        else {
            return deviceFormat(this);
        }
    }
    private String limitSurpassedFormat(PowerNode powerNode) {
        return ColouredJavaOutput.ANSI_RED + powerNode.name + "-> P :" + powerNode.power
                + " -> Σp :" + powerNode.calculateThisPowerSum() + "W Limit: "
                + ColouredJavaOutput.ANSI_BG_RED + ColouredJavaOutput.ANSI_BRIGHT_WHITE
                + powerNode.powerLimit + "(" + (powerNode.calculateThisPowerSum() - powerNode.getPowerLimit()) + "W Diff)"
                + ColouredJavaOutput.ANSI_RESET;
    }
    private String limitOKFormat(PowerNode powerNode) {
        if (powerNode.isPowerUnsure()) {
            return ColouredJavaOutput.ANSI_GREEN + powerNode.name + "-> P : " +
                    ColouredJavaOutput.ANSI_YELLOW + powerNode.power + "W (?)"
                    + ColouredJavaOutput.ANSI_GREEN + " -> Σp : "
                    + powerNode.calculateThisPowerSum() + "W Limit:"
                    + powerNode.powerLimit + "W (" + (powerNode.calculateThisPowerSum() - powerNode.getPowerLimit()) + "W Diff)"
                    + ColouredJavaOutput.ANSI_RESET;
        } else {
            return ColouredJavaOutput.ANSI_GREEN + powerNode.name + "-> P : " + powerNode.power + "W -> Σp : "
                    + powerNode.calculateThisPowerSum() + "W Limit:"
                    + powerNode.powerLimit + "W" + ColouredJavaOutput.ANSI_RESET;
        }
    }
    private String deviceFormat(PowerNode powerNode) {
        if (powerNode.isPowerUnsure()) {
            return powerNode.name + "-> P : " + ColouredJavaOutput.ANSI_YELLOW + powerNode.power + "W (?)" + ColouredJavaOutput.ANSI_RESET;
        } else {
            return powerNode.name + "-> P : " + powerNode.power + "W";
        }
    }

    private String rootFormat(PowerNode powerNode) {
        return "\nPower Report\n"
                + "************\n\n"
                + powerNode.name + " -<> Σp: " + powerNode.calculateThisPowerSum() + "W";
    }

    private class PowerComparatorDesc implements Comparator<PowerNode> {
        @Override
        public int compare(PowerNode o1, PowerNode o2) {
            return (int) o2.getPower() - (int) o1.getPower();
        }
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getPowerLimit() {
        return powerLimit;
    }

    public void setPowerLimit(double powerLimit) {
        this.powerLimit = powerLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPowerUnsure() {
        return powerUnsure;
    }

    public void setPowerUnsure(boolean powerUnsure) {
        this.powerUnsure = powerUnsure;
    }
}
package org.fps.power;

import hu.webarticum.treeprinter.TreeNode;
import hu.webarticum.treeprinter.printer.listing.ListingTreePrinter;

import java.util.LinkedList;
import java.util.Queue;

public class DisplayTree {
    public static void main(final String[] args) throws Exception {
        new ListingTreePrinter().print(buildTree());
    }

    public static double calculatePowerSum(PowerNode root) {
        double sum = root.getPower();
        if (root == null) return -1;
        Queue<PowerNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            for (int i = 0; i < len; i++) { // so that we can reach each level
                PowerNode node = queue.poll();
                for (PowerNode item : node.childrenAsPn()) { // for-Each loop to iterate over all childrens
                    queue.offer(item);
                }
            }
            for (PowerNode node : queue) {
                sum += node.getPower();
            }
        }
        return sum;
    }

    private static TreeNode buildTree() {
        PowerNode rootSocket = new PowerNode("SOCKET", 0, -1);
        PowerNode threeSplit = new PowerNode("WALL_3SPLIT", 0, 3500);
        PowerNode prj = new PowerNode("PRJ", 1.5, PowerNode.Unit.AMP);
        PowerNode fan = new PowerNode("FAN", 40);
        PowerNode ext1 = new PowerNode("EXTENSION_1", 0, 3500);
        PowerNode wifi = new PowerNode("WIFI", 0.7, PowerNode.Unit.AMP);
        PowerNode clock = new PowerNode("CLOCK", 10);
        PowerNode usbx3 = new PowerNode("USBx3", 0.8, PowerNode.Unit.AMP);
        PowerNode extMon = new PowerNode("EXTENSION_MON", 0, 3500);
        PowerNode mon1 = new PowerNode("MON1", 24);
        PowerNode mon2 = new PowerNode("MON2", 24);
        PowerNode ext2Usb = new PowerNode("EXTENSION_2USB", 0, 3500, PowerNode.Unit.AMP);
        PowerNode integrated2xUsb = new PowerNode("INTEGRATED2xUSB", 0.8, PowerNode.Unit.AMP);
        integrated2xUsb.setPowerUnsure(true);
        PowerNode persPcSwitch = new PowerNode("PERS_SWITCH", 0, 3500);
        PowerNode workPcSwitch = new PowerNode("WORK_SWITCH", 0, 3500);
        PowerNode persPc = new PowerNode("PERS_PC", 1.7, PowerNode.Unit.AMP);
        PowerNode workPc = new PowerNode("WORK_PC", 1.7, PowerNode.Unit.AMP);
        PowerNode mixer = new PowerNode("MIXER", 0.2, PowerNode.Unit.AMP);

        rootSocket.addChild(threeSplit);
        threeSplit.addChild(prj);
        threeSplit.addChild(fan);
        threeSplit.addChild(ext1);
        ext1.addChild(wifi);
        ext1.addChild(clock);
        ext1.addChild(usbx3);
        ext1.addChild(extMon);
        extMon.addChild(mon1);
        extMon.addChild(mon2);
        ext1.addChild(ext2Usb);
        ext2Usb.addChild(persPcSwitch);
        ext2Usb.addChild(workPcSwitch);
        persPcSwitch.addChild(persPc);
        workPcSwitch.addChild(workPc);
        ext2Usb.addChild(mixer);
        ext2Usb.addChild(integrated2xUsb);
        rootSocket.getPower();

        return rootSocket;
    }
}
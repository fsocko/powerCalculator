package org.fps.power;

import hu.webarticum.treeprinter.TreeNode;
import hu.webarticum.treeprinter.printer.listing.ListingTreePrinter;

import java.util.LinkedList;
import java.util.Queue;

public class MainCalculatePowerTree {
    public static void main(final String[] args) throws Exception {
        //new ListingTreePrinter().print(buildTree());
        JsonWriteRead.writeJson((PowerNode) buildTree());
        new ListingTreePrinter().print(JsonWriteRead.readJson());
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
        PowerNode extL = new PowerNode("EXTENSION_L", 0, 3680);
        PowerNode extC = new PowerNode("EXTENSION_C", 0, 3500);
        PowerNode extR2Usb = new PowerNode("EXTENSION_R_2USB", 0.8, 3500, PowerNode.Unit.AMP);
        extR2Usb.setPowerUnsure(true);
        //Devices

        PowerNode fan = new PowerNode("FAN", 40);
        //PowerNode prj = new PowerNode("PRJ", 1.5, PowerNode.Unit.AMP);
        PowerNode drill = new PowerNode("DRILL", 650);
        PowerNode iron = new PowerNode("IRON", 2300);

        PowerNode mon1 = new PowerNode("MON1", 24);
        PowerNode mon2 = new PowerNode("MON2", 24);
        PowerNode wifi = new PowerNode("WIFI", 0.7, PowerNode.Unit.AMP);
        PowerNode clock = new PowerNode("CLOCK", 10);
        PowerNode usbx3W = new PowerNode("USBx3_White", 0.8, PowerNode.Unit.AMP);
        PowerNode persPcSwitch = new PowerNode("PERS_SWITCH", 0, 3500);
        PowerNode workPcSwitch = new PowerNode("WORK_SWITCH", 0, 3500);
        PowerNode persPc = new PowerNode("PERS_PC", 2.5, PowerNode.Unit.AMP);
        PowerNode workPc = new PowerNode("WORK_PC", 1.7, PowerNode.Unit.AMP);
        PowerNode mixer = new PowerNode("MIXER", 0.2, PowerNode.Unit.AMP);

        rootSocket.addChild(threeSplit);
        threeSplit.addChild(extL);
        threeSplit.addChild(fan);
        threeSplit.addChild(extC);

        extC.addChild(extR2Usb);
        extC.addChild(wifi);
        extC.addChild(clock);
        extC.addChild(usbx3W);

        extL.addChild(mon1);
        extL.addChild(mon2);
        //extL.addChild(prj);
        extL.addChild(drill);

        //extL.addChild(iron);


        extR2Usb.addChild(persPcSwitch);
        extR2Usb.addChild(workPcSwitch);
        persPcSwitch.addChild(persPc);
        workPcSwitch.addChild(workPc);
        extR2Usb.addChild(mixer);

        return rootSocket;
    }
}
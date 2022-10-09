package org.fps.power;


import hu.webarticum.treeprinter.PlaceholderNode;
import hu.webarticum.treeprinter.TreeNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DisplayTreeTest {

    static PowerNode root;

    @BeforeAll
    static void setUp() throws Exception {

        root = buildTree();


    }

    /*
        SOCKET -> pΣ: 14498.8W
         └─WALL_3SPLIT-> P: 0.0 -> pΣ: 14498.8W Limit:3500.0W
            ├─PRJ-> P: 360.0W
            ├─FAN-> P: 40.0W
            └─EXTENSION_1-> P: 0.0 -> pΣ: 14098.8W Limit:3500.0W
               ├─WIFI-> P: 168.0W
               ├─CLOCK-> P: 10.0W
               ├─USBx3-> P: 192.0W
               ├─EXTENSION_MON-> P: 0.0 -> pΣ: 48.0W Limit:3500.0W
               │  ├─MON1-> P: 24.0W
               │  └─MON2-> P: 24.0W
               └─EXTENSION_2USB-> P: 0.8 -> pΣ: 13680.8W Limit:3500.0W
                  ├─INTEGRATED2xUSB-> P: 24.0W
                  ├─PERS_PC-> P: 408.0W
                  ├─WORK_PC-> P: 13200.0W
                  └─MIXER-> P: 48.0W
     */

    private static PowerNode buildTree() {
        PowerNode rootSocket = new PowerNode("SOCKET",0,-1 );
        PowerNode threeSplit = new PowerNode("WALL_3SPLIT",0,3500 );
        PowerNode prj = new PowerNode("PRJ",1.5, PowerNode.Unit.AMP);
        PowerNode fan = new PowerNode("FAN",40);
        PowerNode ext1 = new PowerNode("EXTENSION_1",0, 3500);
        PowerNode wifi = new PowerNode("WIFI", 0.7, PowerNode.Unit.AMP);
        PowerNode clock = new PowerNode("CLOCK", 10);
        PowerNode usbx3 = new PowerNode("USBx3", 0.8, PowerNode.Unit.AMP);
        PowerNode extMon = new PowerNode("EXTENSION_MON", 0, 3500);
        PowerNode mon1 = new PowerNode("MON1", 24);
        PowerNode mon2 = new PowerNode("MON2", 24);
        PowerNode ext2Usb = new PowerNode("EXTENSION_2USB", 0.8,3500);
        PowerNode integrated2xUsb = new PowerNode("INTEGRATED2xUSB", 24);
        PowerNode persPc= new PowerNode("PERS_PC", 1.7, PowerNode.Unit.AMP);
        PowerNode workPc = new PowerNode("WORK_PC", 55, PowerNode.Unit.AMP);
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
        ext2Usb.addChild(integrated2xUsb);
        ext2Usb.addChild(persPc);
        ext2Usb.addChild(workPc);
        ext2Usb.addChild(mixer);
        rootSocket.getPower();

        return rootSocket;
    }

    @Test
    public void testReturnSum() throws Exception {
        assertEquals(root.calculateThisPowerSum(), 14498.8);
    }

}
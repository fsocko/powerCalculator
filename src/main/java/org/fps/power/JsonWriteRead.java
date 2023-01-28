package org.fps.power;

import com.google.gson.Gson;

import java.io.*;

public class JsonWriteRead {

    private static final String filename = "D:\\power_report.json";

    public static void writeJson(PowerNode node) {
        // Create a new Gson object
        Gson gson = new Gson();
        try {
            //convert the Java object to json
            String jsonString = gson.toJson(node);
            //Write JSON String to file
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(jsonString);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PowerNode readJson() throws FileNotFoundException {
        PowerNode pn = null;
        Gson gson = new Gson();
        try {
            //Read the employee.json file
            BufferedReader br = new BufferedReader(new FileReader(filename));

            //convert the json to  Java object (Employee)
            pn = gson.fromJson(br, PowerNode.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return pn;
    }

}

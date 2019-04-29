package roomBookings;



import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic static class that allows one to read and write out
 * to a file in particular the roomlist.txt file.
 */

public class ReadAndWriteFile {

    public static ArrayList<String> readFile(String filePath) {

        ArrayList<String> data = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {

                data.add(currentLine);
            }
            reader.close();
        } catch (IOException e) {

            e.printStackTrace();
        }


        return data;
    }


    public static void writeFile(String filePath, List<String> data){

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            for (String item : data){
                writer.write(item + "\n");
            }

            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }


    }
}

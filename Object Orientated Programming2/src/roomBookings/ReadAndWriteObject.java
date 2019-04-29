package roomBookings;



import java.io.*;

/**
 * provides static methods for reading and writing out an object to a binary file format.
 * Use serialization to convert the state of an object to a byte stream that can be saved and
 * reconstructed and loaded back into memory at a later point.
 */
public class ReadAndWriteObject  {

    public static void saveDayObject(ReadWriteObjInterface object) {

        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(object.getSaveFilePath()));
            os.writeObject(object);
            os.close();
            System.out.println("Save successful");

        } catch (IOException e) {
            System.out.println("SYSTEM ERROR: Database to User data desynchronization detected. \n"+
                    "system object at:  "+ object.getSaveFilePath() + " can not be found. \n" +
                    " previous booking data has been lost. \n Users account contains booking information" +
                    " that no longer exists.\n");
        }


    }

    public static ReadWriteObjInterface loadDayObject(String filePath) {

        ReadWriteObjInterface day = null;

        if (filePath.endsWith("/day.bin/") && filePath.startsWith("resources/bookingsFileStructure/") || filePath.endsWith(".bin") && filePath.startsWith("resources/users")) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
                day = (ReadWriteObjInterface) in.readObject();
                System.out.println("Load successful");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("SYSTEM ERROR: Database to User data desynchronization detected. \n"+
                        "system object at:  "+ filePath + " can not be found. \n" +
                        " previous booking data has been lost. \n Users account contains booking information" +
                        " that no longer exists.\n");
            }

        } else{
            System.out.println("Invalid load File path");;
        }

        return day;
    }
}

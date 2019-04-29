package roomBookings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class creates a new epty day object containing a Hash of room strings to boolean[] hours booked arrays.
 * serializable is implemented to allow the day object to be outStreamed to a .bin file.
 */
public class Day implements Serializable, ReadWriteObjInterface {
    private static final String roomListFilePath = "resources/rooms/roomList.txt";
    private String saveFilePath;
    private Map<String, boolean[]> bookingTimetable;

    public Day(String saveFilePath){

        if (saveFilePath.endsWith("/day.bin/") && saveFilePath.startsWith("resources/bookingsFileStructure/")){
            this.saveFilePath = saveFilePath;
            this.bookingTimetable = initBookingTimetable();

        } else {
            System.out.println("Invalid filePath nothing has been saved");
        }

    }

    public boolean checkValidBooking(Date date, String room){

        boolean[] bookingRestrictions;


        // get reference map key-value object
        bookingRestrictions = this.getBookingTimetable().get(room);

        int index = date.getHour() - 9;

        if(!bookingRestrictions[index]){
            // changes made here will directly effect the DayObject map
            bookingRestrictions[index] = true;

        }else {
            return false;
        }

        return true;

    }

    private Map<String, boolean[]> initBookingTimetable(){
        ArrayList<String> roomList = ReadAndWriteFile.readFile(roomListFilePath);

        Map<String, boolean[]> timetable = new HashMap<>();
        for (String room: roomList){
            timetable.put(room, new boolean[9]);
        }

        return timetable;
    }



    public Map<String, boolean[]> getBookingTimetable() {
        return bookingTimetable;
    }

    public String getSaveFilePath() {
        return saveFilePath;
    }

    public void setBookingTimetable(String key, boolean[] value) {
        this.bookingTimetable.put(key, value);
    }



}

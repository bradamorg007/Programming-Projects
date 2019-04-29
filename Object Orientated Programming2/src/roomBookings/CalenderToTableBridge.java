

package roomBookings;

import java.util.ArrayList;


/**
 * This class acts as a bridge between the Mycalender bookings object and the draw Jtables methods.
 * It organise the day into a format that can be easily inputted into a Jtable.
 */
public class CalenderToTableBridge implements ReadWriteObjInterface{
    private String[] ColumnTime;
    private String[][] cellData;
    private static final String TEMP_DATA_STORE_PATH = "resources/tempData/";
    private static final String TEMP_DATA_FILE_NAME = "bridgeData.bin/";
    private String dataName;


    /**
     *
     * @param myBookingOnSameDay contains a list of all the users booking that happen on the same day so that
     *                           they can all be viewed at once in a table/grid layout.
     */
    public CalenderToTableBridge(ArrayList<Booking> myBookingOnSameDay){

       // new File(TEMP_DATA_STORE_PATH).mkdir();

        // the 0 is safe as the array list only contains objects on the same day and thus their associatedDaypath will be the same
        Day dayObj = (Day) ReadAndWriteObject.loadDayObject(myBookingOnSameDay.get(0).getFilePathAssociatedDay());

        String[] keys = dayObj.getBookingTimetable().keySet().toArray(new String[0]); // returns an array of keys
        boolean[][] values = dayObj.getBookingTimetable().values().toArray(new boolean[0][0]);
        String[][] dataSet = new String[values.length][values[0].length + 1];

        String dataName;
        // convert the booleans to human readable langauge in context or room bookings
        for (int k = 0; k < values.length; k++ ){

            for (int l = 0; l < values[0].length; l++){


                dataSet[k][0] = keys[k];

                if(values[k][l]) {
                    dataSet[k][l+1] = "BOOKED";
                    for (Booking booking : myBookingOnSameDay) {
                        if (keys[k].equals(booking.getRoom()) && (booking.getDate().getHour()-9)==l) {

                            dataSet[k][l+1] = booking.getPurpose().toUpperCase();

                        }
                    }
                }else {
                    dataSet[k][l+1] = "Available";
                }
            }
        }


        this.dataName = myBookingOnSameDay.get(0).getDate().getYear() + " " + myBookingOnSameDay.get(0).getDate().getMonth() + " " + myBookingOnSameDay.get(0).getDate().getDay();
        this.ColumnTime = new String[]{"Rooms", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"};
        this.cellData = dataSet;


    }

    public void saveBridgeData(){
        ReadAndWriteObject.saveDayObject(this);
    }

    public String getSaveFilePath(){
        return TEMP_DATA_STORE_PATH + TEMP_DATA_FILE_NAME;
    }

    public String[] getColumnTime() {
        return ColumnTime;
    }

    public void setColumnTime(String[] columnTime) {
        ColumnTime = columnTime;
    }

    public String[][] getCellData() {
        return cellData;
    }

    public void setCellData(String[][] cellData) {
        this.cellData = cellData;
    }

    public String getDataName() {
        return dataName;
    }
}

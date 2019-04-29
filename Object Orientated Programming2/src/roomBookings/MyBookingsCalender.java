package roomBookings;

import javax.swing.*;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is the Main interface object for the user. All a user bookings are stored within this object.
 * all booking, cancellation and viewing functions are called from the methods of this class.
 *
 * The general idea is that a booking is stored is a folder structure denoted by the year month and day.
 * each day contains a day.bin file that can be read back into the system as an object. Each day object
 * contains a hash map with the Key(rooms) to value (boolean[] hours) association. each hour is a an array
 * of booleans each element incrementally corresponding to a valid booking time. with false meaning the hour booking i
 * is available with respect to its String (room) key and true meaning the time and room is booked.
 * indexing the hour array is performed via x-min(hour). this along with the folder and hash Map allows the program
 * to more efficiently search for booking information and minimise iterative looped searching.
 */
public class MyBookingsCalender implements ReadWriteObjInterface, Serializable {
    private static final int MAX_FUTURE_BOOKING = 2;
    private static final String topLevelFolderResources = "resources";
    private static final String roomFileFolderName = "resources/rooms/";
    private static final String fileNameResources = "resources/bookingsFileStructure/";
    private static final String fileNameResourcesUsers = "resources/users/";
    private static final String roomListFilePath = "resources/rooms/roomList.txt";
    private static final String DAY_TIME_TABLE_FILEPATH  = "day.bin";
    private static final String[] BACKUP_DEFAULT_ROOMS = new String[]{"R101", "R102","R103", "R104", "R105", "R201", "R202", "R203", "R204", "R205", "R301",  "R302", "R303",  "R304", "R305"};

    private boolean emptyObjectMade = false;
    private String saveFilePath;
    private ArrayList<Booking> myBookings;
    private String userName;

    /**
     * Constructor: checks for suplicate usernames, invalid username entries.
     * checks are made on the folder resources. if it does not exist it is created. if the list of
     * rooms is not created then the system will use a built in backup and recreate the folder.
     * the check and recreation process occurs for both bookingFileStructure and users folders.
     * @param userName The Room bookings account name of the user.
     */
    public MyBookingsCalender(String userName){
        if (MyBookingsCalender.userNameChecker(userName, "CREATE_NEW_USER") && checkNames(userName)) {
            this.userName = userName;
            this.myBookings = new ArrayList<>();
            this.saveFilePath = fileNameResourcesUsers + userName + ".bin/";

            if (!new File(topLevelFolderResources).exists()){
                new File(topLevelFolderResources).mkdir();
                System.out.println("WARNING: Top level data structure at: " + topLevelFolderResources + " Has not been found. Creating new directories now...");
            }

            if (!new File(roomListFilePath).exists()){
                new File(roomFileFolderName).mkdir();
                ReadAndWriteFile.writeFile(roomListFilePath, Arrays.asList(BACKUP_DEFAULT_ROOMS));
                System.out.println("WARNING: " + roomListFilePath + " Has not been found. Back up Store of rooms is being created...");
            }

            if (!new File(fileNameResources).exists()){

                new File(fileNameResources).mkdir();
                System.out.println("WARNING: " + fileNameResources + " Has not been found. Creating new directories now...");
            }

            if(!new File(fileNameResourcesUsers).exists()){

                new File(fileNameResourcesUsers).mkdir();
                System.out.println("WARNING: " + fileNameResourcesUsers + " Has not been found. Creating new directories now...");

            }
            this.saveMyBookingCalender(this);
        }else {
            throw new IllegalArgumentException("Invalid UserName");
        }
    }

    /**
     *
     * @param userName the users Calender account.
     * @return true is the username consists of letters and numbers only. empty, whitespaced or
     * non-alphabetical and numerical values return false.
     */
    private boolean  checkNames(String userName){

        Pattern p = Pattern.compile("[a-zA-Z0-9]+");
        Matcher match1 = p.matcher(userName);

        return match1.find() && !userName.matches("^\\s*$");


    }

    /**
     * This method allows a user to book a room.  First user input is checked, whether a valid date and time
     * has been made. If this is true then the users input is used as the filepath leads the program to an
     * existing day object and if not then then programs creates the folder structure. The program will only
     * create folder paths that do yet exist to prevent overwriting valid day objects. If the day exists then
     * the program will load the day into memory else it will initalize a new empty day. Finally if the
     * the program will check that the booking is not already day taken in the day object. If a succesfully booking is
     * made then the system saves the users profile and newly written to day object back to a binary format.
     * @param day
     * @param hour
     * @param room = room to be booked
     * @param purpose = why the room is being booked
     */
    public void bookRoom(int year, String month, int day, int hour, String room, String purpose){

        try{
            Date date = new Date( day,  month,  year,  hour);

            if(checkTime(date)) {

                String yearStr = String.valueOf(year);
                String dayStr = String.valueOf(day);
                month = month + "/";

                String yearPath = fileNameResources + yearStr + "/";
                String monthPath = yearPath + month;
                String dayPath = monthPath + dayStr +  "/";

                File fileNameYear = new File(yearPath);
                File fileNameMonth = new File(monthPath);
                File fileNameDay = new File(dayPath);


                if (!fileNameYear.exists()) {
                    fileNameYear.mkdir();
                    fileNameMonth.mkdir();
                    fileNameDay.mkdir();

                    Day dayObject = new Day(dayPath + DAY_TIME_TABLE_FILEPATH + "/");
                    ReadAndWriteObject.saveDayObject((ReadWriteObjInterface) dayObject);

                } else if (!fileNameMonth.exists()) {
                    fileNameMonth.mkdir();
                    fileNameDay.mkdir();
                    Day dayObject = new Day(dayPath + DAY_TIME_TABLE_FILEPATH + "/");
                    ReadAndWriteObject.saveDayObject((ReadWriteObjInterface) dayObject);

                } else if (!fileNameDay.exists()) {
                    fileNameDay.mkdir();
                    Day dayObject = new Day(dayPath + DAY_TIME_TABLE_FILEPATH + "/");
                    ReadAndWriteObject.saveDayObject((ReadWriteObjInterface)dayObject);
                }

                // LOAD OBJECT read the day file
                Day dayObject = (Day) ReadAndWriteObject.loadDayObject(dayPath + DAY_TIME_TABLE_FILEPATH + "/");

                try {

                    if (dayObject.checkValidBooking(date, room)) {
                        ReadAndWriteObject.saveDayObject((ReadWriteObjInterface) dayObject);
                        Booking booking = new Booking(date, room, purpose, dayObject.getSaveFilePath());
                        this.getMyBookings().add(booking);
                        this.saveMyBookingCalender(this);
                        System.out.println("\nCONGRATULATIONS: Your booking on " + booking.getDate().getYear() + " " + booking.getDate().getMonth() + " " + booking.getDate().getDay() + "\n at " + booking.getDate().getHour()+ " in room " + booking.getRoom() + " has been completed \n");
                    } else {

                        System.out.println("ERROR: The room you are trying to book is not available");
                    }
                }catch (NullPointerException e){

                    System.out.println("ERROR: The room you are trying to book does not exist");
                }



            }else {

                System.out.println("ERROR: Invalid calenderBookingClasses.Booking time");
            }


        }catch (IllegalArgumentException e){
            System.out.println("ERROR: The booking date is invalid");
        }


    }


    /**
     *
     * @param date Object that contains valid year,month day and hour information.this function checks
     *             whether a booking can be made relative to the current date and time now.
     * @return true if the time of the booking is valid
     */
    public static boolean checkTime(Date date){

        int MAX_FUTURE_BOOKING = 2;

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-kk").format(Calendar.getInstance().getTime());
        String[] timeStampArr = timeStamp.split("-");
        // can only book 2 years in advance

        int currentYear = Integer.valueOf(timeStampArr[0]);
        int currentMonth = Integer.parseInt(timeStampArr[1]);
        int currentDay = Integer.parseInt(timeStampArr[2]);
        int currentHour = Integer.parseInt(timeStampArr[3]);

        if (currentHour == 24){
            currentHour = 0;
        }
        if (date.getYear() == currentYear && date.getNumericValMonth() == currentMonth && date.getDay() == currentDay && date.getHour() >= currentHour){

            return true;

        }else if(date.getYear() == currentYear && date.getNumericValMonth() == currentMonth && date.getDay() > currentDay){

            return true;

        }else if (date.getYear() == currentYear && date.getNumericValMonth() > currentMonth ){

            return true;
        }else if(date.getYear() > currentYear && date.getYear() < currentYear + MAX_FUTURE_BOOKING){
            return true;

        }else if(date.getYear() == currentYear + MAX_FUTURE_BOOKING && date.getNumericValMonth() < currentMonth){
            return true;

        }else if (date.getYear() == currentYear + MAX_FUTURE_BOOKING && date.getNumericValMonth() == currentMonth  && date.getDay() < currentDay){

            return true;
        }else
            return date.getYear() == currentYear + MAX_FUTURE_BOOKING && date.getNumericValMonth() == currentMonth && date.getDay() == currentDay && date.getHour() < currentHour;

    }

    /**
     *
     * @param myBookingsCalenderObj saves the users account to a binary file in the user folder
     */
    public void saveMyBookingCalender(MyBookingsCalender myBookingsCalenderObj){
        ReadAndWriteObject.saveDayObject(myBookingsCalenderObj);
    }

    public static MyBookingsCalender loadMyBookingCalender(String userName){

        if(userNameChecker(userName, "LOAD_EXISTING_USER")){
            return (MyBookingsCalender) ReadAndWriteObject.loadDayObject(fileNameResourcesUsers + userName + ".bin");
        }


        return null;

    }

    /**
     *
     * @param userName users unique id
     * @param returnToggle toggles the logic to determine the return type
     * @return
     */
    private static boolean userNameChecker(String userName, String returnToggle){

        File folder = new File(fileNameResourcesUsers);
        File[] fileList = folder.listFiles();

        if(fileList != null) {
            for (File file : fileList) {

                String fileStr = file.toString();
                String[] fileUserNameArray1= fileStr.split("/");
                String[] fileUserNameArray2 = fileUserNameArray1[2].split(".bin");
                String folderUsername = fileUserNameArray2[0];

                if (folderUsername.equals(userName)) {
                    System.out.println("UserName & profile has been found");

                    if(returnToggle.equals("CREATE_NEW_USER")) {
                        return false;
                    }else if(returnToggle.equals("LOAD_EXISTING_USER")){
                        return true;
                    }
                }
            }


        }


        if(returnToggle.equals("CREATE_NEW_USER")) {
            System.out.println("New User Detected");
            return true;
        }

        System.out.println("ERROR: PreExisting account has been detected");
        return false;
    }


    /**
     * this function allows a user to cancel a booking from their Mybookings list.
     * If cancelling their booking causes the day to be empty then the day will be deleted,
     * likewise if the day being empty cause the month folder to be empty then the month folder
     * will be deleted, this also includes the year. All changes are saved to the resources database
     * @param year the year is ..... erm the... year I suppose.
     * @param month the subdivides of a ... year
     * @param day the day
     * @param hour the hour
     * @param room is the room to be cancelled.
     */
    public void cancelBooking(int year, String month, int day, int hour, String room){


        ArrayList<Booking> bookingList = this.getMyBookings();


        for(Booking booking : bookingList){

            int bookingYear = booking.getDate().getYear();
            String bookingMonth = booking.getDate().getMonth();
            int bookingDay = booking.getDate().getDay();
            int bookingHour = booking.getDate().getHour();
            String bookingRoom = booking.getRoom();

            if (bookingYear == year && bookingMonth.equals(month) && bookingDay == day && bookingHour == hour && bookingRoom.equals(room)){

                Day dayCancel = (Day) ReadAndWriteObject.loadDayObject(booking.getFilePathAssociatedDay());
                boolean[] dayHourList = dayCancel.getBookingTimetable().get(bookingRoom);

                int hourIndex = bookingHour - 9;

                dayHourList[hourIndex] = false;
                bookingList.remove(booking);

                ArrayList<String>  roomList = ReadAndWriteFile.readFile(roomListFilePath);
                boolean match = false;

                // check to see the rest of the day for each room is epty if so it needs to be deleted
                for(String roomItem : roomList){

                    dayHourList = dayCancel.getBookingTimetable().get(roomItem);

                    for(boolean hourItem : dayHourList){

                        if(hourItem){

                            match = true;
                            break;
                        }

                    }

                    if(match){
                        break;
                    }

                }

                if(!match){

                    // cascade upwards from day object folder
                    File fileDelete = new File(booking.getFilePathAssociatedDay());

                    deleteFileOrFolder(fileDelete, "The empty day has been successfully deleted",
                            "Error: empty day has not been deleted" );
                }else {
                    // if you dont delete the day then save it
                    ReadAndWriteObject.saveDayObject(dayCancel);
                }

                // if the day has been saved then the day month and year folder will not be deleted.
                String folderPath = dayCancel.getSaveFilePath();
                String[] folder = folderPath.split("/");

                String dayFolderPath = folder[0] + "/" + folder[1] + "/" + folder[2] + "/" + folder[3] + "/" + folder[4] + "/";
                String monthFolderPath = folder[0] + "/" + folder[1] + "/" + folder[2] + "/" + folder[3] + "/";
                String yearFolderPath = folder[0] + "/" + folder[1] + "/" + folder[2] + "/";

                File[] files = { new File(dayFolderPath), new File(monthFolderPath), new File(yearFolderPath)};
                File[] dayFileList =  files[0].listFiles();


                if (dayFileList != null && dayFileList.length == 0) {

                    deleteFileOrFolder(files[0], "The empty folder has been successfully deleted",
                            "Error: empty folder has not been deleted");
                }

                File[] monthFileList =  files[1].listFiles();
                if (monthFileList != null && monthFileList.length == 0) {

                    deleteFileOrFolder(files[1], "The empty folder has been successfully deleted",
                            "Error: empty folder has not been deleted");
                }

                File[] yearFileList =  files[2].listFiles();
                if (yearFileList != null && yearFileList.length == 0) {
                    deleteFileOrFolder(files[2], "The empty folder has been successfully deleted",
                            "Error: empty folder has not been deleted");
                }


                saveMyBookingCalender(this);
                break;
            }



        }


    }

    /**
     * This method allows a user to display a their bookings visually.
     *
     * @param year
     * @param month
     * @param day
     */
    public void viewMyBooking(int year, String month, int day) {

        ArrayList<Booking> bookingList = this.getMyBookings();

        ArrayList<Booking> myBookingOnSameDay = new ArrayList<>();

        boolean match = false;
        for (Booking booking : bookingList) {

            int bookingYear = booking.getDate().getYear();
            String bookingMonth = booking.getDate().getMonth();
            int bookingDay = booking.getDate().getDay();
            int bookingHour = booking.getDate().getHour();
            String bookingRoom = booking.getRoom();

            if (bookingYear == year && bookingMonth.equals(month) && bookingDay == day) {

                myBookingOnSameDay.add(booking);
                match = true;
            }
        }

        if (match) {
            CalenderToTableBridge calenderToTableBridge = new CalenderToTableBridge(myBookingOnSameDay);
            JFrame jf = new JFrame("h");
            GenerateTableView.runTable(calenderToTableBridge);
        }

        System.out.println("The Booking you are trying to view does not exist within your booking list");
    }

    private void deleteFileOrFolder(File file, String message1, String message2){

        if(file.delete()){
            System.out.println(message1);
        }else {
            System.out.println(message2);
        }
    }


    public ArrayList<Booking> getMyBookings() {
        return myBookings;
    }

    public String getSaveFilePath() {
        return saveFilePath;
    }

    public String getUserName() {
        return userName;
    }

    private boolean isEmptyObjectMade() {
        return emptyObjectMade;
    }

    private void setEmptyObjectMade(boolean emptyObjectMade) {
        this.emptyObjectMade = emptyObjectMade;
    }
}

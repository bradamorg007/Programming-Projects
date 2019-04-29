package roomBookings;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class test {

    public static void main(String[] args) {

        //testMaptoArray();
        try {
//            MyBookingsCalender brad = new  MyBookingsCalender("brad");
//            MyBookingsCalender dan = new MyBookingsCalender("Dan");
//            MyBookingsCalender jen = new MyBookingsCalender("jen");
//            MyBookingsCalender tom = new MyBookingsCalender("tom");
//
//            brad.bookRoom(2019, "February", 13, 14, "R104", "massage");
//
//            dan.bookRoom(2019, "February", 13, 9, "R101", "Running");
//            dan.bookRoom(2019, "February", 13, 10, "R203", "GAMING");
//
//            jen.bookRoom(2019, "February", 13, 17, "R105", "Meeting");
//            tom.bookRoom(2019, "February", 13, 14, "R102", "GroupStudy");
//
//            dan.viewMyBooking(2019, "February", 13);

            MyBookingsCalender dan = MyBookingsCalender.loadMyBookingCalender("Dan");
//            dan.bookRoom(2019, "February", 13, 10, "R203", "NEW GAMING");
            dan.viewMyBooking(2019, "February", 13);


            MyBookingsCalender jen = MyBookingsCalender.loadMyBookingCalender("jen");
            jen.viewMyBooking(2019, "February", 13);




//
//            anotherCal.bookRoom(2019, "February", 13, 14, "R104", "massage");
//            bookingsCalender.viewMyBooking(2019, "February", 13);
            int a = 0;
//            MyBookingsCalender MyCalender = new MyBookingsCalender("brad");
//            // delete day but not the month;
//            MyCalender.bookRoom(2019, "February", 28, 15, "R101", "massage");
//            MyCalender.bookRoom(2019, "February", 13, 15, "R104", "massage");
//
//            // delete everything
//            MyCalender.bookRoom(2018, "December", 5, 11, "R102", "meeting");
//
//            // delete the day and month but not the year
//            MyCalender.bookRoom(2020, "June", 1, 15, "R101", "massage");
//            MyCalender.bookRoom(2020, "February", 28, 15, "R101", "massage");
//
//            MyCalender.bookRoom(2019, "February", 13, 9, "R101", "group study");
//            MyCalender.bookRoom(2019, "February", 14, 9, "R102", "group study");
//            MyCalender.bookRoom(2019, "June", 13, 9, "R104", "group study");
//
//            myBookingsCalender.cancelBooking(2019, "June", 13, 9, "R104");
//
//            calenderBookingClasses.MyBookingsCalender myBookingsCalender = calenderBookingClasses.MyBookingsCalender.loadMyBookingCalender("brad");
//            myBookingsCalender.bookRoom(2019, "February", 13, 12, "R101", "group fighting");

//            calenderBookingClasses.MyBookingsCalender myBookingsCalender = calenderBookingClasses.MyBookingsCalender.loadMyBookingCalender("brad");
//            myBookingsCalender.cancelBooking(2019, "February", 13, 12, "R101");
//            myBookingsCalender.cancelBooking(2019, "February", 14, 9, "R102");
//
//            int a = 0;

        }catch (NullPointerException e){
            System.out.println("The Object is null and can not be used as the load() was unable to find the " +
                    "MybookingCalender file");
        }
//
//
//        // delete everything
//        MyCalender.cancelBooking(2018, "December", 5, 11, "R102");

        // check to see if changes made are persisted across sessions
//        calenderBookingClasses.MyBookingsCalender myBookingsCalender = calenderBookingClasses.MyBookingsCalender.loadMyBookingCalender("brad");
//        myBookingsCalender.cancelBooking(2020, "June", 1, 15, "R101");



//          calenderBookingClasses.MyBookingsCalender myCalenderBrad = calenderBookingClasses.MyBookingsCalender.loadMyBookingCalender("brad");
//          myCalenderBrad.bookRoom(2019, "February", 2, 10, "R101", "massage");
//          myCalenderBrad.bookRoom(2018, "March", 13, 9, "R104", "massage");


//        testFolderList();
//        testRandom();
//        //testMap();
//        calenderBookingClasses.MyBookingsCalender myBookingsCalender = new calenderBookingClasses.MyBookingsCalender("Steph");
//
//        myBookingsCalender.bookRoom(2018, "December", 25, 16, "R102", "sex");
//        myBookingsCalender.bookRoom(2018, "December", 25, 12, "R102", "can drumming");
//
//        calenderBookingClasses.MyBookingsCalender bradsMyBookings = new calenderBookingClasses.MyBookingsCalender("Brad");
//        bradsMyBookings.bookRoom(2018, "December", 25, 16, "R102", "can drumming");
//        bradsMyBookings.bookRoom(2019, "December", 25, 16, "R102", "can drumming");
//        bradsMyBookings.bookRoom(2018, "December", 25, 12, "R101", "can drumming");
//        bradsMyBookings.bookRoom(2018, "December", 25, 12, "R10SS", "can drumming");
//
//        calenderBookingClasses.MyBookingsCalender jeffsMyBookings = new calenderBookingClasses.MyBookingsCalender("jeff");
//        jeffsMyBookings.bookRoom(2018, "May", 2, 16, "R102", "can drumming");
//        jeffsMyBookings.bookRoom(2020, "December", 2, 16, "R102", "can drumming");
//        jeffsMyBookings.bookRoom(2020, "December", 2, 9, "R104", "can drumming");
//        jeffsMyBookings.bookRoom(2020, "December", 1, 16, "R101", "can drumming");
//        jeffsMyBookings.bookRoom(2021, "June", 2, 16, "R101", "can drumming");
//        jeffsMyBookings.bookRoom(2018, "July", 2, 16, "R102", "can drumming");
//
    }


    public static void mainCheckTime(){
        Date dateSame = new Date(1, "December", 2018,  15);
        Date dateSame2 = new Date(25, "December", 2018, 16);
        Date dateSame3 = new Date(12, "December", 2018, 12);

        Date date2018= new Date(25, "December", 2018, 16);
        Date date2019 = new Date(25, "December", 2019, 16);
        Date date2020 = new Date(25, "October", 2020, 16);
        Date dateUnder = new Date(25, "December", 2017, 16);
        Date dateOver = new Date(25, "December", 2025, 16);
        Date dateMAX = new Date(1, "December", 2020, 16);
        Date dateMAXEarlier = new Date(1, "December", 2020, 17);
        Date dataHourGreater = new Date(2, "December", 2018, 9);


        boolean ValidSame1 = checkTime(dateSame);
        boolean ValidSame2 = checkTime(dateSame2);
        boolean ValidSame3 = checkTime(dateSame3);

        boolean Valid1 = checkTime(date2018);
        boolean Valid2 = checkTime(date2019);
        boolean Valid3 = checkTime(date2020);
        boolean Valid4 = checkTime(dateOver);
        boolean Valid5 = checkTime(dateUnder);
        boolean Valid6 = checkTime(dateMAX);
        boolean Valid7 = checkTime(dateMAXEarlier);
        boolean Valid8 = checkTime(dataHourGreater);
    }

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


    public static void testMap(){

        Map<String, boolean[]> map = new HashMap<>();

        String[] rooms = {"R101", "R102", "R103"};

        for(String room : rooms) {
            map.put(room, new boolean[9]);
        }


        boolean[] c = map.get("R102");
        boolean[] d = map.get("R103");

        c[7] = true;
        d[1] = true;

        map.put("R102", c);


    }

    public static void testMaptoArray(){

        String bRoom1 = "R102";
        int time1 = 11;
        String purpose = "Group Study";


        Day dayObj = (Day) ReadAndWriteObject.loadDayObject("resources/bookingsFileStructure/2018/December/5/day.bin/");

        String[] keys = dayObj.getBookingTimetable().keySet().toArray(new String[0]); // returns an array of keys
        boolean[][] values = dayObj.getBookingTimetable().values().toArray(new boolean[0][0]);
        int secondDim = values[0].length;
        String[][] dataSet = new String[values.length][values[0].length];

        for (int k = 0; k < dataSet.length; k++ ){

            for (int l = 0; l < dataSet[0].length; l++){

                if(values[k][l]) {
                    dataSet[k][l] = "Unavailable";
                }else {
                    dataSet[k][l] = "Available";
                }

            }
        }

        int roomIndex = 0;
        for(int i = 0; i < keys.length; i++){

            if(keys[i].equals(bRoom1)){
                roomIndex = i;
            }
        }

        dataSet[roomIndex][time1-9] = purpose;


        // map.entrySet().toArray();
    }

    public static void testRandom(){

        int a = (int)(Math.random() *1000000);
        String b = String.valueOf(a);
        int c = b.hashCode();
        String d = String.valueOf(c);

    }


    public static void test2(){


    }

    public static void testFolderList(){

//
        File folder = new File("resources/users");
        File[] fileList = folder.listFiles();
        String file = fileList[0].toString();
        String[] username = file.split("/");
        String a = username[2];

        String[] D = a.split(".bin");

        System.out.println(D);
//        String username = "brad";
//
//            File folder = new File("resources/users");
//            File[] fileList = folder.listFiles();
//
//
//            if(fileList != null) {
//                    for (File file : fileList) {
//
//                        String fileStr = file.toString();
//
//                        if (fileStr.equals(username)) {
//                            System.out.println("return True");
//                        }
//                    }
//            }
//
//        System.out.println("return false");
    }


//    public static void saveDayObject(calenderBookingClasses.ReadWriteObjInterface object) {
//
//        calenderBookingClasses.ReadWriteObjInterface saveObj = null;
//        if (object.getClassName().equals("Class.calenderBookingClasses.Day")){
//            saveObj = (calenderBookingClasses.Day) object;
//        }else if(object.getClassName().equals("Class.MyCalender")){
//            saveObj = (calenderBookingClasses.MyBookingsCalender) object;
//        }
//
//        if(saveObj != null) {
//            try {
//                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(saveObj.getSaveFilePath()));
//                os.writeObject(object);
//                os.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("Object to be saved is neither Object of type calenderBookingClasses.Day or calender");
//
//    }

}

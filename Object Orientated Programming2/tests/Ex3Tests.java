
import org.junit.Test;
import roomBookings.*;

import java.io.File;

import static org.junit.Assert.*;



public class Ex3Tests implements ReadWriteObjInterface {


    @Test //Link1
    public void SingleBooking() {

        int year = 2019;
        String month = "May";
        int day = 20;
        int hour = 15;
        String testRoom = "R104";
        String purpose = "Company Meeting";

        String dataFilePath = "resources/bookingsFileStructure/" + year + "/" + month + "/" +
                day + "/" + "day.bin/";

        MyBookingsCalender bradCalender = new MyBookingsCalender("dan");
        bradCalender.bookRoom(year, month, day, hour, testRoom, purpose);


        Booking booking = bradCalender.getMyBookings().get(0);

        Day dayObj = (Day) ReadAndWriteObject.loadDayObject(dataFilePath);

        boolean[] hoursOfDay = dayObj.getBookingTimetable().get(testRoom);

        assertEquals(year, booking.getDate().getYear());
        assertEquals(month, booking.getDate().getMonth());
        assertEquals(day, booking.getDate().getDay());
        assertEquals(testRoom, booking.getRoom());
        assertEquals(purpose, booking.getPurpose());
        assertTrue(hoursOfDay[booking.getDate().getHour() - 9]);

    }

    @Test // Link1  tests Cancellations across program sessions
    public void singleBookingCancelation() {

        int year = 2019;
        String month = "May";
        int day = 20;
        int hour = 15;
        String testRoom = "R104";
        String purpose = "Company Meeting";

        // add new booking to the day so it isnt deleted
        MyBookingsCalender extraGuy = new MyBookingsCalender("Extra Guy");
        extraGuy.bookRoom(year, month, day, 12, "R101", "Gaming");

        String dataFilePath = "resources/bookingsFileStructure/" + year + "/" + month + "/" +
                day + "/" + "day.bin/";

        MyBookingsCalender bradCalender = MyBookingsCalender.loadMyBookingCalender("Brad");
        bradCalender.cancelBooking(year, month, day, hour, testRoom);

        int expectedBookingListSize = 0;
        int actualBookingListSize = bradCalender.getMyBookings().size();
        assertEquals(expectedBookingListSize, actualBookingListSize);

        Day dayObject = (Day) ReadAndWriteObject.loadDayObject(dataFilePath);

        boolean[] hoursOfDay = dayObject.getBookingTimetable().get(testRoom);

        assertFalse(hoursOfDay[hour - 9]);

    }

    @Test //Link2
    public void bookAndCancelMultipleBookings(){

        int[] years =    {2019,  2020,    2018,       2018,       2020,      2019,     2019,      2020,    2020,    2018,       2019,    2018,      2018,       2020};
        String[] months = {"May", "June", "December", "December", "January", "August", "February", "March", "April", "December", "July", "December", "December", "November"};
        int[] days =      {20,     12,     23,          23,          1,        2,          28,        23,      17,      19,         12,      30,          29,          11};
        int[] hours =     {17,     9,     16,          14,          13,        11,          10,        12,      9,      10,         15,      16,          17,          17};
        String[]rooms =   {"R101", "R102","R103",     "R104",     "R105",    "R201",      "R202",     "R203",  "R204", "R205",    "R301",   "R302",     "R303",      "R304"};
        String[] purposes = {"Drugs", "running", "playing", "crying", "smoking", "execution", "staring", "headbanging", "toilet", "orange peeling", "raging", "gaming", "FIST PUMPING", "party", "meeting"};


        MyBookingsCalender person1 = new MyBookingsCalender("person1");
        MyBookingsCalender person2 = new MyBookingsCalender("person2");
        MyBookingsCalender person3 = new MyBookingsCalender("person3");
        MyBookingsCalender person4 = new MyBookingsCalender("person4");

        MyBookingsCalender person5 = new MyBookingsCalender("person5");
        MyBookingsCalender person6 = new MyBookingsCalender("person6");
        MyBookingsCalender person7 = new MyBookingsCalender("person7");
        MyBookingsCalender person8 = new MyBookingsCalender("person8");

        MyBookingsCalender person9 = new MyBookingsCalender("person9");
        MyBookingsCalender person10 = new MyBookingsCalender("person10");
        MyBookingsCalender person11 = new MyBookingsCalender("person11");
        MyBookingsCalender person12 = new MyBookingsCalender("person12");

        MyBookingsCalender person13 = new MyBookingsCalender("person13");


        person1.bookRoom(years[0], months[0], days[0], hours[0], rooms[0], purposes[0]);
         person2.bookRoom(years[1], months[1], days[1], hours[1], rooms[1], purposes[1]);
         person3.bookRoom(years[2], months[2], days[2], hours[2], rooms[2], purposes[2]);
         person4.bookRoom(years[3], months[3], days[3], hours[3], rooms[3], purposes[3]);

         person5.bookRoom(years[4], months[4], days[4], hours[4], rooms[4], purposes[4]);
         person6.bookRoom(years[5], months[5], days[5], hours[5], rooms[5], purposes[5]);
         person7.bookRoom(years[6], months[6], days[6], hours[6], rooms[6], purposes[6]);
         person8.bookRoom(years[7], months[7], days[7], hours[7], rooms[7], purposes[7]);
         person9.bookRoom(years[8], months[8], days[8], hours[8], rooms[8], purposes[8]);
         person10.bookRoom(years[9], months[9], days[9], hours[9], rooms[9], purposes[9]);
         person11.bookRoom(years[10], months[10], days[10], hours[10], rooms[10], purposes[10]);
         person12.bookRoom(years[11], months[11], days[11], hours[11], rooms[11], purposes[11]);

         person13.bookRoom(years[12], months[12], days[12], hours[12], rooms[12], purposes[12]);



         String[] dataFilePaths = new String[years.length];
         Day[] dayObjects = new Day[years.length];

         for (int i = 0; i < years.length; i++) {
             dataFilePaths[i] = "resources/bookingsFileStructure/" + years[i] + "/" + months[i] + "/" +
                     days[i] + "/" + "day.bin/";

         }

         Booking booking1 = person1.getMyBookings().get(0);
         Booking booking2 = person2.getMyBookings().get(0);
         Booking booking3 = person3.getMyBookings().get(0);
         Booking booking4 = person4.getMyBookings().get(0);

        Booking booking5 = person5.getMyBookings().get(0);
        Booking booking6 = person6.getMyBookings().get(0);
        Booking booking7 = person7.getMyBookings().get(0);
        Booking booking8 = person8.getMyBookings().get(0);

        Booking booking9 = person9.getMyBookings().get(0);
        Booking booking10 = person10.getMyBookings().get(0);
        Booking booking11 = person11.getMyBookings().get(0);
        Booking booking12 = person12.getMyBookings().get(0);
        Booking booking13 = person13.getMyBookings().get(0);


       Day dayObj1 = (Day) ReadAndWriteObject.loadDayObject(dataFilePaths[0]);
       Day dayObj2 = (Day) ReadAndWriteObject.loadDayObject(dataFilePaths[1]);
        Day dayObj3 = (Day) ReadAndWriteObject.loadDayObject(dataFilePaths[2]);
        Day dayObj4 = (Day) ReadAndWriteObject.loadDayObject(dataFilePaths[3]);

        Day dayObj5 = (Day) ReadAndWriteObject.loadDayObject(dataFilePaths[4]);
        Day dayObj6 = (Day) ReadAndWriteObject.loadDayObject(dataFilePaths[5]);
        Day dayObj7 = (Day) ReadAndWriteObject.loadDayObject(dataFilePaths[6]);
        Day dayObj8 = (Day) ReadAndWriteObject.loadDayObject(dataFilePaths[7]);

        Day dayObj9 = (Day) ReadAndWriteObject.loadDayObject(dataFilePaths[8]);
        Day dayObj10 = (Day) ReadAndWriteObject.loadDayObject(dataFilePaths[9]);
        Day dayObj11 = (Day) ReadAndWriteObject.loadDayObject(dataFilePaths[10]);
        Day dayObj12 = (Day) ReadAndWriteObject.loadDayObject(dataFilePaths[11]);

        Day dayObj13 = (Day) ReadAndWriteObject.loadDayObject(dataFilePaths[12]);

        boolean[] hoursOfDay1 = dayObj1.getBookingTimetable().get(rooms[0]);
        boolean[] hoursOfDay2 = dayObj2.getBookingTimetable().get(rooms[1]);
        boolean[] hoursOfDay3 = dayObj3.getBookingTimetable().get(rooms[2]);
        boolean[] hoursOfDay4 = dayObj4.getBookingTimetable().get(rooms[3]);

        boolean[] hoursOfDay5 = dayObj5.getBookingTimetable().get(rooms[4]);
        boolean[] hoursOfDay6 = dayObj6.getBookingTimetable().get(rooms[5]);
        boolean[] hoursOfDay7 = dayObj7.getBookingTimetable().get(rooms[6]);
        boolean[] hoursOfDay8 = dayObj8.getBookingTimetable().get(rooms[7]);

        boolean[] hoursOfDay9 = dayObj9.getBookingTimetable().get(rooms[8]);
        boolean[] hoursOfDay10 = dayObj10.getBookingTimetable().get(rooms[9]);
        boolean[] hoursOfDay11 = dayObj11.getBookingTimetable().get(rooms[10]);
        boolean[] hoursOfDay12 = dayObj12.getBookingTimetable().get(rooms[11]);

        boolean[] hoursOfDay13 = dayObj13.getBookingTimetable().get(rooms[12]);

        // person1
        assertEquals(years[0], booking1.getDate().getYear());
        assertEquals(months[0], booking1.getDate().getMonth());
        assertEquals(days[0], booking1.getDate().getDay());
        assertEquals(rooms[0], booking1.getRoom());
        assertEquals(purposes[0], booking1.getPurpose());
        assertTrue(hoursOfDay1[booking1.getDate().getHour() - 9]);

        // person2
        assertEquals(years[1], booking2.getDate().getYear());
        assertEquals(months[1], booking2.getDate().getMonth());
        assertEquals(days[1], booking2.getDate().getDay());
        assertEquals(rooms[1], booking2.getRoom());
        assertEquals(purposes[1], booking2.getPurpose());
        assertTrue(hoursOfDay2[booking2.getDate().getHour() - 9]);

        // person3
        assertEquals(years[2], booking3.getDate().getYear());
        assertEquals(months[2], booking3.getDate().getMonth());
        assertEquals(days[2], booking3.getDate().getDay());
        assertEquals(rooms[2], booking3.getRoom());
        assertEquals(purposes[2], booking3.getPurpose());
        assertTrue(hoursOfDay3[booking3.getDate().getHour() - 9]);

        // person4
        assertEquals(years[3], booking4.getDate().getYear());
        assertEquals(months[3], booking4.getDate().getMonth());
        assertEquals(days[3], booking4.getDate().getDay());
        assertEquals(rooms[3], booking4.getRoom());
        assertEquals(purposes[3], booking4.getPurpose());
        assertTrue(hoursOfDay4[booking4.getDate().getHour() - 9]);

        // person5
        assertEquals(years[4], booking5.getDate().getYear());
        assertEquals(months[4], booking5.getDate().getMonth());
        assertEquals(days[4], booking5.getDate().getDay());
        assertEquals(rooms[4], booking5.getRoom());
        assertEquals(purposes[4], booking5.getPurpose());
        assertTrue(hoursOfDay5[booking5.getDate().getHour() - 9]);

        // person6
        assertEquals(years[5], booking6.getDate().getYear());
        assertEquals(months[5], booking6.getDate().getMonth());
        assertEquals(days[5], booking6.getDate().getDay());
        assertEquals(rooms[5], booking6.getRoom());
        assertEquals(purposes[5], booking6.getPurpose());
        assertTrue(hoursOfDay6[booking6.getDate().getHour() - 9]);

        // person7
        assertEquals(years[6], booking7.getDate().getYear());
        assertEquals(months[6], booking7.getDate().getMonth());
        assertEquals(days[6], booking7.getDate().getDay());
        assertEquals(rooms[6], booking7.getRoom());
        assertEquals(purposes[6], booking7.getPurpose());
        assertTrue(hoursOfDay7[booking7.getDate().getHour() - 9]);

        // person8
        assertEquals(years[7], booking8.getDate().getYear());
        assertEquals(months[7], booking8.getDate().getMonth());
        assertEquals(days[7], booking8.getDate().getDay());
        assertEquals(rooms[7], booking8.getRoom());
        assertEquals(purposes[7], booking8.getPurpose());
        assertTrue(hoursOfDay8[booking8.getDate().getHour() - 9]);

        // person9
        assertEquals(years[8], booking9.getDate().getYear());
        assertEquals(months[8], booking9.getDate().getMonth());
        assertEquals(days[8], booking9.getDate().getDay());
        assertEquals(rooms[8], booking9.getRoom());
        assertEquals(purposes[8], booking9.getPurpose());
        assertTrue(hoursOfDay9[booking9.getDate().getHour() - 9]);

        // person10
        assertEquals(years[9], booking10.getDate().getYear());
        assertEquals(months[9], booking10.getDate().getMonth());
        assertEquals(days[9], booking10.getDate().getDay());
        assertEquals(rooms[9], booking10.getRoom());
        assertEquals(purposes[9], booking10.getPurpose());
        assertTrue(hoursOfDay10[booking10.getDate().getHour() - 9]);

        // person11
        assertEquals(years[10], booking11.getDate().getYear());
        assertEquals(months[10], booking11.getDate().getMonth());
        assertEquals(days[10], booking11.getDate().getDay());
        assertEquals(rooms[10], booking11.getRoom());
        assertEquals(purposes[10], booking11.getPurpose());
        assertTrue(hoursOfDay11[booking11.getDate().getHour() - 9]);

        // person12
        assertEquals(years[11], booking12.getDate().getYear());
        assertEquals(months[11], booking12.getDate().getMonth());
        assertEquals(days[11], booking12.getDate().getDay());
        assertEquals(rooms[11], booking12.getRoom());
        assertEquals(purposes[11], booking12.getPurpose());
        assertTrue(hoursOfDay12[booking12.getDate().getHour() - 9]);


        // person13
        assertEquals(years[12], booking13.getDate().getYear());
        assertEquals(months[12], booking13.getDate().getMonth());
        assertEquals(days[12], booking13.getDate().getDay());
        assertEquals(rooms[12], booking13.getRoom());
        assertEquals(purposes[12], booking13.getPurpose());
        assertTrue(hoursOfDay13[booking13.getDate().getHour() - 9]);


    }

    @Test // link 2
    public void testMultipleCancellationsWithFolderMaintenance(){

        int[] years =    {2019,  2020,    2018,       2018,       2020,      2019,     2019,      2020,    2020,    2018,       2019,    2018,      2018,       2020};
        String[] months = {"May", "June", "December", "December", "January", "August", "February", "March", "April", "December", "July", "December", "December", "November"};
        int[] days =      {20,     12,     23,          23,          1,        2,          28,        23,      17,      19,         12,      30,          29,          11};
        int[] hours =     {17,     9,     16,          14,          13,        11,          10,        12,      9,      10,         15,      16,          17,          17};
        String[]rooms =   {"R101", "R102","R103",     "R104",     "R105",    "R201",      "R202",     "R203",  "R204", "R205",    "R301",   "R302",     "R303",      "R304"};
        String[] purposes = {"Drugs", "running", "playing", "crying", "smoking", "execution", "staring", "headbanging", "toilet", "orange peeling", "raging", "gaming", "getting high", "party", "meeting"};

        MyBookingsCalender person1 =  MyBookingsCalender.loadMyBookingCalender("person1");
        MyBookingsCalender person2 =  MyBookingsCalender.loadMyBookingCalender("person2");
        MyBookingsCalender person3 =  MyBookingsCalender.loadMyBookingCalender("person3");
        MyBookingsCalender person4 =  MyBookingsCalender.loadMyBookingCalender("person4");

        MyBookingsCalender person5 =  MyBookingsCalender.loadMyBookingCalender("person5");
        MyBookingsCalender person6 =  MyBookingsCalender.loadMyBookingCalender("person6");
        MyBookingsCalender person7 =  MyBookingsCalender.loadMyBookingCalender("person7");
        MyBookingsCalender person8 =  MyBookingsCalender.loadMyBookingCalender("person8");

        MyBookingsCalender person9 =  MyBookingsCalender.loadMyBookingCalender("person9");
        MyBookingsCalender person10 = MyBookingsCalender.loadMyBookingCalender("person10");
        MyBookingsCalender person11 = MyBookingsCalender.loadMyBookingCalender("person11");
        MyBookingsCalender person12 = MyBookingsCalender.loadMyBookingCalender("person12");

        MyBookingsCalender person13 = MyBookingsCalender.loadMyBookingCalender("person13");
        MyBookingsCalender person14 = MyBookingsCalender.loadMyBookingCalender("person14");

        person1.cancelBooking(years[0], months[0], days[0], hours[0], rooms[0]);
        person2.cancelBooking(years[1], months[1], days[1], hours[1], rooms[1]);
        person3.cancelBooking(years[2], months[2], days[2], hours[2], rooms[2]);
        person4.cancelBooking(years[3], months[3], days[3], hours[3], rooms[3]);

        person5.cancelBooking(years[4], months[4], days[4], hours[4], rooms[4]);
        person6.cancelBooking(years[5], months[5], days[5], hours[5], rooms[5]);
        person7.cancelBooking(years[6], months[6], days[6], hours[6], rooms[6]);
        person8.cancelBooking(years[7], months[7], days[7], hours[7], rooms[7]);
        person9.cancelBooking(years[8], months[8], days[8], hours[8], rooms[8]);
        person10.cancelBooking(years[9], months[9], days[9], hours[9], rooms[9]);
        person11.cancelBooking(years[10], months[10], days[10], hours[10], rooms[10]);
        person12.cancelBooking(years[11], months[11], days[11], hours[11], rooms[11]);

        //person13.cancelBooking(years[12], months[12], days[12], hours[12], rooms[12]);


        File bookingsFolder = new File("resources/bookingsFileStructure/");
        File[] bookingFolderContent = bookingsFolder.listFiles();


        assertEquals(1, person13.getMyBookings().size());
        assertTrue(new File(person13.getMyBookings().get(0).getFilePathAssociatedDay()).exists());

       // assertEquals(bookingFolderContent);
        assertEquals(2, bookingFolderContent.length);

    }

    @Test // link 2
    public void manualDisplayTest(){

        try {
            MyBookingsCalender person13 = new MyBookingsCalender("person13");
            person13.bookRoom(2018, "December", 29, 9, "R203", "Drumming");
            person13.bookRoom(2018, "December", 29, 10, "R101", "gaming");
            person13.bookRoom(2018, "December", 29, 16, "R205", "getting high");
            person13.bookRoom(2018, "December", 29, 12, "R101", "people watching");


            MyBookingsCalender person14 = new MyBookingsCalender("person14");
            person14.bookRoom(2018, "December", 29, 9, "R103", "worksheet");
            //person14.viewMyBooking(2018, "December", 29);

            MyBookingsCalender person15 = new MyBookingsCalender("person15");
            person15.bookRoom(2018, "December", 29, 11, "R304", "tap dancing");
           // person15.viewMyBooking(2018, "December", 29);

            MyBookingsCalender person16 = new MyBookingsCalender("person16");
            person16.bookRoom(2018, "December", 29, 17, "R202", "movie night");
          //  person16.viewMyBooking(2018, "December", 29);

            MyBookingsCalender person17 = new MyBookingsCalender("person17");
            person17.bookRoom(2018, "December", 29, 14, "R201", "social");
          //  person17.viewMyBooking(2018, "December", 29);

           // person13.viewMyBooking(2018, "December", 29);
            Thread.sleep(30000);


        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Test
    public void bookAtsameTime(){

        int year = 2019;
        String month = "May";
        int day = 20;
        int hour = 15;
        String testRoom = "R104";
        String purpose = "Company Meeting";


        int year2 = 2019;
        String month2 = "May";
        int day2 = 20;
        int hour2 = 15;
        String testRoom2 = "R104";
        String purpose2 = "Company Meeting";

        String dataFilePath = "resources/bookingsFileStructure/" + year + "/" + month + "/" +
                day + "/" + "day.bin/";

        MyBookingsCalender bradCalender = new MyBookingsCalender("brad");
        bradCalender.bookRoom(year, month, day, hour, testRoom, purpose);

        MyBookingsCalender lisacal = new MyBookingsCalender("lisa");
        lisacal.bookRoom(year2, month2, day2, hour2, testRoom2, purpose2);

        assertEquals(0, lisacal.getMyBookings().size());
    }

    @Override
    public String getSaveFilePath() {
        // TODO Auto-generated method stub
        return null;
    }

}
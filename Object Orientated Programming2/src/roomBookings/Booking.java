package roomBookings;


import java.io.Serializable;

public class Booking implements Serializable {
    private Date date;
    private String room;
    private String purpose;
    private String filePathAssociatedDay;
    public Booking(Date date, String rooom, String purpose, String filePathAssociatedDay){

        this.date = date;
        this.room = rooom;
        this.purpose = purpose;
        this.filePathAssociatedDay = filePathAssociatedDay;

    }

    public Date getDate() {
        return date;
    }

    public String getRoom() {
        return room;
    }


    public String getPurpose() {
        return purpose;
    }

    public String getFilePathAssociatedDay() {
        return filePathAssociatedDay;
    }
}

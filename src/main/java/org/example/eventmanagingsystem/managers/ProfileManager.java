package org.example.eventmanagingsystem.managers;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.eventmanagingsystem.models.Attendee;
import org.example.eventmanagingsystem.models.Gender;

import java.time.LocalDate;

public class ProfileManager {

    @FXML
    private Label  dashboardLink;

    @FXML
    private Label usernameField;

    @FXML
    private Label IdField;

    @FXML
    private Label genderField;

    @FXML
    private Label dobField;

    @FXML
    private Label addressField;

//   private  Attendee createAttendee(){
//      private  Attendee  attendee  = new Attendee("Tarek", "password123", LocalDate.of(2002, 4, 20), "Cairo", Gender.MALE);
//        return attendee;
//    }
    @FXML
    private void initialize() {
//        usernameField.setText(getUsername(attendee));  //pass attendee here!!
//        IdField.setText(String.valueOf(getId(attendee)));
//        genderField.setText(getGender(attendee).toString());
//        dobField.setText(getDOFB(attendee).toString());
//        addressField.setText(getAddress(attendee));

            usernameField.setText("TEST_USERNAME");
            IdField.setText("123");
            genderField.setText("TEST_GENDER");

            System.out.println("Labels should show TEST values now");

    }

    private String getUsername(Attendee attendee){
        return attendee.getUserName();
    }
    private int getId(Attendee attendee){
        return attendee.getId();
    }
    private Gender getGender(Attendee attendee){
        return attendee.getGender();
    }
    private LocalDate getDOFB(Attendee attendee){
        return attendee.getDateOfBirth();
    }
    private String getAddress(Attendee attendee){
        return  attendee.getAddress();
    }

//    public  void setAttendee(Attendee newAttendee) {
//        this.attendee = newAttendee;
//        System.out.println("Attempting to set: " + newAttendee.getUserName());
//    }


}

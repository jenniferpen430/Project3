package com.example.project3;

import java.util.InputMismatchException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

/**
 This class is the main connection to the JavaFX GUI application. The methods written here are to make
 the buttons

 functional. Employees are added, removed, hours set, printed, payment calculated, imported,
 and exported through several GUI buttons.
 @author jennifer, adams
 */

public class MainController {
    Roster roster = new Roster();
    @FXML
    private RadioButton NYID;

    @FXML
    private Button addButton;

    @FXML
    private RadioButton baID;

    @FXML
    private RadioButton baIDpayment;

    @FXML
    private TextField creditHours;

    @FXML
    private RadioButton csID;

    @FXML
    private RadioButton csIDpayment;

    @FXML
    private RadioButton ctID;

    @FXML
    private RadioButton eceID;

    @FXML
    private RadioButton eeIDpayment;

    @FXML
    private TextField finAidID;

    @FXML
    private RadioButton internationalID;

    @FXML
    private RadioButton itID;

    @FXML
    private RadioButton itIDpayment;

    @FXML
    private ToggleGroup location;

    @FXML
    private ToggleGroup majors;

    @FXML
    private ToggleGroup majorsPayment;

    @FXML
    private RadioButton meID;

    @FXML
    private RadioButton meIDpayment;

    @FXML
    private TextArea messageArea1;

    @FXML
    private TextArea messageArea2;

    @FXML
    private TextField name;

    @FXML
    private RadioButton nonresidentID;

    @FXML
    private TextField paymentAmountID;

    @FXML
    private DatePicker paymentDate;

    @FXML
    private AnchorPane paymentDateID;

    @FXML
    private Button removeButton;

    @FXML
    private RadioButton residentID;

    @FXML
    private ToggleGroup state;

    @FXML
    private ToggleGroup status;

    @FXML
    private CheckBox studyabroad;

    @FXML
    private RadioButton tristateID;

    @FXML
    private TextField tuition;

    @FXML
     public boolean dataChecker(){
        String nameText = name.getText();
        RadioButton major = (RadioButton) majors.getSelectedToggle();
        String dept = major.getText();

        if(nameText == null){
            messageArea1.appendText("Missing name");
            return false;
        }
        if(dept == null) {
            messageArea1.appendText("Missing major");
            return false;
        }

        RadioButton resOrNr = (RadioButton) status.getSelectedToggle();
        String statusText = resOrNr.getText();
        if(statusText == null){
            messageArea1.appendText("Missing Status");
            return false;
        }
        if(!creditchecker()){
            return false;
        }
        int credits = Integer.parseInt(creditHours.getText());

        if(statusText.equals("NonResident")) {
            RadioButton locations = (RadioButton) location.getSelectedToggle();
            String locationText = locations.getText();
            if (locationText.equals("Tristate")) {
                RadioButton states = (RadioButton) state.getSelectedToggle();
                String stateText = states.getText();
                if(stateText == null){
                    messageArea1.appendText("Missing State");
                    return false;
                }
                return true;
            } else if(locationText.equals("International")){
                if(studyabroad.isSelected()){
                    if(credits != 12){
                        messageArea1.appendText("Study Abroad Student must be at 12 credits");
                        return false;
                    }
                }else {
                    if(credits < 12 || credits > 24) {
                        return false;
                    }
                }
                return true;
            }
            return true;
        }
        else if(statusText.equals("Resident")){
            return true;
        }

        return false;

    }

    public boolean dataCheckerTab2(){
        String nameText = name.getText();
        RadioButton major = (RadioButton) majors.getSelectedToggle();
        String dept = major.getText();
        if(nameText == null){
            messageArea1.appendText("Missing name");
            return false;
        }
        if(dept == null) {
            messageArea1.appendText("Missing major");
            return false;
        }
        //
    }

    public boolean creditchecker(){
        try{
            int credits = Integer.parseInt(creditHours.getText());
            if(credits == 0){
                messageArea1.appendText("Credit cannot be 0");
                return false;
            }
            if(credits < 0 ){
                messageArea1.appendText("Credit cannot be negative");
                return false;
            }
            if(credits < 3){
                messageArea1.appendText("Minimum credit hours is 3.");
            }
            if(credits > 24){
                messageArea1.appendText("Credit hours exceed the maximum 24.");
            }
            return true;
        }
        catch (InputMismatchException e){
            messageArea1.appendText("Input must be an integer");
            return false;
        }
    }

    /**
     * Event Handler for the add button
     * @param event
     */
    @FXML
    void add(ActionEvent event) {
        if(dataChecker()){
            String nameText = name.getText();
            RadioButton major = (RadioButton) majors.getSelectedToggle();
            String dept = major.getText();
            Profile profile = new Profile(nameText, dept);
            RadioButton resOrNr = (RadioButton) status.getSelectedToggle();
            String statusText = resOrNr.getText();
            int credits = Integer.parseInt(creditHours.getText());

            if(statusText.equals("Resident")) {
                Resident resident = new Resident(profile, credits);
                if (roster.add(resident)) {
                    messageArea1.appendText("Student added! \n");
                } else {
                    messageArea1.appendText("Student already exists! \n");
                }
            }
            else if(statusText.equals("NonResident")){
                RadioButton locations = (RadioButton) location.getSelectedToggle();
                String locationText = locations.getText();
                if(locationText == null){
                    NonResident nr = new NonResident(profile,credits);
                    if (roster.add(nr)) {
                        messageArea1.appendText("Student added! \n");
                    } else {
                        messageArea1.appendText("Student already exists! \n");
                    }
                } else if(locationText.equals("Tristate")) {
                    RadioButton states = (RadioButton) state.getSelectedToggle();
                    String stateText = states.getText();
                    TriState ts = new TriState(profile,credits, stateText);
                    if (roster.add(ts)) {
                        messageArea1.appendText("Student added! \n");
                    } else {
                        messageArea1.appendText("Student already exists! \n");
                    }
                } else if(locationText.equals("International")) {
                    boolean sa = false;
                    if(studyabroad.isSelected()){
                        sa = true;
                    }
                    International in = new International(profile, credits, sa);
                    if (roster.add(in)) {
                        messageArea1.appendText("Student added! \n");
                    } else {
                        messageArea1.appendText("Student already exists! \n");
                    }
                }

            }
        }
    }

    @FXML
    /**
     Event Handler for the remove button
     @param event
     */
    void remove(ActionEvent event) {
       if(dataChecker()) {
           String emp = name.getText();
           RadioButton major = (RadioButton) majors.getSelectedToggle();
           String majorText = major.getText();
           Profile profile = new Profile(emp, majorText);
           Student student = new Student(profile);
           if (roster.remove(student)) {
               messageArea1.appendText("Student removed. \n");
           } else if (roster.getNumStudents() == 0) {
               messageArea1.appendText("Student database is empty. \n");
           } else {
               messageArea1.appendText("Student does not exist. \n");
           }
       }

    }

    @FXML
    /**
     Event Handler for the calculate button
     @param event
     */
    void calculatePayment(ActionEvent event) {
        if(roster.getNumStudents() > 0) {
            roster.calculateTuition();
            messageArea2.appendText("Calculation of employee payments is done \n");
        } else {
            messageArea2.appendText("No employees in database.\n");
        }
    }


    @FXML
    void residentButtonClick(ActionEvent event) {
        nonresidentID.setDisable(true);
    }

    @FXML
    void tristateButtonClick(ActionEvent event) {

    }
    @FXML
    void nonResidentButtonClick(ActionEvent event) {

    }
    @FXML
    void CTClick(ActionEvent event) {

    }

    @FXML
    void NYClick(ActionEvent event) {

    }
    @FXML
    void internationalButtonClick(ActionEvent event) {

    }

    @FXML
    /**
     Event Handler for the print button
     @param event
     */
    String print(ActionEvent event) {
        if(roster.getNumStudents() > 0) {
            messageArea2.appendText("--Printing earning statements-- \n");
            messageArea2.appendText(roster.print());
        } else {
            messageArea2.appendText("Student database is empty. \n");
        }

        return roster.print();
    }


    @FXML
    /**
     Event Handler for the print by date button
     @param event
     */
    String printByDate(ActionEvent event) {
        if(roster.getNumStudents() > 0) {
            messageArea2.appendText("--Printing earning statements by date hired-- \n");
            String printDate = roster.printByDate();
            messageArea2.appendText(printDate);
        } else {
            messageArea2.appendText("student database is empty.\n");
        }

        return roster.printByDate();
    }
}

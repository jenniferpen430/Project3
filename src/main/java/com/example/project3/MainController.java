package com.example.project3;
import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 This class is the main connection to the JavaFX GUI application. The methods written here are to make
 the buttons functional. Employees are added, removed, hours set, printed, payment calculated, imported,
 and exported through several GUI buttons.
 @author jennifer, adams
 */

public class MainController {
    Roster roster = new Roster();

    @FXML
    private TextField name;

    @FXML
    private DatePicker dateHired;

    @FXML
    private TextField tuition;

    @FXML
    private TextField creditHours;

    @FXML
    private TextField rate;

    @FXML
    private Button addButton;


    @FXML
    private Button removeButton;

    @FXML
    private Button printEmp;

    @FXML
    private CheckBox studyabroad;
    @FXML
    private Button printDep;

    @FXML
    private Button importButton;

    @FXML
    private Button exportButton;

    @FXML
    private Button printDate;

    @FXML
    private Button sethrButton;

    @FXML
    private Button paymentCalc;

    @FXML
    private TextArea messageArea1;

    @FXML
    private TextArea messageArea2;

    @FXML
    private ToggleGroup majors, state, location, status;

    @FXML
    private RadioButton csID, itID, eceID, meID, baID, NYID, ctID, ManagementID,
            internationalID, nonresidentID;

    @FXML
     public boolean dataChecker(){
        String nameText = name.getText();
        RadioButton major = (RadioButton) majors.getSelectedToggle();

        if(nameText == null){
            messageArea1.appendText("Missing name");
            return false;
        }
        if(major.getText() == null){
            messageArea1.appendText("Missing major");
            return false;
        }
        RadioButton locations = (RadioButton) location.getSelectedToggle();
        String locationText = locations.getText();

        RadioButton resOrNr = (RadioButton) status.getSelectedToggle();
        String statusText = resOrNr.getText();
        if(locationText.equals("Tristate")) {
            RadioButton states = (RadioButton) state.getSelectedToggle();
            String stateText = states.getText();
            TriState ts = new TriState(profile,credits, stateText);
            if (roster.add(ts)) {
                messageArea1.appendText("Student added! \n");
            } else {
                messageArea1.appendText("Student already exists! \n");
            }
        }

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
        try {
            String emp = name.getText();
            RadioButton major = (RadioButton) majors.getSelectedToggle();
            String majorText = major.getText();
            Profile profile = new Profile(emp, majorText);
            Student student = new Student(profile);
            if(roster.remove(student)) {
                messageArea1.appendText("Student removed. \n");
            }
            else if(roster.getNumStudents()==0) {
                messageArea1.appendText("Student database is empty. \n");
            } else {
                messageArea1.appendText("Student does not exist. \n");
            }
        }
        catch (Exception e) {
            messageArea1.appendText("Error. \n");
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
    /**
     Event Handler for the set hours button
     @param event
     */
    void setHours(ActionEvent event) {
        try {
            String emp = name.getText();
            String[] dateSplit = dateHired.getValue().toString().split("-");
            String formattedDate = dateSplit[1] + "/" + dateSplit[2] + "/" + dateSplit[0];
            RadioButton selectDep = (RadioButton) dep.getSelectedToggle();
            String dept = selectDep.getText();
            String hours = hrsWorked.getText();
            double hourss = Double.parseDouble(hours);
            Profile profile = new Profile(emp, dept, formattedDate);
            Parttime parttime=new Parttime(profile, 0);
            parttime.setHours(hourss);

            if(hourss<0) {
                messageArea1.appendText("Working hours cannot be negative. \n");
            } else if(hourss>100) {
                messageArea1.appendText("Invalid Hours: over 100. \n");
            } else if(company.setHours(parttime)) {
                messageArea1.appendText("Working hours set. \n");
            }
        }
        catch (Exception e) {
            messageArea1.appendText("Error! Please recheck input! \n");
        }
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
            messageArea2.appendText("Employee database is empty.\n");
        }

        return roster.printByDate();
    }
}

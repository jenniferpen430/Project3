package com.example.project3;

import java.util.InputMismatchException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;

/**
 This main class is the connection to the JavaFX GUI application.
 Students  are added, removed, tuition calculation, payments, etc. through several GUI buttons.
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
    private Button payButton;

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
    private Button finAidSet;

    @FXML
    private RadioButton itIDpayment;

    @FXML
    private ToggleGroup areaIn;

    @FXML
    private ToggleGroup majors;

    @FXML
    private ToggleGroup majorsPayment;

    @FXML
    private RadioButton meID;

    @FXML
    private RadioButton meIDpayment;

    @FXML
    private TextArea messageArea1, messageArea2, messageArea3;

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
    private TextField namePayment;

    private boolean calculated;

    @FXML
    /**
     Validates the data for adding a student
     */
     public boolean dataChecker(){
        String nameText = name.getText();

        if(nameText == null || nameText.isEmpty() ){
            messageArea1.appendText("Missing name \n");
            return false;
        }

        RadioButton major = (RadioButton) majors.getSelectedToggle();

        if( majors.getSelectedToggle() == null ){
            messageArea1.appendText("Missing major \n");
            return false;
        }
        String dept = major.getText();


        RadioButton resOrNr = (RadioButton) status.getSelectedToggle();
        if(status.getSelectedToggle() == null){
            messageArea1.appendText("Missing Status \n");
            return false;
        }
        String statusText = resOrNr.getText();
        if(!creditchecker()){
            return false;
        }
        int credits = Integer.parseInt(creditHours.getText());

        if(statusText.equals("Non Resident")) {
            if(areaIn.getSelectedToggle()==null){
                return true;
            }else {
                RadioButton locations = (RadioButton) areaIn.getSelectedToggle();
                String locationText = locations.getText();
                if (locationText.equals("Tristate")) {
                    RadioButton states = (RadioButton) state.getSelectedToggle();
                    //String stateText = states.getText();
                    if (state.getSelectedToggle() == null) {
                        messageArea1.appendText("Missing State \n");
                        return false;
                    }
                    return true;
                } else if (locationText.equals("International")) {
                    if (studyabroad.isSelected()) {
                        if (credits != 12) {
                            messageArea1.appendText("Study Abroad Student must be at 12 credits \n");
                            return false;
                        }
                    } else {
                        if (credits < 12 || credits > 24) {
                            messageArea1.appendText("International students must enroll in at least 12 credits \n");
                            return false;
                        }
                    }
                    return true;
                }
                return true;
                //
            }
        }
        else if(statusText.equals("Resident")){
            return true;
        }

        return false;

    }
    /**
     Validates the students in the second tab
     */
    public boolean dataCheckerTab2(){
        if(namePayment.getText() == null || namePayment.getText().isEmpty() ){
            messageArea2.appendText("Missing name \n");
            return false;
        }
        String nameText = namePayment.getText();
        if(majorsPayment.getSelectedToggle() == null ) {
            messageArea2.appendText("Missing major \n");
            return false;
        }
        RadioButton major = (RadioButton) majorsPayment.getSelectedToggle();
        String dept = major.getText();
        Profile profile = new Profile(nameText, dept);
        Student student = new Student(profile);
        student = roster.placement(student);
        if( !roster.isHere(student) ){
            messageArea2.appendText("Student does not exist.\n");
            return false;
        }
        //Removing payment check if statement
        //Removing date check if statement
        return true;
    }

    /**
     validates the aid input
     */
    public boolean aidChecker(Student student){
        try{
            if( !(student instanceof Resident) ){
                messageArea2.appendText("Not a resident student. \n");
                return false;
            }
            if(finAidID.getText() == null){
                messageArea2.appendText("Missing fin aid \n");
                return false;
            }
            double aidAmount = Double.parseDouble(finAidID.getText());
            if( aidAmount < 0 || aidAmount > 10000 ){
                messageArea2.appendText("Invalid amount. \n");
                return false;
            }
            if(student.getStatus() == -1){
                messageArea2.appendText("Parttime student doesn't qualify for the award. \n");
                return false;
            }
            if(student.getfinAidApplied()){
                messageArea2.appendText("Awarded once already. \n");
                return false;
            }
            return true;
        }
        catch( InputMismatchException e ){
            messageArea2.appendText("Invalid amount. \n");
            return false;
        }
        catch (NumberFormatException e){
            messageArea2.appendText("Input must be a double \n");
            return false;
        }
    }

    /**
        validates whether the payment input is valid and returns a boolean
     */
    public boolean paymentChecker(Student student){
        try{
            double paymentAmount = Double.parseDouble(paymentAmountID.getText());
            if( paymentAmount < 0 ){
                messageArea2.appendText("Payment amount cannot be negative \n");
                return false;
            }
            if( student.getTuition() < paymentAmount ){
                messageArea2.appendText("Amount is greater than amount due \n");
                return false;
            }
            return true;
        }
        catch (InputMismatchException e){
            messageArea2.appendText("Input must be an integer \n");
            return false;
        }
        catch (NumberFormatException e){
            messageArea2.appendText("Input must be an double \n");
            return false;
        }
    }

    /**
     Validates the credit input to see whether it checks out with the guidelines
     */
    public boolean creditchecker(){
        try{
            if(creditHours.getText().isEmpty()){
                messageArea1.appendText("Missing credit hours\n");
                return false;
            }
            int credits = Integer.parseInt(creditHours.getText());
            if(credits == 0){
                messageArea1.appendText("Credit cannot be 0 \n");
                return false;
            }
            if(credits < 0 ){
                messageArea1.appendText("Credit cannot be negative \n");
                return false;
            }
            if(credits < 3){
                messageArea1.appendText("Minimum credit hours is 3. \n");
                return false;
            }
            if(credits > 24){
                messageArea1.appendText("Credit hours exceed the maximum 24. \n");
                return false;
            }
            return true;
        }
        catch (NumberFormatException e){
            messageArea1.appendText("Input must be an integer \n");
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
            else if(statusText.equals("Non Resident")){
                RadioButton locations = (RadioButton) areaIn.getSelectedToggle();
                if(areaIn.getSelectedToggle() == null){
                    NonResident nr = new NonResident(profile,credits);
                    if (roster.add(nr)) {
                        messageArea1.appendText("Student added! \n");
                    } else {
                        messageArea1.appendText("Student already exists! \n");
                    }
                }
                else if(locations.getText().equals("Tristate")) {
                    RadioButton states = (RadioButton) state.getSelectedToggle();
                    String stateText = states.getText();
                    TriState ts = new TriState(profile,credits, stateText);
                    if (roster.add(ts)) {
                        messageArea1.appendText("Student added! \n");
                    } else {
                        messageArea1.appendText("Student already exists! \n");
                    }
                } else if(locations.getText().equals("International")) {
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
            clearAll();
        }
    }

    /**
     * This method is used to set buttons to default values.
     */
    void clearAll(){
        residentID.setSelected(false);
        nonresidentID.setSelected(false);
        tristateID.setSelected(false);
        NYID.setSelected(false);
        ctID.setSelected(false);
        internationalID.setSelected(false);
        studyabroad.setSelected(false);
        name.clear();
        creditHours.clear();
        majors.selectToggle(null);
        tristateID.setDisable(true);
        internationalID.setDisable(true);
        studyabroad.setDisable(true);
    }
    @FXML
    /**
     Event Handler for the remove button
     @param event
     */
    void remove(ActionEvent event) {
        String nameText = name.getText();
        RadioButton major = (RadioButton) majors.getSelectedToggle();

        if(nameText == null || nameText.isEmpty()){
            messageArea2.appendText("Missing name \n");
        }
        else if(majors.getSelectedToggle() == null) {
            messageArea2.appendText("Missing major \n");
        }
        else {
            String dept = major.getText();
           Profile profile = new Profile(nameText, dept);
           Student student = new Student(profile);
           if (roster.remove(student)) {
               messageArea1.appendText("Student removed. \n");
           } else if (roster.getNumStudents() == 0) {
               messageArea1.appendText("Student database is empty. \n");
           } else {
               messageArea1.appendText("Student does not exist. \n");
           }
       }
        clearAll();
    }

    @FXML
    /**
     Event Handler for the pay button
     @param event
     */
    void pay(ActionEvent event){
        if( dataCheckerTab2() ){
            if(paymentDate.getValue() == null ){
                messageArea2.appendText("Missing payment date \n");
            }else {
                String[] dateArray = paymentDate.getValue().toString().split("-");
                String preDate = dateArray[1] + "/" + dateArray[2] + "/" + dateArray[0];
                Date postDate = new Date(preDate);
                if( !postDate.isValid() ){
                    messageArea2.appendText("Payment date invalid. \n");
                }else {
                    String nameText = namePayment.getText();
                    RadioButton major = (RadioButton) majorsPayment.getSelectedToggle();
                    String dept = major.getText();
                    Profile profile = new Profile(nameText, dept);
                    Student student = new Student(profile);
                    student = roster.placement(student);
                    if (paymentChecker(student)) {
                        double paymentAmount = Double.parseDouble(paymentAmountID.getText());
                        student.setLastPaymentDate(preDate);
                        student.payTuition(paymentAmount);
                        messageArea2.appendText("Payment Applied. \n");
                    }
                }
            }
        }
        clearAll();
    }

    @FXML
    /**
     Event Handler for the finaid button
     @param event
     */
    void finaid(ActionEvent event){
        if( dataCheckerTab2() ){
            String nameText = namePayment.getText();
            RadioButton major = (RadioButton) majorsPayment.getSelectedToggle();
            String dept = major.getText();
            Profile profile = new Profile(nameText, dept);
            Student student = new Student(profile);
            student = roster.placement(student);
            if ( aidChecker(student) ) {
                double aidAmount = Double.parseDouble(finAidID.getText());
                student.applyAid(aidAmount);
                student.setFinAidApplied(true);
                messageArea2.appendText("Tuition updated.\n");
            }
        }
        clearAll();
    }

    @FXML
    /**
     Event Handler for the calculate button
     @param event
     */
    void calculatePayment(ActionEvent event) {
        if(roster.getNumStudents() > 0) {
            roster.calculateTuition();
            calculated = true;
            messageArea3.appendText("Calculation of student payments is done \n");
        } else {
            messageArea3.appendText("No students in database.\n");
        }
    }


    @FXML
    /**
     Event Handler for the resident button
     @param event
     */
    void residentButtonClick(ActionEvent event) {
        tristateID.setDisable(true);
        NYID.setDisable(true);
        ctID.setDisable(true);
        internationalID.setDisable(true);
        studyabroad.setDisable(true);
        NYID.setSelected(false);
        tristateID.setSelected(false);
        internationalID.setSelected(false);
        studyabroad.setSelected(false);
        ctID.setSelected(false);
    }

    @FXML
    /**
     Event Handler for the tristate button
     @param event
     */
    void tristateButtonClick(ActionEvent event) {
        NYID.setDisable(false);
        ctID.setDisable(false);
        studyabroad.setDisable(true);
        internationalID.setSelected(false);
        studyabroad.setSelected(false);
    }
    @FXML
    /**
     Event Handler for the nonResident button
     @param event
     */
    void nonResidentButtonClick(ActionEvent event) {
        tristateID.setDisable(false);
        internationalID.setDisable(false);
    }

    /**
     Event Handler for the International button
     @param event
     */
    @FXML
    void internationalButtonClick(ActionEvent event) {
        NYID.setDisable(true);
        ctID.setDisable(true);
        studyabroad.setDisable(false);
        NYID.setSelected(false);
        ctID.setSelected(false);
    }

    @FXML
    /**
     * setStudyAbroad is used to first check if there is a student, if so whether the student can be made
     * a study abroad student, then set them as such.
     */
    void setStudyAbroad(ActionEvent event){
        String nameText = name.getText();
        RadioButton major = (RadioButton) majors.getSelectedToggle();
        if( majors.getSelectedToggle() == null ){
            messageArea1.appendText("Missing major \n");
        }else {
            String dept = major.getText();
            if (nameText == null || nameText.isEmpty() ) {
                messageArea1.appendText("Missing name \n");
            }else {
                Profile profile = new Profile(nameText, dept);
                Student student = new Student(profile);
                student = roster.placement(student);
                if (!roster.isHere(student)) {
                    messageArea1.appendText("Couldn't find the international student.\n");
                }else {
                    if (student instanceof International) {
                        ((International) student).setStudyAbroad(true);
                        student.setLastPaymentDate("--/--/--");
                        student.setPaymentMade(0.00);
                        messageArea1.appendText("Tuition updated.\n");
                        if(calculated){
                            student.tuitionDue();
                        }
                    } else {
                        messageArea1.appendText("Student must be International\n");
                    }
                }
            }
        }
        clearAll();
    }

    @FXML
    /**
     Event Handler for the print button
     @param event
     */
    void print(ActionEvent event) {
        if(roster.getNumStudents() > 0) {
            messageArea3.appendText("Printing student roster \n");
            messageArea3.appendText(roster.print());
        } else {
            messageArea3.appendText("Student roster is empty. \n");
        }
    }

    @FXML
    void printByName(ActionEvent event){
        if(roster.getNumStudents() > 0) {
            messageArea3.appendText("**Printing student roster by name** \n");
            String printName = roster.printByName();
            messageArea3.appendText(printName);
        } else {
            messageArea3.appendText("Student roster is empty.\n");
        }
    }


    @FXML
    /**
     Event Handler for the print by date button
     @param event
     */
    void printByDate(ActionEvent event) {
        if(roster.getNumStudents() > 0) {
            messageArea3.appendText("--Printing student roster by payment date-- \n");
            String printDate = roster.printByDate();
            messageArea3.appendText(printDate);
        } else {
            messageArea3.appendText("Student roster is empty.\n");
        }
    }

}

package com.example.project3;
import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    Student student = new Student();

    @FXML
    private TextField name;

    @FXML
    private DatePicker dateHired;

    @FXML
    private TextField annSal;

    @FXML
    private TextField hrsWorked;

    @FXML
    private TextField rate;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button printEmp;

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
    private ToggleGroup dep, empType, mgmtType;

    @FXML
    private RadioButton csID, itID, eceID, FullTimeID, PartTimeID, ManagementID,
            managerID, depheadID, directorID;

    @FXML
    /**
     * Event Handler for the add button
     * @param event
     */
    void add(ActionEvent event) {
        try {
            String emp = name.getText();
            String[] dateSplit = dateHired.getValue().toString().split("-");
            String formattedDate = dateSplit[1] + "/" + dateSplit[2] + "/" + dateSplit[0];
            Date dateObj = new Date(formattedDate);
            RadioButton selectDep = (RadioButton) dep.getSelectedToggle();
            String dept = selectDep.getText();
            Profile profile = new Profile(emp, dept, formattedDate);
            RadioButton selectEmp = (RadioButton) empType.getSelectedToggle();
            String employeeType = selectEmp.getText();

            if(dateObj.isValid()) {
                if(employeeType.equals("Full Time")) {
                    String annualSal = annSal.getText();
                    double annSalary = Double.parseDouble(annualSal);
                    Fulltime fulltime = new Fulltime(profile, annSalary);
                    if(company.add(fulltime)) {
                        messageArea1.appendText("Employee added! \n");
                    } else {
                        messageArea1.appendText("Employee already exists! \n");
                    }

                } else if (employeeType.contentEquals("Management")) {
                    String annualSal = annSal.getText();
                    double annSalary = Double.parseDouble(annualSal);
                    RadioButton selectMgmt = (RadioButton) mgmtType.getSelectedToggle();
                    String mgmtRole = selectMgmt.getText();

                    if(mgmtRole.contentEquals("Manager")) {
                        Management management = new Management(profile, annSalary, mgmtRole);
                        management.setRole("Manager");
                        if(company.add(management)) {
                            messageArea1.appendText("Employee added! \n");
                        } else {
                            messageArea1.appendText("Employee already exists! \n");
                        }
                    }else if(mgmtRole.contentEquals("Department Head")) {
                        Management management = new Management(profile, annSalary, mgmtRole);
                        management.setRole("Department Head");
                        if(company.add(management)) {
                            messageArea1.appendText("Employee added! \n");
                        } else {
                            messageArea1.appendText("Employee already exists! \n");
                        }

                    }else if(mgmtRole.contentEquals("Director")) {
                        Management management = new Management(profile, annSalary, mgmtRole);
                        management.setRole("Director");
                        if(company.add(management)) {
                            messageArea1.appendText("Employee added! \n");
                        } else {
                            messageArea1.appendText("Employee already exists! \n");
                        }
                    }

                } else if (employeeType.contentEquals("Part Time")) {
                    String ratePHour = rate.getText();
                    double ratePerHour = Double.parseDouble(ratePHour);
                    Parttime parttime = new Parttime(profile, ratePerHour);
                    if(company.add(parttime)) {
                        messageArea1.appendText("Employee added! \n");
                    } else {
                        messageArea1.appendText("Employee already exists! \n");
                    }
                }
            } else {
                messageArea1.appendText("Invalid com.example.project3.Date! \n");
            }
        }
        catch (Exception e) {
            messageArea1.appendText("Error. Please recheck inputs! \n");
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
            String[] dateSplit = dateHired.getValue().toString().split("-");
            String formattedDate = dateSplit[1] + "/" + dateSplit[2] + "/" + dateSplit[0];
            RadioButton selectDep = (RadioButton) dep.getSelectedToggle();
            String dept = selectDep.getText();
            Profile profile = new Profile(emp, dept, formattedDate);
            Employee employee = new Employee(profile);
            if(company.remove(employee)) {
                messageArea1.appendText("Employee removed. \n");
            }
            else if(company.getnumEmployee()==0) {
                messageArea1.appendText("Employee database is empty. \n");
            } else {
                messageArea1.appendText("Employee does not exist. \n");
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
        if(company.getnumEmployee() > 0) {
            company.processPayments();
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
        if(company.getnumEmployee() > 0) {
            messageArea2.appendText("--Printing earning statements-- \n");
            String print = company.print();
            messageArea2.appendText(print);
        } else {
            messageArea2.appendText("Employee database is empty. \n");
        }

        return company.print();
    }

    @FXML
    /**
     Event Handler for the print by dep button
     @param event
     */
    String printByDept(ActionEvent event) {
        if(company.getnumEmployee() > 0) {
            messageArea2.appendText("--Printing earning statements by department-- \n");
            String printDep = company.printByDepartment();
            messageArea2.appendText(printDep);
        } else {
            messageArea2.appendText("Employee database is empty. \n");
        }

        return company.printByDepartment();
    }

    @FXML
    /**
     Event Handler for the print by date button
     @param event
     */
    String printByDate(ActionEvent event) {
        if(company.getnumEmployee() > 0) {
            messageArea2.appendText("--Printing earning statements by date hired-- \n");
            String printDate = company.printByDate();
            messageArea2.appendText(printDate);
        } else {
            messageArea2.appendText("Employee database is empty.\n");
        }

        return company.printByDate();
    }

    @FXML
    /**
     This method imports the database from the file.
     @param event
     */
    void importFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Import File");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"), //**idk wtf this is
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();

        try {
            File file = chooser.showOpenDialog(stage);
            String filePath = file.getAbsolutePath();
            String fileName = file.getName();
            File database = new File(filePath);

            Scanner scanner = new Scanner(database); //**no errors ig
            String line = scanner.nextLine();
            while (scanner.hasNextLine()) {
                StringTokenizer st = new StringTokenizer(line, ",", false);
                String command = st.nextToken();
                if (command.equals("P")) { //part-time employee
                    String name = st.nextToken();
                    String depCode = st.nextToken();
                    String date = st.nextToken();
                    double hourlyRate = Double.parseDouble(st.nextToken());
                    Profile profile = new Profile(name, depCode, date);
                    Parttime parttime = new Parttime(profile, hourlyRate);
                    company.add(parttime);
                }
                else if (command.equals("F")) { //full-time employee
                    String name = st.nextToken();
                    String depCode = st.nextToken();
                    String date = st.nextToken();
                    double annualSalary = Double.parseDouble(st.nextToken());
                    Fulltime fulltime = new Fulltime(new Profile(name, depCode, date), annualSalary);
                    company.add(fulltime);
                }
                else if(command.equals("M")) { //management
                    String name = st.nextToken();
                    String depCode = st.nextToken();
                    String date = st.nextToken();
                    double annualSalary = Double.parseDouble(st.nextToken());
                    int intCode = Integer.parseInt(st.nextToken());
                    final int MANAGER_CODE = 1;
                    final int DEPARTMENT_HEAD_CODE = 2;
                    final int DIRECTOR_CODE = 3;
                    Management management = null;
                    if (intCode == MANAGER_CODE) {
                        management = new Management(new Profile(name, depCode, date), annualSalary, "Manager");
                    }
                    else if (intCode == DEPARTMENT_HEAD_CODE) {
                        management = new Management(new Profile(name, depCode, date), annualSalary, "Department Head");
                    }
                    else if (intCode == DIRECTOR_CODE) { //could just do else ig
                        management = new Management(new Profile(name, depCode, date), annualSalary, "Director");
                    }
                    company.add(management);
                }
                line = scanner.nextLine();
            }
            scanner.close();
        }
        catch (Exception e) {
            messageArea1.appendText("Error. \n");
        }
    }

    @FXML
    /**
     The method exports the database from the file.
     @param event
     */
    void exportFile(ActionEvent event) {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Export File");
            chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
                    new ExtensionFilter("All Files", "*.*"));
            Stage stage = new Stage();
            File file = chooser.showSaveDialog(stage);
            String filePath = file.getAbsolutePath();
            String fileName = file.getName();
            company.exportDatabase(filePath);
            messageArea2.appendText("File exported. \n");
        }
        catch (Exception e) {
            messageArea2.appendText("Error. \n");
        }
    }

}

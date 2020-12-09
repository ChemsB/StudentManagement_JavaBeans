/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.proven.model;

import cat.proven.model.Student;
import cat.proven.views.StudentPanel;
import cat.rpoven.mode.persist.StudentDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * This class acts as an observer of the component and its events.
 *
 * @author chems
 */
public class Model implements PropertyChangeListener, VetoableChangeListener, ActionListener {

    //Attributes
    private StudentPanel studentPanel;
    private boolean[] validValues = new boolean[4];
    private long oldId;
    private StudentDao dao;
    private Student student;
    private String[] letrasDni = {"T", "R", "w", "A", "G", "M", "Y", "F", "P", "D", "X",
        "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"
    };

    /**
     * Constructor add all listeners to the component
     */
    public Model() {
        dao = new StudentDao();
        studentPanel = StudentPanel.getPanel();
        studentPanel.getStudent().addPropertyChangeListener(this);
        studentPanel.getStudent().addVetoableChangeListener(this);
        studentPanel.getUpdateButton().addActionListener(this);
        studentPanel.getDeleteButton().addActionListener(this);
        studentPanel.getFindButton().addActionListener(this);
        student = studentPanel.getStudent();

    }

    /**
     * Controls change events
     *
     * @param evt event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        switch (propertyName) {
            case "id":
                studentPanel.getInitId().setText(String.valueOf(evt.getNewValue()));
                break;

            case "nif":
                studentPanel.getInitNif().setText(String.valueOf(evt.getNewValue()));
                break;

            case "name":
                studentPanel.getInitName().setText(String.valueOf(evt.getNewValue()));
                break;

            case "age":
                studentPanel.getInitAge().setText(String.valueOf(evt.getNewValue()));
                break;
        }
    }

    /**
     * Controls vetoable events
     *
     * @param evt event
     */
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        switch (evt.getPropertyName()) {

            case "id":
                if (((long) evt.getNewValue()) < 0) {
                    throw new PropertyVetoException("Id not valid", evt);
                }
                break;

            case "nif":
                if (checkDni((String) evt.getNewValue()) == -1) {
                    throw new PropertyVetoException("Invalid NIF sintax", evt);
                } else if (checkDni((String) evt.getNewValue()) == 0) {
                    throw new PropertyVetoException("Inavlid nif letter", evt);
                }
                break;

            case "age":
                if (((int) evt.getNewValue()) < 0) {
                    throw new PropertyVetoException("Age not valid", evt);
                }
                break;

            case "name":

                if (evt.getNewValue().toString().length() <= 0) {
                    throw new PropertyVetoException("Name not valid", evt);
                }
                break;
        }

    }

    /**
     * Controls action
     *
     * @param e event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action != null) {
            switch (action) {
                case "delete":
                    validValues = studentPanel.getValidValues();
                    if (checkIfCorrectValues()) {
                        deleteUser();
                    } else {
                        studentPanel.showMessage("Please check Values");
                    }

                    break;

                case "update":
                    validValues = studentPanel.getValidValues();
                    if (checkIfCorrectValues()) {
                        updateUser();
                    } else {
                        studentPanel.showMessage("Please check Values");
                    }

                    break;

                case "find":
                    validValues = studentPanel.getValidValues();
                    if (checkIfCorrectValues()) {
                        findUser();
                    } else {
                        studentPanel.showMessage("Please check Values");
                    }

                    break;
            }
        }
    }

    /**
     * Check with irregular expression if nif is valid
     *
     * @param dni to check
     * @return 1 if nif is valid, 0 in case of invalid letter nif, -1 in case of
     * invalid sintax
     */
    private int checkDni(String dni) {

        if (dni.length() == 9) {
            String letter = dni.substring(8);

            Pattern pattern = Pattern.compile("[0-9]{8}[A-Za-z]");
            Matcher check = pattern.matcher(dni);
            boolean correctSintax = check.matches();

            if (correctSintax) {
                String number = dni.substring(0, 8);
                int checkLetter = Integer.parseInt(number) % 23;
                if (letter.equalsIgnoreCase(letrasDni[checkLetter])) {
                    return 1;
                }
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }

    }

    /**
     * Delete a student, and change panel view
     */
    private void deleteUser() {

        boolean res = false;
        res = dao.removeStudent(Long.parseLong(studentPanel.getInitId().getText()));

        if (res) {
            dao.init();
        }

        studentPanel.removeUser(res);

    }

    /**
     * Update a student, and change panel view
     */
    private void updateUser() {
        boolean res = false;
        
        res = dao.updateStudent(student, oldId);
        if (res) {
            dao.init();
        }
        studentPanel.updateUser(res);

    }

    /**
     * Search for user and print the data in the panel
     */
    private void findUser() {

        long id = Long.parseLong(studentPanel.getInitId().getText());
        oldId = id;
        student = dao.findById(id);
        if (student != null) {
            student.addPropertyChangeListener(this);
            student.addVetoableChangeListener(this);
            studentPanel.setStudent(student);
            studentPanel.printStudent();
            studentPanel.setEnabledComponents(true);
        }

    }

    /**
     * Check if the input values ​​are correct
     *
     * @return true in case of correct values false if is any invalid value
     */
    private boolean checkIfCorrectValues() {

        boolean res = true;
        for (int i = 0; i < validValues.length; i++) {
            if (validValues[i] == false) {
                res = false;
            }
        }
        return res;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.proven.views;

import cat.proven.model.Student;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

/**
 * This is a component
 *
 * @author chems
 */
public class StudentPanel extends JPanel implements Serializable{

    //Attributes
    private JTextField initId;
    private JTextField initNif;
    private JTextField initName;
    private JTextField initAge;
    private JRadioButton yesButton;
    private JRadioButton noButton;
    private boolean[] validValues = new boolean[4];
    private JLabel infoId;
    private JLabel infoName;
    private JLabel infoAge;
    private JLabel infoNif;
    private JButton deleteButton;
    private JButton findButton;
    private JButton updateButton;

    private Student student;
    private static StudentPanel panel = new StudentPanel();
    private boolean update=false;

    public StudentPanel() {
        initComponents();
        student = new Student();
    }

    /**
     * Get this panel
     *
     * @return this panel
     */
    public static StudentPanel getPanel() {
        return panel;
    }

    /**
     * Init all components and make some modifications
     *
     */
    private void initComponents() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel idText = new JLabel();
        idText.setText("Id: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(idText, gbc);

        initId = new JTextField(10);
        initId.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent event) {
                idTextFocusLost(event);
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(initId, gbc);

        infoId = new JLabel();
        infoId.setText("OK.");
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(infoId, gbc);

        JLabel nifText = new JLabel();
        nifText.setText("Nif: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(nifText, gbc);

        initNif = new JTextField(10);
        initNif.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent event) {
                nifTextFocusLost(event);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(initNif, gbc);

        infoNif = new JLabel();
        infoNif.setText("OK.");
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(infoNif, gbc);

        JLabel nameText = new JLabel();
        nameText.setText("Name: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(nameText, gbc);

        initName = new JTextField(10);
        initName.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent event) {
                nameTextFocusLost(event);
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(initName, gbc);

        infoName = new JLabel();
        infoName.setText("OK.");
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(infoName, gbc);

        JLabel ageText = new JLabel();
        ageText.setText("Age: ");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(ageText, gbc);

        initAge = new JTextField(10);
        initAge.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent event) {
                ageTextFocusLost(event);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(initAge, gbc);

        infoAge = new JLabel();
        infoAge.setText("OK.");
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(infoAge, gbc);

        JLabel minorText = new JLabel();
        minorText.setText("Minor: ");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(minorText, gbc);

        yesButton = new JRadioButton("YES");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(yesButton, gbc);

        noButton = new JRadioButton("NO");
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(noButton, gbc);

        yesButton.setEnabled(false);
        noButton.setEnabled(false);

        deleteButton = new JButton();
        deleteButton.setText("Delete ");
        deleteButton.setActionCommand("delete");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(deleteButton, gbc);

        findButton = new JButton();
        findButton.setText("Find ");
        findButton.setActionCommand("find");
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(findButton, gbc);

        updateButton = new JButton();
        updateButton.setText("Update ");
        updateButton.setActionCommand("update");
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(updateButton, gbc);

        clearTextFields();
        setEnabledComponents(false);
        initCheck();

        add(panel, gbc);

    }

    /**
     * Init the list to check if the fields are correct to true
     */
    private void initCheck() {
        for (int i = 0; i < validValues.length; i++) {
            validValues[i] = true;
        }
    }

    /**
     * Check if the new id is valid and inform the user. If is valid modify
     * student id and introduce true in the list of validations, In case of
     * error not modify it and introduce false in the list of validations
     *
     * @param evt event
     */
    private void idTextFocusLost(FocusEvent evt) {
        try {
            student.setId(Integer.parseInt(initId.getText()));
            infoId.setText("OK.");
            validateValues(0, true);
        } catch (PropertyVetoException | NumberFormatException ex) {
            infoId.setText("invalid id**");
            validateValues(0, false);
        }
    }

    /**
     * Check if the new age is valid and inform the user. If is valid modify
     * student age and introduce true in the list of validations, in case of
     * error not modify it and introduce false in the list of validations
     *
     * @param evt event
     */
    private void ageTextFocusLost(FocusEvent evt) {
        try {
            checkAge(Integer.parseInt(initAge.getText()));
            student.setAge(Integer.parseInt(initAge.getText()));
            validateValues(3, true);

        } catch (PropertyVetoException | NumberFormatException ex) {
            infoAge.setText("Invalid age**");
            validateValues(3, false);
            yesButton.setSelected(false);
            noButton.setSelected(false);
        }
    }

    /**
     * Check if the new nif is valid and inform the user. If is valid modify
     * student nif and introduce true in the list of validations, in case of
     * error not modify it and introduce false in the list of validations
     *
     * @param evt event
     */
    private void nifTextFocusLost(FocusEvent evt) {
        try {
            student.setNif(initNif.getText());
            infoNif.setText("OK.");
            validateValues(1, true);
        } catch (PropertyVetoException | NumberFormatException ex) {
            validateValues(1, false);
            infoNif.setText("Invalid NIF**");
        }
    }

    /**
     * Check if the new name is valid and inform the user. If is valid modify
     * student name and introduce true in the list of validations, in case of
     * error not modify it and introduce false in the list of validations
     *
     * @param evt event
     */
    private void nameTextFocusLost(FocusEvent evt) {
        try {
            student.setName(initName.getText());
            infoName.setText("OK.");
            validateValues(2, true);
        } catch (PropertyVetoException ex) {
            validateValues(2, false);
            infoName.setText("Invalid NAME**");
        }
    }

    /**
     * check if student age are minor in case of minor the button yes is
     * autochemscally marked in otherwise case the button no is autochemscally
     * marked
     *
     * @param age
     */
    private void checkAge(int age) {
        if (age >= 18) {
            try {
                yesButton.setSelected(false);
                noButton.setSelected(true);
                student.setMinor(false);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(StudentPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (age >= 0 && age < 18) {
            try {
                noButton.setSelected(false);
                yesButton.setSelected(true);
                student.setMinor(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(StudentPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Display student
     */
    public void printStudent() {
        clearTextFields();
        initId.setText(String.valueOf(student.getId()));
        initName.setText(student.getName());
        initNif.setText(student.getNif());
        initAge.setText(String.valueOf(student.getAge()));
        checkAge(student.getAge());

    }

    /**
     * Clear all panel textFields
     */
    public void clearTextFields() {

        initId.setText("0");
        initName.setText("Input name");
        initNif.setText("Input NIF");
        initAge.setText("0");

    }

    /**
     * Remove the student and display the result
     *
     * @param result result of remove the student
     */
    public void removeUser(boolean result) {

        if (result) {
            clearTextFields();
            showMessage("User removed");
            setEnabledComponents(false);
        } else {
            showMessage("User not removed");
        }

    }

    /**
     * Update the student and display the result
     *
     * @param result result of update the student
     */
    public void updateUser(boolean result) {

        if (result) {
            showMessage("User modified");
        } else {
            showMessage("User not modified");
        }

    }

    /**
     * displays message
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(panel, message);
    }

    /**
     * Enable and disable some components
     * @param state new state for the component
     */
    public void setEnabledComponents(boolean state) {

        deleteButton.setEnabled(state);
        updateButton.setEnabled(state);
        initNif.setEnabled(state);
        initName.setEnabled(state);
        initAge.setEnabled(state);

    }

    //Getters Setters
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    private void validateValues(int index, boolean result) {
        validValues[index] = result;
    }

    public boolean[] getValidValues() {
        return validValues;
    }

    public void setValidValues(boolean[] validValues) {
        this.validValues = validValues;
    }

    public JTextField getInitId() {
        return initId;
    }

    public void setInitId(JTextField initId) {
        this.initId = initId;
    }

    public JTextField getInitNif() {
        return initNif;
    }

    public void setInitNif(JTextField initNif) {
        this.initNif = initNif;
    }

    public JTextField getInitName() {
        return initName;
    }

    public void setInitName(JTextField initName) {
        this.initName = initName;
    }

    public JTextField getInitAge() {
        return initAge;
    }

    public void setInitAge(JTextField initAge) {
        this.initAge = initAge;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public JButton getFindButton() {
        return findButton;
    }

    public void setFindButton(JButton findButton) {
        this.findButton = findButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public void setUpdateButton(JButton updateButton) {
        this.updateButton = updateButton;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
    
    

}

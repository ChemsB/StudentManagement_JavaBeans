/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.proven.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

/**
 *
 * @author chems
 */
public class Student implements Serializable {

    //attributes
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);

    private long id;
    private String nif;
    private String name;
    private int age;
    private boolean minor;

    //Constructors
    public Student() {

    }

    public Student(long id, String nif, String name, int age, boolean minor) {

        this.id = id;
        this.nif = nif;
        this.name = name;
        this.age = age;
        this.minor = minor;
    }

    //Getters Setters
    public long getId() {
        return id;
    }

    public void setId(long id) throws PropertyVetoException {
        long previous = getId();
        vetoableChangeSupport.fireVetoableChange("id", previous, id);
        this.id = id;
        propertyChangeSupport.firePropertyChange("id", previous, id);
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) throws PropertyVetoException {

        String previous = getNif();
        vetoableChangeSupport.fireVetoableChange("nif", previous, nif);
        this.nif = nif;
        propertyChangeSupport.firePropertyChange("nif", previous, nif);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws PropertyVetoException {

        String previous = getName();
        vetoableChangeSupport.fireVetoableChange("name", previous, name);
        this.name = name;
        propertyChangeSupport.firePropertyChange("name", previous, name);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws PropertyVetoException {

        int previous = getAge();
        vetoableChangeSupport.fireVetoableChange("age", previous, age);
        this.age = age;
        propertyChangeSupport.firePropertyChange("age", previous, age);
    }

    public boolean isMinor() {
        return minor;
    }

    public void setMinor(boolean minor) throws PropertyVetoException {
        this.minor = minor;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void addVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.addVetoableChangeListener(listener);
    }

    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.removeVetoableChangeListener(listener);
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", nif=" + nif + ", name=" + name + ", age=" + age + ", minor=" + minor + '}';
    }

}

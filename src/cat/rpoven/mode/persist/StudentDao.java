package cat.rpoven.mode.persist;

import cat.proven.model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Student dao class
 *
 * @author Chems
 */
public class StudentDao {

    //Attributes
    private ResultSet resultSet;
    private Student student;
    private Connection connection;
    private List<Student> listWithStudents = new ArrayList<>();

    //Constructor
    public StudentDao() {
        init();
    }

    /**
     * connect to the database and get all list of students
     */
    public void init() {
        try {
            DbConnect.loadDriver();
            connection = DbConnect.getConnection();
            if (connection != null) {
                Statement stmt = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                String sql = "SELECT * FROM student";
                resultSet = stmt.executeQuery(sql);

                while (resultSet.next()) {
                    student = resultSetToStudent(resultSet);
                    listWithStudents.add(student);
                }

            }

        } catch (ClassNotFoundException |  SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Converts data from current row of resulset into a student
     *
     * @param rs the result to extract data from
     * @return
     */
    private Student resultSetToStudent(ResultSet rs) throws SQLException {

        long id = rs.getLong("id");
        String nif = rs.getString("nif");
        String name = rs.getString("name");
        int age = rs.getInt("age");
        boolean minor = rs.getBoolean("minor");
        Student p = new Student(id, nif, name, age, minor);
        return p;

    }

    /**
     * Find student by id
     *
     * @param id to search
     * @return student with same id
     */
    public Student findById(long id) {

        Student studentId = new Student();
        try {
            boolean res = false;

            DbConnect.loadDriver();
            connection = DbConnect.getConnection();
            String sql = "select * from student where id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setLong(1, id);
            resultSet = stmt.executeQuery();
            resultSet.next();
            studentId = resultSetToStudent(resultSet);
            return studentId;
            
        } catch (ClassNotFoundException | SQLException ex) {
            studentId=null;
        }

        return studentId;

    }

    /**
     * remove student from the database
     *
     * @param id to find student
     * @return true in case of removed, false in case of error
     */
    public boolean removeStudent(long id) {

        boolean res = false;
        Student studentToRemove = new Student();
        try {
            DbConnect.loadDriver();
            connection = DbConnect.getConnection();
            String sql = "delete from student where id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);

            for (Student std : listWithStudents) {
                if (std.getId() == id) {
                    studentToRemove = std;
                    res = true;
                }
            }

            if (res && studentToRemove != null) {
                listWithStudents.remove(studentToRemove);
                stmt.executeUpdate();
            } else {
                res = false;
            }

        } catch (SQLException ex) {
            res = false;
        } catch (ClassNotFoundException ex) {
            res = false;
        }
        return res;

    }

    /**
     * Update a students data
     *
     * @param student new student data
     * @param oldId to find student
     * @return true in case of updated, false in case of error
     */
    public boolean updateStudent(Student student, long oldId) {
        boolean res = false;
        try {

            for (Student std : listWithStudents) {
                if (std.getId() == oldId) {
                    res = true;
                }
            }

            if (res) {
                DbConnect.loadDriver();
                connection = DbConnect.getConnection();
                String sql = "UPDATE student SET id = ?, nif = ?, name = ?, age = ?, minor = ? WHERE id = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setLong(1, student.getId());
                stmt.setString(2, student.getNif());
                stmt.setString(3, student.getName());
                stmt.setInt(4, student.getAge());
                stmt.setBoolean(5, student.isMinor());
                stmt.setLong(6, oldId);

                stmt.executeUpdate();
                stmt.close();
                res = true;
            } else {
                res = false;
            }

        } catch (ClassNotFoundException ex) {
            res = false;
        } catch (SQLException ex) {
            res = false;
        }
        return res;

    }

    //Getters setters
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}

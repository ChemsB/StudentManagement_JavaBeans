package cat.proven.views;

import cat.proven.model.Model;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Main Frame student app
 *
 * @author chems
 */
public class MainFrame extends JFrame implements ActionListener {

    private String aboutMessage;
    private ActionListener listener;
    private JMenuBar menuBar;

    private StudentPanel studentPanel;

    //Constructor
    public MainFrame() {
        listener = this;
        aboutMessage = "<html> <p> <strong>Student APP V1.0</strong> </p> <p> Autor: Chems </p> </html>";
        initComponents();
    }

    /**
     * Init components from MainFrame
     */
    private void initComponents() {

        setTitle("Student App");

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();

            }
        });
        buildMenu();
        getContentPane().add(new WelcomePanel());
        setResizable(false);
        setSize(500, 500);
    }

    /**
     * Build menu from MainFrame
     */
    private void buildMenu() {
        menuBar = new JMenuBar();
        JMenu mnu;
        JMenuItem mItem;

        mnu = new JMenu("File");
        mItem = new JMenuItem("Exit");
        mItem.setActionCommand("exit");
        mItem.addActionListener(this);
        mnu.add(mItem);
        menuBar.add(mnu);

        mnu = new JMenu("Edit");
        mItem = new JMenuItem("Show student form");
        mItem.setActionCommand("showStudentF");
        mItem.addActionListener(this);
        mnu.add(mItem);
        menuBar.add(mnu);

        mnu = new JMenu("About");
        mItem = new JMenuItem("about");
        mItem.addActionListener(this);
        mnu.add(mItem);
        menuBar.add(mnu);
        setJMenuBar(menuBar);

    }

    /**
     * exits application after asking for confirchemson.
     */
    private void exitApplication() {

        int answer = JOptionPane.showConfirmDialog(this, "Exit application are you sure?");
        if (answer == JOptionPane.OK_OPTION) {
            System.exit(0);
        }

    }

    /**
     * Menu actions
     * @param e action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action != null) {
            switch (action) {
                case "exit":
                    exitApplication();
                    break;

                case "showStudentF":
                    showStudentPanel();
                    break;

                case "about":
                    showAboutDialog();
                    break;
            }
        }
    }

    /**
     * displays about dialog
     */
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this, aboutMessage, "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * show panel with student form
     */
    private void showStudentPanel() {
        Container panel = getContentPane();
        panel.removeAll();
        studentPanel = StudentPanel.getPanel();
        Model controllers = new Model();
        panel.add(studentPanel);
        validate();

    }

}

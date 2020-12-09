package cat.proven.views;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Welcome panel
 * 
 * @author chems
 */
public class WelcomePanel extends JPanel{
    private String welcomeMessage;
    //Constructor
    public WelcomePanel(){
        welcomeMessage= "WELCOME TO STUDENT APP";
        initComponents();
    }

    //initialize components
    private void initComponents() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel(welcomeMessage);
        add(label,BorderLayout.CENTER);
        
    }
}

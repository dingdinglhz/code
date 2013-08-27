
/*package taskprogram;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class TaskProgram{
    //Jframe = window
    private JFrame frame;
    private JPanel panel;
    private JButton toUpperCase;
    private Rectangle rT;
    public final void iniFrame(){
        frame = new JFrame("Task Program");
        frame.setVisible(true);
        frame.setSize(750,750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        frame.setContentPane(panel);
         
        
        toUpperCase = new JButton("To Upper Case");
        rT = new Rectangle(50,50,100,100);
        toUpperCase.setBounds(rT);
        toUpperCase.setVisible(true);
        toUpperCase.setAlignmentX(10);
        //toUpperCase.setPreferredSize(new Dimension(50,50));
        panel.add(toUpperCase);
        toUpperCase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              ToUpperCase i = new ToUpperCase();
            } });
       
        panel.repaint();
        panel.setVisible(true);
    }
    public TaskProgram(){
        iniFrame();
        //panel.repaint();
    }
    public static void main(String[] args) {
        TaskProgram taskProgram = new TaskProgram();
    }
}
*/
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import taskprogram.ToUpperCase;
public class TaskProgram extends JFrame{
    private JPanel panel;
    private JButton toUpperCase;
    private Rectangle rT;
    public final void iniFrame(){
        this.setTitle("Task Program");
        this.setVisible(true);
        this.setSize(750,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        panel = new JPanel();
        
        toUpperCase = new JButton("To Upper Case");
        //rT = new Rectangle(50,50,100,100);
        //toUpperCase.setBounds(0,0,100,200);
        toUpperCase.setVisible(true);
        //toUpperCase.setAlignmentX(10);
        //toUpperCase.setPreferredSize(new Dimension(50,50));
        //panel.add(toUpperCase);
        toUpperCase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              ToUpperCase i = new ToUpperCase();
            } });
        this.setContentPane(panel);
        panel.repaint();
        panel.setVisible(true);
    }
    public TaskProgram(){
        iniFrame();
        //panel.repaint();
    }
    public static void main(String[] args) {
        TaskProgram taskProgram = new TaskProgram();
    }
}
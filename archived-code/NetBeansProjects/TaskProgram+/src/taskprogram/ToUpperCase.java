package taskprogram;

import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class ToUpperCase{
    //Jframe = window
    private JFrame frame;
    private JPanel panel;
    private JButton toUpperCase;
    private Rectangle rT;
    private Rectangle rTe;
    private TextField text;
    public final void iniFrame(){
        frame = new JFrame("To Upper Case");
        frame.setVisible(true);
        frame.setSize(750,750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        frame.setContentPane(panel);
        
        text = new TextField();
        rTe = new Rectangle(500,500,100,100);
        text.setBounds(rTe);
        panel.add(text);
        
         
        
        toUpperCase = new JButton("uuasuidsahdsa");
        rT = new Rectangle(100,100,50,50);
        rT.setLocation(100, 100);
        toUpperCase.setBounds(rT);
        toUpperCase.setVisible(true);
        toUpperCase.setLocation(0, 0);
        panel.add(toUpperCase);
        toUpperCase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               text.setText(text.getText().toUpperCase());
            } });
       
        panel.repaint();
    }
    public ToUpperCase(){
        iniFrame();
        //panel.repaint();
    }
    public static void main(String[] args) {
        ToUpperCase oUpperCase = new ToUpperCase();
    }
}

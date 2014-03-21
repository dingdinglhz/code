import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TrafficLight extends Applet {
	Button gBtn=new Button("Green");
	Button yBtn=new Button("Yello");
	Button rBtn=new Button("Red");
	char colorCode='R';
	public void init(){
		setBackground(Color.black);
		gBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				colorCode='G';
				repaint();
			}
		});
		yBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				colorCode='Y';
				repaint();
			}
		});
		rBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				colorCode='R';
				repaint();
			}
		});
		this.add(gBtn);
		this.add(yBtn);
		this.add(rBtn);
	}
	public void paint(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 20, 150, 450);
		g.setColor(Color.gray);
		for(int i=45;i<470;i+=150){
			g.fillOval(25, i, 100,100);
		}
		switch(colorCode){
		case 'G':
			g.setColor(Color.green);
			g.fillOval(25, 45, 100,100);
			break;
		case 'Y':
			g.setColor(Color.yellow);
			g.fillOval(25, 195, 100,100);
			break;
		case 'R':
			g.setColor(Color.red);
			g.fillOval(25, 345, 100,100);
			break;
		}
	}
}

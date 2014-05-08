import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;



public class LensDemoTutorialDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -657503752198036664L;
	static final int DEFAULT_WIDTH=500;
	static final int DEFAULT_HEIGHT=225;
	private static final int ANI_FRAME = 103;
	private static final long ANI_PERIOD = 10;
	private double f[],u[];
	private String text[];
	private int n;
	private int sceneNum=-1;
	private int oldSceneNum;
	private JTextArea textArea;
	private JButton btnNext;
	private JButton btnPrevious;
	private LensDemoApplication parentDemo;
	private TutorialAnimateTask task;
	private Timer timer;
	
	/**
	 * Create the dialog.
	 */
	public LensDemoTutorialDialog() {
		
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationByPlatform(true);
		
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		btnPrevious = new JButton("\u2190 Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setScene(sceneNum-1);
			}
		});
		panel.add(btnPrevious);
		
		btnNext = new JButton("Next \u2192");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(sceneNum==n){
					dispose();
				}else{
				setScene(sceneNum+1);
				}
			}
		});
		panel.add(btnNext);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setMargin(new Insets(5, 5, 5, 5));
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		getContentPane().add(textArea, BorderLayout.CENTER);
		
		loadScript();
	}
	
	private void loadScript(){
		Properties prop= new Properties();
		InputStream in = LensDemoTutorialDialog.class.getResourceAsStream("resource.dtd");
	    try {
			prop.loadFromXML(in);
		} catch (InvalidPropertiesFormatException e) {
			System.out.println("Invalid Properties Format: resource.dtd");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException!");
			e.printStackTrace();
		}
	    n=Integer.parseInt(prop.getProperty("n"));
	    System.out.println(n);
	    f=new double[n+1];
	    u=new double[n+1];
	    text=new String[n+1];
	    for(int i=0;i<=n;i++){
	    	f[i]=Double.parseDouble(prop.getProperty("f-"+i));
	    	u[i]=Double.parseDouble(prop.getProperty("u-"+i));
	    	text[i]=prop.getProperty("text-"+i);
	    	//System.out.println("i:"+i+" f:"+f[i]+" u:"+u[i]+" text:"+text[i]);
	    }
	}
	
	protected void setScene(int newSceneNum){
		if(newSceneNum>n || newSceneNum<0){
			System.out.print("There is only "+n+" scenes. Scene No."+newSceneNum+" not Found");
		}else{
			oldSceneNum=sceneNum;
			sceneNum=newSceneNum;
			if(newSceneNum==n){
				btnNext.setText("Finish Tutorial");
			}else{
				btnNext.setText("Next \u2192");
			}
			if(newSceneNum==0){
				btnPrevious.setEnabled(false);
			}else{
				btnPrevious.setEnabled(true);
			}
			textArea.setText(text[newSceneNum]);
			if(parentDemo!=null){
				if(oldSceneNum==-1){
					parentDemo.setValueExternal(f[sceneNum], u[sceneNum], 0,
							   Double.NaN, LensDemoApplication.SolveFor.V);
				}
				else{
					timer=new Timer();
					task = new TutorialAnimateTask(f[oldSceneNum],u[oldSceneNum],
							f[sceneNum],u[sceneNum],ANI_FRAME);
					timer.schedule(task,0, ANI_PERIOD);
				}
			}else{
				System.out.print("The tutorial dialog is unable to find the parent QAQ!");
			}
		}
	}

	public void setParentDemo(LensDemoApplication parentDemo) {
		this.parentDemo = parentDemo;
	}
	class TutorialAnimateTask extends TimerTask {
		private double f1,u1,f2,u2;
		private int n,i;
		TutorialAnimateTask(double f1, double u1, double f2, double u2, int n){
			this.f1=f1;
			this.u1=u1;
			this.f2=f2;
			this.u2=u2;
			this.n =n ;
			i=0;
			btnNext.setEnabled(false);
			btnPrevious.setEnabled(false);
		}
		@Override
		public void run() {
			if(i>n){
				cancel();
				timer.cancel();
				btnNext.setEnabled(true);
				btnPrevious.setEnabled(true);
				return;
			}
			parentDemo.setValueExternal(f1+(f2-f1)*i/n, u1+(u2-u1)*i/n, 0,
							   Double.NaN, LensDemoApplication.SolveFor.V);
			i++;
		}
		
	}
}

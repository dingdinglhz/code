/*--------------------------------------------------------------------------------------*/
/*  Program: LensDemoApplication                                                        */
/* 	An application that is able to solve an optical system involving one thin lens. It  */
/*  is able to solve the thin lens imaging equation 1/f = 1/v + 1/u to give an answer.  */
/*  Also, it is able to draw a clear optics ray diagram to illustrate graphically how   */
/*  the image is formed by refracting light rays using a thin lens. A brief tutorial is */
/*  implemented to guide the user recognize all typical patterns of how images are 	    */
/*  formed. As an educational program, learning is embeded in the demonstrations.       */
/*--------------------------------------------------------------------------------------*/
/*  Source File:LensDemoTutorialDialog.java                                                       */
/*  The class that implements the tutorial, which guide the user to learn different     */
/*  patterns of thin lens imaging.                                                      */
/*--------------------------------------------------------------------------------------*/
/*  Author: Hanzhen, Lin                                                                */
/*  Date:  May, 8th, 2014                                                               */
/*--------------------------------------------------------------------------------------*/
/*  Input :Tutorial scripts loaded from *.jar file.                                     */
/*  Output:Pre-set f and u values (passed to the main window); explanatory text.        */
/*--------------------------------------------------------------------------------------*/
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
//necessary imports.

public class LensDemoTutorialDialog extends JDialog {

	private static final long serialVersionUID = -657503752198036664L;
	static final int DEFAULT_WIDTH=500;
	static final int DEFAULT_HEIGHT=225;
	private static final int ANI_FRAME = 103;
	private static final long ANI_PERIOD = 10;
	//Useful constants concerning the size of the dialog and the animation.
	private double f[],u[];
	private String text[];
	private int n;
	//Arrays that stores the script of the tutorial.
	private int sceneNum=-1;
	private int oldSceneNum;
	//Store the current and previous scene number.
	private JTextArea textArea;
	private JButton btnNext;
	private JButton btnPrevious;
	private LensDemoApplication parentDemo;
	//Components on the dialog.
	private TutorialAnimateTask task;
	private Timer timer;
	//Timer and TimerTask used for animated transition.

	public LensDemoTutorialDialog() {
		
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationByPlatform(true);
		//Set the size
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		//Panel at the bottom to put the 2 buttons.
		btnPrevious = new JButton("\u2190 Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setScene(sceneNum-1);//go to previous scene
			}
		});
		panel.add(btnPrevious);
		//Add "previous" button
		btnNext = new JButton("Next \u2192");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(sceneNum==n){
					dispose(); //quite the program at last scene
				}else{
				setScene(sceneNum+1);//go to next scene
				}
			}
		});
		panel.add(btnNext);
		//Add "next" button
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true); //Do not separate word.
		textArea.setMargin(new Insets(5, 5, 5, 5));
		textArea.setEditable(false); // Not editable
		textArea.setLineWrap(true); //
		getContentPane().add(textArea, BorderLayout.CENTER);
		//Add the text area to display the explanatory message.
		loadScript(); //Load tutorial script.
	}
	
	private void loadScript(){
		//The method that load the tutorial script from resource file.
		Properties prop= new Properties();
		InputStream in = LensDemoTutorialDialog.class.getResourceAsStream("resource.dtd");
		//get input stream of the resource file.
	    try {
			prop.loadFromXML(in);//Parse resource file as DTD XML file.
		} catch (InvalidPropertiesFormatException e) {
			System.out.println("Invalid Properties Format: resource.dtd");
			e.printStackTrace();//exception
		} catch (IOException e) {
			System.out.println("IOException!");
			e.printStackTrace();//exception
		}
	    n=Integer.parseInt(prop.getProperty("n"));//Get the number of scenes.
	    //System.out.println(n); debug
	    f=new double[n+1];
	    u=new double[n+1];
	    text=new String[n+1];
	    //Initialize array according to n
	    for(int i=0;i<=n;i++){ //scene indexed from 0 to n
	    	f[i]=Double.parseDouble(prop.getProperty("f-"+i));
	    	u[i]=Double.parseDouble(prop.getProperty("u-"+i));
	    	text[i]=prop.getProperty("text-"+i);
	    	//get the pre-set value of f,u and the explanatory text.
	    	//System.out.println("i:"+i+" f:"+f[i]+" u:"+u[i]+" text:"+text[i]);
	    }
	}
	
	protected void setScene(int newSceneNum){
		//The method that set the program to a new Scene.
		if(newSceneNum>n || newSceneNum<0){
			System.out.print("There is only "+n+" scenes. Scene No."+newSceneNum+" not Found");
			//reject undefined scene index.
		}else{
			oldSceneNum=sceneNum;
			sceneNum=newSceneNum;
			//update the new scene number.
			textArea.setText(text[newSceneNum]);
			//display the explanatory text.
			if(parentDemo!=null){ //If parent demo exist, set the value of f and v.
				if(oldSceneNum==-1){ //If this is the first scene, simply set the value.
					parentDemo.setValueExternal(f[sceneNum], u[sceneNum], 0,
							   Double.NaN, LensDemoApplication.SolveFor.V);
				}
				else{//If previous scene exist, create an animated transition.
					timer=new Timer();
					task = new TutorialAnimateTask(f[oldSceneNum],u[oldSceneNum],
							f[sceneNum],u[sceneNum],ANI_FRAME);
					timer.schedule(task,0, ANI_PERIOD);
				}
			}else{
				System.out.print("The tutorial dialog is unable to find the parent QAQ!");
			}

			if(newSceneNum==n){ //The text of next button is different for the last scene.
				btnNext.setText("Finish Tutorial");
			}else{
				btnNext.setText("Next \u2192");
			}//The text of next button is different for the last scene.
			if(newSceneNum==0){
				btnPrevious.setEnabled(false);
			}else{
				btnPrevious.setEnabled(true);
			}//The previous button is disabled at first scene.
		}
	}

	public void setParentDemo(LensDemoApplication parentDemo) {
		this.parentDemo = parentDemo; //Set access to main window.
	}
	class TutorialAnimateTask extends TimerTask {//The TimerTask used in animation.
		private double f1,u1,f2,u2; //The original and final set of f,u value.
		private int n,i; //Number of frames and index of current frame
		TutorialAnimateTask(double f1, double u1, double f2, double u2, int n){
			//The constructor that set necessary values.
			this.f1=f1;
			this.u1=u1;
			this.f2=f2;
			this.u2=u2;
			this.n =n ;
			i=0; //Starts from frame number 0
			btnNext.setEnabled(false); 
			btnPrevious.setEnabled(false);
			//Disable the 2 buttons during the animation to avoid confusion.
		}
		@Override
		public void run() {
			if(i>n){
				cancel();
				timer.cancel();
				btnNext.setEnabled(true); //Enable the buttons since the
				btnPrevious.setEnabled(true); //animation has stopped.
				return; //If the current frame exceeds the last frame, stop.
			}
			parentDemo.setValueExternal(f1+(f2-f1)*i/n, u1+(u2-u1)*i/n, 0,
							   Double.NaN, LensDemoApplication.SolveFor.V);
			//Set the f and u value externally.
			i++; //Go to the next frame.
		}
		
	}
}

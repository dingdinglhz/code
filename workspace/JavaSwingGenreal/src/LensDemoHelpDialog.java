/*--------------------------------------------------------------------------------------*/
/*  Program: LensDemoApplication                                                        */
/* 	An application that is able to solve an optical system involving one thin lens. It  */
/*  is able to solve the thin lens imaging equation 1/f = 1/v + 1/u to give an answer.  */
/*  Also, it is able to draw a clear optics ray diagram to illustrate graphically how   */
/*  the image is formed by refracting light rays using a thin lens. A brief tutorial is */
/*  implemented to guide the user recognize all typical patterns of how images are 	    */
/*  formed. As an educational program, learning is embeded in the demonstrations.       */
/*--------------------------------------------------------------------------------------*/
/*  Source File:LensDemoHelp.java                                                       */
/*  A simple class implementing the help message dialog.                                */
/*--------------------------------------------------------------------------------------*/
/*  Author: Hanzhen, Lin                                                                */
/*  Date:  May, 8th, 2014                                                               */
/*--------------------------------------------------------------------------------------*/
/*  Input :Help message loaded from *.jar file.                                         */
/*  Output:Help message.                                                                */
/*--------------------------------------------------------------------------------------*/
import java.awt.Component;
import java.awt.FlowLayout;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class LensDemoHelpDialog extends JDialog {

	private static final long serialVersionUID = -3438570127843520704L;
	/**
	 * Create the dialog.
	 */
	public LensDemoHelpDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		InputStream in = LensDemoHelpDialog.class.getResourceAsStream("helpMessage.html");
		Scanner scan=new Scanner(in,"UTF-8");
		String helpMessage="";
		while(scan.hasNext()){
			helpMessage+=scan.nextLine();
		}
		JLabel lblHelpInfo = new JLabel(helpMessage);
		lblHelpInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblHelpInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(lblHelpInfo);
	}
}

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

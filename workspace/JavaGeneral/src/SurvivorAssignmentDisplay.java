import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class SurvivorAssignmentDisplay {

	private JFrame frame;
	private JTextField textField;
	private SurvivorAssignment survivor;
	private JButton MainButton;
	private JButton EliminateButton;
	private JTextArea textArea;
	private Box horizontalBox;
	private JLabel infoLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SurvivorAssignmentDisplay window = new SurvivorAssignmentDisplay();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/*
	 * public static void main(String[] args) {//main method;
		System.out.println("Backup is done");
		survivor.elimination(scan);//elimination process
		survivor.summary();//summary process.
	}
	 * */
	void startSurvivor(){
		Scanner scan=new Scanner(textArea.getText());
		survivor.input(scan);
		textArea.setText("");
		textArea.append("Input is processed Successfully\n");
		MainButton.setEnabled(false);
		MainButton.setVisible(false);
		survivor.backUp();
		EliminateButton.setEnabled(true);
		EliminateButton.setVisible(true);
		textArea.append("Backup is done.\n Please eliminate contestors.\n");
	}
	void delete(){
		if(survivor.delete(textField.getText())){
			infoLabel.setText("The Contestor is eliminated successfully");
			JOptionPane.showConfirmDialog(null, "The Contestor is eliminated successfully",
					"Success",JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			infoLabel.setText("Deletion is not successful, please" +
					" check if the name is correct!");
			JOptionPane.showConfirmDialog(null, "Please check if the name is correct!",
					"Deletion is not successful",JOptionPane.ERROR_MESSAGE);
		}
		textField.setText("");
		if(survivor.hasWinner()){summary();}
	}
	void summary(){
		EliminateButton.setEnabled(false);
		EliminateButton.setVisible(false);
		textArea.append(survivor.summaryString());
		infoLabel.setText("Winner is determined!");
	}
	
	/**
	 * Create the application.
	 */
	public SurvivorAssignmentDisplay() {
		initialize();
		survivor=new SurvivorAssignment();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textArea = new JTextArea();
		frame.getContentPane().add(textArea, BorderLayout.CENTER);
		
		horizontalBox = Box.createHorizontalBox();
		frame.getContentPane().add(horizontalBox, BorderLayout.SOUTH);
		
		textField = new JTextField();
		horizontalBox.add(textField);
		textField.setColumns(10);
		
		EliminateButton = new JButton("Eliminate");
		EliminateButton.setVisible(false);
		EliminateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				delete();
			}
		});
		EliminateButton.setEnabled(false);
		horizontalBox.add(EliminateButton);
		
		MainButton = new JButton("Enter Survivor List");
		MainButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				startSurvivor();
			}
		});
		horizontalBox.add(MainButton);
		
		infoLabel = new JLabel("Enter the name of contesters:");
		frame.getContentPane().add(infoLabel, BorderLayout.NORTH);
	}

}

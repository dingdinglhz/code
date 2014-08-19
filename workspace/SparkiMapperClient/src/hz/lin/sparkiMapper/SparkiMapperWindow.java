package hz.lin.sparkiMapper;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class SparkiMapperWindow {

	private JFrame frame;
	private MapCanvas canvas;
	private MapData data;
	/**
	 * Launch the application.
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SparkiMapperWindow window = new SparkiMapperWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws FileNotFoundException 
	 */
	public SparkiMapperWindow() throws FileNotFoundException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws FileNotFoundException 
	 */
	private void initialize() throws FileNotFoundException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas=new MapCanvas();
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		
		Scanner scan=new Scanner(new File("D:\\programming\\code\\arduino\\SparkiMapper\\angle data\\0814151043-bk.txt"));
		data=new MapData();
		data.loadFromScanner(scan);
		data.debug();
		canvas.setDataSource(data);
		canvas.repaint();
	}

}

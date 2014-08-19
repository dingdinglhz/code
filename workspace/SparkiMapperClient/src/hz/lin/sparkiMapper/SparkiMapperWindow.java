package hz.lin.sparkiMapper;


import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JSlider;

public class SparkiMapperWindow {

	private static final int SlIDER_MAX = 10000;
	private JFrame frame;
	private MapCanvas canvas;
	private MapData data;
	private JLabel labelErrBase;
	private JSpinner spinnerErrBase;
	private JLabel labelDisBase;
	private JSpinner spinnerDisBase;
	private JLabel labelDisThld;
	private JSpinner spinnerDisThld;
	private JLabel lblPortion;
	private JSlider sliderPortion;
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
		Scanner scan=new Scanner(new File("D:\\programming\\code\\arduino\\SparkiMapper\\angle data\\0819163645.txt"));
		//0819144946 ,0814151043-bk, 0819150609,0819163645
		data=new MapData();
		data.loadFromScanner(scan);
		//data.debug();
		canvas.setDataSource(data);
		canvas.repaint();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws FileNotFoundException 
	 */
	private void initialize()  {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas=new MapCanvas();
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 22, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblPortion = new JLabel("Portion:");
		GridBagConstraints gbc_lblPortion = new GridBagConstraints();
		gbc_lblPortion.insets = new Insets(0, 0, 5, 5);
		gbc_lblPortion.gridx = 0;
		gbc_lblPortion.gridy = 0;
		panel.add(lblPortion, gbc_lblPortion);
		
		sliderPortion = new JSlider(0,SlIDER_MAX,SlIDER_MAX);
		sliderPortion.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider tmp = (JSlider) e.getSource();
                double i = (double)tmp.getValue() /((double)SlIDER_MAX);
                canvas.setPortion(i);
                canvas.repaint();
                
            }
        });
		GridBagConstraints gbc_sliderPortion = new GridBagConstraints();
		gbc_sliderPortion.fill = GridBagConstraints.BOTH;
		gbc_sliderPortion.gridwidth = 5;
		gbc_sliderPortion.insets = new Insets(0, 0, 5, 5);
		gbc_sliderPortion.gridx = 1;
		gbc_sliderPortion.gridy = 0;
		panel.add(sliderPortion, gbc_sliderPortion);
		
		labelErrBase = new JLabel("ErrBase:");
		GridBagConstraints gbc_labelErrBase = new GridBagConstraints();
		gbc_labelErrBase.anchor = GridBagConstraints.EAST;
		gbc_labelErrBase.insets = new Insets(0, 0, 0, 5);
		gbc_labelErrBase.gridx = 0;
		gbc_labelErrBase.gridy = 1;
		panel.add(labelErrBase, gbc_labelErrBase);
		
		spinnerErrBase = new JSpinner();
		spinnerErrBase.setModel(new SpinnerNumberModel(0.618, 0, 1.0, 0.01));
		spinnerErrBase.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerNumberModel source = (SpinnerNumberModel) e.getSource();
                canvas.setValidityErrBase(source.getNumber().doubleValue());
                canvas.repaint();
            }
        });
		GridBagConstraints gbc_spinnerErrBase = new GridBagConstraints();
		gbc_spinnerErrBase.fill = GridBagConstraints.BOTH;
		gbc_spinnerErrBase.insets = new Insets(0, 0, 0, 5);
		gbc_spinnerErrBase.gridx = 1;
		gbc_spinnerErrBase.gridy = 1;
		panel.add(spinnerErrBase, gbc_spinnerErrBase);
		
		labelDisBase = new JLabel("DisBase:");
		GridBagConstraints gbc_labelDisBase = new GridBagConstraints();
		gbc_labelDisBase.insets = new Insets(0, 0, 0, 5);
		gbc_labelDisBase.gridx = 2;
		gbc_labelDisBase.gridy = 1;
		panel.add(labelDisBase, gbc_labelDisBase);
		
		spinnerDisBase = new JSpinner();
		spinnerDisBase.setModel(new SpinnerNumberModel(1.13, 1.0, 10.0, 0.01));
		spinnerDisBase.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerNumberModel source = (SpinnerNumberModel) e.getSource();
                canvas.setValidityDisBase(source.getNumber().doubleValue());
                canvas.repaint();
            }
        });
		GridBagConstraints gbc_spinnerDisBase = new GridBagConstraints();
		gbc_spinnerDisBase.fill = GridBagConstraints.BOTH;
		gbc_spinnerDisBase.insets = new Insets(0, 0, 0, 5);
		gbc_spinnerDisBase.gridx = 3;
		gbc_spinnerDisBase.gridy = 1;
		panel.add(spinnerDisBase, gbc_spinnerDisBase);
		
		labelDisThld = new JLabel("DisThld:");
		GridBagConstraints gbc_labelDisThld = new GridBagConstraints();
		gbc_labelDisThld.insets = new Insets(0, 0, 0, 5);
		gbc_labelDisThld.gridx = 4;
		gbc_labelDisThld.gridy = 1;
		panel.add(labelDisThld, gbc_labelDisThld);
		
		spinnerDisThld = new JSpinner();
		spinnerDisThld.setModel(new SpinnerNumberModel(80.0, 0.0, 300.0, 5.0));
		spinnerDisThld.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerNumberModel source = (SpinnerNumberModel) e.getSource();
                canvas.setValidityDisThld(source.getNumber().doubleValue());
                canvas.repaint();
            }
        });
		GridBagConstraints gbc_spinnerDisThld = new GridBagConstraints();
		gbc_spinnerDisThld.fill = GridBagConstraints.BOTH;
		gbc_spinnerDisThld.insets = new Insets(0, 0, 0, 5);
		gbc_spinnerDisThld.gridx = 5;
		gbc_spinnerDisThld.gridy = 1;
		panel.add(spinnerDisThld, gbc_spinnerDisThld);
		
		
	}

}

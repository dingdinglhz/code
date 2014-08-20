package hz.lin.sparkiMapper;


import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class SparkiMapperWindow {

	private static final int SlIDER_MAX = 10000;
	private JFrame frmSparkiMapper;
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
	private JButton btnSelectfile;
	private JFileChooser chooser;
	private JLabel lblScale;
	private JSpinner spinnerScale;
	
	private SerialReceiver serRec;
	private JButton btnSelectport;
	/**
	 * Launch the application.
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SparkiMapperWindow window = new SparkiMapperWindow();
					window.frmSparkiMapper.setVisible(true);
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
		chooser = new JFileChooser(System.getProperty("user.dir"));
		
	}
	private void changeDataFile(File f){
		try {
			Scanner scan=new Scanner(f);
			data=new MapData();
			data.loadFromScanner(scan);
			//data.debug();
			canvas.setDataSource(data);
			canvas.repaint();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot access selected file!");
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws FileNotFoundException 
	 */
	private void initialize()  {
		frmSparkiMapper = new JFrame();
		frmSparkiMapper.setTitle("Sparki Mapper");
		frmSparkiMapper.setMinimumSize(new Dimension(600, 300));
		frmSparkiMapper.setBounds(100, 100, 450, 300);
		frmSparkiMapper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas=new MapCanvas();
		frmSparkiMapper.getContentPane().add(canvas, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		frmSparkiMapper.getContentPane().add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 22};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0};
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
		gbc_sliderPortion.gridwidth = 7;
		gbc_sliderPortion.insets = new Insets(0, 0, 5, 5);
		gbc_sliderPortion.gridx = 1;
		gbc_sliderPortion.gridy = 0;
		panel.add(sliderPortion, gbc_sliderPortion);
		
		btnSelectport = new JButton("SelectPort");
		btnSelectport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String portName=JOptionPane.showInputDialog("Please input the name of the serial port:");
				if(serRec!=null){
					serRec.stop();
				}
				serRec=new SerialReceiver(portName);
				canvas.setDataSource(serRec.data);
				serRec.setCanvas(canvas);
			}
		});
		GridBagConstraints gbc_btnSelectport = new GridBagConstraints();
		gbc_btnSelectport.insets = new Insets(0, 0, 5, 0);
		gbc_btnSelectport.gridx = 8;
		gbc_btnSelectport.gridy = 0;
		panel.add(btnSelectport, gbc_btnSelectport);
		
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
		
		btnSelectfile = new JButton("SelectFile");
		btnSelectfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(frmSparkiMapper);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				    	File f=chooser.getSelectedFile();
				       //System.out.println("You chose to open this file: " +f.getAbsolutePath());
				       changeDataFile(f);
				    }
			}
		});
		
		lblScale = new JLabel("Scale:");
		GridBagConstraints gbc_lblScale = new GridBagConstraints();
		gbc_lblScale.insets = new Insets(0, 0, 0, 5);
		gbc_lblScale.gridx = 6;
		gbc_lblScale.gridy = 1;
		panel.add(lblScale, gbc_lblScale);
		
		spinnerScale = new JSpinner();
		spinnerScale.setModel(new SpinnerNumberModel(1.0,0.0, 30.0,0.5));
		spinnerScale.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerNumberModel source = (SpinnerNumberModel) e.getSource();
                canvas.setScale(source.getNumber().doubleValue());
                canvas.repaint();
            }
        });
		GridBagConstraints gbc_spinnerScale = new GridBagConstraints();
		gbc_spinnerScale.fill = GridBagConstraints.BOTH;
		gbc_spinnerScale.insets = new Insets(0, 0, 0, 5);
		gbc_spinnerScale.gridx = 7;
		gbc_spinnerScale.gridy = 1;
		panel.add(spinnerScale, gbc_spinnerScale);
		GridBagConstraints gbc_btnSelectfile = new GridBagConstraints();
		gbc_btnSelectfile.fill = GridBagConstraints.BOTH;
		gbc_btnSelectfile.gridx = 8;
		gbc_btnSelectfile.gridy = 1;
		panel.add(btnSelectfile, gbc_btnSelectfile);
		
		
	}

}

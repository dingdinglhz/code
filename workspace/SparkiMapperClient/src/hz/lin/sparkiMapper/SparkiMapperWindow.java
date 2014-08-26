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
import javax.swing.JToggleButton;
import javax.swing.BoxLayout;

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
	private JPanel panelEast;
	private JToggleButton tglbtnManualControl;
	private JSpinner spinnerCT;
	private JButton btnTurn;
	private JSpinner spinnerCM;
	private JButton btnMove;
	private JToggleButton tglbtnNoWarning;
	
	private boolean suppressControlWarning=false;
	private boolean controlStarted=false;
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
	private void startControl(){
		if(serRec==null){
			JOptionPane.showMessageDialog(frmSparkiMapper,"Cannot control robot while serial port is null",
					"Cannot Start Control",JOptionPane.ERROR_MESSAGE);
			tglbtnManualControl.setSelected(false);
			return;
		}
		controlStarted=true;
		serRec.writeData("CS\n");
		spinnerCT.setEnabled(true);
		btnTurn.setEnabled(true);
		spinnerCM.setEnabled(true);
		btnMove.setEnabled(true);
		tglbtnNoWarning.setEnabled(true);
	}
	private void stopControl(){
		if(serRec==null){
			JOptionPane.showMessageDialog(frmSparkiMapper,"Serial port is null while stopping control",
					"Unexpected Situation",JOptionPane.WARNING_MESSAGE);
		}
		controlStarted=false;
		serRec.writeData("CE\n");
		spinnerCT.setEnabled(false);
		btnTurn.setEnabled(false);
		spinnerCM.setEnabled(false);
		btnMove.setEnabled(false);
		tglbtnNoWarning.setEnabled(false);
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
		canvas.setEnabled(false);
		frmSparkiMapper.getContentPane().add(canvas, BorderLayout.CENTER);
		
		JPanel panelSouth = new JPanel();
		frmSparkiMapper.getContentPane().add(panelSouth, BorderLayout.SOUTH);
		GridBagLayout gbl_panelSouth = new GridBagLayout();
		gbl_panelSouth.columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelSouth.rowHeights = new int[]{0, 0};
		gbl_panelSouth.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0};
		gbl_panelSouth.rowWeights = new double[]{0.0, 0.0};
		panelSouth.setLayout(gbl_panelSouth);
		
		lblPortion = new JLabel("Portion:");
		GridBagConstraints gbc_lblPortion = new GridBagConstraints();
		gbc_lblPortion.insets = new Insets(0, 0, 5, 5);
		gbc_lblPortion.gridx = 0;
		gbc_lblPortion.gridy = 0;
		panelSouth.add(lblPortion, gbc_lblPortion);
		
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
		panelSouth.add(sliderPortion, gbc_sliderPortion);
		
		btnSelectport = new JButton("SelectPort");
		btnSelectport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tglbtnManualControl.setEnabled(true);
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
		panelSouth.add(btnSelectport, gbc_btnSelectport);
		
		labelErrBase = new JLabel("ErrBase:");
		GridBagConstraints gbc_labelErrBase = new GridBagConstraints();
		gbc_labelErrBase.anchor = GridBagConstraints.EAST;
		gbc_labelErrBase.insets = new Insets(0, 0, 0, 5);
		gbc_labelErrBase.gridx = 0;
		gbc_labelErrBase.gridy = 1;
		panelSouth.add(labelErrBase, gbc_labelErrBase);
		
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
		panelSouth.add(spinnerErrBase, gbc_spinnerErrBase);
		
		labelDisBase = new JLabel("DisBase:");
		GridBagConstraints gbc_labelDisBase = new GridBagConstraints();
		gbc_labelDisBase.insets = new Insets(0, 0, 0, 5);
		gbc_labelDisBase.gridx = 2;
		gbc_labelDisBase.gridy = 1;
		panelSouth.add(labelDisBase, gbc_labelDisBase);
		
		spinnerDisBase = new JSpinner();
		spinnerDisBase.setModel(new SpinnerNumberModel(1.13, 1.0, null, 0.01));
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
		panelSouth.add(spinnerDisBase, gbc_spinnerDisBase);
		
		labelDisThld = new JLabel("DisThld:");
		GridBagConstraints gbc_labelDisThld = new GridBagConstraints();
		gbc_labelDisThld.insets = new Insets(0, 0, 0, 5);
		gbc_labelDisThld.gridx = 4;
		gbc_labelDisThld.gridy = 1;
		panelSouth.add(labelDisThld, gbc_labelDisThld);
		
		spinnerDisThld = new JSpinner();
		spinnerDisThld.setModel(new SpinnerNumberModel(80.0, 0.0, null, 5.0));
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
		panelSouth.add(spinnerDisThld, gbc_spinnerDisThld);
		
		btnSelectfile = new JButton("SelectFile");
		btnSelectfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//JFileChooser chooser = new JFileChooser();
				tglbtnManualControl.setEnabled(false);
				int returnVal = chooser.showOpenDialog(frmSparkiMapper);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				    	if(serRec!=null){
							serRec.stop();
							serRec=null;
						}
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
		panelSouth.add(lblScale, gbc_lblScale);
		
		spinnerScale = new JSpinner();
		spinnerScale.setModel(new SpinnerNumberModel(1.0,0.0, null,0.5));
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
		panelSouth.add(spinnerScale, gbc_spinnerScale);
		GridBagConstraints gbc_btnSelectfile = new GridBagConstraints();
		gbc_btnSelectfile.fill = GridBagConstraints.BOTH;
		gbc_btnSelectfile.gridx = 8;
		gbc_btnSelectfile.gridy = 1;
		panelSouth.add(btnSelectfile, gbc_btnSelectfile);
		
		panelEast = new JPanel();
		frmSparkiMapper.getContentPane().add(panelEast, BorderLayout.EAST);
		GridBagLayout gbl_panelEast = new GridBagLayout();
		gbl_panelEast.columnWidths = new int[] {0, 0};
		gbl_panelEast.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
		gbl_panelEast.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelEast.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelEast.setLayout(gbl_panelEast);
		
		tglbtnManualControl = new JToggleButton("Manual Ctrl");
		tglbtnManualControl.setEnabled(false);
		tglbtnManualControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tglbtnManualControl.isSelected()){
					startControl();
				}else{
					stopControl();
				}
			}
		});
		GridBagConstraints gbc_tglbtnManualControl = new GridBagConstraints();
		gbc_tglbtnManualControl.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnManualControl.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnManualControl.gridx = 0;
		gbc_tglbtnManualControl.gridy = 0;
		panelEast.add(tglbtnManualControl, gbc_tglbtnManualControl);
		
		spinnerCT = new JSpinner();
		spinnerCT.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(10)));
		spinnerCT.setEnabled(false);
		GridBagConstraints gbc_spinnerCT = new GridBagConstraints();
		gbc_spinnerCT.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerCT.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerCT.gridx = 0;
		gbc_spinnerCT.gridy = 1;
		panelEast.add(spinnerCT, gbc_spinnerCT);
		
		btnTurn = new JButton("Turn");
		btnTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SpinnerNumberModel model =(SpinnerNumberModel)(spinnerCT.getModel());
				serRec.writeData("CT"+model.getNumber().intValue()+"\n");
			}
		});
		btnTurn.setEnabled(false);
		GridBagConstraints gbc_btnTurn = new GridBagConstraints();
		gbc_btnTurn.insets = new Insets(0, 0, 5, 0);
		gbc_btnTurn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnTurn.gridx = 0;
		gbc_btnTurn.gridy = 2;
		panelEast.add(btnTurn, gbc_btnTurn);
		
		spinnerCM = new JSpinner();
		spinnerCM.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		spinnerCM.setEnabled(false);
		GridBagConstraints gbc_spinnerCM = new GridBagConstraints();
		gbc_spinnerCM.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerCM.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerCM.gridx = 0;
		gbc_spinnerCM.gridy = 3;
		panelEast.add(spinnerCM, gbc_spinnerCM);
		
		btnMove = new JButton("Move");
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SpinnerNumberModel model =(SpinnerNumberModel)(spinnerCM.getModel());
				serRec.writeData("CM"+model.getNumber().intValue()+"\n");
			}
		});
		btnMove.setEnabled(false);
		GridBagConstraints gbc_btnMove = new GridBagConstraints();
		gbc_btnMove.insets = new Insets(0, 0, 5, 0);
		gbc_btnMove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnMove.gridx = 0;
		gbc_btnMove.gridy = 4;
		panelEast.add(btnMove, gbc_btnMove);
		
		tglbtnNoWarning = new JToggleButton("No Warning");
		tglbtnNoWarning.setEnabled(false);
		GridBagConstraints gbc_tglbtnNoWarning = new GridBagConstraints();
		gbc_tglbtnNoWarning.gridx = 0;
		gbc_tglbtnNoWarning.gridy = 5;
		panelEast.add(tglbtnNoWarning, gbc_tglbtnNoWarning);
		
		
	}

}

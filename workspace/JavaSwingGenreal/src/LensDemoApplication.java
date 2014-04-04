import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;
import javax.swing.JScrollPane;
import java.awt.Canvas;
import java.awt.Frame;


public class LensDemoApplication {

	private JFrame frmThinLensDemonstration;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LensDemoApplication window = new LensDemoApplication();
					window.frmThinLensDemonstration.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LensDemoApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmThinLensDemonstration = new JFrame();
		frmThinLensDemonstration.setTitle("Thin Lens Demonstration");
		frmThinLensDemonstration.setBounds(100, 100, 450, 300);
		frmThinLensDemonstration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmThinLensDemonstration.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelBottom = new JPanel();
		panelBottom.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		frmThinLensDemonstration.getContentPane().add(panelBottom, BorderLayout.SOUTH);
		GridBagLayout gbl_panelBottom = new GridBagLayout();
		gbl_panelBottom.columnWidths = new int[] {0, 0, 0, 0, 5};
		gbl_panelBottom.rowHeights = new int[] {0, 0, 0, 0};
		gbl_panelBottom.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelBottom.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		panelBottom.setLayout(gbl_panelBottom);
		
		JLabel lblSetTheValues = new JLabel("Set the values:");
		GridBagConstraints gbc_lblSetTheValues = new GridBagConstraints();
		gbc_lblSetTheValues.fill = GridBagConstraints.BOTH;
		gbc_lblSetTheValues.anchor = GridBagConstraints.WEST;
		gbc_lblSetTheValues.gridwidth = 2;
		gbc_lblSetTheValues.insets = new Insets(0, 5, 5, 5);
		gbc_lblSetTheValues.gridx = 0;
		gbc_lblSetTheValues.gridy = 0;
		panelBottom.add(lblSetTheValues, gbc_lblSetTheValues);
		
		JButton btnHelp = new JButton("Help");
		GridBagConstraints gbc_btnHelp = new GridBagConstraints();
		gbc_btnHelp.insets = new Insets(0, 0, 5, 5);
		gbc_btnHelp.gridx = 2;
		gbc_btnHelp.gridy = 0;
		panelBottom.add(btnHelp, gbc_btnHelp);
		
		JButton btnStartDemo = new JButton("Start Demo");
		GridBagConstraints gbc_btnStartDemo = new GridBagConstraints();
		gbc_btnStartDemo.insets = new Insets(0, 0, 5, 0);
		gbc_btnStartDemo.gridx = 3;
		gbc_btnStartDemo.gridy = 0;
		panelBottom.add(btnStartDemo, gbc_btnStartDemo);
		
		JLabel lblF = new JLabel("f:");
		GridBagConstraints gbc_lblF = new GridBagConstraints();
		gbc_lblF.fill = GridBagConstraints.BOTH;
		gbc_lblF.insets = new Insets(0, 5, 5, 5);
		gbc_lblF.gridx = 0;
		gbc_lblF.gridy = 1;
		panelBottom.add(lblF, gbc_lblF);
		
		JLabel lblU = new JLabel("u:");
		GridBagConstraints gbc_lblU = new GridBagConstraints();
		gbc_lblU.fill = GridBagConstraints.BOTH;
		gbc_lblU.insets = new Insets(0, 5, 5, 5);
		gbc_lblU.gridx = 0;
		gbc_lblU.gridy = 2;
		panelBottom.add(lblU, gbc_lblU);
		
		JLabel lblV = new JLabel("v:");
		GridBagConstraints gbc_lblV = new GridBagConstraints();
		gbc_lblV.fill = GridBagConstraints.BOTH;
		gbc_lblV.insets = new Insets(0, 5, 0, 5);
		gbc_lblV.gridx = 0;
		gbc_lblV.gridy = 3;
		panelBottom.add(lblV, gbc_lblV);
		
		JSlider sliderF = new JSlider();
		GridBagConstraints gbc_sliderF = new GridBagConstraints();
		gbc_sliderF.fill = GridBagConstraints.BOTH;
		gbc_sliderF.insets = new Insets(0, 0, 5, 5);
		gbc_sliderF.gridx = 1;
		gbc_sliderF.gridy = 1;
		panelBottom.add(sliderF, gbc_sliderF);
		
		JSlider sliderU = new JSlider();
		GridBagConstraints gbc_sliderU = new GridBagConstraints();
		gbc_sliderU.fill = GridBagConstraints.BOTH;
		gbc_sliderU.insets = new Insets(0, 0, 5, 5);
		gbc_sliderU.gridx = 1;
		gbc_sliderU.gridy = 2;
		panelBottom.add(sliderU, gbc_sliderU);
		
		JSlider sliderV = new JSlider();
		GridBagConstraints gbc_sliderV = new GridBagConstraints();
		gbc_sliderV.insets = new Insets(0, 0, 0, 5);
		gbc_sliderV.fill = GridBagConstraints.BOTH;
		gbc_sliderV.gridx = 1;
		gbc_sliderV.gridy = 3;
		panelBottom.add(sliderV, gbc_sliderV);
		
		JSpinner spinnerF = new JSpinner();
		spinnerF.setModel(new SpinnerNumberModel(5.0, -15.0, 15.0, 1.0));
		spinnerF.setPreferredSize(new Dimension(50, 22));
		GridBagConstraints gbc_spinnerF = new GridBagConstraints();
		gbc_spinnerF.fill = GridBagConstraints.BOTH;
		gbc_spinnerF.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerF.gridx = 2;
		gbc_spinnerF.gridy = 1;
		panelBottom.add(spinnerF, gbc_spinnerF);
		
		JSpinner spinnerU = new JSpinner();
		spinnerU.setModel(new SpinnerNumberModel(10.0, -15.0, 15.0, 1.0));
		spinnerU.setPreferredSize(new Dimension(50, 22));
		GridBagConstraints gbc_spinnerU = new GridBagConstraints();
		gbc_spinnerU.fill = GridBagConstraints.BOTH;
		gbc_spinnerU.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerU.gridx = 2;
		gbc_spinnerU.gridy = 2;
		panelBottom.add(spinnerU, gbc_spinnerU);
		
		JSpinner spinnerV = new JSpinner();
		spinnerV.setModel(new SpinnerNumberModel(10.0, -15.0, 15.0, 1.0));
		spinnerV.setPreferredSize(new Dimension(50, 22));
		GridBagConstraints gbc_spinnerV = new GridBagConstraints();
		gbc_spinnerV.fill = GridBagConstraints.BOTH;
		gbc_spinnerV.insets = new Insets(0, 0, 0, 5);
		gbc_spinnerV.gridx = 2;
		gbc_spinnerV.gridy = 3;
		panelBottom.add(spinnerV, gbc_spinnerV);
		
		JRadioButton rdbtnF=new JRadioButton("Solve for f.");
		GridBagConstraints gbc_rdbtnF = new GridBagConstraints();
		gbc_rdbtnF.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnF.fill = GridBagConstraints.BOTH;
		gbc_rdbtnF.gridx = 3;
		gbc_rdbtnF.gridy = 1;
		panelBottom.add(rdbtnF, gbc_rdbtnF);
		
		JRadioButton rdbtnU = new JRadioButton("Solve for u.");
		GridBagConstraints gbc_rdbtnU = new GridBagConstraints();
		gbc_rdbtnU.fill = GridBagConstraints.BOTH;
		gbc_rdbtnU.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnU.gridx = 3;
		gbc_rdbtnU.gridy = 2;
		panelBottom.add(rdbtnU, gbc_rdbtnU);
		
		JRadioButton rdbtnV = new JRadioButton("Solve for v.");
		GridBagConstraints gbc_rdbtnV = new GridBagConstraints();
		gbc_rdbtnV.fill = GridBagConstraints.BOTH;
		gbc_rdbtnV.gridx = 3;
		gbc_rdbtnV.gridy = 3;
		panelBottom.add(rdbtnV, gbc_rdbtnV);
		
		JLabel lblNewLabel = new JLabel("Hello World");
		frmThinLensDemonstration.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		frmThinLensDemonstration.getContentPane().add(panel, BorderLayout.CENTER);
	}

}

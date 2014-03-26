import javax.swing.JApplet;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Label;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ShapeApplet extends JApplet {

	private ShapePaintingCanvas canvasCenter;
	
	private JPanel panelTop;
	private JButton btnAnimate;
	private JButton btnDraw;

	private JPanel panelEast;
	private JLabel lblColors;
	private JRadioButton rdbtnBlue;
	private JRadioButton rdbtnYellow;
	private JRadioButton rdbtnRed;
	private JRadioButton rdbtnGreen;
	private ButtonGroup buttonGroupColor = new ButtonGroup();

	
	private JPanel panelWest;
	private Label lblShape;
	private JRadioButton rdbtnSquare;
	private JRadioButton rdbtnRectangle;
	private JRadioButton rdbtnCircle;
	private JRadioButton rdbtnEllipse;
	private ButtonGroup buttonGroupShape = new ButtonGroup();
	
	private JLabel infoLabel;
	
	private Color color;
	private char shape;
	/**
	 * Create the applet.
	 */
	public ShapeApplet() {
		getContentPane().setSize(new Dimension(400, 300));
		getContentPane().setMinimumSize(new Dimension(400, 300));
		getContentPane().setPreferredSize(new Dimension(400, 300));
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		infoLabel = new JLabel("Hello world");
		getContentPane().add(infoLabel, BorderLayout.SOUTH);
		
		
		//Construct the shape selection panel on the west side.
		panelWest = new JPanel();
		panelWest.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		getContentPane().add(panelWest, BorderLayout.WEST);
		panelWest.setLayout(new GridLayout(0, 1));
		
		lblShape = new Label("Shapes:");
		lblShape.setAlignment(Label.CENTER);
		panelWest.add(lblShape);
		
		rdbtnSquare = new JRadioButton("Square");
		rdbtnSquare.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				shape='S';
			}
		});
		buttonGroupShape.add(rdbtnSquare);
		panelWest.add(rdbtnSquare);
		
		rdbtnRectangle = new JRadioButton("Rectangle");
		rdbtnRectangle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				shape='R';
			}
		});
		buttonGroupShape.add(rdbtnRectangle);
		panelWest.add(rdbtnRectangle);
		
		rdbtnCircle = new JRadioButton("Circle");
		rdbtnCircle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				shape='C';
			}
		});
		buttonGroupShape.add(rdbtnCircle);
		panelWest.add(rdbtnCircle);
		
		rdbtnEllipse = new JRadioButton("Ellipse");
		rdbtnEllipse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				shape='E';
			}
		});
		buttonGroupShape.add(rdbtnEllipse);
		panelWest.add(rdbtnEllipse);
		
		
		//Construct the color selection panel on the west side.
		panelEast = new JPanel();
		panelEast.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		getContentPane().add(panelEast, BorderLayout.EAST);
		panelEast.setLayout(new GridLayout(0, 1));
		
		lblColors = new JLabel("Colors:");
		lblColors.setHorizontalAlignment(SwingConstants.CENTER);
		panelEast.add(lblColors);
		
		rdbtnGreen = new JRadioButton("Green");
		rdbtnGreen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				color=Color.green;
			}
		});
		buttonGroupColor.add(rdbtnGreen);
		panelEast.add(rdbtnGreen);
		
		rdbtnRed = new JRadioButton("Red");
		rdbtnRed.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				color=Color.red;
			}
		});
		buttonGroupColor.add(rdbtnRed);
		panelEast.add(rdbtnRed);
		
		rdbtnYellow = new JRadioButton("Yellow");
		rdbtnYellow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				color=Color.yellow;
			}
		});
		buttonGroupColor.add(rdbtnYellow);
		panelEast.add(rdbtnYellow);
		
		rdbtnBlue = new JRadioButton("Blue");
		rdbtnBlue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				color=Color.blue;
			}
		});
		buttonGroupColor.add(rdbtnBlue);
		panelEast.add(rdbtnBlue);
		
		//Add the 2 buttons on the top of the main frame.
		panelTop = new JPanel();
		getContentPane().add(panelTop, BorderLayout.NORTH);
		
		btnDraw = new JButton("Draw");
		btnDraw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvasCenter.draw(shape, color);
			}
		});
		panelTop.add(btnDraw);
		
		btnAnimate = new JButton("Animate");
		btnAnimate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvasCenter.animate(shape, color);
			}
		});
		panelTop.add(btnAnimate);
		
		//Add the shape drawing panel in the center.
		canvasCenter = new ShapePaintingCanvas();
		getContentPane().add(canvasCenter, BorderLayout.CENTER);

	}
	
	
}

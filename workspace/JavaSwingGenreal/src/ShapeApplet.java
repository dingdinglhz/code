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
//Necessary imports.

public class ShapeApplet extends JApplet {
	private ShapePaintingCanvas canvasCenter;
	//The canvas that draw shapes in the center.
	
	private JPanel panelTop;
	private JButton btnAnimate;
	private JButton btnDraw;
	//2 buttons on the top of the main frame.
	
	private JPanel panelEast;
	private JLabel lblColors;
	private JRadioButton rdbtnBlue;
	private JRadioButton rdbtnYellow;
	private JRadioButton rdbtnRed;
	private JRadioButton rdbtnGreen;
	private ButtonGroup buttonGroupColor = new ButtonGroup();
	//The panel on the east of the main frame to let the users choose a color.
	
	private JPanel panelWest;
	private Label lblShape;
	private JRadioButton rdbtnSquare;
	private JRadioButton rdbtnRectangle;
	private JRadioButton rdbtnCircle;
	private JRadioButton rdbtnEllipse;
	private ButtonGroup buttonGroupShape = new ButtonGroup();
	//The panel on the west of the main frame to let the users choose a shape.
	
	private JLabel infoLabel;
	//A label to display the information.
	
	private Color color;
	//to store the color the user chooses.
	private char shape;
	//to store the shape the user chooses.'S' for square,
	//'R' for rectangle, 'C' for circle, 'E' for ellipse.
	
	public ShapeApplet() {
		//Setup the main user interface in the constructor. Although this is done
		//in init() method in examples in class, the IDE recommends this way.
		getContentPane().setSize(new Dimension(400, 300));
		getContentPane().setMinimumSize(new Dimension(400, 300));
		getContentPane().setPreferredSize(new Dimension(400, 300));
		getContentPane().setLayout(new BorderLayout(0, 0));
		//set basic properties of the main panel.
		
		infoLabel = new JLabel("Hello world");
		getContentPane().add(infoLabel, BorderLayout.SOUTH);
		//add the information label in the bottom.
		
		//Construct the shape selection panel on the west side.
		panelWest = new JPanel();//create a new JPanel.
		panelWest.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));//set border type.
		getContentPane().add(panelWest, BorderLayout.WEST);//add the panel to the west.
		panelWest.setLayout(new GridLayout(0, 1));//set this panel to GridLayout.
		//create a new panel that is ready to add stuffs in.
		lblShape = new Label("Shapes:");//create a new label to display "Shapes:"
		lblShape.setAlignment(Label.CENTER);
		panelWest.add(lblShape);//add this label to west panel.
		
		rdbtnSquare = new JRadioButton("Square");//create a new radio button
		rdbtnSquare.addMouseListener(new MouseAdapter() {
			@Override //"@Override" indicates that the original mouseClicked() method is overwritten. 
			public void mouseClicked(MouseEvent arg0) {
				shape='S';
				infoLabel.setText("Square selected");
				//When the radio button is clicked, set the shape to square, and display a message.
			}
		});
		//Add a action listener to the radio button.It set the shape whenever the radio button is clicked. 
		buttonGroupShape.add(rdbtnSquare);
		//Add the radio button to a button group so that only one of the options get chosen at a time.
		panelWest.add(rdbtnSquare);//add this radio button to the west panel.
		//This segment of code create, set and add a radio button to the west panel. 
		//Identical process is carried out for the other buttons, so there won't be redundant commenting.
		rdbtnRectangle = new JRadioButton("Rectangle");
		rdbtnRectangle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				shape='R';
				infoLabel.setText("Rectangle selected");
			}
		});
		buttonGroupShape.add(rdbtnRectangle);
		panelWest.add(rdbtnRectangle);
		//Create rectangle radio button.
		rdbtnCircle = new JRadioButton("Circle");
		rdbtnCircle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				shape='C';
				infoLabel.setText("Circle selected");
			}
		});
		buttonGroupShape.add(rdbtnCircle);
		panelWest.add(rdbtnCircle);
		//Create circle radio button.
		rdbtnEllipse = new JRadioButton("Ellipse");
		rdbtnEllipse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				shape='E';
				infoLabel.setText("Ellipse selected");
			}
		});
		buttonGroupShape.add(rdbtnEllipse);
		panelWest.add(rdbtnEllipse);
		//Create ellipse radio button.
		
		//Construct the color selection panel on the east side.
		//The process is similar to the construction of west panel. 
		//Therefore, again, redundant commenting is saved for clarity.
		panelEast = new JPanel();
		panelEast.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		getContentPane().add(panelEast, BorderLayout.EAST);
		panelEast.setLayout(new GridLayout(0, 1));
		
		lblColors = new JLabel("Colors:");
		lblColors.setHorizontalAlignment(SwingConstants.CENTER);
		panelEast.add(lblColors);
		////Create "Colors:" label.
		rdbtnGreen = new JRadioButton("Green");
		rdbtnGreen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				color=Color.green;
				infoLabel.setText("Green selected");
			}
		});
		buttonGroupColor.add(rdbtnGreen);
		panelEast.add(rdbtnGreen);
		//Create green radio button.
		rdbtnRed = new JRadioButton("Red");
		rdbtnRed.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				color=Color.red;
				infoLabel.setText("Red selected");
			}
		});
		buttonGroupColor.add(rdbtnRed);
		panelEast.add(rdbtnRed);
		//Create red radio button.
		rdbtnYellow = new JRadioButton("Yellow");
		rdbtnYellow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				color=Color.yellow;
				infoLabel.setText("Yellow selected");
			}
		});
		buttonGroupColor.add(rdbtnYellow);
		panelEast.add(rdbtnYellow);
		//Create yellow radio button.
		rdbtnBlue = new JRadioButton("Blue");
		rdbtnBlue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				color=Color.blue;
				infoLabel.setText("Blue selected");
			}
		});
		buttonGroupColor.add(rdbtnBlue);
		panelEast.add(rdbtnBlue);
		//Create blue radio button.
		
		//Add the 2 buttons on the top of the main frame.
		panelTop = new JPanel();
		getContentPane().add(panelTop, BorderLayout.NORTH);
		//Create a panel on top to hold buttons.
		btnDraw = new JButton("Draw");//Create "Draw" button.
		btnDraw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvasCenter.draw(shape, color);
				infoLabel.setText("Shape drawn");
				//When the button is clicked, the draw function of the canvas is called.
			}
		});//Add action listener to the "Draw" button.
		panelTop.add(btnDraw);//Add this button to top panel.
		
		btnAnimate = new JButton("Animate");
		btnAnimate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvasCenter.animate(shape, color);
				infoLabel.setText("Animation started");
			}
		});
		panelTop.add(btnAnimate);//Similarly, "Animate" button is created.
		
		//Add the shape drawing canvas in the center of the main frame.
		canvasCenter = new ShapePaintingCanvas();
		getContentPane().add(canvasCenter, BorderLayout.CENTER);
	}
}

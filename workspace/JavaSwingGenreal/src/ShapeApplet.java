import javax.swing.JApplet;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.SpinnerModel;

import java.awt.GridLayout;
import java.awt.Label;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class ShapeApplet extends JApplet {
	
	//private ShapePaintingCanvas canvasCenter;
	private ShapePaintingPanel canvasCenter;
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
	private JRadioButton rdbtnRectangleE;
	private JRadioButton rdbtnRectangleF;
	private JRadioButton rdbtnOvalE;
	private JRadioButton rdbtnOvalF;
	private ButtonGroup buttonGroupShape = new ButtonGroup();
	//The panel on the west of the main frame to let the users choose a shape.
	
	private JPanel panelBottom;
	private JLabel infoGeneral;//labels to display information.
	private JLabel infoShape;
	private JLabel infoColor;
	private JLabel lblSetWidth;
	private JSpinner spinnerWidth;
	private JLabel lblSetHeight;
	private JSpinner spinnerHeight;
	//The panel on the bottom of the main frame to display the information.
	
	public void init() {
		//Setup the main user interface in the constructor. Although this is done
		//in init() method in examples in class, the IDE recommends this way.
		getContentPane().setSize(new Dimension(400, 300));
		getContentPane().setMinimumSize(new Dimension(400, 300));
		getContentPane().setPreferredSize(new Dimension(400, 300));
		getContentPane().setLayout(new BorderLayout(0, 0));
		//set basic properties of the main panel.
		
		
		//Construct the shape selection panel on the west side.
		panelWest = new JPanel();//create a new JPanel.
		panelWest.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));//set border type.
		getContentPane().add(panelWest, BorderLayout.WEST);//add the panel to the west.
		panelWest.setLayout(new GridLayout(0, 1));//set this panel to GridLayout.
		//create a new panel that is ready to add stuffs in.
		lblShape = new Label("Shapes:");//create a new label to display "Shapes:"
		lblShape.setAlignment(Label.CENTER);
		panelWest.add(lblShape);//add this label to west panel.
		
		rdbtnRectangleE = new JRadioButton("Empty Rectangle");//create a new radio button
		rdbtnRectangleE.addItemListener(new ShapeHandler('r')); 
		//Add a action listener to the radio button.It set the shape whenever the radio button is clicked. 
		buttonGroupShape.add(rdbtnRectangleE);
		//Add the radio button to a button group so that only one of the options get chosen at a time.
		panelWest.add(rdbtnRectangleE);//add this radio button to the west panel.
		//This segment of code create, set and add a radio button to the west panel. 
		//Identical process is carried out for the other buttons, so there won't be redundant commenting.
		
		rdbtnRectangleF = new JRadioButton("Filled Rectangle");
		rdbtnRectangleF.addItemListener(new ShapeHandler('R'));
		buttonGroupShape.add(rdbtnRectangleF);
		panelWest.add(rdbtnRectangleF);
		//Create rectangle radio button.
		
		rdbtnOvalE = new JRadioButton("Empty Oval");
		rdbtnOvalE.addItemListener(new ShapeHandler('o')); 
		buttonGroupShape.add(rdbtnOvalE);
		panelWest.add(rdbtnOvalE);
		//Create circle radio button.
		
		rdbtnOvalF = new JRadioButton("Filled Oval");
		rdbtnOvalF.addItemListener(new ShapeHandler('O'));
		buttonGroupShape.add(rdbtnOvalF);
		panelWest.add(rdbtnOvalF);
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
		rdbtnGreen.addItemListener(new ColorHandler(Color.green));
		buttonGroupColor.add(rdbtnGreen);
		panelEast.add(rdbtnGreen);
		//Create green radio button.
		
		rdbtnRed = new JRadioButton("Red");
		rdbtnRed.addItemListener(new ColorHandler(Color.red));
		buttonGroupColor.add(rdbtnRed);
		panelEast.add(rdbtnRed);
		//Create red radio button.
		
		rdbtnYellow = new JRadioButton("Yellow");
		rdbtnYellow.addItemListener(new ColorHandler(Color.yellow));
		buttonGroupColor.add(rdbtnYellow);
		panelEast.add(rdbtnYellow);
		//Create yellow radio button.
		
		rdbtnBlue = new JRadioButton("Blue");
		rdbtnBlue.addItemListener(new ColorHandler(Color.blue));
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
				if(canvasCenter.ready()){
					infoGeneral.setText("Shape drawn");
					canvasCenter.draw();
				}
				else{
					infoGeneral.setText("Not ready yet... Have you selected your shape and color?");
				}
				//When the button is clicked, the draw function of the canvas is called.
			}
		});//Add action listener to the "Draw" button.
		panelTop.add(btnDraw);//Add this button to top panel.
		
		btnAnimate = new JButton("Animate");
		btnAnimate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(canvasCenter.ready()){
					infoGeneral.setText("Animation started");
					canvasCenter.animate();
				}
				else{
					infoGeneral.setText("Not ready yet... Have you selected your shape and color?");
				}
			}
		});
		panelTop.add(btnAnimate);//Similarly, "Animate" button is created.
		
		lblSetWidth = new JLabel("Set Width:");
		panelTop.add(lblSetWidth);
		
		spinnerWidth = new JSpinner();
		spinnerWidth.setModel(new SpinnerNumberModel(25, 10, 100, 1));
		spinnerWidth.getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
				SpinnerModel model= (SpinnerModel)e.getSource();
				if(model instanceof SpinnerNumberModel){
					SpinnerNumberModel source=(SpinnerNumberModel) model;
					canvasCenter.setShapeWidth(source.getNumber().intValue());
				}
			}
		});
		panelTop.add(spinnerWidth);
		
		lblSetHeight = new JLabel("Set Height:");
		panelTop.add(lblSetHeight);
		
		spinnerHeight = new JSpinner();
		spinnerHeight.setModel(new SpinnerNumberModel(25, 10, 100, 1));
		spinnerHeight.getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
				SpinnerModel model= (SpinnerModel)e.getSource();
				if(model instanceof SpinnerNumberModel){
					SpinnerNumberModel source=(SpinnerNumberModel) model;
					canvasCenter.setShapeHeight(source.getNumber().intValue());
				}
			}
		});
		panelTop.add(spinnerHeight);
		
		//Add the shape drawing canvas in the center of the main frame.
		//canvasCenter = new ShapePaintingCanvas();
		//getContentPane().add(canvasCenter, BorderLayout.CENTER);
		canvasCenter = new ShapePaintingPanel();
		getContentPane().add(canvasCenter, BorderLayout.CENTER);
		
		//add the information label in the bottom.
		panelBottom = new JPanel();
		getContentPane().add(panelBottom, BorderLayout.SOUTH);
		panelBottom.setLayout(new BoxLayout(panelBottom, BoxLayout.Y_AXIS));
				
		infoGeneral = new JLabel("Hello world");
		panelBottom.add(infoGeneral);
				
		infoShape = new JLabel();
		infoShape.setText("Current Shape:");
		panelBottom.add(infoShape);
		
		infoColor = new JLabel("Current Color:");
		panelBottom.add(infoColor);
	}
	
	private class ShapeHandler implements ItemListener{
		private char shape;
		ShapeHandler(char shape){
			this.shape=shape;
		}
		private String shapeToString(){
			switch(this.shape){
			case 'r':
				return "Empty Rectangle";
			case 'R':
				return "Filled Rectangler";
			case 'o':
				return "Empty Oval";
			case 'O':
				return "Filled Oval";
			}
			return "Undefined";
		}
		@Override//"@Override" indicates that the original mouseClicked() method is overwritten. 
		public void itemStateChanged(ItemEvent arg0) {
			canvasCenter.setShape(this.shape);
			infoShape.setText("Current Shape:"+shapeToString());
			//When the radio button is clicked, set the shape, and display a message.
		}
	}
	private class ColorHandler implements ItemListener{
		private Color color;
		ColorHandler(Color color){
			this.color=color;
		}
		@Override//"@Override" indicates that the original mouseClicked() method is overwritten. 
		public void itemStateChanged(ItemEvent arg0) {
			canvasCenter.setColor(this.color);
			infoColor.setText("Current Color:"+color.toString());
			//When the radio button is clicked, set the color, and display a message.
		}
	}
}

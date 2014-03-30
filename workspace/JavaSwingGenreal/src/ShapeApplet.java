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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
//useful imports.

public class ShapeApplet extends JApplet {
	
	private ShapePaintingPanel canvasCenter;
	//The canvas that draw shapes in the center.
	
	private JPanel panelTop;
	private JButton btnAnimate;
	private JButton btnDraw;
	private JLabel lblSetWidth;
	private JSpinner spinnerWidth;
	private JLabel lblSetHeight;
	private JSpinner spinnerHeight;
	//The panel on the top of the main frame. It contains the "draw" and "animate"
	//buttons as well as 2 spinners to set the dimension of the shape.
	
	private JPanel panelWest;
	private Label lblShape;
	private JRadioButton rdbtnRectangleE;
	private JRadioButton rdbtnRectangleF;
	private JRadioButton rdbtnOvalE;
	private JRadioButton rdbtnOvalF;
	private ButtonGroup buttonGroupShape = new ButtonGroup();
	//The panel on the west of the main frame to let the users choose a shape.
	
	private JPanel panelEast;
	private JLabel lblColors;
	private JRadioButton rdbtnBlue;
	private JRadioButton rdbtnYellow;
	private JRadioButton rdbtnRed;
	private JRadioButton rdbtnGreen;
	private ButtonGroup buttonGroupColor = new ButtonGroup();
	//The panel on the east of the main frame to let the users choose a color.
	
	private JPanel panelBottom;
	private JLabel infoGeneral;
	private JLabel infoShape;
	private JLabel infoColor;//labels to display information.
	//The panel on the bottom of the main frame to display the information.
	
	public void init() {
		//Setup the main user interface in the constructor. Although this is done
		//in init() method in examples in class, the IDE recommends this way.
		getContentPane().setSize(new Dimension(400, 300));
		getContentPane().setMinimumSize(new Dimension(400, 300));
		getContentPane().setPreferredSize(new Dimension(400, 300));
		getContentPane().setLayout(new BorderLayout(0, 0));
		//set basic properties and the layout of the main panel.
		
		
		//Construct the shape selection panel on the west side.
		panelWest = new JPanel();//create a new JPanel.
		panelWest.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));//set border type.
		getContentPane().add(panelWest, BorderLayout.WEST);//add the panel to the west.
		panelWest.setLayout(new GridLayout(0, 1));//set this panel to GridLayout.
		//create a new panel that is ready to add stuffs in.
		lblShape = new Label("Shapes:");//create a new label to display "Shapes:"
		lblShape.setAlignment(Label.CENTER);
		panelWest.add(lblShape);//add this label to west panel.
		
		rdbtnRectangleE = new JRadioButton("Empty Rectangle");//create the "Empty Rectangle" radio button.
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
		//Create "Filled Rectangle" radio button.
		
		rdbtnOvalE = new JRadioButton("Empty Oval");
		rdbtnOvalE.addItemListener(new ShapeHandler('o')); 
		buttonGroupShape.add(rdbtnOvalE);
		panelWest.add(rdbtnOvalE);
		//Create "Empty Oval" radio button.
		
		rdbtnOvalF = new JRadioButton("Filled Oval");
		rdbtnOvalF.addItemListener(new ShapeHandler('O'));
		buttonGroupShape.add(rdbtnOvalF);
		panelWest.add(rdbtnOvalF);
		//Create "Filled Oval" radio button.
		
		//Construct the color selection panel on the east side.
		//The process is similar to the construction of west panel. 
		//Therefore, again, redundant commenting is saved for clarity and readability.
		panelEast = new JPanel();
		panelEast.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		getContentPane().add(panelEast, BorderLayout.EAST);
		panelEast.setLayout(new GridLayout(0, 1));
		
		lblColors = new JLabel("Colors:");
		lblColors.setHorizontalAlignment(SwingConstants.CENTER);
		panelEast.add(lblColors);
		////Create east panel and "Colors:" label.
		
		rdbtnGreen = new JRadioButton("Green");
		rdbtnGreen.addItemListener(new ColorHandler(Color.green));
		buttonGroupColor.add(rdbtnGreen);
		panelEast.add(rdbtnGreen);
		//Create "green" radio button.
		
		rdbtnRed = new JRadioButton("Red");
		rdbtnRed.addItemListener(new ColorHandler(Color.red));
		buttonGroupColor.add(rdbtnRed);
		panelEast.add(rdbtnRed);
		//Create "red" radio button.
		
		rdbtnYellow = new JRadioButton("Yellow");
		rdbtnYellow.addItemListener(new ColorHandler(Color.yellow));
		buttonGroupColor.add(rdbtnYellow);
		panelEast.add(rdbtnYellow);
		//Create "yellow" radio button.
		
		rdbtnBlue = new JRadioButton("Blue");
		rdbtnBlue.addItemListener(new ColorHandler(Color.blue));
		buttonGroupColor.add(rdbtnBlue);
		panelEast.add(rdbtnBlue);
		//Create "blue" radio button.
		
		//Construct the panel on the top of the main frame.
		panelTop = new JPanel();
		getContentPane().add(panelTop, BorderLayout.NORTH);
		//Create a panel on top to hold buttons.
		btnDraw = new JButton("Draw");//Create "Draw" button.
		btnDraw.addMouseListener(new MouseAdapter() {//add action listener to "draw" button
			@Override
			public void mouseClicked(MouseEvent e) {//called when button is clicked.
				if(canvasCenter.ready()){//check if the canvas is ready to draw a shape.
					canvasCenter.draw(); //call the canvas to draw the shape.
					infoGeneral.setText("Shape drawn");//diaplay a message on the bottom.
				}
				else{//if the canvas is not ready, display a message to indicate the problem.
					infoGeneral.setText("Not ready yet... Have you selected your shape and color?");
				}
			}
		});
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
		panelTop.add(btnAnimate);//Similarly, "Animate" button is created with an actionListener.
		
		lblSetWidth = new JLabel("Set Width:");
		panelTop.add(lblSetWidth);//Just add some text for user friendliness.
		
		spinnerWidth = new JSpinner();//A spinner to input a number is created.
		spinnerWidth.setModel(new SpinnerNumberModel(25, 10, 100, 5));
		//set input model of the spinner: minimum value is 10, maximum is 25,
		//default is 25, and increase/decrease by 5 each time  the user clicks the arrow.
		spinnerWidth.getModel().addChangeListener(new ChangeListener() {
			//add a actionListener for a change in value.
			public void stateChanged(ChangeEvent e) {//called when the value is changed.
				SpinnerNumberModel source=(SpinnerNumberModel) e.getSource();				
				//get the caller which is the 'SpinnerNumberModel".
				canvasCenter.setShapeWidth(source.getNumber().intValue());
				//get the value of the spinner from the caller and pass it down to the canvas.
			}
		});
		panelTop.add(spinnerWidth);//add the spinner to the top panel.
		
		lblSetHeight = new JLabel("Set Height:");
		panelTop.add(lblSetHeight);
		
		spinnerHeight = new JSpinner();
		spinnerHeight.setModel(new SpinnerNumberModel(25, 10, 100, 5));
		spinnerHeight.getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				SpinnerNumberModel source=(SpinnerNumberModel) e.getSource();
				canvasCenter.setShapeHeight(source.getNumber().intValue());
			}
		});
		panelTop.add(spinnerHeight);//Similarly, another spinner is added to set the height.
		
		//Add the shape drawing canvas in the center of the main frame.
		canvasCenter = new ShapePaintingPanel();
		getContentPane().add(canvasCenter, BorderLayout.CENTER);
		
		//add the information label in the bottom.
		panelBottom = new JPanel();
		getContentPane().add(panelBottom, BorderLayout.SOUTH);
		panelBottom.setLayout(new BoxLayout(panelBottom, BoxLayout.Y_AXIS));
		//create bottom panel.
		infoGeneral = new JLabel("Hello world");
		panelBottom.add(infoGeneral);
		//create info label to display the actions.
		infoShape = new JLabel();
		infoShape.setText("Current Shape:");
		panelBottom.add(infoShape);
		//create info label to display the shape.
		infoColor = new JLabel("Current Color:");
		panelBottom.add(infoColor);
		//create info label to display the color.
	}
	
	private class ShapeHandler implements ItemListener{
		//a action listener for a change in shape.
		private char shape;//which shape is this listener going to change to.
		ShapeHandler(char shape){
			this.shape=shape;
		}//constructor, to set the 'shape'
		private String shapeToString(){//a method to interpret the shape code to String
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
			//called when the item is changed, usually when it is clicked.
			canvasCenter.setShape(this.shape);
			//set the shape of the canvas to be that of the current listener.
			infoShape.setText("Current Shape:"+shapeToString());//Display the message.
		}
	}
	private class ColorHandler implements ItemListener{//a action listerner for change in color.
		private Color color;//which color is this listener going to change to.
		ColorHandler(Color color){
			this.color=color;
		}//constructor, to set the 'color'.
		@Override//"@Override" indicates that the original mouseClicked() method is overwritten. 
		public void itemStateChanged(ItemEvent arg0) {
			canvasCenter.setColor(this.color);
			infoColor.setText("Current Color:"+color.toString());
			//When the radio button is clicked, set the color, and display a message.
		}
	}
}

//杜孝顯_104403016_資管3A
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

public class PaintFrame extends JFrame{

	private PaintArea paintArea;	//paint area
	private JPanel toolBar;				//tool area
	private final JLabel statusBar;		//status bar
	
	private final JComboBox toolBox;	//drawing tools toolbox
	private final JLabel toolBoxLabel;	//label for toolbox
	private static final String[] toolNames =
		{"Brush", "Line", "Oval", "Rectangle", "Rounded Rectangles"};	//array to store drawing tool names
	
	//brush size
	private final JLabel brushSizeLabel;	//label for brush size selection
	private JRadioButton smallBrush;		//brush RadioButtons
	private JRadioButton mediumBrush;
	private JRadioButton largeBrush;
	private ButtonGroup brushSizeGroup;		//groups RadioButtons
	public int brushSize;
	
	private final JCheckBox fillBox;		//fill checkbox
	private boolean filled;
	
	
	private final JButton foregroundButton;	//Buttons
	private final JButton backgroundButton;
	private final JButton clearButton;
	private final JButton undoButton;
	public Color forecolor=Color.LIGHT_GRAY;
	private Color backcolor=Color.LIGHT_GRAY;
	
	public final JCheckBox eraser;
	public boolean eraserchosen;
	
	private Scrollbar sizeSlider;
	private JLabel sizeSliderLabel;
	
	private final JButton discoDisco;
	private Timer discoTimer;
	private int discoR, discoRR;
	private int discoG, discoGG;
	private int discoB, discoBB;
	

	
	public PaintFrame(){
		super("Paint Party");	//title of window
		
		paintArea = new PaintArea();
		paintArea.setBackground(Color.WHITE);
	    //mouse events
	    MouseHandler handler = new MouseHandler();
	    paintArea.addMouseListener(handler);
	    paintArea.addMouseMotionListener(handler);
		
		add(paintArea, BorderLayout.CENTER);	//add paintPanel to JFrame
		
		toolBar = new JPanel();
		toolBar.setLayout(new GridLayout(15,1));	//however many items there are in the toolbar requires just as many in GridLayout
		add(toolBar, BorderLayout.WEST);			//add toolBar to JFrame
		
		
		statusBar = new JLabel(); 
		statusBar.setBackground(Color.LIGHT_GRAY);
	    add(statusBar, BorderLayout.SOUTH);		//add statusBar to JFrame
	    
	    //drawing tools toolbox
	    toolBox = new JComboBox<String>(toolNames);		//sets up drawing tool JComboBox
	    toolBox.setMaximumRowCount(3); 					//set max rows to 3
	    toolBox.addItemListener(new ItemListener(){		//inner class

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					JOptionPane.showMessageDialog(null, "Drawing tool: " + toolNames[toolBox.getSelectedIndex()]);
					paintArea.setShape((toolBox.getSelectedIndex())+1);
					// Cancel Fill if select brush
					if (toolBox.getSelectedIndex() == 0) {
						//paintArea.setFill(false);
						filled=false;
						fillBox.setEnabled(false);
					} else {
						fillBox.setEnabled(true);
					}
				}
			}   	
	    });
		toolBoxLabel = new JLabel("Drawing Tools");		//label for drawing tools
	    
	    
	    toolBar.add(toolBoxLabel);
	    toolBar.add(toolBox);
	    
	    //brush size RadioButtons
	    smallBrush = new JRadioButton("small");		//creates buttons
	    mediumBrush = new JRadioButton("medium");
	    largeBrush = new JRadioButton("large");
	    brushSizeGroup = new ButtonGroup();			//creates ButtonGroup and adds buttons to group
	    brushSizeGroup.add(smallBrush);
	    brushSizeGroup.add(mediumBrush);
	    brushSizeGroup.add(largeBrush);
	    
	    String[] brushSize={"small", "medium", "large"};
	    smallBrush.addItemListener(new RadioButtonHandler(brushSize[0]));	//radio button event handling
	    mediumBrush.addItemListener(new RadioButtonHandler(brushSize[1]));
	    largeBrush.addItemListener(new RadioButtonHandler(brushSize[2]));
	    
	    sizeSlider = new Scrollbar(Scrollbar.HORIZONTAL, 0,1,1,10);
	    sizeSlider.addAdjustmentListener(
	    				new AdjustmentListener() {
	    					public void adjustmentValueChanged(AdjustmentEvent e) {
	    						paintArea.setBrush(sizeSlider.getValue());
	    						sizeSliderLabel.setText("Brush Size Slider is at " + sizeSlider.getValue());
	    					}
	    				}
	    		
	    		
	    		);
	    
	    
	    brushSizeLabel = new JLabel("Brush Size");	//brush size label
	    sizeSliderLabel = new JLabel("Brush Size Slider");
	    toolBar.add(brushSizeLabel);
	    toolBar.add(smallBrush);
	    toolBar.add(mediumBrush);
	    toolBar.add(largeBrush);
	    toolBar.add(sizeSliderLabel);
	    toolBar.add(sizeSlider);
	    
	    CheckBoxHandler fillHandler = new CheckBoxHandler();
	    fillBox = new JCheckBox("Fill");
	    fillBox.setEnabled(false);
	    fillBox.addActionListener(fillHandler);
	    toolBar.add(fillBox);
	    
	    
	    eraser = new  JCheckBox("Eraser");							//checkbox for eraser
		eraser.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						
						if(eraser.isSelected()){
							JOptionPane.showMessageDialog(PaintFrame.this,"Eraser Selected");
							eraserchosen=true;
						}
						else{
							JOptionPane.showMessageDialog(PaintFrame.this,"Eraser Not Selected");
							eraserchosen=false;
						}
						paintArea.eraser(eraserchosen);
					}
				}
		);
		toolBar.add(eraser);
	    
	    
	    foregroundButton = new JButton("Foreground Color");
	    backgroundButton = new JButton("Background Color");
	    clearButton = new JButton("Clear");
	    undoButton=new JButton("Undo");
	    foregroundButton.addActionListener(new ButtonHandler());
	    backgroundButton.addActionListener(new ButtonHandler());
	    clearButton.addActionListener(new ButtonHandler());
	    undoButton.addActionListener(new ButtonHandler());
	    toolBar.add(foregroundButton);
	    toolBar.add(backgroundButton);
	    toolBar.add(clearButton);
	    toolBar.add(undoButton);
	    
		discoDisco = new JButton("Disco Time");
		discoDisco.addActionListener(new ButtonHandler());
		toolBar.add(discoDisco);
		discoTimer = new Timer();
		
	}
	
	private class RadioButtonHandler implements ItemListener{
		private String s;
		public RadioButtonHandler(String string){
			this.s = string;
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(smallBrush.isSelected() ||mediumBrush.isSelected()||largeBrush.isSelected()){
				JOptionPane.showMessageDialog(null,"Brush Size: " + s, "Brush Sizes", 1);
			}
			if(smallBrush.isSelected())
			{
				brushSize=1;
			}
			else if(mediumBrush.isSelected())
			{
				brushSize=2;
			}
			else if(largeBrush.isSelected())
			{
				brushSize=3;
			}
			paintArea.setBrush(brushSize);
		}
		
	}
	
	
	private class CheckBoxHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Fill toggled.");					//message at console
			if(fillBox.isSelected()){
				JOptionPane.showMessageDialog(null, "Fill is selected.","Fill",1);
				System.out.println("Fill is selected.");	
				filled=true;
			}else{
				JOptionPane.showMessageDialog(null, "Fill is not selected.","Fill",0);
				System.out.println("Fill is not selected.");	
				filled=false;
			}
			paintArea.setFilled(filled);
		}
		
	}
	
	
	private class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Foreground Color")){		
				JOptionPane.showMessageDialog(null, e.getActionCommand());
				forecolor=JColorChooser.showDialog(PaintFrame.this,"Foreground color",forecolor);
				paintArea.setForegroundColor(forecolor);
				foregroundButton.setBackground(forecolor);
				if(forecolor==null){
					forecolor=Color.BLACK;
				}
			}
			else if(e.getActionCommand().equals("Background Color")){
				JOptionPane.showMessageDialog(null, e.getActionCommand());
				backcolor=JColorChooser.showDialog(PaintFrame.this,"Background Color",backcolor);
				backgroundButton.setBackground(backcolor);
				if(backcolor==null)
				{
					backcolor=Color.WHITE;
					backgroundButton.setBackground(backcolor);
				}
				paintArea.setBackground(backcolor);
				paintArea.setBackgroundColor(backcolor);
			}
			else if(e.getActionCommand().equals("Clear")){
				JOptionPane.showMessageDialog(null, e.getActionCommand());
				
				int reply = JOptionPane.showConfirmDialog(PaintFrame.this,"Really Clear?","Clear time", JOptionPane.YES_NO_OPTION);
			    if (reply == JOptionPane.YES_OPTION){
			    	backcolor=Color.WHITE;
			    	paintArea.setBackground(backcolor);
			    	backgroundButton.setBackground(backcolor);
			    	paintArea.reset();
			    }
			
			}
			else if(e.getActionCommand().equals("Undo"))
			{
				int reply = JOptionPane.showConfirmDialog(PaintFrame.this,"Really Undo?","Undo time", JOptionPane.YES_NO_OPTION);
				
			    if (reply == JOptionPane.YES_OPTION){
			    	paintArea.previousStep();
			    }
		}
			else if(e.getActionCommand().equals("Disco Time")){
				int reply = JOptionPane.showConfirmDialog(PaintFrame.this,"Really Disco? There's NO TURNING BACK","Disco time", JOptionPane.YES_NO_OPTION);
			    if (reply == JOptionPane.YES_OPTION){
			    	int reply1 = JOptionPane.showConfirmDialog(PaintFrame.this,"Photosensitive seizure warning\n" + 
			    			"A very small percentage of people may experience a seizure when exposed to certain visual images,"+"\n"+" including flashing lights or patterns that may appear in video games."+"\n"+"Even people who have no history of seizures or epilepsy may have an undiagnosed condition that can cause these “photosensitive epileptic seizures” while watching video games.\n" + 
			    			"\n" + 
			    			"These seizures may have a variety of symptoms, including lightheadedness, altered vision, eye or face twitching,"+"\n"+" jerking or shaking of arms or legs, disorientation, confusion, or momentary loss of awareness. "+"\n"+"Seizures may also cause loss of consciousness or convulsions that can lead to injury from falling down or striking nearby objects.\n" + 
			    			"\n" + 
			    			"Immediately stop playing and consult a doctor if you experience any of these symptoms. Parents should watch for or ask their children about the above symptoms."+"\n"+"  Children and teenagers are more likely than adults to experience these seizures.\n" + 
			    			"\n" + 
			    			"The risk of photosensitive epileptic seizures may be reduced by taking the following precautions:\n" + 
			    			"\n" + 
			    			"Play in a well-lit room\n" + 
			    			"Do not play if you are drowsy or fatigued\n" + 
			    			"If you or any of your relatives have a history of seizures or epilepsy, consult a doctor before playing.\n" + 
			    			"\n" + 
			    			"","WARNING", JOptionPane.YES_NO_OPTION);
			    	 if (reply1 == JOptionPane.YES_OPTION){
			    	discoTimer.scheduleAtFixedRate(new TimerTask() {
			    		  @Override
			    		  public void run() {
			    			  
			    			  Random rn = new Random();
			    			  discoR = rn.nextInt(255) + 1;
			    			  discoG = rn.nextInt(255) + 1;
			    			  discoB = rn.nextInt(255) + 1;
			    			  discoRR = rn.nextInt(255) + 1;
			    			  discoGG = rn.nextInt(255) + 1;
			    			  discoBB = rn.nextInt(255) + 1;
			    			  
			    			paintArea.setForegroundColor(new Color(discoRR,discoGG,discoBB));
			    		    backcolor =  new Color(discoR,discoG,discoB);
					    	paintArea.setBackground(backcolor);
					    	clearButton.setBackground(new Color(discoRR,discoGG,discoBB));
					    	undoButton.setBackground(new Color(discoRR,discoGG,discoBB));
					    	backgroundButton.setBackground(new Color(discoRR,discoGG,discoBB));
					    	foregroundButton.setBackground(new Color(discoRR,discoGG,discoBB));
					    	discoDisco.setBackground(new Color(discoRR,discoGG,discoBB));
					    	toolBar.setBackground(new Color(discoRR,discoGG,discoBB));
					    	statusBar.setForeground(new Color(discoRR,discoGG,discoBB));
					    	statusBar.setBackground(new Color(discoRR,discoGG,discoBB));
			    		  }
			    		}, 250, 250);
			    	 }
			    	
			    

			    }
				
			}
	}
	}
	private class MouseHandler implements MouseListener, MouseMotionListener{
		
		@Override
		public void mouseClicked(MouseEvent event){
			statusBar.setText(String.format("Clicked at [%d,%d]", event.getX(), event.getY()));
		}
		
		@Override
		public void mousePressed(MouseEvent event){
			statusBar.setText(String.format("Pressed at [%d,%d]", event.getX(), event.getY()));
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			statusBar.setText(String.format("Dragged at [%d,%d]", e.getX(), e.getY()));
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			statusBar.setText(String.format("Hovering at [%d, %d]", e.getX(), e.getY()));
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			statusBar.setText(String.format("Exited at [%d,%d]- Cursor not in frame.", e.getX(), e.getY()));
			
		}
	

	}

}

package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class WaitTimeSelectionDialog extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JOptionPane optionPane;
	
	public int selectedValue;
	
	public WaitTimeSelectionDialog(JFrame parent, int min, int max, int step, int defaultValue) {
		optionPane = new JOptionPane();
		JSlider slider = new JSlider();
		selectedValue = defaultValue;
		
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setMajorTickSpacing(step);
		slider.setValue(selectedValue);
		
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
	     
		slider.addChangeListener(new ChangeListener() {
		    @Override  
	    	public void stateChanged(ChangeEvent changeEvent) {
		    	JSlider theSlider = (JSlider) changeEvent.getSource();
		    	if (!theSlider.getValueIsAdjusting()) {
		    		selectedValue = theSlider.getValue();
		    	}
		    }
	    });
		
		optionPane.setMessage(new Object[] { "How long is the computer delay?: ", slider });
	    
		optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
	    optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
	    
	    JDialog dialog = optionPane.createDialog(parent, "Set Wait Time Length");
	    dialog.setVisible(true);
		
	}
	
	public Object getSelectedOption() {
		return optionPane.getValue();
	}
	
	
}

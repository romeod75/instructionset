package za.co.logoinstructor.factory;

import javax.swing.JTextArea;

import za.co.logoinstructor.jaxb.TurnType;
import za.co.logoinstructor.ui.GraphicsPane;

/**
 * Implemetation of TurnAction.<br>
 * A TurnAction is a turn in a specific direction at a specific angle.
 * 
 * @author Romeo Dickason
 * @version 1.0
 * @see MoveAtion
 * @see TurnAction
 */
public class TurnAction implements Action
{
	private TurnType turnType;
	
	
	public TurnAction(TurnType turnType)
	{
		this.turnType = (TurnType)turnType;
	}

	/**
	 * The pointer is to turn in a specific direction and angle.
	 * 
	 * @param graphicsPane
	 *            the vanvas on which to apply the action
	 * @param outputTA
	 *            the JTextArea to which progress is displayed       
	 */
	public void executeAction(GraphicsPane graphicsPane, JTextArea outputTA)
	{
		StringBuilder outputSB = new StringBuilder();
		outputSB.append(outputTA.getText());
		
		outputSB.append("TURN [" + turnType.getDirection() + " " + turnType.getAngle() + "]"
			+ System.getProperty("line.separator"));
		
		if("left".equals(turnType.getDirection()))
		{
			graphicsPane.left(turnType.getAngle());
		} else if("right".equals(turnType.getDirection()))
		{
			graphicsPane.right(turnType.getAngle());
		}	
		
		outputTA.setText(outputSB.toString());
	} 	
}

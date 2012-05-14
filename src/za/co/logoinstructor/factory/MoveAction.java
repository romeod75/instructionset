package za.co.logoinstructor.factory;

import java.awt.Color;

import javax.swing.JTextArea;

import za.co.logoinstructor.jaxb.MoveType;
import za.co.logoinstructor.ui.GraphicsPane;

/**
 * Implemetation of MoveAction.<br>
 * A MoveAction is a move forward or back for a specific distance and can be of
 * an color or invisible.
 * 
 * @author Romeo Dickason
 * @version 1.0
 * @see MoveAtion
 * @see TurnAction
 */
public class MoveAction implements Action
{
	private MoveType moveType;

	public MoveAction(MoveType moveType)
	{
		this.moveType = (MoveType) moveType;
	}

	/**
	 * The color, penup/pendown and action details for a move.<br>
	 * Command is printed and action drawn (if applicable).
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

		// Do color and inivisible settings
		if (moveType.getInvisible() != null && "Y".equalsIgnoreCase(moveType.getInvisible()))
		{
			outputSB.append("Lifting pen up" + System.getProperty("line.separator"));
			graphicsPane.penUp();
		}
		else if (moveType.getColor() != null)
		{
			outputSB.append("Changing color to	: " + moveType.getColor() + System.getProperty("line.separator"));
			graphicsPane.setPenColor(getColor(moveType.getColor()));
		}

		// Output the command text
		outputSB.append("MOVE [" + moveType.getDirection() + " " + moveType.getDistance() + "]"
			+ System.getProperty("line.separator"));

		// Draw the action
		if ("forward".equals(moveType.getDirection()))
		{
			graphicsPane.forward(moveType.getDistance().floatValue());
		}
		else if ("back".equals(moveType.getDirection()))
		{
			graphicsPane.back(moveType.getDistance().floatValue());
		}

		// Reset color and inivisible settings if needed
		if (moveType.getInvisible() != null && "Y".equalsIgnoreCase(moveType.getInvisible()))
		{
			outputSB.append("Putting pen down" + System.getProperty("line.separator"));
			graphicsPane.penDown();
		}
		else if (moveType.getColor() != null)
		{
			outputSB.append("Reset color back	: black" + System.getProperty("line.separator"));
			graphicsPane.setPenColor(Color.BLACK);
		}

		outputTA.setText(outputSB.toString());
	}

	/**
	 * Convert color string in XML to Color object.
	 * 
	 * @param color
	 *            the value from the XML
	 * @return the color as an object
	 */
	private Color getColor(String color)
	{
		if ("red".equals(color))
		{
			return Color.RED;
		}
		else if ("blue".equals(color))
		{
			return Color.BLUE;
		}
		else if ("green".equals(color))
		{
			return Color.GREEN;
		}
		else if ("orange".equals(color))
		{
			return Color.ORANGE;
		}
		else if ("yellow".equals(color))
		{
			return Color.YELLOW;
		}
		return Color.BLACK;
	}
}

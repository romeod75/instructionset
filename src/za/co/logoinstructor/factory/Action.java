package za.co.logoinstructor.factory;

import javax.swing.JTextArea;

import za.co.logoinstructor.ui.GraphicsPane;

/**
 * The super interface that will be returned as the final end product from the factories.
 * 
 * @author Romeo Dickason
 * @version 1.0
 * @see MoveAtion
 * @see TurnAction
 */
public interface Action
{
	public void executeAction(GraphicsPane graphicsPane, JTextArea outputTA);
}

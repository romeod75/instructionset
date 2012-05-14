package za.co.logoinstructor.executor;

import javax.swing.JTextArea;

import za.co.logoinstructor.factory.Action;
import za.co.logoinstructor.factory.ActionFactory;
import za.co.logoinstructor.ui.GraphicsPane;

/**
 * The InstructionSetActionExecutor is a Singleton class that will receive every
 * action.
 * 
 * @author Romeo Dickason
 * @version 1.0
 */
public class InstructionSetActionExecutor
{
	private static InstructionSetActionExecutor instructionSetActionExecutor;

	/**
	 * Private constructor to prevent instantiation.
	 */
	private InstructionSetActionExecutor()
	{
	}

	/**
	 * Get an instance of this class.
	 * 
	 * @return instructionSetActionExecutor
	 */
	public static InstructionSetActionExecutor getInstance()
	{
		if (instructionSetActionExecutor == null)
		{
			instructionSetActionExecutor = new InstructionSetActionExecutor();
		}

		return instructionSetActionExecutor;
	}

	/**
	 * Execute this action.
	 * 
	 * @param actionFactory
	 *            the factory for this action
	 * @param graphicsPane
	 *            the panel for output of the image
	 * @param outputTA
	 *            the textarea for output the commands
	 */
	public void execute(ActionFactory actionFactory, GraphicsPane graphicsPane, JTextArea outputTA)
	{
		Action action = actionFactory.createAction();
		action.executeAction(graphicsPane, outputTA);
	}
}

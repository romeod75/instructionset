package za.co.logoinstructor;

import za.co.logoinstructor.exception.LOGOInstructorException;
import za.co.logoinstructor.processor.InstructionSetProcessor;

/**
 * <p>
 * The LOGOInstructor will start the application. It will present a JFrame for
 * output as pass control of file for processing.
 * </p>
 * 
 * @author Romeo Dickason
 * @version 1.0
 */
public class LOGOInstructor
{
	/**
	 * Main method for the LOGOInstructor application.<br>
	 * Usage:<br>
	 * java -jar LOGOInstructor [filename] [waitFor]<br>
	 * [filename] - [Optional]the (path) and name of the XML instruction set to
	 * process.<br>
	 * [waitFor] - [Optional] ask the thread to wait a specific period between
	 * actions. If not specified default is 0.<br>
	 * 
	 * @param args
	 *            application usage parameters
	 */
	public static void main(String[] args)
	{
		try
		{
			String filename = null;
			Long waitFor = null;
			
			// only one argument passed
			if (args.length == 1)
			{
				// Check if numeric therefore <waitFor>
				try
				{
					waitFor = new Long(args[0]);
					filename = null;
					
				} 
				// must be sending <filename> then
				catch(NumberFormatException e)
				{
					filename = args[0];
					waitFor = null;					
				}
			// two arguments passed so must be in order and valid. <filename> <waitFor>
			} else if (args.length == 2)
			{
				
				try
				{
					filename = args[0];
					waitFor = new Long(args[1]);
					
				} catch(NumberFormatException e)
				{
					System.out.println("Invalid value passed for [waitFor] - must be numeric");
					System.exit(0);
				}			
			}
			
			// Start the processing
			InstructionSetProcessor instructionSetProcessor = new InstructionSetProcessor(filename, waitFor);
			instructionSetProcessor.process();
		}
		catch (LOGOInstructorException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
}

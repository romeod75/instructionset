package za.co.logoinstructor.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import za.co.logoinstructor.exception.LOGOInstructorException;
import za.co.logoinstructor.executor.InstructionSetActionExecutor;
import za.co.logoinstructor.factory.ActionFactory;
import za.co.logoinstructor.factory.MoveFactory;
import za.co.logoinstructor.factory.TurnFactory;
import za.co.logoinstructor.jaxb.Commands;
import za.co.logoinstructor.jaxb.Commands.Command;
import za.co.logoinstructor.jaxb.InstructionSet;
import za.co.logoinstructor.jaxb.Actions.Action;
import za.co.logoinstructor.ui.LOGOInstructorFrame;
import za.co.logoinstructor.util.PropertiesUtil;

/**
 * The InstructionSetProcessor will do some basic validation on the instruction
 * set XML file.<br>
 * It will request the XML JAXB objects from @see InstructionSetXMLProcessor.<br>
 * It will provide some basic info about the instruction set and pass the
 * command for each command to @see InstructionSetActionExecutor.<br>
 * 
 * @author Romeo Dickason
 * @version 1.0
 */
public class InstructionSetProcessor
{
	private String 				instructionSetXMLFilename;
	private File 				instructionSetXMLFile;
	private LOGOInstructorFrame logoInstructorFrame;
	private Long 				waitFor;
	private InstructionSet 		instructionSet;

	/**
	 * Class constructor.
	 * 
	 * @param instructionSetXMLFilename
	 *            the filename of the instruction set
	 * @param waitFor
	 *            period in milliseconds to wait between processing each command and action
	 * @throws LOGOInstructorException
	 */
	public InstructionSetProcessor(String instructionSetXMLFilename, Long waitFor) throws LOGOInstructorException
	{
		// initialise the instruction set xml to process
		// use default value from config.properties if not provided
		if (instructionSetXMLFilename == null)
		{
			instructionSetXMLFile = new File(".");
			this.instructionSetXMLFilename = PropertiesUtil.getProperty("instructionSetFilename");
		}
		else
		{
			this.instructionSetXMLFilename = instructionSetXMLFilename;
		}
		// initialise the waitFor value
		// use default value from config.properties if not provided	
		if(waitFor == null)
		{
			try
			{
				this.waitFor = new Long(PropertiesUtil.getProperty("waitFor"));
			}
			catch (NumberFormatException e)
			{
				throw new LOGOInstructorException("Invalid value for [waitFor] in config.properties - must be numeric", e);
			}
		} else
		{
			this.waitFor = waitFor;
		}
	}

	/**
	 * Process the instruction set.
	 * 
	 * @throws LOGOInstructorException
	 */
	public void process() throws LOGOInstructorException
	{
		try
		{
			instructionSetXMLFile = new File(instructionSetXMLFilename);
			
			// Load file and do some basic validations
			validateFile();
			
			// Unmarshall instruction set XML
			InstructionSetXMLProcessor instructionSetXMLProcessor = new InstructionSetXMLProcessor(instructionSetXMLFilename);
			instructionSet = instructionSetXMLProcessor.unmarshallInstructionSet();
			
			// The JFrame used for the output
			logoInstructorFrame = new LOGOInstructorFrame();
			StringBuilder outputTextSB = new StringBuilder();
			
			// Some detail about the instruction set being processed
			printInstructionSetInfo(outputTextSB);

			// The Executor (A singleton class that will process the actions
			// i.e. move and turn)
			InstructionSetActionExecutor executor = InstructionSetActionExecutor.getInstance();

			// Get all the commands for this instruction set
			int counter = 1;
			for (Commands.Command command : instructionSet.getCommands().getCommand())
			{
				Thread.sleep(waitFor);
			
				int repeats = 1;
				if (command.getRepeat() != null)
				{
					repeats = command.getRepeat().intValue();
				}
				
				printCommandDetails(command, counter, repeats);

				for (int i = 0; i < repeats; i++)
				{
					for (Action action : command.getActions().getAction())
					{
						Thread.sleep(waitFor);
						
						// It is a move
						if (action.getMove() != null)
						{
							ActionFactory moveFactory = new MoveFactory(action.getMove());
							executor.execute(moveFactory, logoInstructorFrame.getGraphicsPane(), logoInstructorFrame.getOutputTA());
						}
						// It is a turn
						else if (action.getTurn() != null)
						{
							ActionFactory turnFactory = new TurnFactory(action.getTurn());
							executor.execute(turnFactory, logoInstructorFrame.getGraphicsPane(), logoInstructorFrame.getOutputTA());
						}
					}
				}
				counter++;
			}
		}
		catch (Exception e)
		{
			throw new LOGOInstructorException("InstructionSetProcessor.process()", e);
		}
	}
	
	/**
	 * Do some basic file validations before XML unmarshalling and schema validation.
	 * @throws LOGOInstructorException
	 */
	private void validateFile() throws LOGOInstructorException
	{
		try
		{
			String canonicalFilePath = instructionSetXMLFile.getCanonicalPath();

			// Does the file exist?
			if (!instructionSetXMLFile.exists())
			{
				throw new LOGOInstructorException("Could not locate the file: " + canonicalFilePath);
			}

			// Is it empty?
			FileInputStream fis = new FileInputStream(instructionSetXMLFile);
			int b = fis.read();
			if (b == -1)
			{
				throw new LOGOInstructorException("File is empty or corrupt: " + canonicalFilePath);
			}

			// Is it an XML file?
			String fileExtension = canonicalFilePath.substring(canonicalFilePath.lastIndexOf('.') + 1, canonicalFilePath.length());
			if (!"xml".equals(fileExtension))
			{
				throw new LOGOInstructorException("File is not an XML file: " + canonicalFilePath);
			}

		}
		catch (IOException e)
		{
			throw new LOGOInstructorException("InstructionSetProcessor.validateFile()", e);
		}
	}	

	/**
	 * Output the details about the construction set.
	 * 
	 * <element ref="{}instructionSetName"/> 
	 * <element ref="{}version"/>
	 * <element ref="{}author"/> 
	 * <element ref="{}instructionSetDescription"/>
	 * 
	 * @param outputTextSB
	 */
	private void printInstructionSetInfo(StringBuilder outputTextSB)
	{
		outputTextSB.append("Now processing the instruction set from: " + instructionSetXMLFile 		+ System.getProperty("line.separator"));
		outputTextSB.append(System.getProperty("line.separator"));
		outputTextSB.append("Name	: " + instructionSet.getInstructionSetName() 						+ System.getProperty("line.separator"));
		outputTextSB.append("Version	: " + instructionSet.getVersion() 								+ System.getProperty("line.separator"));
		outputTextSB.append("Author	: " + instructionSet.getAuthor() 									+ System.getProperty("line.separator"));
		outputTextSB.append(System.getProperty("line.separator"));
		outputTextSB.append("Summary :" 																+ System.getProperty("line.separator"));
		outputTextSB.append(instructionSet.getInstructionSetDescription() 								+ System.getProperty("line.separator"));		
		
		outputTextSB.append("******************************************************************************"
			+ System.getProperty("line.separator"));
		
		logoInstructorFrame.getOutputTA().setText(outputTextSB.toString());
	}

	/**
	 * Output the details about the command.
	 * 
	 * @param command
	 * @param counter
	 * @param repeats
	 */
	private void printCommandDetails(Command command, int counter , int repeats)
	{
		StringBuilder commandSB = new StringBuilder(logoInstructorFrame.getOutputTA().getText());
		
		commandSB.append(System.getProperty("line.separator"));
			
		commandSB.append("*******************************" 		+ System.getProperty("line.separator"));
		commandSB.append("Command no	: " + counter			+ System.getProperty("line.separator"));
		commandSB.append("*******************************" 		+ System.getProperty("line.separator"));
		
		commandSB.append("Repeats	: ");
		if (command.getRepeat() != null)
		{
			repeats = command.getRepeat().intValue();
		}
		commandSB.append(repeats 								+ System.getProperty("line.separator"));
		
		commandSB.append("No of actions	: " + command.getActions().getAction().size() 	
			+ System.getProperty("line.separator"));
		
		commandSB.append("Description	: ");
		if (command.getCommandDescription() != null)
		{
			commandSB.append(command.getCommandDescription() 	+ System.getProperty("line.separator"));
		}
		else
		{
			commandSB.append("N/A" 								+ System.getProperty("line.separator"));
		}		
		commandSB.append(System.getProperty("line.separator"));		

		logoInstructorFrame.getOutputTA().setText(commandSB.toString());		
	}
}

package za.co.logoinstructor.exception;

/**
 * <p>Generic exception wrapper for the application.</p>
 * 
 * @author 	Romeo Dickason
 * @version 1.0
 * @see 	java.lang.Exception
 */
public class LOGOInstructorException extends Exception
{
	private static final long serialVersionUID = 2091058899005696363L;

	public LOGOInstructorException()
	{
		super();
	}

	public LOGOInstructorException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

	public LOGOInstructorException(String arg0)
	{
		super(arg0);
	}

	public LOGOInstructorException(Throwable arg0)
	{
		super(arg0);
	}

	
}

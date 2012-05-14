package za.co.logoinstructor.factory;

import za.co.logoinstructor.jaxb.TurnType;

/**
 * Creates the TurnAction and provides the TurnType and the move details.
 * 
 * @author Romeo Dickason
 * @version 1.0
 * @see MoveFactory
 * @see TurnFactory
 */
public class TurnFactory implements ActionFactory
{
	private TurnType turnType;
	
	public TurnFactory(TurnType turnType)
	{
		this.turnType = turnType;
	}
	
	public Action createAction()
	{
		return new TurnAction(turnType);
	}

}

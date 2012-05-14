package za.co.logoinstructor.factory;

import za.co.logoinstructor.jaxb.MoveType;

/**
 * Creates the MoveAction and provides the MoveType and the move details.
 * 
 * @author Romeo Dickason
 * @version 1.0
 * @see MoveFactory
 * @see TurnFactory
 */
public class MoveFactory implements ActionFactory
{
	private MoveType moveType;
	
	public MoveFactory(MoveType moveType)
	{
		this.moveType = moveType;
	}

	public Action createAction()
	{
		return new MoveAction(moveType);
	}
}

package za.co.logoinstructor.factory;

/**
 * The interface for which the factory implementation should be done
 * 
 * @author Romeo Dickason
 * @version 1.0
 * @see MoveFactory
 * @see TurnFactory
 */
public interface ActionFactory
{
	 public Action createAction();
}

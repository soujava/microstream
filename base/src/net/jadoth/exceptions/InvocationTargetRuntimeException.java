package net.jadoth.exceptions;

import java.lang.reflect.InvocationTargetException;


/**
 *
 * @author Thomas Muenz
 */
public class InvocationTargetRuntimeException extends WrapperRuntimeException
{
	///////////////////////////////////////////////////////////////////////////
	// constructors //
	/////////////////

	public InvocationTargetRuntimeException(final InvocationTargetException actual)
	{
		super(actual);
	}



	///////////////////////////////////////////////////////////////////////////
	// override methods //
	/////////////////////

	@Override
	public InvocationTargetException getActual()
	{
		return (InvocationTargetException)super.getActual(); // safe via constructor
	}

}
package one.microstream.com.binarydynamic;

public class ComMessageStatus implements ComMessage
{
	///////////////////////////////////////////////////////////////////////////
	// instance fields //
	////////////////////
	
	private final boolean status;

	
	///////////////////////////////////////////////////////////////////////////
	// constructors //
	/////////////////
	
	public ComMessageStatus(final boolean status)
	{
		super();
		this.status = status;
	}

	///////////////////////////////////////////////////////////////////////////
	// methods //
	////////////
	
	public boolean status()
	{
		return this.status;
	}
	
}
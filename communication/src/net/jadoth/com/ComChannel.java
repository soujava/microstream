package net.jadoth.com;

import static net.jadoth.X.notNull;

import net.jadoth.persistence.types.PersistenceManager;


public interface ComChannel
{
	public Object receive();
	
	public void send(Object graphRoot);
	
	public default Object request(final Object graphRoot)
	{
		synchronized(this)
		{
			this.send(graphRoot);
			return this.receive();
		}
	}
	
	public void close();
	
	
	
	public static ComChannel New(final PersistenceManager<?> persistenceManager)
	{
		return new ComChannel.Implementation(
			notNull(persistenceManager)
		);
	}
		
	public class Implementation implements ComChannel
	{
		///////////////////////////////////////////////////////////////////////////
		// instance fields //
		////////////////////
		
		private final PersistenceManager<?> persistenceManager;
		
		
		
		///////////////////////////////////////////////////////////////////////////
		// constructors //
		/////////////////

		protected Implementation(final PersistenceManager<?> persistenceManager)
		{
			super();
			this.persistenceManager = persistenceManager;
		}
		
		
		
		///////////////////////////////////////////////////////////////////////////
		// methods //
		////////////
		
		@Override
		public final void send(final Object graphRoot)
		{
			/*
			 * "store" is a little unfitting here.
			 * However, technically, it is correct. The graph is "stored" (written) to the network connection.
			 */
			this.persistenceManager.store(graphRoot);
		}
		
		@Override
		public final Object receive()
		{
			/*
			 * in the context of a network connection, the generic get() means
			 * receive whatever the other side is sending.
			 */
			return this.persistenceManager.get();
		}
		
		@Override
		public final void close()
		{
			this.persistenceManager.close();
		}
		
	}
	
}
package one.microstream.com;

import java.nio.ByteOrder;

import one.microstream.persistence.types.PersistenceIdStrategy;
import one.microstream.persistence.types.PersistenceTypeDictionaryViewProvider;


@FunctionalInterface
public interface ComProtocolProviderCreator<C>
{
	public ComProtocolProvider<C> creatProtocolProvider(
		String                                name                  ,
		String                                version               ,
		ByteOrder                             byteOrder             ,
		long                                  inactivityTimeout     ,
		PersistenceIdStrategy                 idStrategy            ,
		PersistenceTypeDictionaryViewProvider typeDictionaryProvider,
		ComProtocolCreator                    protocolCreator
	);
	
	
	
	public static <C> ComProtocolProviderCreator<C> New()
	{
		return new ComProtocolProviderCreator.Default<>();
	}
	
	public final class Default<C> implements ComProtocolProviderCreator<C>
	{
		///////////////////////////////////////////////////////////////////////////
		// constructors //
		/////////////////
		
		Default()
		{
			super();
		}
		
		
		///////////////////////////////////////////////////////////////////////////
		// methods //
		////////////

		@Override
		public ComProtocolProvider<C> creatProtocolProvider(
			final String                                name                  ,
			final String                                version               ,
			final ByteOrder                             byteOrder             ,
			final long                                  inactivityTimeout     ,
			final PersistenceIdStrategy                 idStrategy            ,
			final PersistenceTypeDictionaryViewProvider typeDictionaryProvider,
			final ComProtocolCreator                    protocolCreator
			
		)
		{
			return new ComProtocolProvider.Default<>(
				name                  ,
				version               ,
				byteOrder             ,
				inactivityTimeout     ,
				idStrategy            ,
				typeDictionaryProvider,
				protocolCreator
			);
		}
	}
	
}

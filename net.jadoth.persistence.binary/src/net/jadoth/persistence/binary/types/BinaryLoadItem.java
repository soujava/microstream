package net.jadoth.persistence.binary.types;

import java.nio.ByteBuffer;

import net.jadoth.functional._longProcedure;
import net.jadoth.persistence.types.PersistenceTypeHandler;

public final class BinaryLoadItem extends Binary
{
	///////////////////////////////////////////////////////////////////////////
	// instance fields  //
	/////////////////////

	PersistenceTypeHandler<Binary, Object> handler;
	Object contextInstance, localInstance;
	BinaryLoadItem next, link;



	///////////////////////////////////////////////////////////////////////////
	// constructors     //
	/////////////////////

	BinaryLoadItem(
		final long                                   entityContentAddress,
		final Object                                 contextInstance     ,
		final PersistenceTypeHandler<Binary, Object> handler
	)
	{
		super();
		this.address         = entityContentAddress;
		this.handler         = handler             ;
		this.contextInstance = contextInstance     ;
	}



	///////////////////////////////////////////////////////////////////////////
	// methods //
	////////////
	
	@Override
	public final Binary channelChunk(final int channelIndex)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final int channelCount()
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void iterateEntityData(final BinaryEntityDataReader reader)
	{
		// technically, the single data set could be iterated, but designwise, it's not the task, here.
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Some binary entries serve as a skip entry, so that an entry for a particular object id already exists.
	 * Naturally, those entries don't have data then, which must be checked (be checkable) later on.
	 *
	 * @return whether this instances carries (actually "knows") binary build data or not.
	 */
	public final boolean hasData()
	{
		/*
		 * since all proper build items are validated to have a non-null handler,
		 * a null handler can be safely used to indicate skip items, i.e. no data.
		 * 
		 */
		return this.handler != null;
	}

	@Override
	public final long loadItemEntityContentAddress()
	{
		return this.address;
	}
	
	@Override
	public final void setLoadItemEntityContentAddress(final long entityContentAddress)
	{
		this.address = entityContentAddress;
	}
		
	@Override
	public final void iterateKeyValueEntriesReferences(
		final long           offset  ,
		final _longProcedure iterator
	)
	{
		// (29.01.2019 TM)FIXME: JET-49: offset validation
		
		final long elementCount = this.getBinaryListElementCountValidating(
			offset,
			keyValueBinaryLength()
		);

		BinaryPersistence.iterateReferenceRange(
			this.binaryListElementsAddressRelative(offset),
			keyValueReferenceCount() * elementCount,
			iterator
		);
	}
		
	@Override
	public final long getListElementCountKeyValue(final long listStartOffset)
	{
		// (29.01.2019 TM)FIXME: JET-49: offset validation
		
		return this.getBinaryListElementCountValidating(
			listStartOffset,
			keyValueBinaryLength()
		);
	}
	
	
				
	@Override
	public final long storeEntityHeader(
		final long entityContentLength,
		final long entityTypeId       ,
		final long entityObjectId
	)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public final ByteBuffer[] buffers()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public final void clear()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean isEmpty()
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final long totalLength()
	{
		throw new UnsupportedOperationException();
	}

}
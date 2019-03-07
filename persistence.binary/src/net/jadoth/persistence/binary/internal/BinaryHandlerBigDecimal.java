package net.jadoth.persistence.binary.internal;

import java.math.BigDecimal;

import net.jadoth.persistence.binary.types.Binary;
import net.jadoth.persistence.types.PersistenceStoreHandler;

public final class BinaryHandlerBigDecimal extends AbstractBinaryHandlerNativeCustomValueVariableLength<BigDecimal>
{
	///////////////////////////////////////////////////////////////////////////
	// constructors     //
	/////////////////////

	public BinaryHandlerBigDecimal()
	{
		super(
			BigDecimal.class,
			pseudoFields(
				chars("value")
			)
		);
	}



	///////////////////////////////////////////////////////////////////////////
	// methods //
	////////////

	@Override
	public void store(final Binary bytes, final BigDecimal instance, final long oid, final PersistenceStoreHandler handler)
	{
		// there's a char[] constructor but no char[] utility method, so there's no other option than this
		bytes.storeStringValue(this.typeId(), oid, instance.toString());
	}

	@Override
	public BigDecimal create(final Binary bytes)
	{
		return new BigDecimal(bytes.buildArray_char());
	}

}
package net.jadoth.persistence.binary.types;

import static net.jadoth.X.mayNull;
import static net.jadoth.X.notNull;

import net.jadoth.collections.types.XGettingTable;
import net.jadoth.exceptions.TypeCastException;
import net.jadoth.persistence.types.PersistenceLegacyTypeHandlingListener;
import net.jadoth.persistence.types.PersistenceLoadHandler;
import net.jadoth.persistence.types.PersistenceTypeDefinition;
import net.jadoth.persistence.types.PersistenceTypeHandlerReflective;

public final class BinaryLegacyTypeHandlerReflective<T>
extends AbstractBinaryLegacyTypeHandlerTranslating<T>
{
	///////////////////////////////////////////////////////////////////////////
	// static methods //
	///////////////////
	
	public static <T> BinaryLegacyTypeHandlerReflective<T> New(
		final PersistenceTypeDefinition                     typeDefinition              ,
		final PersistenceTypeHandlerReflective<Binary, T>   typeHandler                 ,
		final XGettingTable<BinaryValueSetter, Long>        translatorsWithTargetOffsets,
		final PersistenceLegacyTypeHandlingListener<Binary> listener                    ,
		final boolean                                       switchByteOrder
	)
	{
		return new BinaryLegacyTypeHandlerReflective<>(
			notNull(typeDefinition)                      ,
			notNull(typeHandler)                         ,
			toTranslators(translatorsWithTargetOffsets)  ,
			toTargetOffsets(translatorsWithTargetOffsets),
			mayNull(listener)                            ,
			switchByteOrder
		);
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// constructors //
	/////////////////

	BinaryLegacyTypeHandlerReflective(
		final PersistenceTypeDefinition                     typeDefinition  ,
		final PersistenceTypeHandlerReflective<Binary, T>   typeHandler     ,
		final BinaryValueSetter[]                           valueTranslators,
		final long[]                                        targetOffsets   ,
		final PersistenceLegacyTypeHandlingListener<Binary> listener        ,
		final boolean                                       switchByteOrder
	)
	{
		super(typeDefinition, typeHandler, valueTranslators, targetOffsets, listener, switchByteOrder);
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// methods //
	////////////
	
	@Override
	public PersistenceTypeHandlerReflective<Binary, T> typeHandler()
	{
		// cast safety guranteed by constructor typing
		return (PersistenceTypeHandlerReflective<Binary, T>)super.typeHandler();
	}
	
	@Override
	protected T internalCreate(final Binary rawData)
	{
		return this.typeHandler().create(rawData);
	}
	
	@Override
	public final void update(final Binary rawData, final T instance, final PersistenceLoadHandler builder)
	{
		/*
		 * Explicit type check to avoid memory getting overwritten with bytes not fitting to the actual type.
		 * This can be especially critical if a custom root resolver returns an instance that does not match
		 * the type defined by the typeId.
		 */
		if(!this.type().isInstance(instance))
		{
			throw new TypeCastException(this.type(), instance);
		}

		rawData.updateFixedSize(instance, this.valueTranslators(), this.targetOffsets(), builder);
	}

	@Override
	public final void complete(final Binary medium, final T instance, final PersistenceLoadHandler builder)
	{
		// no-op for reflective logic
	}
	
}
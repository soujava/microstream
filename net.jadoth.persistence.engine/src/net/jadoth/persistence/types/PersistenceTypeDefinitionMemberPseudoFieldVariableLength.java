package net.jadoth.persistence.types;

import static net.jadoth.X.notNull;
import static net.jadoth.math.XMath.positive;

public interface PersistenceTypeDefinitionMemberPseudoFieldVariableLength
extends PersistenceTypeDefinitionMemberPseudoField, PersistenceTypeDescriptionMemberPseudoFieldVariableLength
{

	public static PersistenceTypeDefinitionMemberPseudoFieldVariableLength.Implementation New(
		final PersistenceTypeDescriptionMemberPseudoFieldVariableLength description
	)
	{
		return PersistenceTypeDefinitionMemberPseudoFieldVariableLength.New(
			description.typeName()               ,
			description.name()                   ,
			description.persistentMinimumLength(),
			description.persistentMaximumLength()
		);
	}
	
	public static PersistenceTypeDefinitionMemberPseudoFieldVariableLength.Implementation New(
		final String typeName               ,
		final String name                   ,
		final long   persistentMinimumLength,
		final long   persistentMaximumLength
	)
	{
		return new PersistenceTypeDefinitionMemberPseudoFieldVariableLength.Implementation(
			 notNull(typeName)               ,
			 notNull(name)                   ,
			         false                   ,
			positive(persistentMinimumLength),
			positive(persistentMaximumLength)
		);
	}

	public static PersistenceTypeDefinitionMemberPseudoFieldVariableLength.Implementation Bytes(
		final String name                   ,
		final long   persistentMinimumLength,
		final long   persistentMaximumLength
	)
	{
		return New(
			PersistenceTypeDictionary.Symbols.TYPE_BYTES,
			name                   ,
			persistentMinimumLength,
			persistentMaximumLength
		);
	}

	public static PersistenceTypeDefinitionMemberPseudoFieldVariableLength.Implementation Chars(
		final String name                   ,
		final long   persistentMinimumLength,
		final long   persistentMaximumLength
	)
	{
		return New(
			PersistenceTypeDictionary.Symbols.TYPE_CHARS,
			name                   ,
			persistentMinimumLength,
			persistentMaximumLength
		);
	}



	public class Implementation
	extends PersistenceTypeDescriptionMemberPseudoFieldVariableLength.Implementation
	implements PersistenceTypeDefinitionMemberPseudoFieldVariableLength
	{
		///////////////////////////////////////////////////////////////////////////
		// constructors //
		/////////////////

		Implementation(
			final String  typeName               ,
			final String  name                   ,
			final boolean hasReferences          ,
			final long    persistentMinimumLength,
			final long    persistentMaximumLength
		)
		{
			super(typeName, name, hasReferences, persistentMinimumLength, persistentMaximumLength);
		}



		///////////////////////////////////////////////////////////////////////////
		// methods //
		////////////
				
		@Override
		public final Class<?> type()
		{
			return null;
		}

	}

}
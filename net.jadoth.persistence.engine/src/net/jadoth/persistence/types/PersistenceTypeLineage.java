package net.jadoth.persistence.types;

import static net.jadoth.X.mayNull;
import static net.jadoth.X.notNull;

import net.jadoth.collections.EqHashTable;
import net.jadoth.collections.types.XGettingTable;

public interface PersistenceTypeLineage<T>
{
	public String typeName();
	
	public Class<T> runtimeType();
	
	public XGettingTable<Long, PersistenceTypeDefinition<T>> entries();
	
	public PersistenceTypeDefinition<T> latest();
	
	public PersistenceTypeDefinition<T> runtimeDefinition();
	
	public boolean registerTypeDefinition(PersistenceTypeDefinition<T> typeDefinition);
	
	

	public boolean initializeRuntimeTypeDefinition(PersistenceTypeDefinition<T> runtimeDefinition);
	
	
	
	public static <T> PersistenceTypeLineage.Implementation<T> New(
		final String   typeName   ,
		final Class<T> runtimeType
	)
	{
		return new PersistenceTypeLineage.Implementation<>(
			notNull(typeName)             , // may never be null as this is the lineage's identity.
			mayNull(runtimeType)            // can be null if the type cannot be resolved into a runtime class.
		);
	}
		
	public final class Implementation<T> implements PersistenceTypeLineage<T>
	{
		////////////////////////////////////////////////////////////////////////////
		// instance fields //
		////////////////////

		final String                                          typeName             ;
		final Class<T>                                        runtimeType          ;
		final EqHashTable<Long, PersistenceTypeDefinition<T>> entries              ;
		      PersistenceTypeDefinition<T>                    runtimeDefinition   ; // initialized effectively final



		////////////////////////////////////////////////////////////////////////////
		// constructors //
		/////////////////

		Implementation(final String typeName, final Class<T> runtimeType)
		{
			super();
			this.typeName    = typeName         ;
			this.runtimeType = runtimeType      ;
			this.entries     = EqHashTable.New();
		}



		////////////////////////////////////////////////////////////////////////////
		// methods //
		////////////

		@Override
		public final String typeName()
		{
			return this.typeName;
		}

		@Override
		public final XGettingTable<Long, PersistenceTypeDefinition<T>> entries()
		{
			return this.entries;
		}

		@Override
		public final Class<T> runtimeType()
		{
			return this.runtimeType;
		}

		@Override
		public final PersistenceTypeDefinition<T> runtimeDefinition()
		{
			return this.runtimeDefinition;
		}
		
		@Override
		public final PersistenceTypeDefinition<T> latest()
		{
			synchronized(this.entries)
			{
				return this.entries.values().peek();
			}
		}
		
		private void validate(final PersistenceTypeDefinition<T> typeDefinition)
		{
			if(isValid(typeDefinition))
			{
				return;
			}
			
			// (12.10.2017 TM)EXCP: proper exception
			throw new RuntimeException("Invalid type definition for type lineage " + this.typeName);
		}
		
		private boolean isValid(final PersistenceTypeDefinition<T> typeDefinition)
		{
			return this.typeName.equals(typeDefinition.typeName()) && this.runtimeType == typeDefinition.type();
		}
		
		@Override
		public boolean registerTypeDefinition(final PersistenceTypeDefinition<T> typeDefinition)
		{
			this.validate(typeDefinition);
			return this.internalRegisterTypeDefinition(typeDefinition);
		}
		
		private boolean internalRegisterTypeDefinition(final PersistenceTypeDefinition<T> typeDefinition)
		{
			synchronized(this.entries)
			{
				if(this.entries.add(typeDefinition.typeId(), typeDefinition))
				{
					this.entries.keys().sort(Long::compare);
					return true;
				}
				return false;
			}
		}
				
		@Override
		public final boolean initializeRuntimeTypeDefinition(final PersistenceTypeDefinition<T> runtimeDefinition)
		{
			synchronized(this.entries)
			{
				// true indicates no-op, actual non-viability causes exceptions
				if(!this.checkViability(runtimeDefinition))
				{
					return false;
				}
				
				// normal case: effective final initialization
				this.runtimeDefinition = runtimeDefinition;
				
				// correct behavior of the put has been checked above
				this.entries.put(runtimeDefinition.typeId(), runtimeDefinition);
				
				return true;
			}
		}
		
		private boolean checkViability(final PersistenceTypeDefinition<T> runtimeDefinition)
		{
			if(this.runtimeDefinition != null)
			{
				if(this.runtimeDefinition == runtimeDefinition)
				{
					// no-op call, abort
					return false;
				}
				
				// conflicting call/usage
				// (26.09.2017 TM)EXCP: proper exception
				throw new RuntimeException("Runtime definition already initialized");
			}
			
			final Long                         typeId     = runtimeDefinition.typeId();
			final PersistenceTypeDefinition<T> latest     = this.latest();
			final PersistenceTypeDefinition<T> equivalent = this.entries.get(typeId);
			if(equivalent != null && equivalent != latest)
			{
				// (28.09.2017 TM)EXCP: proper exception
				throw new RuntimeException("Invalid runtime definition for type id: " + typeId);
			}
						
			return true;
		}
		
	}

}

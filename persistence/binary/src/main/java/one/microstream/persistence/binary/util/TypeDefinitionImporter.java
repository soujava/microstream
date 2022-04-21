package one.microstream.persistence.binary.util;

/*-
 * #%L
 * MicroStream Persistence Binary
 * %%
 * Copyright (C) 2019 - 2022 MicroStream Software
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 * #L%
 */

import org.slf4j.Logger;

import one.microstream.collections.types.XGettingSequence;
import one.microstream.equality.Equalator;
import one.microstream.persistence.binary.types.Binary;
import one.microstream.persistence.types.PersistenceTypeDefinition;
import one.microstream.persistence.types.PersistenceTypeDescriptionMember;
import one.microstream.persistence.types.PersistenceTypeHandler;
import one.microstream.persistence.types.PersistenceTypeHandlerEnsurer;
import one.microstream.persistence.types.PersistenceTypeHandlerManager;
import one.microstream.util.logging.Logging;

public interface TypeDefinitionImporter
{
	public void importTypeDefinition(PersistenceTypeDefinition typeDefintion);
	
	void importTypeDefinitions(XGettingSequence<PersistenceTypeDefinition> typeDefintions);

	public static class Default implements TypeDefinitionImporter
	{
		private final static Logger logger = Logging.getLogger(Default.class);
		
		///////////////////////////////////////////////////////////////////////////
		// instance fields //
		////////////////////
		
		private final PersistenceTypeHandlerManager<Binary> typeHandlerManager;
		private final PersistenceTypeHandlerEnsurer<Binary> typeHandlerEnsurer;
		
		
		///////////////////////////////////////////////////////////////////////////
		// constructors //
		/////////////////
		
		public Default(
			final PersistenceTypeHandlerManager<Binary> typeHandlerManager,
			final PersistenceTypeHandlerEnsurer<Binary> typeHandlerEnsurer
		)
		{
			super();
			this.typeHandlerManager = typeHandlerManager;
			this.typeHandlerEnsurer = typeHandlerEnsurer;
		}
		
		
		///////////////////////////////////////////////////////////////////////////
		// methods //
		////////////
		
		@Override
		public void importTypeDefinition(final PersistenceTypeDefinition typeDefintion)
		{
			if(typeDefintion.type() != null)
			{
//				final PersistenceTypeHandler<Binary, ?> typeHandler = this.ensureTypeHandler(typeDefintion);
//				this.ensureLegacyTypeHandler(typeDefintion, typeHandler);
				
				final PersistenceTypeHandler<Binary, ?> handler = this.typeHandlerManager.lookupTypeHandler(typeDefintion.type());
				
				if(handler != null)
				{
					if(PersistenceTypeDescriptionMember.equalMembers(typeDefintion.allMembers(), handler.allMembers(), this.memberValidator))
					{
						logger.trace("handler for type {}, typeId {} already registered",typeDefintion.type(), typeDefintion.typeId());
					}
					else
					{
						logger.trace("trying to create legacy type handler for type {}, typeId {}",typeDefintion.type(), typeDefintion.typeId());
						this.typeHandlerManager.updateCurrentHighestTypeId(typeDefintion.typeId());
						this.typeHandlerManager.ensureLegacyTypeHandler(typeDefintion, handler);
					}
				}
				else
				{
					final PersistenceTypeHandler<Binary, ?> th = this.typeHandlerEnsurer.ensureTypeHandler(typeDefintion.type());
									
					if(PersistenceTypeDescriptionMember.equalMembers(typeDefintion.allMembers(), th.allMembers(), this.memberValidator))
					{
						logger.trace("trying to create type handler for new type {}, typeId {}",typeDefintion.type(), typeDefintion.typeId());
						this.typeHandlerManager.ensureTypeHandler(typeDefintion.type());
					}
					else
					{
						logger.trace("trying to create legacy type handler for new type {}, typeId {}",typeDefintion.type(), typeDefintion.typeId());
						this.typeHandlerManager.updateCurrentHighestTypeId(typeDefintion.typeId());
						this.typeHandlerManager.ensureLegacyTypeHandler(typeDefintion, th);
					}
				}
				
			}
			else
			{
				logger.error("Failed to resolve new type {}", typeDefintion.typeName());
				throw new PersistenceExceptionTypeImportTypeNotFound(typeDefintion.typeName());
			}
			
		}
		
		@Override
		public void importTypeDefinitions(final XGettingSequence<PersistenceTypeDefinition> typeDefintions)
		{
			for (final PersistenceTypeDefinition typeDefintion : typeDefintions)
			{
				this.importTypeDefinition(typeDefintion);
			}
		}
							
		private final Equalator<PersistenceTypeDescriptionMember> memberValidator = (m1, m2) ->
		{
			if(m1 == null || m2 == null)
			{
				return false;
			}

			if(m1.equalsStructure(m2))
			{
				return true;
			}

			return false;
		};
	}
}

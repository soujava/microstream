package one.microstream.storage.io.fs;

import java.io.File;
import java.util.function.Predicate;

import one.microstream.collections.EqHashTable;
import one.microstream.collections.types.XGettingTable;
import one.microstream.io.XFiles;
import one.microstream.storage.io.ProtageWritableDirectory;
import one.microstream.storage.io.ProtageWritableFile;


public interface ProtageDirectoryFS extends ProtageWritableDirectory
{
	public File directory();
	
	@Override
	public ProtageFileFS createFile(String fileName);
	
	
	
	public static ProtageDirectoryFS New(final File directory, final Predicate<? super File> isRelevantFile)
	{
		ProtageFileSystem.validateExistingDirectory(directory);
		ProtageFileSystem.validateIsDirectory(directory);
		
		final String qualifier  = XFiles.ensureNormalizedPathSeperators(directory.getParent());
		final String name       = directory.getName();
		final String identifier = XFiles.ensureTrailingSlash(qualifier) + name;
		final String qualIdent  = XFiles.ensureTrailingSlash(identifier);
		
		final EqHashTable<String, ProtageFileFS.Default>   files      = EqHashTable.New();
		final XGettingTable<String, ProtageFileFS.Default> viewFiles  = files.view();
		
		final ProtageDirectoryFS.Default instance = new ProtageDirectoryFS.Default(
			directory, qualifier, name, identifier, qualIdent, files, viewFiles
		);
		instance.initializeFiles(isRelevantFile);
		
		return instance;
	}
	
	public class Default implements ProtageDirectoryFS
	{
		///////////////////////////////////////////////////////////////////////////
		// instance fields //
		////////////////////
		
		private final File   directory           ;
		private final String qualifier           ;
		private final String name                ;
		private final String identifier          ;
		private final String qualifyingIdentifier;
		
		private final EqHashTable<String, ProtageFileFS.Default>   files    ;
		private final XGettingTable<String, ProtageFileFS.Default> viewFiles;
		
		
		
		///////////////////////////////////////////////////////////////////////////
		// constructors //
		/////////////////

		Default(
			final File                                                directory           ,
			final String                                              qualifier           ,
			final String                                              name                ,
			final String                                              identifier          ,
			final String                                              qualifyingIdentifier,
			final EqHashTable<String, ProtageFileFS.Default>   files               ,
			final XGettingTable<String, ProtageFileFS.Default> viewFiles
		)
		{
			super();
			this.directory            = directory           ;
			this.qualifier            = qualifier           ;
			this.name                 = name                ;
			this.identifier           = identifier          ;
			this.qualifyingIdentifier = qualifyingIdentifier;
			this.files                = files               ;
			this.viewFiles            = viewFiles           ;
		}
		
		
		
		///////////////////////////////////////////////////////////////////////////
		// methods //
		////////////
		
		@Override
		public final String qualifier()
		{
			return this.qualifier;
		}

		@Override
		public final String name()
		{
			return this.name;
		}
		
		@Override
		public final String identifier()
		{
			return this.identifier;
		}
		
		@Override
		public final String qualifyingIdentifier()
		{
			return this.qualifyingIdentifier;
		}

		@Override
		public final File directory()
		{
			return this.directory;
		}

		@Override
		public final XGettingTable<String, ? extends ProtageWritableFile> files()
		{
			return this.viewFiles;
		}
		
		@Override
		public boolean contains(final String fileName)
		{
			return this.files.keys().contains(fileName);
		}
		
		protected void validateNotYetContained(final String fileName)
		{
			if(!this.contains(fileName))
			{
				return;
			}
			
			// (15.10.2018 TM)EXCP: proper exception
			throw new RuntimeException(
				"File \"" + fileName + "\" already exist in directory \"" + this.name() + "\"."
			);
		}
		
		protected File internalCreateSystemFile(final String fileName)
		{
			this.validateNotYetContained(fileName);
			return ProtageFileSystem.createWriteableFile(this.directory, fileName);
		}
				
		@Override
		public synchronized ProtageFileFS createFile(final String fileName)
		{
			final File file = this.internalCreateSystemFile(fileName);
			return this.internalCreateFile(file, fileName);
		}
		
		final synchronized void initializeFiles(final Predicate<? super File> isRelevantFile)
		{
			for(final File file : this.directory.listFiles())
			{
				if(!isRelevantFile.test(file))
				{
					continue;
				}
				
				final String fileName = file.getName();
				this.validateNotYetContained(fileName);
				ProtageFileSystem.validateWriteableFile(file);
				
				this.internalCreateFile(file, fileName);
			}
		}
		
		protected synchronized ProtageFileFS internalCreateFile(final File file, final String fileName)
		{
			// file is created in closed state to allow a complete creation of a preliminary directory instance
			final ProtageFileFS.Default createdFile = new ProtageFileFS.Default(
				this, fileName, file, null, null
			);
			
			// success of the addition is guaranteed by the synchronization and the validation above.
			this.files.add(fileName, createdFile);
			
			return createdFile;
		}
		
	}
	
}

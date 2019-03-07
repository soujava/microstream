package net.jadoth.storage.types;

import java.io.File;
import java.nio.channels.FileLock;


public interface StorageInventoryFile extends StorageLockedFile, StorageNumberedFile
{
	@Override
	public default StorageInventoryFile inventorize()
	{
		return this;
	}
	
	

	public static StorageInventoryFile New(
		final int  channelIndex,
		final long number      ,
		final File file
	)
	{
		return new StorageInventoryFile.Implementation(
			channelIndex,
			number,
			file,
			StorageLockedFile.openLockedFileChannel(file)
		);
	}

	public class Implementation extends StorageLockedFile.Implementation implements StorageInventoryFile
	{
		////////////////////////////////////////////////////////////////////////////
		// instance fields //
		////////////////////

		private final int  channelIndex;
		private final long number      ;



		////////////////////////////////////////////////////////////////////////////
		// constructors //
		/////////////////

		Implementation(
			final int      channelIndex,
			final long     number      ,
			final File     file        ,
			final FileLock lock
		)
		{
			super(file, lock);
			this.channelIndex = channelIndex;
			this.number       = number      ;
		}



		////////////////////////////////////////////////////////////////////////////
		// methods //
		////////////
		
		@Override
		public final int channelIndex()
		{
			return this.channelIndex;
		}
		
		@Override
		public final long number()
		{
			return this.number;
		}

	}

}
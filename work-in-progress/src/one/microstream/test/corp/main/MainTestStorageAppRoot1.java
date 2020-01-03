package one.microstream.test.corp.main;

import java.util.Date;

import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;
import one.microstream.test.corp.logic.Test;
import one.microstream.test.corp.logic.TestImportExport;
import one.microstream.time.XTime;


public class MainTestStorageAppRoot1
{
	static
	{
//		Test.clearDefaultStorageDirectory();
	}
	
	// Option 1: Explicit application root provided at startup (specific typing)
	static final AppRoot<Date> APP_ROOT = new AppRoot<>();
	static final EmbeddedStorageManager STORAGE = EmbeddedStorage.start(APP_ROOT);

	public static void main(final String[] args)
	{
		// object graph with root either loaded on startup from an existing DB or required to be generated.
		if(APP_ROOT.value == null)
		{
			// first execution enters here (database creation)
			Test.print("Model data required.");
			APP_ROOT.set(XTime.now());
			
			Test.print("Storing ...");
			STORAGE.storeRoot();
			Test.print("Storing completed.");
			
			Test.print("Exporting data ...");
			TestImportExport.testExport(STORAGE, Test.provideTimestampedDirectory("testExport"));
			Test.print("Data export completed.");
		}
		else
		{
			// subsequent executions enter here (database reading)

			STORAGE.storeRoot();
			Test.printInitializationTime(STORAGE);
			Test.printOperationModeTime(STORAGE);
			Test.print("Model data loaded.");
			Test.print("Root instance: " + APP_ROOT);
			
			Test.print("Exporting data ...");
			TestImportExport.testExport(STORAGE, Test.provideTimestampedDirectory("testExport"));
			Test.print("Data export completed.");
		}
		
		STORAGE.shutdown();
		
		// no shutdown required, the storage concept is inherently crash-safe
		System.exit(0);
	}
	
}
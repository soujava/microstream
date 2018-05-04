package net.jadoth.persistence.test;

import java.io.File;
import java.util.Date;

import net.jadoth.X;
import net.jadoth.chars.VarString;
import net.jadoth.collections.BulkList;
import net.jadoth.collections.EqConstHashTable;
import net.jadoth.collections.types.XEnum;
import net.jadoth.meta.JadothDebug;
import net.jadoth.persistence.binary.types.BinaryPersistence;
import net.jadoth.persistence.types.PersistenceTypeDictionary;
import net.jadoth.storage.types.StorageConnection;
import net.jadoth.storage.types.StorageDataFileEvaluator;
import net.jadoth.storage.types.StorageRawFileStatistics;
import net.jadoth.storage.types.StorageTransactionsFileAnalysis;
import net.jadoth.swizzling.types.Lazy;
import net.jadoth.time.JadothTime;
import net.jadoth.typing.JadothTypes;


public class MainTestStorage extends TestStorage
{

	public static void main(final String[] args) throws Throwable
	{
		// print what the root references (null on first start, stored reference on later starts)
//		final Object root = ROOT.get();
//		System.out.println(System.identityHashCode(root));


//		ROOT.set(new TEST());

//		STORAGE.createConnection().storeFull(ROOT);

		testBig2();
//		testTruncate();
//		testBigGraph();
//		testExport();
//		testCleanUp();
//		testCleanUp();
//		testImport();
		System.exit(0);
	}


	static void testTruncate()
	{
		STORAGE.truncateData();
	}


	static void testBigGraph()
	{
		final StorageConnection connection = STORAGE.createConnection();
//		createBigGraph(10, connection);
		testContinuousHouseKeeping(connection, ROOT);
	}

	static void createBigGraph(final int p6size, final StorageConnection connection)
	{
		JadothDebug.debugln("big graph initialization ("+p6size+")");
		final Integer[][][][][][] ints0 = new Integer[p6size][p6size][p6size][p6size][p6size][p6size];
		for(int i0 = 0; i0 < ints0.length; i0++)
		{
			final Integer[][][][][] ints1 = ints0[i0];
			for(int i1 = 0; i1 < ints1.length; i1++)
			{
				final Integer[][][][] ints2 = ints1[i1];
				for(int i2 = 0; i2 < ints2.length; i2++)
				{
					final Integer[][][] ints3 = ints2[i2];
					for(int i3 = 0; i3 < ints3.length; i3++)
					{
						final Integer[][] ints4 = ints3[i3];
						for(int i4 = 0; i4 < ints4.length; i4++)
						{
							final Integer[] ints5 = ints4[i4];
							for(int i5 = 0; i5 < ints5.length; i5++)
							{
								ints5[i5] = new Integer(i5);
							}
						}
					}
				}
			}
		}
		ROOT.set(Lazy.Reference(ints0));
		JadothDebug.debugln("store big graph ...");
		connection.store(ROOT);
		JadothDebug.debugln("store big graph done");
	}


	static void testContinuousHouseKeeping(final StorageConnection connection, final Object instance)
	{
//		JadothThreads.sleep(2000);
		for(int i = 1000; i --> 0;)
		{
			JadothDebug.debugln("round "+i);

			// do one round of explicitely issued house keeping
			connection.store(instance);
			storageCleanup(connection);

//			if(Math.random() < 0.5)
//			{
//				storageCleanup(connection);
//			}
//			else if(Math.random() < 0.2)
//			{
//				JadothConsole.debugln("long");
//				JadothThreads.sleep(16_000);
//			}
//			else
//			{
//				JadothConsole.debugln("short");
//				JadothThreads.sleep(100 + (int)(200d*Math.random()));
//			}
		}
	}

	public static void storageCleanup(final StorageConnection connection)
	{
		JadothDebug.debugln("GC#1");
		connection.issueFullGarbageCollection();
		JadothDebug.debugln("GC#2");
		connection.issueFullGarbageCollection();
		JadothDebug.debugln("cache check");
		connection.issueFullCacheCheck();
		JadothDebug.debugln("file check");
		connection.issueFullFileCheck();
		JadothDebug.debugln("Done cleanup");
	}

	static void oldTestStuff()
	{
//		testRawFileStatistics();


		// thread-local light-weight relaying instance to embedded storage manager (= Storage PersistenceManager)
//		final StorageConnection storageConnection = STORAGE.createConnection();

//		final Object complexStuff = complexStuff();
//		final long[][] largeStuff = largeStuff(20_000);

		// simple storing test
//		ROOT.set(EqConstHashTable.New(keyValue("A", "One"), keyValue("B", "Two"), keyValue("C", "Schnitzel")));
//		ROOT.set(X.List(
//			new Person("Alice", "A", null, JadothTime.timestamp(1990, 9, 19), 23, 52.1f, 1.70, 'F', 1234, true, false, (short)20000, (byte)112),
//			new Person("Bob", "B", null, JadothTime.timestamp(1980, 8, 18), 33, 69.8f, 1.80, 'M', 1337, true, false, (short)30000, (byte)127)
//		));
//		ROOT.set(X.List(
//			complexStuff,
//			largeStuff
//		));
//		ROOT.set(testObjects());
//		ROOT.set(X.Enum(1, 2, 3));
//		ROOT.set(X.Enum("A", "B", "C"));
//		ROOT.set(X.Enum(X.Enum(1, 2, 3), 1));
//		ROOT.set("Hello World");       // trivial "graph" of only one String instance for simple example

//		storageConnection.storeFull(ROOT);


//		storageConnection.exportTypes(new StorageEntityTypeExportFileProvider() {
//			@Override
//			public File provideExportFile(final StorageEntityTypeHandler entityType)
//			{
//				return new File(
//					JadothFiles.ensureDirectory(new File("c:/Files/export/")),
//					entityType.typeName()+".bin"
//				);
//			}
//		});

//		for(int i = 100; i --> 0;)
//		{
//			createIntegers(ints, 1000);
//			Thread.sleep(10);
//			storageConnection.storeOnDemand(ints);
//			Thread.sleep(10);
//			System.out.println("saved "+(count + ints.size()));
//		}

//		final BulkList<Integer[]> ints = BulkList.New();
//		ROOT.set(ints);
//		storageConnection.store(ROOT); // save whole graph recursively, starting at root
//		for(int i = 1_00; i --> 0;)
//		{
//			createIntegers2(ints, 3_00);
//			storageConnection.storeOnDemand(ints);
//		}

//		while(true)
//		{
//			Thread.sleep(1000);
//			storageConnection.store(largeStuff);
//		}

//		System.out.println(ROOT.get());



//		System.exit(0); // no need to explictely "shutdown". Storage is robust enough to guarantee consistency

//		final StorageRoot<Object[][][]> root = new StorageRoot<>(testGraph());
//		final long rootId = storageConnection.store(root);
//		System.out.println(rootId);

//		final StorageRoot<Object[][][]> root = (StorageRoot<Object[][][]>)storageConnection.get(1000000000000000001L);
//		System.out.println(root.get()[10][10][10]);
//		root.clear();
//		storageConnection.store(root);

//		ROOT.set("rootstesthohoho");
		////		ROOT.set(testGraph());
//		for(int i = 1; i <= 1; i++)
//		{
//			final long tStart = System.nanoTime();
//			final long oid    = storageConnection.store(ROOT);
//			final long tStop  = System.nanoTime();
//			System.out.println("Stored "+oid + " Elapsed Time: " + new java.text.DecimalFormat("00,000,000,000").format(tStop - tStart));
//		}

//		STORAGE.shutdown();

//		System.exit(0);

//		storageConnection.exportChannels(new StorageExportFileProvider.Implementation(new File("D:/storageexport"), "channelBackup_"));
//		storageConnection.exportChannels(Storage.ExportFileProvider(new File("D:/storageexport"), "channelBackup_"));
	}


	static void testImport()
	{
		final StorageConnection         connection = STORAGE.createConnection();
		final PersistenceTypeDictionary dictionary = BinaryPersistence.provideTypeDictionaryFromFile(
			new File("C:/FilesImport/PersistenceTypeDictionary.ptd")
		);
		final XEnum<File>               dataFiles  = X.Enum(new File("C:/FilesImport/channel_0").listFiles())
			.sort((f1, f2) -> Long.compare(parseStorageFileNumber(f1), parseStorageFileNumber(f2)))
		;

		connection.persistenceManager().updateMetadata(dictionary);
		connection.importFiles(dataFiles);
	}

	static long parseStorageFileNumber(final File file)
	{
		final String filename = file.getName();
		return Long.valueOf(filename.substring(filename.lastIndexOf('_')+1, filename.lastIndexOf('.')));
	}


	static void testCleanUp() throws InterruptedException
	{
		final StorageDataFileEvaluator fileEvaluator = STORAGE.configuration().fileEvaluator();

		STORAGE.createConnection().issueFullGarbageCollection();
		STORAGE.createConnection().issueFullCacheCheck((a, b, e) -> {
//			System.out.println(a+" Clearing "+e.objectId()+" "+e.type().typeHandler().typeName());
			return true;
		});

		STORAGE.createConnection().issueCacheCheck(System.nanoTime()+100_000_000);
		STORAGE.createConnection().issueFullFileCheck(
			f -> {
//				System.out.println("evaluating file "+f);
				return fileEvaluator.needsDissolving(f);
			}
		);

		for(int i = 1; i --> 0;)
		{
			STORAGE.createConnection().issueCacheCheck(System.nanoTime()+100_000_000);
			Thread.sleep(1000);
		}
		System.out.println("Done");
	}


	static void testBig() throws Throwable
	{
		final StorageConnection storageConnection = STORAGE.createConnection();

		final BulkList<Integer> ints = BulkList.New();
		ROOT.set(ints);
		JadothDebug.debugln("initial storing root...");
		storageConnection.store(ROOT); // save whole graph recursively, starting at root

		createIntegers(ints, 100_000);
		for(int i = 0; i < 1; i++)
		{
//			ints.clear();
//			createIntegers(ints, 10000);
//			Thread.sleep(987);
//			JadothConsole.debugln("storing ints #"+i);
			final long tStart = System.nanoTime();
			storageConnection.store(ints);
			final long tStop = System.nanoTime();
			System.out.println("Elapsed Time: " + new java.text.DecimalFormat("00,000,000,000").format(tStop - tStart));
//			Thread.sleep(1000);
//			System.out.println("saved "+(count + ints.size()));
		}
	}

	static void testBig2() throws Throwable
	{
		/* Import testdata:
		 * 1  channel, 100, 10_000, 0.75
		 * 5*100 Integer
		 */

		System.out.println("testing big 2");
		final StorageConnection storageConnection = STORAGE.createConnection();

		final BulkList<BulkList<Integer>> ints = BulkList.New();
		ROOT.set(ints);
		JadothDebug.debugln("initial storing root...");
		storageConnection.store(ROOT); // save whole graph recursively, starting at root

		for(int i = 0; i < 100; i++)
		{
			final BulkList<Integer> subInts = BulkList.New();
			createIntegers(subInts, 1000);
			ints.add(subInts);

			final long tStart = System.nanoTime();
			storageConnection.store(ints);
			final long tStop = System.nanoTime();
			System.out.println("Elapsed Time: " + new java.text.DecimalFormat("00,000,000,000").format(tStop - tStart));
		}
	}

	static void testRawFileStatistics()
	{
		final StorageConnection storageConnection = STORAGE.createConnection();

		final StorageRawFileStatistics stats = storageConnection.createStorageStatistics();
		System.out.println(stats);

		final BulkList<BulkList<Integer>> ints = BulkList.New();
		ROOT.set(ints);
		storageConnection.store(ROOT);

		for(int i = 100; i --> 0;)
		{
			ints.add(createIntegers(BulkList.New(), 1_000));
			storageConnection.store(ints);
		}

//		final StorageRawFileStatistics stats = storageConnection.createStorageStatitics();
//		System.out.println(stats);
	}



	static BulkList<Integer> createIntegers(final BulkList<Integer> ints, final int size)
	{
		final int first = 5001 + JadothTypes.to_int(ints.size());
		final int bound = first + size;
		for(int i = first; i < bound; i++)
		{
			ints.add(i);
		}
		return ints;
	}

	static void createIntegers2(final BulkList<Integer[]> ints, final int size)
	{
		final Integer[] array = new Integer[size];
		for(int i = 0; i < size; i++)
		{
			array[i] = 10_001 + i;
		}
		ints.add(array);
	}


	static File toCsvFile(final File file, final String ending)
	{
		final File dir = file.getParentFile();
		final String name = file.getName();

		return new File(dir, name.substring(0, name.lastIndexOf(ending))+".csv");
	}

	static final class Person
	{
		String  firstname, lastname, whatever;
		Date    doB   ;
		int     age   ;
		float   weight;
		double  height;
		long    ssid  ;
		boolean b1, b2;
		short   stuff ;
		char    sex   ;
		byte    bla   ;

		public Person(
			final String  firstname,
			final String  lastname ,
			final String  whatever ,
			final Date    doB      ,
			final int     age      ,
			final float   weight   ,
			final double  height   ,
			final char    sex      ,
			final long    ssid     ,
			final boolean b1       ,
			final boolean b2       ,
			final short   stuff    ,
			final byte    bla
			)
		{
			super();
			this.firstname = firstname;
			this.lastname = lastname;
			this.whatever = whatever;
			this.doB = doB;
			this.age = age;
			this.weight = weight;
			this.height = height;
			this.sex = sex;
			this.ssid = ssid;
			this.b1 = b1;
			this.b2 = b2;
			this.stuff = stuff;
			this.bla = bla;
		}
	}


	static Object complexStuff()
	{
		return X.List(
			X.List(
				new Person("Alice", "A", null, JadothTime.timestamp(1990, 9, 19), 23, 52.1f, 1.70, 'F', 1234, true, false, (short)20000, (byte)112),
				new Person("Bob"  , "B", null, JadothTime.timestamp(1980, 8, 18), 33, 69.8f, 1.80, 'M', 1337, true, false, (short)30000, (byte)127)
				),
				new Date[][][]{
				{
					{new Date(), new Date()},
					{new Date(), new Date()}
				},
				null,
				{
					{new Date()},
					{null},
					null
				},
			},
			EqConstHashTable.New(X.KeyValue("A", "One"), X.KeyValue("B", "Two"), X.KeyValue("C", "Schnitzel")),
			5,
			1337L,
			X.Enum("A", "B", "C")
			);
	}

	static long[][] largeStuff(final int size)
	{
		final long[][] array = new long[channelCount][size];
		for(int a = 0; a < array.length; a++)
		{
			for(int i = 0; i < size; i++)
			{
				array[a][i] = i;
			}
		}
		return array;
	}

	static void printTransactionsFiles(final File... files)
	{
		for(final File file : files)
		{
			printTransactionsFile(file);
		}
	}

	static void printTransactionsFile(final File file)
	{
		final VarString vs = VarString.New(file.toString()).lf();
		StorageTransactionsFileAnalysis.EntryAssembler.assembleHeader(vs, "\t").lf();
		final VarString s = StorageTransactionsFileAnalysis.Logic.parseFile(file, vs)
			.lf().lf()
		;
		JadothDebug.debugln(s.toString());
	}

}

//-server -Xms4g -Xmx4g -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:ParallelGCThreads=1 -XX:ConcGCThreads=1 -verbose:gc
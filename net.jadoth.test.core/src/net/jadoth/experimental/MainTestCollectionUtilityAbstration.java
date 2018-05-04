package net.jadoth.experimental;

import net.jadoth.collections.AbstractArrayStorage;
import net.jadoth.collections.BulkList;
import net.jadoth.functional.IsSame;
import net.jadoth.math.JadothMath;

public class MainTestCollectionUtilityAbstration
{
	static final int RUNS = 1000;
	static final int RUNS2 = 10000;
	static final int SIZE = 1000;
	static final Integer[] INTS = JadothMath.sequence((Integer)(SIZE-1));

	static final BulkList<Object> HEAP = new BulkList<>(RUNS);


	public static void main(final String[] args)
	{
		for(int r = RUNS; r --> 0;)
		{
			final long tStart = System.nanoTime();
			int index = 0;
			for(int r2 = RUNS2; r2 --> 0;)
			{
//				index = AbstractArrayStorage.indexOfR(INTS, SIZE, INTS[SIZE - 1], 0, SIZE);
				index = AbstractArrayStorage.forwardIndexOf(INTS, 0, SIZE, INTS[SIZE - 1]);
			}
			final long tStop = System.nanoTime();
			System.out.println("Elapsed Time: " + new java.text.DecimalFormat("00,000,000,000").format(tStop - tStart));
			System.out.println(index);
			HEAP.add(new IsSame<>(INTS[0]));
		}
	}
}

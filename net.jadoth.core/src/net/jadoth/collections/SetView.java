
package net.jadoth.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import net.jadoth.collections.old.AbstractOldGettingSet;
import net.jadoth.collections.types.XGettingCollection;
import net.jadoth.collections.types.XGettingSet;
import net.jadoth.collections.types.XImmutableSet;
import net.jadoth.equality.Equalator;
import net.jadoth.typing.JadothTypes;


/**
 * Wrapper class that reduces the services provided by any wrapped {@link XGettingSet} to only those of
 * {@link XGettingSet}, effectively making the wrapped {@link XGettingSet} instance immutable (or read-only)
 * if used through an instance of this class.
 * <p>
 * All methods declared in {@link XGettingSet} are transparently passed to the wrapped set.<br>
 * All modifying methods declared in {@link Collection} and {@link Set}
 * (all variations of add~(), remove~() and retain~() as well as set() and clear()) immediately throw an
 * {@link UnsupportedOperationException} when called.
 * <p>
 * This concept can be very useful if a class wants to provide public read access to an internal set without
 * either the danger of the set being modified from the outside or the need to copy the whole set on every access.
 * <p>
 * This is one of many useful concepts that are missing in the JDK Collections Framework and thus so far lead to
 * either inefficient or unneccessary verbose program code (or both).
 *
 * @author Thomas Muenz
 *
 */
public final class SetView<E> implements XGettingSet<E>
{
	///////////////////////////////////////////////////////////////////////////
	// instance fields //
	////////////////////

	private final XGettingSet<E> subject;



	///////////////////////////////////////////////////////////////////////////
	// constructors //
	/////////////////

	public SetView(final XGettingSet<E> set)
	{
		super();
		this.subject = set;
	}



	///////////////////////////////////////////////////////////////////////////
	// override methods //
	/////////////////////

	@Override
	public E get()
	{
		return this.subject.get();
	}

	@Override
	public Equalator<? super E> equality()
	{
		return this.subject.equality();
	}

	@Override
	public boolean hasVolatileElements()
	{
		return this.subject.hasVolatileElements();
	}

//	@Override
//	public <R> R aggregate(final Aggregator<? super E, R> aggregate)
//	{
//		return this.subject.iterate(aggregate);
//	}

	@Override
	public boolean containsSearched(final Predicate<? super E> predicate)
	{
		return this.subject.containsSearched(predicate);
	}

	@Override
	public boolean applies(final Predicate<? super E> predicate)
	{
		return this.subject.applies(predicate);
	}

//	@Override
//	public boolean contains(final E sample, final Equalator<? super E> equalator)
//	{
//		return this.set.contains(sample, equalator);
//	}

	@Override
	public boolean nullAllowed()
	{
		return this.subject.nullAllowed();
	}

	@Override
	public boolean nullContained()
	{
		return this.subject.nullContained();
	}

//	@Override
//	public boolean containsAll(final XGettingCollection<? extends E> samples, final Equalator<? super E> equalator)
//	{
//		return this.subject.containsAll(samples, equalator);
//	}

	@Override
	public boolean containsAll(final XGettingCollection<? extends E> elements)
	{
		return this.subject.containsAll(elements);
	}

	@Override
	public boolean contains(final E element)
	{
		return this.subject.contains(element);
	}

	@Override
	public boolean containsId(final E element)
	{
		return this.subject.containsId(element);
	}

	@Override
	public SetView<E> copy()
	{
		return new SetView<>(this.subject);
	}

	@Override
	public <C extends Consumer<? super E>> C filterTo(final C target, final Predicate<? super E> predicate)
	{
		return this.subject.filterTo(target, predicate);
	}

	@Override
	public <C extends Consumer<? super E>> C copyTo(final C target)
	{
		return this.subject.copyTo(target);
	}

	@Override
	public long count(final E element)
	{
		return this.subject.count(element);
	}

	@Override
	public long countBy(final Predicate<? super E> predicate)
	{
		return this.subject.countBy(predicate);
	}

	@Override
	public <C extends Consumer<? super E>> C distinct(final C target, final Equalator<? super E> equalator)
	{
		return this.subject.distinct(target, equalator);
	}

	@Override
	public <C extends Consumer<? super E>> C distinct(final C target)
	{
		return this.subject.distinct(target);
	}

	@Override
	public boolean equals(final XGettingCollection<? extends E> samples, final Equalator<? super E> equalator)
	{
		return this.subject.equals(this.subject, equalator);
	}

	@Override
	public boolean equalsContent(final XGettingCollection<? extends E> samples, final Equalator<? super E> equalator)
	{
		return this.subject.equalsContent(this.subject, equalator);
	}

	@Override
	public <C extends Consumer<? super E>> C except(
		final XGettingCollection<? extends E> other    ,
		final Equalator<? super E>            equalator,
		final C                               target
	)
	{
		return this.subject.except(other, equalator, target);
	}

	@Override
	public final <P extends Consumer<? super E>> P iterate(final P procedure)
	{
		return this.subject.iterate(procedure);
	}

	@Override
	public final <A> A join(final BiConsumer<? super E, ? super A> joiner, final A aggregate)
	{
		return this.subject.join(joiner, aggregate);
	}

//	@Override
//	public boolean hasDistinctValues(final Equalator<? super E> equalator)
//	{
//		return this.subject.hasDistinctValues(equalator);
//	}
//
//	@Override
//	public boolean hasDistinctValues()
//	{
//		return this.subject.hasDistinctValues();
//	}

	@Override
	public <C extends Consumer<? super E>> C intersect(
		final XGettingCollection<? extends E> other    ,
		final Equalator<? super E>            equalator,
		final C                               target
	)
	{
		return this.subject.intersect(other, equalator, target);
	}

	@Override
	public E max(final Comparator<? super E> comparator)
	{
		return this.subject.max(comparator);
	}

	@Override
	public E min(final Comparator<? super E> comparator)
	{
		return this.subject.min(comparator);
	}

//	@Override
//	public E search(final E sample, final Equalator<? super E> equalator)
//	{
//		return this.set.search(sample, equalator);
//	}

	@Override
	public E seek(final E sample)
	{
		return this.subject.seek(sample);
	}

	@Override
	public E search(final Predicate<? super E> predicate)
	{
		return this.subject.search(predicate);
	}

	@Override
	public E[] toArray(final Class<E> type)
	{
		return this.subject.toArray(type);
	}

	@Override
	public <C extends Consumer<? super E>> C union(
		final XGettingCollection<? extends E> other    ,
		final Equalator<? super E>            equalator,
		final C                               target
	)
	{
		return this.subject.union(other, equalator, target);
	}



	///////////////////////////////////////////////////////////////////////////
	// java.util.list and derivatives  //
	////////////////////////////////////

	@Deprecated
	@Override
	public int hashCode()
	{
		return this.subject.hashCode();
	}

	@Deprecated
	@Override
	public boolean equals(final Object o)
	{
		return this.subject.equals(o);
	}

	@Override
	public boolean isEmpty()
	{
		return this.subject.isEmpty();
	}

	@Override
	public long size()
	{
		return JadothTypes.to_int(this.subject.size());
	}

	@Override
	public long maximumCapacity()
	{
		return this.subject.maximumCapacity();
	}

	@Override
	public boolean isFull()
	{
		return this.subject.isFull();
	}

	@Override
	public long remainingCapacity()
	{
		return this.subject.remainingCapacity();
	}

	@Override
	public Object[] toArray()
	{
		return this.subject.toArray();
	}

	@Override
	public XImmutableSet<E> immure()
	{
		return this.subject.immure();
	}



	@Override
	public OldSetView<E> old()
	{
		return new OldSetView<>(this);
	}

	public static final class OldSetView<E> extends AbstractOldGettingSet<E>
	{
		OldSetView(final SetView<E> set)
		{
			super(set);
		}

		@Override
		public SetView<E> parent()
		{
			return (SetView<E>)super.parent();
		}

	}

	@Override
	public Iterator<E> iterator()
	{
		throw new net.jadoth.meta.NotImplementedYetError(); // FIXME Auto-generated method stub, not implemented yet
	}

	@Override
	public SetView<E> view()
	{
		return this;
	}

}

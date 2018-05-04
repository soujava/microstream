package net.jadoth.collections.old;

import java.util.Collection;
import java.util.Iterator;

import net.jadoth.collections.types.XGettingSet;
import net.jadoth.functional.JadothFunctional;
import net.jadoth.typing.JadothTypes;

public abstract class AbstractOldGettingSet<E> implements OldSet<E>
{
	///////////////////////////////////////////////////////////////////////////
	// instance fields  //
	/////////////////////

	final XGettingSet<E> subject;



	///////////////////////////////////////////////////////////////////////////
	// constructors     //
	/////////////////////

	protected AbstractOldGettingSet(final XGettingSet<E> set)
	{
		super();
		this.subject = set;
	}



	///////////////////////////////////////////////////////////////////////////
	// override methods //
	/////////////////////

	@Override
	public XGettingSet<E> parent()
	{
		return this.subject;
	}

	@Override
	public boolean add(final E e) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(final Collection<? extends E> c) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(final Object o)
	{
		return this.subject.containsSearched(JadothFunctional.isEqualTo((E)o));
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean containsAll(final Collection<?> c)
	{
		for(final Object o : c)
		{
			if(!this.subject.containsSearched(JadothFunctional.isEqualTo((E)o)))
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty()
	{
		return this.subject.isEmpty();
	}

	@Override
	public Iterator<E> iterator()
	{
		return this.subject.iterator();
	}

	@Override
	public boolean remove(final Object o) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(final Collection<?> c) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(final Collection<?> c) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int size()
	{
		return JadothTypes.to_int(this.subject.size());
	}

	@Override
	public Object[] toArray()
	{
		return this.subject.toArray();
	}

	@Override
	public String toString()
	{
		return this.subject.toString();
	}


}

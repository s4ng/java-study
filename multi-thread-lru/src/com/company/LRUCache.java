package com.company;

import java.util.ArrayDeque;
import java.util.Deque;

public class LRUCache {

	private final Deque< Integer > dq;
	private final Integer size;

	public LRUCache( int size ) {

		this.size = size;
		this.dq = new ArrayDeque<>( size );
	}

	public void cacheData( Integer num ) {

		String condition = "Miss ";
		if ( dq.contains( num ) ) {
			dq.removeFirstOccurrence( num );
			condition = "Hit  ";
		}
		addDataToDq( num );

		printDq( condition );
	}

	private void addDataToDq( Integer num ) {

		if ( dq.size() == size ) {
			dq.removeLast();
		}
		dq.addFirst( num );

	}

	public void printDq( String condition ) {

		if ( dq.isEmpty() ) {
			System.out.println( "Empty" );
			return;
		}

		StringBuilder sb = new StringBuilder();
		sb.append( condition );

		for ( Integer item : dq ) {
			sb.append( item ).append( " " );
		}

		sb.setLength( sb.length() - 1 );

		System.out.println( sb );
	}
}

package com.company;

import java.util.ArrayDeque;
import java.util.Deque;

public class LRUCache {

	public static final Deque< Integer > dq = new ArrayDeque<>();
	private static Integer size;

	public LRUCache( int size ) {

		this.size = size;
	}

	public static synchronized void cacheData( Integer num ) {

		String condition = "Fault! ";
		if ( dq.contains( num ) ) {
			dq.removeFirstOccurrence( num );
			condition = "Hit!   ";
		}
		addDataToDq( num );

		printDq( num, condition );
	}

	private static void addDataToDq( Integer num ) {

		if ( dq.size() == size ) {
			dq.removeLast();
		}
		dq.addFirst( num );

	}

	public static void printDq( Integer num, String condition ) {

		if ( dq.isEmpty() ) {
			System.out.println( "Empty" );
			return;
		}

		StringBuilder sb = new StringBuilder();
		sb.append( condition );
		sb.append( num ).append( " -> " );
		sb.append( "[ " );

		for ( Integer item : dq ) {
			sb.append( item ).append( ", " );
		}

		sb.setLength( sb.length() - 2 );
		sb.append( " ]" );

		System.out.println( sb );
	}
}

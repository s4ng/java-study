package com.company;

import java.util.Scanner;

public class Main {

	public static void main( String[] args ) {

		Scanner sc = new Scanner( System.in );

		int threadSize;
		int cacheSize;

		System.out.print( "thread size = " );
		threadSize = sc.nextInt();
		System.out.print( "cache size = " );
		cacheSize = sc.nextInt();

		new LRUCache( cacheSize );

		Runnable task = new CacheThread();
		for ( int i = 0; i < threadSize; i++ ) {
			Thread thread = new Thread( task );
			thread.start();
		}
	}
}

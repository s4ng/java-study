package com.company;

public class CacheThread implements Runnable {

	@Override
	public void run() {

		double randomDouble = Math.random();
		int randomInt = ( int ) ( randomDouble * 10 );
		LRUCache.cacheData( randomInt );
	}
}

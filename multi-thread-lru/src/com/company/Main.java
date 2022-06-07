package com.company;

public class Main {

    public static void main(String[] args) {

        int[] arr = {1, 2, 3, 4, 2, 3 ,4, 5, 4, 3, 2, 7};
        int cacheSize = 5;

        LRUCache cache = new LRUCache( cacheSize );

        for(int i : arr) {
            cache.cacheData( i );
        }
    }
}

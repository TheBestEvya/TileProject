package test;


import java.util.HashSet;

public class CacheManager {

    private int size;
    private CacheReplacementPolicy crp;
    private HashSet<String> cache;
	
    public CacheManager(int s,CacheReplacementPolicy c)
    {
        this.size=s;
        this.crp=c;
        this.cache = new HashSet<>();
    }

    public boolean query(String str) { return cache.contains(str); }

    public void add(String str) {

        if (cache.size() >= size)
            cache.remove(crp.remove());

        crp.add(str);
        cache.add(str);
    }
}

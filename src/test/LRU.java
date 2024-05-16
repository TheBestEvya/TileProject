package test;


import java.util.Iterator;
import java.util.LinkedHashSet;

public class LRU implements CacheReplacementPolicy {

    private LinkedHashSet<String> words = new LinkedHashSet<>();
    @Override
    public void add(String word) {
    words.remove(word);
    words.add(word);
    }

    @Override
    public String remove() {
        Iterator<String> itr = words.iterator();
        String temp = itr.next();
        words.remove(itr.next());

        return temp;
    }
}

package com.theleafapps.pro.shopnick.utils;

import java.util.LinkedHashMap;
import java.util.Set;

public class LinkedMap<K, V> extends LinkedHashMap<K, V> {

    public V getValue(int i) {

        Entry<K, V> entry = this.getEntry(i);
        if (entry == null) return null;

        return entry.getValue();
    }

    public Entry<K, V> getEntry(int i) {
        // check if negative index provided
        Set<Entry<K, V>> entries = entrySet();
        int j = 0;

        for (Entry<K, V> entry : entries)
            if (j++ == i) return entry;

        return null;

    }
}

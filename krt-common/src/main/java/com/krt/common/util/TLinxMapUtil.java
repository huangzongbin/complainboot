package com.krt.common.util;

import java.util.*;

/**
 * @author zhangdb
 * @date 2019/6/24 17:25
 */
public class TLinxMapUtil {
    private Map map = new HashMap();
    private Set keySet = map.keySet();

    public Object get(String key) {
        return map.get(key);
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public void sort() {
        List list = new ArrayList(map.keySet());

//		Collections.sort(list, new Comparator() {
//			public int compare(Object a, Object b) {
//				return a.toString().compareTo(b.toString());
//			}
//		});

        this.keySet = new TreeSet(list);
    }

    public Set keySet() {
        return this.keySet;
    }
}

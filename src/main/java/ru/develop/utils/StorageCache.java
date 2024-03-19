package ru.develop.utils;

import lombok.extern.slf4j.Slf4j;
import ru.develop.entity.FractionCacheData;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class StorageCache {
    private static HashMap<Integer, FractionCacheData> storageCash = new HashMap<>();

    public static void addCache(int key, int num, int denum, int timeout, double valueCacheMethod) {

        storageCash.put(key, new FractionCacheData(num, denum, timeout, valueCacheMethod));
        clearCache(key);
    }

    public static Double getCache(Integer key) {
        //log.info("cache");
        return (Double) storageCash.get(key).getDoubleCacheValue();
    }

    public static Integer getLastKeys(){
        Set<Integer> keySet= storageCash.keySet();
        ArrayList<Integer> arrayList = new ArrayList<>(keySet);
        Collections.sort(arrayList);
        Optional<Integer> result;
        try {
            result = Optional.ofNullable(arrayList.get(arrayList.size() - 1));
        }catch (IndexOutOfBoundsException e){
            result = Optional.empty();
        };
        //return (Integer) storageCash.keySet().toArray()[storageCash.keySet().toArray().length-1];
        return result.orElse(0);
    }

    public static HashMap<Integer, FractionCacheData> getStorageCash(){
        return storageCash;
    }

    public static boolean containKey(Integer key) {
        return storageCash.containsKey(key);
    }

    public static Set<Integer> getKeysStorage(){
        return storageCash.keySet();
    }
    public static Integer getContainsKeys(int val, String method) {
        Optional<Integer> result = Optional.empty();
        Stream<Map.Entry<Integer, FractionCacheData>> data =
                storageCash.entrySet().stream();

        if(method.equals("setNum")) {
            List<Map.Entry<Integer, FractionCacheData>> collectKeysNum =
                    data.filter(e -> e.getValue().getNum() == val).collect(Collectors.toList());
            if(!collectKeysNum.isEmpty()) result = Optional.ofNullable(collectKeysNum.get(0).getKey());
        } else if (method.equals("setDenum")){
            List<Map.Entry<Integer, FractionCacheData>> collectKeysDenum =
                    data.filter(e -> e.getValue().getDenum() == val).collect(Collectors.toList());
            if(!collectKeysDenum.isEmpty()) result = Optional.ofNullable(collectKeysDenum.get(0).getKey());
        }

        return result.orElse(null);
    }
    public static String toStringCache() {
        return storageCash.toString();
    }

    public static void refreshTimeOut(Integer key){
        storageCash.get(key).setTimeout(1000);
    }

    private static void clearCache(Integer key){
        Thread thread = new Thread(()->{

            while (storageCash.get(key).getTimeout()>0){
                try {
                    int t = storageCash.get(key).getTimeout();
                    //log.info("timeout: " + t);
                    Thread.sleep(t);
                    storageCash.get(key).setTimeout(storageCash.get(key).getTimeout()-t);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            storageCash.remove(key);
            //log.info("remove");

        });
        thread.start();

    }

}

package ru.develop.utils;

import lombok.extern.slf4j.Slf4j;
import ru.develop.my.annotations.Cache;
import ru.develop.my.annotations.Mutator;

import java.lang.reflect.*;


@Slf4j
public class CacheService<T> implements InvocationHandler {

    private T o;
    private Double cacheValue;
    private int keyCache = 0;

    public CacheService(T o) {
        this.o = o;
    }

    public static <T> T cache(T in) {
        Class<?> inClass = in.getClass();
        Method[] methods = inClass.getMethods();

        ClassLoader classLoader = in.getClass().getClassLoader();
        Class[] interfaces = in.getClass().getInterfaces();
        T out = (T) Proxy.newProxyInstance(classLoader, interfaces, new CacheService<>(in));
        return out;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //log.info("key_cache:" + keyCache);

        Method[] basicMethods = o.getClass().getMethods();
        for (Method m : basicMethods) {
            if (m.getName() == method.getName() && m.isAnnotationPresent(Cache.class)) {
                Cache cacheAnnotation = m.getAnnotation(Cache.class);
                if(!StorageCache.containKey(keyCache)){
                    int arrField[] = getPrivateFields();

                    StorageCache.addCache(keyCache,arrField[0],arrField[1],cacheAnnotation.lifetime(),(double)m.invoke(o,args));
                    return StorageCache.getCache(keyCache).doubleValue();
                }
                return StorageCache.getCache(keyCache).doubleValue();

            } else if (m.getName() == method.getName() && m.isAnnotationPresent(Mutator.class)) {
                Integer val = StorageCache.getContainsKeys((Integer) args[0],method.getName());
                if(val==null){

                    keyCache = StorageCache.getLastKeys();
                    keyCache++;

                }else {
                    keyCache = val.intValue();
                    StorageCache.refreshTimeOut(keyCache);
                }


            }
        }
        return method.invoke(o, args);
    }

    private int[] getPrivateFields() throws IllegalAccessException {
        Field[] arrField = o.getClass().getDeclaredFields();
        int result[] = new int[arrField.length];
        for (int i=0;i<arrField.length;i++){
            arrField[i].setAccessible(true);
            if(arrField[i].getType() == int.class){
                result[i] = arrField[i].getInt(o);
            }
        }
        return result;
    }

}

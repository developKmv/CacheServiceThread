import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.develop.entity.Fraction;
import ru.develop.entity.Fractionable;
import ru.develop.utils.CacheService;
import ru.develop.utils.StorageCache;

import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestingTask {


    @Test
    public void testCache() throws InterruptedException {

        Fraction f3 = new Fraction(2,3);
        Fractionable proxyF5 = CacheService.cache(f3);

        proxyF5.doubleValue();//sout сработал
        proxyF5.doubleValue();//sout молчит
        Assertions.assertEquals(StorageCache.getStorageCash().get(0).getDoubleCacheValue(),StorageCache.getCache(0));

        proxyF5.setNum(5);
        proxyF5.doubleValue();//sout сработал
        proxyF5.doubleValue();//sout молчит
        Assertions.assertEquals(StorageCache.getStorageCash().get(1).getDoubleCacheValue(),StorageCache.getCache(1));

        proxyF5.setNum(2);
        proxyF5.doubleValue();//sout молчит
        proxyF5.doubleValue();//sout молчит

        Thread.sleep(1500);
        Assertions.assertThrows(NullPointerException.class,()->StorageCache.getStorageCash().get(0).getDoubleCacheValue());
        proxyF5.doubleValue();// sout сработал
        proxyF5.doubleValue();//sout молчит



        /*Thread testThread = Thread.currentThread();
        testThread.join(1000);*/

       /* proxyF5.doubleValue();

        log.info(String.valueOf(Double.valueOf(proxyF5.doubleValue())));
        log.info(String.valueOf(Double.valueOf(proxyF5.doubleValue())));

        proxyF5.setNum(6);
        proxyF5.doubleValue();

        log.info(String.valueOf(Double.valueOf(proxyF5.doubleValue())));
        log.info(String.valueOf(Double.valueOf(proxyF5.doubleValue())));

        proxyF5.setNum(4);
        log.info(String.valueOf(Double.valueOf(proxyF5.doubleValue())));
        log.info(String.valueOf(Double.valueOf(proxyF5.doubleValue())));

        proxyF5.setNum(7);
        log.info(String.valueOf(Double.valueOf(proxyF5.doubleValue())));
        log.info(StorageCache.toStringCache());
        log.info(String.valueOf(Double.valueOf(proxyF5.doubleValue())));
        proxyF5.setNum(7);
        log.info(StorageCache.toStringCache());
        log.info(String.valueOf(Double.valueOf(proxyF5.doubleValue())));
        TimeUnit.MILLISECONDS.sleep(3000);
        log.info(StorageCache.toStringCache());

        proxyF5.setNum(10);
        log.info(String.valueOf(Double.valueOf(proxyF5.doubleValue())));
        log.info(StorageCache.toStringCache());
        Thread testThread = Thread.currentThread();
        testThread.join(1000);*/


    }
}

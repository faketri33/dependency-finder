package org.faketri.proxy;

import org.faketri.logger.BaseLoggerFactory;
import org.faketri.logger.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.function.Supplier;


public class GlobalProxyHandler implements InvocationHandler {

    private static boolean profile = true;

    private final Object target;
    private final Logger log;

    private GlobalProxyHandler(Object target){
        this.target = target;
        log = BaseLoggerFactory.getLogger(target.getClass());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!profile) return method.invoke(target, args);

        return smallProfiler(() -> {
            try {
                return method.invoke(target, args);
            } catch (IllegalAccessException e) {
                log.error("Method name {} exception {}", method, e.getMessage());
                //throw new RuntimeException(e);
            } catch (InvocationTargetException e){
                log.error("Method name {} exception {}", method, e.getTargetException());
            }
            return null;
        }, method.toString());
    }

    private Object smallProfiler(Supplier<Object> supplier, String methodName){
        log.debug("Start {} method {}",target.getClass().getSimpleName(), methodName);
        long t0 = System.currentTimeMillis();
        Object val = supplier.get();
        log.debug("End {} - time {} ms", target.getClass().getSimpleName(), System.currentTimeMillis() - t0);
        return val;
    }

    public static void disableProfiling(){
        profile = false;
    }

    public static void enableProfiling(){
        profile = true;
    }

    @SuppressWarnings("unchecked")
    public static <T> T newProxy(Object clazz, Class<?> interfaces) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(interfaces);
        return (T) Proxy.newProxyInstance(clazz.getClass().getClassLoader(), new Class[]{interfaces}, new GlobalProxyHandler(clazz));
    }
}

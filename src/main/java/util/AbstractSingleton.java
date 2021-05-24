package util;

import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractSingleton<T> {

    private final AtomicReference<T> atomicReference =
            new AtomicReference<T>();

    public T getInstance(){
        T ret = atomicReference.get();
        if (ret == null){
            synchronized (this){
                ret = newObj();
                atomicReference.set(ret);
            }
        }
        return ret;
    }
    protected abstract T newObj();
}
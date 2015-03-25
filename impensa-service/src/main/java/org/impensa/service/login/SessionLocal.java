/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.login;

/**
 *
 * @author manosahu
 */
public class SessionLocal <T > extends ThreadLocal<T>{

    @Override
    public T get() {
        
        return (T)super.get();
    }

    @Override
    protected T initialValue() {
        return (T)super.initialValue();
    }

    @Override
    public void remove() {
        super.remove();
    }

    
    @Override
    public void set(T value) {
        super.set(value);
    }


      
}

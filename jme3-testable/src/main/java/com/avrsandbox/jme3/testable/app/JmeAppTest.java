package com.avrsandbox.jme3.testable.app;

import com.jme3.app.SimpleApplication;
import com.avrsandbox.jme3.testable.Testable;

/**
 * A base implementation of the testable api for jMonkeyEngine Apps.
 *
 * @param <T> the genre of user data object
 * @author pavl_g
 */
public abstract class JmeAppTest<T> extends SimpleApplication implements Testable<T> {

    /**
     * Keeps track of the current test state.
     * True: if the current test is still running.
     * False: if the current test stops.
     * Default value: false, i.e: not active.
     */
    protected volatile boolean active = false;

    @Override
    public void launch(T userData) {
        active = true;
    }

    @Override
    public void destroy() {
        super.destroy();
        active = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }
}

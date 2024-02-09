package com.avrsandbox.jme3.testable.app.state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.avrsandbox.jme3.testable.Testable;

/**
 * A base implementation of the testable api for appstates.
 *
 * @param <T> the genre of user data object
 * @author pavl_g
 */
public abstract class JmeStateTest<T> extends BaseAppState implements Testable<T> {

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
    protected void cleanup(Application app) {
        active = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }
}

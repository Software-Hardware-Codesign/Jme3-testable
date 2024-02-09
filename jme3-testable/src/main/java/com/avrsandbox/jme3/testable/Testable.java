package com.avrsandbox.jme3.testable;

/**
 * The abstract layer for running and tracking the testables.
 *
 * @param <T> the genre of the user data object
 * @author pavl_g
 */
public interface Testable<T> {

    /**
     * Launches the test, setting {@link Testable#isActive()} to true.
     *
     * @param userData user data object to pass for the test, this could of any datatype
     */
    void launch(T userData);

    /**
     * Tests whether the test is still active.
     *
     * @return true if the test is active, false otherwise
     */
    boolean isActive();
}

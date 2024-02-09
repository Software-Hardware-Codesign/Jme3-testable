package com.avrsandbox.jme3.testable;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Annotation used for tagging some {@link com.avrsandbox.jme3.testable.Testable}s with some tags to be selectively launched
 * by {@link com.avrsandbox.jme3.testable.util.TestableExecutor}.
 *
 * @see com.avrsandbox.jme3.testable.util.TestableExecutor#launch(String[], Object, String[])
 * @see com.avrsandbox.jme3.testable.util.TestableExecutor#launch(String, Object, String[])
 * @see com.avrsandbox.jme3.testable.app.JmeAppTest
 * @see com.avrsandbox.jme3.testable.app.state.JmeStateTest
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TestableTags {
    String[] value();
}

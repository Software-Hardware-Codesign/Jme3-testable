# Jme3-testable API

This is an initial  implementation for the testable api, targeting issue #1649, which suggests building a better test chooser backend for jme3-examples, where one can pickup which test cases to run inside a package.

This is a video demonstrating testables in action: 

[![Video](https://user-images.githubusercontent.com/60224159/170361202-c6c75a54-4197-446a-a653-fbe2fb2b7e7d.jpg)](https://www.youtube.com/watch?v=u9biai9Yg38)

## F.A.Q: 

**How can I make my own test?**: 
A: Your test should `extends JmeTestApp` or `JmeTestState`, in either cases, you will override `launch()` method and add what you want to test there, whether a `JmeApplication.attachState(this); for AppStates` or `setting up AppSettings and start()` or `setting up a JmeSurfaceView for android`. 

**Does this approach provide a recursive way of opening a package?**
A; Yep, all you need to do, is to give the package, which can be done by 2 methods : 
METHOD-A : 
```java
 TestableExecutor.execute("jme3test", "--No-Data--", signatures);
```
METHOD-B : 
```java
 TestableExecutor.execute(Main.class.getPackage().getName(), "--No-Data--", signatures);
```
**Can i test multiple packages that aren't on the same tree, at the same time?**
A: Yep, just use : 
```java
TestableExecutor.execute(new String[] {"jme3test.app", "jme3test.animation"}, "--No-Data--", signatures);
```
**Can i use multiple signatures in the same `@Test` annoation?**
A: Yep, use this : 
```java
@Annotations.Test(signatures = {Launcher.SIG_WATER_FILTERS, Launcher.SIG_ALL})
```
But, no `@Test` repetition...it complicates simple stuff and not clean anyway.

**What will happen if i have non-class files on my package**
A: Don't worry, the utility skips those.

**What will happen if i have other classes not implementing the Testable?** 
A: Those are skipped too.
 
**Can i use this on android?**
A: Theoretically, you can, but i haven't found the time to test that yet.

**What will happen if i extended JmeTestApp and didn't add a `@Test` with a signature?**
A: The utility will exclude this test, because how should it know 'is it ready to be executed?', if you want to run all Testables under a package then use a unified signature in all those classes, for example, add this to all the testables you want to run `Launcher.SIG_ALL` while you can still use other signatures to filter your tests for other run configurations : 
```java
@Annotations.Test(signatures = {Launcher.SIG_WATER_FILTERS, Launcher.SIG_ALL})
``` 
 
I think that is it, for now, if that is what you are seeking, then i hope it will be helpful, please give a quick review.

## Examples:
1) The Testable class : 
```java
/*
 * Copyright (c) 2009-2021 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
package jme3test.app.test;

import com.jme3.system.AppSettings;
import com.jme3.testable.impl.JmeAppTest;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.Annotations;
import jme3test.Launcher;

/**
 * Test a bare-bones application, without SimpleApplication.
 */
@Annotations.TestableTags({Launcher.SIG_ALL})
public class BareBonesAppTest extends JmeAppTest<AppSettings> {

    private Geometry boxGeom;

    public static void main(String[] args){
        AppSettings settings = new AppSettings(true);
        settings.setRenderer(AppSettings.LWJGL_OPENGL2);
        settings.setAudioRenderer(AppSettings.LWJGL_OPENAL);
        new BareBonesAppTest().launch(settings);
    }


    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);
        boxGeom.rotate(tpf * 2, tpf * 4, tpf * 3);
    }

    @Override
    public void simpleInitApp() {

        // create a box
        boxGeom = new Geometry("Box", new Box(2, 2, 2));

        // load some default material
        boxGeom.setMaterial(assetManager.loadMaterial("Interface/Logo/Logo.j3m"));

        // attach box to display in primary viewport
        rootNode.attachChild(boxGeom);
    }


    @Override
    public void launch(AppSettings userData) {
        super.launch(userData);
        setSettings(userData);
        setShowSettings(false);
        start();
    }
}
```
2) The Launcher (main class) : 
```java
package jme3test;

import com.jme3.system.AppSettings;
import com.jme3.util.TestableExecutor;
import java.lang.reflect.InvocationTargetException;

/**
 * Tests the new jme test api.
 *
 * @author pavl_g.
 */
public class Launcher {
    public static final String SIG_ALL = "ALL";
    public static final String SIG_WATER_FILTERS = "WATER-FILTER";
    public static final String PBR = "PBR";

    public static final String[] signatures = new String[] {
            SIG_ALL
    };

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        final AppSettings settings = new AppSettings(true);
        settings.setRenderer(AppSettings.LWJGL_OPENGL2);
        settings.setAudioRenderer(AppSettings.LWJGL_OPENAL);
        TestableExecutor.getInstance().launch(new String[] {"jme3test"}, settings, signatures);
    }
}
```

// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.flexiblemovement;

import org.junit.Test;

public class FlyingMovementTest extends FlexibleMovementTestingEnvironment {
    @Test
    public void simpleWall() throws InterruptedException {
        runTest(new String[]{
                "XXX|XXX|XXX",
                "   |   |XXX",
                "XXX|XXX|XXX",
        }, new String[]{
                "?  |   |   ",
                "   |   |   ",
                "  !|   |   "
        });
    }

    private void runTest(String[] world, String[] path) {
        executeExample(world, path, "flying");
    }
}

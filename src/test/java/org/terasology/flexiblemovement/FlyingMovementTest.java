// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.flexiblemovement;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.terasology.moduletestingenvironment.MTEExtension;
import org.terasology.moduletestingenvironment.extension.Dependencies;

@ExtendWith(MTEExtension.class)
@Dependencies({"FlexibleMovement", "CoreAssets"})
@Tag("MteTest")
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

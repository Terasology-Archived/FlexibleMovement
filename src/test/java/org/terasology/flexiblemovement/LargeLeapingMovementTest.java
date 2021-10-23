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
public class LargeLeapingMovementTest extends FlexibleMovementTestingEnvironment {
    @Test
    public void smokeTest() throws InterruptedException {
        runTest(new String[]{
                "XXX XXX|XXX XXX|XXX XXX",
                "XXXXXXX|XXXXXXX|XXXXXXX",
                "XXX XXX|XXX XXX|XXX XXX",
                "XXXXXXX|XXXXXXX|XXXXXXX",
                "XXXXXXX|XXXXXXX|XXXXXXX",
                "XXXXXXX|XXXXXXX|XXXXXXX"
        }, new String[]{
                "       |       |       ",
                "       | ?   ! |       ",
                "       |       |       ",
                "       |       |       ",
                "       |       |       ",
                "       |       |       "
        });
    }

    @Test
    public void steps() {
        runTest(new String[]{
                "XXX      |XXXXX    |XXXXXXX  |XXXXXXXXX",
                "XXX      |XXXXX    |XXXXXXX  |XXXXXXXXX",
                "XXX      |XXXXX    |XXXXXXX  |XXXXXXXXX",
        }, new String[]{
                "         |         |         |         ",
                "         | ?       | 12!     |         ",
                "         |         |         |         ",
        });
    }

    private void runTest(String[] world, String[] path) {
        executeExample(world, path, 2.7f, 1.2f, "walking", "leaping");
    }
}

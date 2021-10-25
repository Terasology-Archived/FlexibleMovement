// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.flexiblemovement;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.terasology.moduletestingenvironment.extension.Dependencies;

@Dependencies("FlexibleMovement")
@Tag("MteTest")
public class SwimmingWalkingLeapingMovementTest extends FlexibleMovementTestingEnvironment {
    @Test
    public void simpleLeap() throws InterruptedException {
        runTest(new String[]{
                "~  |   ",
                "~  |XXX"
        }, new String[]{
                "?  |   ",
                "1  |23!"
        });
    }

    @Test
    public void threeDimensionalMoves() throws InterruptedException {
        runTest(new String[]{
                "~~~|~~ |   ",
                "~ ~|~~~| XX",
                "~~~| ~ | XX"
        }, new String[]{
                "?  |   |   ",
                "   | 1 |   ",
                "   |   |  !"
        });
    }

    private void runTest(String[] world, String[] path) {
        executeExample(world, path, "walking", "leaping", "swimming");
    }
}

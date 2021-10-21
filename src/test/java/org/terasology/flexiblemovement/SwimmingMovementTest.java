// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.flexiblemovement;

import org.junit.jupiter.api.Test;

public class SwimmingMovementTest extends FlexibleMovementTestingEnvironment {
    @Test
    public void simpleStraight() throws InterruptedException {
        runTest(new String[]{
                "~  ",
                "~  ",
                "~~~",
        }, new String[]{
                "?  ",
                "1  ",
                "!  "
        });
    }

    @Test
    public void simpleLeap() throws InterruptedException {
        runTest(new String[]{
                "~  |~~~|~~~",
                "~  |~~~|~~~",
                "~~~|~~~|~~~",
        }, new String[]{
                "?  |123|~~~",
                "   |  !|~~~",
                "   |   |~~~"
        });
    }

    @Test
    public void simpleDiagonal() throws InterruptedException {
        runTest(new String[]{
                "~  |~  ",
                "~  |~  ",
                "~~~|~~~"
        }, new String[]{
                "?  |   ",
                "1  |   ",
                "23!|   "
        });
    }

    @Test
    public void corridor() throws InterruptedException {
        runTest(new String[]{
                "~~~~~~~~~~~~~~~",
                "~            ~~",
                "~ ~~~~~~~~~~~~~",
                "~~~            ",
                "               ",
        }, new String[]{
                "?123456789abcd ",
                "             e ",
                "  qponmlkjihgf ",
                "  !            ",
                "               ",
        });

    }

    @Test
    public void threeDimensionalMoves() throws InterruptedException {
        runTest(new String[]{
                "~~~|~~ |   ",
                "~ ~|~~~| ~~",
                "~~~| ~ | ~~"
        }, new String[]{
                "?  |   |   ",
                "   | 1 |   ",
                "   |   |  !"
        });
    }

    private void runTest(String[] world, String[] path) {
        executeExample(world, path, "swimming");
    }
}

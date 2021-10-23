// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.flexiblemovement;

import org.junit.jupiter.api.Test;

@ExtendWith(MTEExtension.class)
@Dependencies("FlexibleMovement", "CoreAssets")
@Tag("MteTest")
public class WalkingLeapingMovementTest extends FlexibleMovementTestingEnvironment {
    @Test
    public void simpleStraight() throws InterruptedException {
        runTest(new String[]{
                "X  ",
                "X  ",
                "XXX",
        }, new String[]{
                "?  ",
                "1  ",
                "!  "
        });
    }

    @Test
    public void simpleLeap() throws InterruptedException {
        runTest(new String[]{
                "X  |XXX|XXX|XXX",
                "X  |XXX|XXX|XXX",
        }, new String[]{
                "?  |123|XXX|XXX",
                "   |  !|XXX|XXX",
        });
    }

    @Test
    public void simpleDiagonal() throws InterruptedException {
        runTest(new String[]{
                "X  |X  ",
                "X  |X  ",
                "XXX|XXX"
        }, new String[]{
                "?  |   ",
                "1  |   ",
                "23!|   "
        });
    }

    @Test
    public void corridor() throws InterruptedException {
        runTest(new String[]{
                "XXXXXXXXXXXXXXX",
                "X            XX",
                "X XXXXXXXXXXXXX",
                "XXX            ",
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
                "XXX|XX |   ",
                "X X|XXX| XX",
                "XXX| X | XX"
        }, new String[]{
                "?  |   |   ",
                "   | 1 |   ",
                "   |   |  !"
        });
    }

    @Test
    public void jumpOver() throws InterruptedException {
        runTest(new String[]{
                "X X|XXX|XXX|XXX"
        }, new String[]{
                "? !|123|   |   "
        });
    }

    private void runTest(String[] world, String[] path) {
        executeExample(world, path, "walking", "leaping");
    }
}

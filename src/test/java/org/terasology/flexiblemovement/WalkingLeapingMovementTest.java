// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.flexiblemovement;

import org.junit.jupiter.api.Test;
import com.google.common.collect.Sets;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.engine.entitySystem.entity.EntityManager;
import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.engine.logic.characters.CharacterMovementComponent;
import org.terasology.engine.logic.characters.CharacterTeleportEvent;
import org.terasology.engine.logic.location.LocationComponent;
import org.terasology.engine.physics.engine.PhysicsEngine;
import org.terasology.engine.registry.In;
import org.terasology.engine.world.WorldProvider;
import org.terasology.engine.world.block.Block;
import org.terasology.engine.world.block.BlockManager;
import org.terasology.engine.world.block.BlockRegion;
import org.terasology.engine.world.block.BlockRegionc;
import org.terasology.engine.world.block.Blocks;
import org.terasology.moduletestingenvironment.MTEExtension;
import org.terasology.moduletestingenvironment.ModuleTestingHelper;
import org.terasology.moduletestingenvironment.extension.Dependencies;

@ExtendWith(MTEExtension.class)
@Dependencies({"FlexibleMovement", "CoreAssets"})
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

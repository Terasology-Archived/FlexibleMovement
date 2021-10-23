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

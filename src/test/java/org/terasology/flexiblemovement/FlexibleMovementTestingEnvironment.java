// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.flexiblemovement;

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
@Dependencies("FlexibleMovement")
@Tag("MteTest")
public class FlexibleMovementTestingEnvironment {
    private static final Logger logger = LoggerFactory.getLogger(FlexibleMovementTestingEnvironment.class);

    @In
    private ModuleTestingHelper helper;

    public void executeFailingExample(String[] world, String[] path) {
        // do nothing
    }

    public void executeExample(String[] world, String[] path, String... movementTypes) {
        executeExample(world, path, 0.9f, 0.3f, movementTypes);

    }

    public void executeExample(String[] world, String[] path, float height, float radius, String... movementTypes) {
        int airHeight = 41;

        WorldProvider worldProvider = helper.getHostContext().get(WorldProvider.class);
        Block air = helper.getHostContext().get(BlockManager.class).getBlock("engine:air");
        Block dirt = helper.getHostContext().get(BlockManager.class).getBlock("CoreAssets:dirt");
        Block water = helper.getHostContext().get(BlockManager.class).getBlock("CoreAssets:water");

        BlockRegionc extents = getPaddedExtents(world, airHeight);

        for (Vector3ic pos : extents) {
            helper.forceAndWaitForGeneration(pos);
        }

        for (Vector3ic pos : extents) {
            worldProvider.setBlock(pos, dirt);
        }

        // set blocks from world data
        for (int z = 0; z < world.length; z++) {
            int y = airHeight;
            String row = world[z];
            int x = 0;
            for (char c : row.toCharArray()) {
                switch (c) {
                    case 'X':
                        worldProvider.setBlock(new Vector3i(x, y, z), air);
                        x += 1;
                        break;
                    case ' ':
                        worldProvider.setBlock(new Vector3i(x, y, z), dirt);
                        x += 1;
                        break;
                    case '~':
                        worldProvider.setBlock(new Vector3i(x, y, z), water);
                        x += 1;
                        break;
                    case '|':
                        y += 1;
                        x = 0;
                        break;
                    default:
                        x += 1;
                        break;
                }
            }
        }

        // find start and goal positions from path data
        Vector3i start = new Vector3i();
        Vector3i stop = new Vector3i();
        for (int z = 0; z < path.length; z++) {
            int y = airHeight;
            String row = path[z];
            int x = 0;
            for (char c : row.toCharArray()) {
                switch (c) {
                    case '?':
                        start.set(x, y, z);
                        x += 1;
                        break;
                    case '!':
                        stop.set(x, y, z);
                        x += 1;
                        break;
                    case '|':
                        y += 1;
                        x = 0;
                        break;
                    default:
                        x += 1;
                        break;
                }
            }
        }

        EntityRef entity = helper.getHostContext().get(EntityManager.class).create("flexiblemovement:testcharacter");
        entity.send(new CharacterTeleportEvent(new Vector3f(start)));
        entity.getComponent(FlexibleMovementComponent.class).setPathGoal(stop);
        entity.getComponent(FlexibleMovementComponent.class).movementTypes.clear();
        entity.getComponent(FlexibleMovementComponent.class).movementTypes.addAll(Sets.newHashSet(movementTypes));

        entity.getComponent(CharacterMovementComponent.class).height = height;
        entity.getComponent(CharacterMovementComponent.class).radius = radius;

        // after updating character collision stuff we have to remake the collider, based on the playerHeight command
        // TODO there should probably be a helper for this instead
        helper.getHostContext().get(PhysicsEngine.class).removeCharacterCollider(entity);
        helper.getHostContext().get(PhysicsEngine.class).getCharacterCollider(entity);

        helper.runUntil(() -> Blocks.toBlockPos(entity.getComponent(LocationComponent.class)
                .getWorldPosition(new Vector3f())).distance(start) == 0);

        helper.runWhile(() -> {
            Vector3f pos = entity.getComponent(LocationComponent.class).getWorldPosition(new Vector3f());
            logger.warn("pos: {}", pos);
            return Blocks.toBlockPos(pos).distance(stop) > 0;
        });
    }

    private BlockRegionc getPaddedExtents(String[] world, int airHeight) {

        BlockRegion extents = new BlockRegion(new Vector3i(0, airHeight, 0));
        for (int z = 0; z < world.length; z++) {
            int y = airHeight;
            String row = world[z];
            int x = 0;
            for (char c : row.toCharArray()) {
                extents.expand(new Vector3i(x, y, z)); // TODO!
                switch (c) {
                    case 'X':
                        x += 1;
                        break;
                    case ' ':
                        x += 1;
                        break;
                    case '|':
                        y += 1;
                        x = 0;
                        break;
                    default:
                        x += 1;
                        break;
                }
            }
        }
        extents.expand(1, 1, 1);
        return extents;
    }
}

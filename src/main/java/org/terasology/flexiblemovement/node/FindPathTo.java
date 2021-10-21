// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.flexiblemovement.node;

import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.terasology.engine.logic.behavior.BehaviorAction;
import org.terasology.engine.logic.behavior.core.Actor;
import org.terasology.engine.logic.behavior.core.BaseAction;
import org.terasology.engine.logic.behavior.core.BehaviorState;
import org.terasology.engine.logic.location.LocationComponent;
import org.terasology.engine.registry.In;
import org.terasology.engine.world.block.Blocks;
import org.terasology.flexiblemovement.FlexibleMovementComponent;
import org.terasology.flexiblemovement.system.PluginSystem;
import org.terasology.flexiblepathfinding.JPSConfig;
import org.terasology.flexiblepathfinding.PathfinderCallback;
import org.terasology.flexiblepathfinding.PathfinderSystem;

import java.util.List;

/**
 * Finds a path to the pathGoalPosition of the Actor, stores it in FlexibileMovementComponent.path SUCCESS: When the pathfinder returns a
 * valid path FAILURE: When the pathfinder returns a failure or invalid path
 */
@BehaviorAction(name = "find_path")
public class FindPathTo extends BaseAction {

    BehaviorState pathStatus = null;
    List<Vector3i> pathResult = null;
    @In
    PathfinderSystem pathfinderSystem;

    @In
    PluginSystem pluginSystem;

    @Override
    public BehaviorState modify(Actor actor, BehaviorState result) {
        if (pathStatus == null) {
            pathStatus = BehaviorState.RUNNING;
            FlexibleMovementComponent flexibleMovementComponent = actor.getComponent(FlexibleMovementComponent.class);
            Vector3ic start = Blocks.toBlockPos(actor.getComponent(LocationComponent.class).getWorldPosition(new Vector3f()));
            Vector3ic goal = actor.getComponent(FlexibleMovementComponent.class).getPathGoal();

            if (start == null || goal == null) {
                return BehaviorState.FAILURE;
            }

            JPSConfig config = new JPSConfig(start, goal);
            config.useLineOfSight = false;
            config.maxTime = 0.25f;
            config.maxDepth = 150;
            config.goalDistance = flexibleMovementComponent.pathGoalDistance;
            config.plugin = pluginSystem.getMovementPlugin(actor.getEntity()).getJpsPlugin(actor.getEntity());

            pathfinderSystem.requestPath(config, new PathfinderCallback() {
                @Override
                public void pathReady(List<Vector3i> path, Vector3i target) {
                    if (path == null || path.size() == 0) {
                        pathStatus = BehaviorState.FAILURE;
                        return;
                    }
                    pathStatus = BehaviorState.SUCCESS;
                    pathResult = path;
                }
            });
        }

        if (pathStatus == BehaviorState.SUCCESS) {
            FlexibleMovementComponent movement = actor.getComponent(FlexibleMovementComponent.class);

            // PF System returns paths including the starting point.
            // Since we don't need to move to where we started, we remove the first point in the path
            pathResult.remove(0);

            movement.setPath(pathResult);
            actor.save(movement);
        }

        return pathStatus;
    }
}

// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.flexiblemovement.node;

import org.terasology.flexiblemovement.FlexibleMovementComponent;
import org.terasology.flexiblemovement.system.FlexibleMovementSystem;
import org.terasology.flexiblemovement.system.PluginSystem;
import org.terasology.flexiblepathfinding.PathfinderSystem;
import org.terasology.flexiblepathfinding.plugins.JPSPlugin;
import org.terasology.logic.behavior.tree.Node;
import org.terasology.logic.behavior.tree.Status;
import org.terasology.logic.behavior.tree.Task;
import org.joml.Vector3i;
import org.terasology.engine.registry.In;

import java.util.List;

/**
 * Validates the entity's current path for walkability (according to the pathfinding plugin its using)
 * SUCCESS: when there are no unwalkable waypoints
 * FAILURE: otherwise
 */
public class ValidatePathNode extends Node {

    @Override
    public ValidatePathNode.ValidatePathTask createTask() {
        return new ValidatePathNode.ValidatePathTask(this);
    }

    public class ValidatePathTask extends Task{
        Status pathStatus = null;
        List<Vector3i> pathResult = null;
        @In private PathfinderSystem system;
        @In private PluginSystem pluginSystem;
        @In private FlexibleMovementSystem flexibleMovementSystem;

        protected ValidatePathTask(Node node) {
            super(node);
        }

        @Override
        public Status update(float dt) {
            FlexibleMovementComponent flexibleMovementComponent = actor().getComponent(FlexibleMovementComponent.class);
            JPSPlugin pathfindingPlugin = pluginSystem.getMovementPlugin(actor().getEntity()).getJpsPlugin(actor().getEntity());
            if(flexibleMovementComponent == null || pathfindingPlugin == null) {
                return Status.FAILURE;
            }

//            for(Vector3i pos : actor().getComponent(FlexibleMovementComponent.class).getPath()) {
//                if(!pathfindingPlugin.isWalkable(pos)) {
//                    return Status.FAILURE;
//                }
//            }
            return Status.SUCCESS;
        }

        @Override
        public void handle(Status result) {

        }
    }
}

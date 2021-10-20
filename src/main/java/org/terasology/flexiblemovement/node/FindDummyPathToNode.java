// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.flexiblemovement.node;

import org.terasology.flexiblemovement.FlexibleMovementComponent;
import org.terasology.flexiblepathfinding.PathfinderSystem;
import org.terasology.logic.behavior.tree.Node;
import org.terasology.logic.behavior.tree.Status;
import org.terasology.logic.behavior.tree.Task;
import org.terasology.math.geom.Vector3i;
import org.terasology.engine.registry.In;
import org.terasology.engine.world.WorldProvider;

import java.util.Arrays;

/**
 * Constructs a dummy two-step path consisting of only the actor's current position and goal position
 * Meant as a cheap fallback when full pathing is not needed or possible
 * SUCCESS: Always
 */
public class FindDummyPathToNode extends Node {
    @Override
    public FindDummyPathToTask createTask() {
        return new FindDummyPathToTask(this);
    }
    public static class FindDummyPathToTask extends Task{
        @In
        PathfinderSystem system;

        @In
        private WorldProvider world;

        protected FindDummyPathToTask(Node node) {
            super(node);
        }

        @Override
        public Status update(float dt) {

            FlexibleMovementComponent movement = actor().getComponent(FlexibleMovementComponent.class);
            Vector3i goal = actor().getComponent(FlexibleMovementComponent.class).getPathGoal();

            if(goal == null) {
                return Status.FAILURE;
            }

            movement.setPath(Arrays.asList(goal));
            actor().save(movement);

            return Status.SUCCESS;
        }

        @Override
        public void handle(Status result) {

        }
    }
}

// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.flexiblemovement.plugin;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.terasology.engine.core.Time;
import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.engine.logic.characters.CharacterMoveInputEvent;
import org.terasology.engine.logic.characters.CharacterMovementComponent;
import org.terasology.engine.world.WorldProvider;
import org.terasology.flexiblepathfinding.plugins.JPSPlugin;
import org.terasology.flexiblepathfinding.plugins.basic.SwimmingPlugin;
import org.terasology.math.TeraMath;

public class SwimmingMovementPlugin extends MovementPlugin {
    public SwimmingMovementPlugin(WorldProvider world, Time time) {
        super(world, time);
    }

    public SwimmingMovementPlugin() {
        super();
    }

    @Override
    public JPSPlugin getJpsPlugin(EntityRef entity) {
        CharacterMovementComponent component = entity.getComponent(CharacterMovementComponent.class);
        return new SwimmingPlugin(getWorld(), component.radius * 2.0f, component.height);
    }

    @Override
    public CharacterMoveInputEvent move(EntityRef entity, Vector3fc dest, int sequence) {
        Vector3f delta = getDelta(entity, dest);
        float yaw = getYaw(delta);
        long dt = getTime().getGameDeltaInMs();
        float pitch = getPitch(delta);

        CharacterMovementComponent movement = entity.getComponent(CharacterMovementComponent.class);
        return new CharacterMoveInputEvent(sequence, pitch, yaw, delta, false, false, movement.grounded, dt);
    }

    private float getPitch(Vector3f delta) {
        return ((float) Math.atan2(delta.y, Math.hypot(delta.x, delta.z))) * TeraMath.RAD_TO_DEG + 180;
    }
}

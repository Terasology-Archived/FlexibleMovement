// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.flexiblemovement.system;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.engine.entitySystem.event.ReceiveEvent;
import org.terasology.engine.entitySystem.systems.*;
import org.terasology.flexiblemovement.FlexibleMovementComponent;
import org.terasology.engine.logic.characters.CharacterMoveInputEvent;
import org.terasology.engine.logic.characters.events.HorizontalCollisionEvent;
import org.terasology.engine.registry.Share;

import java.util.Map;


@Share(FlexibleMovementSystem.class)
@RegisterSystem(RegisterMode.AUTHORITY)
public class FlexibleMovementSystem extends BaseComponentSystem implements UpdateSubscriberSystem {
    private Map<EntityRef, CharacterMoveInputEvent> eventQueue = Maps.newHashMap();
    private Logger logger = LoggerFactory.getLogger(FlexibleMovementSystem.class);

    @Override
    public void update(float delta) {
        for(Map.Entry<EntityRef, CharacterMoveInputEvent> entry : eventQueue.entrySet()) {
            if(entry.getKey() != null && entry.getKey().exists()) {
                entry.getKey().send(entry.getValue());
            }
        }
        eventQueue.clear();
    }

    @ReceiveEvent
    public void markHorizontalCollision(HorizontalCollisionEvent event, EntityRef entity, FlexibleMovementComponent flexibleMovementComponent) {
        if(flexibleMovementComponent == null) {
            return;
        }

        flexibleMovementComponent.collidedHorizontally = true;
    }

    public void enqueue(EntityRef entity, CharacterMoveInputEvent event) {
        eventQueue.put(entity, event);
    }
}

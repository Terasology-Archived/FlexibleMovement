// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.flexiblemovement.plugin;

import com.google.common.collect.Lists;
import org.terasology.engine.core.Time;
import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.flexiblemovement.FlexibleMovementHelper;
import org.terasology.flexiblepathfinding.plugins.JPSPlugin;
import org.terasology.flexiblepathfinding.plugins.basic.CompositePlugin;
import org.terasology.engine.logic.characters.CharacterMoveInputEvent;
import org.terasology.engine.logic.location.LocationComponent;
import org.terasology.math.geom.Vector3f;
import org.terasology.math.geom.Vector3i;
import org.terasology.engine.world.WorldProvider;

import java.util.Collection;
import java.util.List;

public class CompositeMovementPlugin extends MovementPlugin {
    public List<MovementPlugin> getPlugins() {
        return plugins;
    }

    List<MovementPlugin> plugins = Lists.newArrayList();
    public CompositeMovementPlugin(WorldProvider world, Time time, MovementPlugin ... plugins) {
        super(world, time);
        this.plugins = Lists.newArrayList(plugins);
    }

    public CompositeMovementPlugin(WorldProvider worldProvider, Time time, Collection<MovementPlugin> plugins) {
        super(worldProvider, time);
        this.plugins = Lists.newArrayList(plugins);
    }

    @Override
    public JPSPlugin getJpsPlugin(EntityRef entity) {
        CompositePlugin jpsPlugin = new CompositePlugin();
        for (MovementPlugin plugin : plugins) {
            jpsPlugin.addPlugin(plugin.getJpsPlugin(entity));
        }
        return jpsPlugin;
    }

    @Override
    public CharacterMoveInputEvent move(EntityRef entity, Vector3f dest, int sequence) {
        Vector3i from = FlexibleMovementHelper.posToBlock(entity.getComponent(LocationComponent.class).getWorldPosition());
        Vector3i to = FlexibleMovementHelper.posToBlock(dest);
        for (MovementPlugin plugin : plugins) {
            if (plugin.getJpsPlugin(entity).isReachable(to, from)) {
                return plugin.move(entity, dest, sequence);
            }
        }
        return null;
    }
}

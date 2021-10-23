// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.flexiblemovement;

import org.terasology.math.geom.Vector3f;
import org.terasology.math.geom.Vector3i;

import java.math.RoundingMode;

/**
 * Miscellaneous helper methods
 */
public final class FlexibleMovementHelper {
    private FlexibleMovementHelper() { }

    public static Vector3i posToBlock(Vector3f pos) {
        return new Vector3i(pos, RoundingMode.HALF_UP);
    }
}

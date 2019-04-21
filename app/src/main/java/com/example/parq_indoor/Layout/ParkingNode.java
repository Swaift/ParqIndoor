package com.example.parq_indoor.Layout;

import java.util.EnumSet;
import java.util.List;

public class ParkingNode extends RectangleNode {
    public boolean isOccupied;

    public ParkingNode(
            int id,
            float left,
            float top,
            float right,
            float bottom,
            EnumSet<Side> paintBoundaries,
            EnumSet<Side> wallBoundaries,
            boolean isOccupied,
            List<Integer> adjacentIds)
    {
        super(
                id,
                left,
                top,
                right,
                bottom,
                paintBoundaries,
                wallBoundaries,
                adjacentIds);
        this.isOccupied = isOccupied;
    }
}

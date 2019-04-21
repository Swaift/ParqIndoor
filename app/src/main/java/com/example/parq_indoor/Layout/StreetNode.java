package com.example.parq_indoor.Layout;

import java.util.EnumSet;
import java.util.List;

public class StreetNode extends RectangleNode {
    public StreetNode(
            int id,
            float left,
            float top,
            float right,
            float bottom,
            EnumSet<Side> paintBoundaries,
            EnumSet<Side> wallBoundaries,
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
    }
}

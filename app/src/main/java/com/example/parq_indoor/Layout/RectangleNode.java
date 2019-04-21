package com.example.parq_indoor.Layout;

import android.graphics.Rect;

import java.util.EnumSet;
import java.util.List;

public class RectangleNode extends LayoutNode {
    public enum Side {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM
    }

    public Rect rect;
    public EnumSet<Side> paintBoundaries;
    public EnumSet<Side> wallBoundaries;
    public RectangleNode(
            int id,
            float left,
            float top,
            float right,
            float bottom,
            EnumSet<Side> paintBoundaries,
            EnumSet<Side> wallBoundaries,
            List<Integer> adjacentIds)
    {
        super(id, adjacentIds);
        rect = new Rect((int)left, (int)top, (int)right, (int)bottom);
        this.paintBoundaries = EnumSet.copyOf(paintBoundaries);
        this.wallBoundaries = EnumSet.copyOf(wallBoundaries);
    }
}

package com.example.parq_indoor.Layout;

import java.util.ArrayList;
import java.util.EnumSet;

public class GateNode extends RectangleNode {
    public boolean isEntrance;

    public GateNode(
            int id,
            float left,
            float top,
            float right,
            float bottom,
            EnumSet<Side> paintBoundaries,
            EnumSet<Side> wallBoundaries,
            boolean isEntrance)
    {
        super(
                id,
                left,
                top,
                right,
                bottom,
                paintBoundaries,
                wallBoundaries,
                new ArrayList<Integer>());
        this.isEntrance = isEntrance;
    }
}

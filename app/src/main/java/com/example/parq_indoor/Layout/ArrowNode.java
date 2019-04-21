package com.example.parq_indoor.Layout;

import java.util.ArrayList;

public class ArrowNode extends LayoutNode {
    public float startX;
    public float startY;
    public float endX;
    public float endY;

    public ArrowNode(
            int id,
            float startX,
            float startY,
            float endX,
            float endY)
    {
        super(id, new ArrayList<Integer>());
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }
}

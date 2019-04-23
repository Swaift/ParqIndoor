package com.example.parq_indoor.Layout;

import java.util.ArrayList;
import java.util.List;

public class LayoutGraph {
    public List<LayoutNode> nodes;

    public LayoutGraph() {
        nodes = new ArrayList<>();
    }

    public void buildEdges() {
        for (LayoutNode node: nodes) {
            node.buildAdjacentNodes(this);
        }
    }

    public LayoutNode findNodeById(int id) {
        for (LayoutNode layoutNode: nodes) {
            if (layoutNode.id == id) {
                return layoutNode;
            }
        }
        return null;
    }
}

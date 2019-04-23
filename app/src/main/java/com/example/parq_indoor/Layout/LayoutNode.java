package com.example.parq_indoor.Layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayoutNode {
    public int id;
    public float centerX;
    public float centerY;
    public Map<LayoutNode, Double> adjacentNodes;
    public List<Integer> adjacentIds;
    public List<LayoutNode> shortestPath;
    public double distance;

    public LayoutNode(int id, List<Integer> adjacentIds) {
        this.id = id;
        distance = Double.MAX_VALUE;
        shortestPath = new ArrayList<>();
        adjacentNodes = new HashMap<>();
        this.adjacentIds = new ArrayList<>(adjacentIds);
        centerX = 0;
        centerY = 0;
    }

    public void buildAdjacentNodes(LayoutGraph layoutGraph) {
        for (int adjacentId: adjacentIds) {
            LayoutNode adjacentNode = layoutGraph.findNodeById(adjacentId);
            // find distance
            double xDiff = adjacentNode.centerX - centerX;
            double yDiff = adjacentNode.centerY - centerY;
            double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
            adjacentNodes.put(adjacentNode, distance);
            continue;
        }
    }
}

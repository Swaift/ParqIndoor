package com.example.parq_indoor;

import com.example.parq_indoor.Layout.LayoutGraph;
import com.example.parq_indoor.Layout.LayoutNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PathFinder {
    public static List<LayoutNode> findPath(
            LayoutGraph layoutGraph,
            LayoutNode startNode,
            LayoutNode endNode)
    {
        // build path
        for (LayoutNode layoutNode: layoutGraph.nodes) {
            layoutNode.shortestPath.clear();
            layoutNode.distance = Double.MAX_VALUE;
        }

        List<LayoutNode> settledNodes = new ArrayList<>();
        List<LayoutNode> unsettledNodes = new ArrayList<>();

        startNode.distance = 0;
        unsettledNodes.add(startNode);

        while (unsettledNodes.size() != 0) {
            LayoutNode currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<LayoutNode, Double> entry: currentNode.adjacentNodes.entrySet()) {
                LayoutNode adjacentNode = entry.getKey();
                Double edgeWeight = entry.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
            if (currentNode == endNode) {
                break;
            }
        }

        endNode.shortestPath.add(endNode);
        return endNode.shortestPath;
    }

    private static LayoutNode getLowestDistanceNode(List<LayoutNode> unsettledNodes) {
        LayoutNode lowestDistanceNode = null;
        double lowestDistance = Double.MAX_VALUE;
        for (LayoutNode node: unsettledNodes) {
            double nodeDistance = node.distance;
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(
            LayoutNode evaluationNode,
            double edgeWeight,
            LayoutNode sourceNode)
    {
        double sourceDistance = sourceNode.distance;
        if (sourceDistance + edgeWeight < evaluationNode.distance) {
            evaluationNode.distance = sourceDistance + edgeWeight;
            List<LayoutNode> shortestPath = new ArrayList<>(sourceNode.shortestPath);
            shortestPath.add(sourceNode);
            evaluationNode.shortestPath = shortestPath;
        }
    }

}

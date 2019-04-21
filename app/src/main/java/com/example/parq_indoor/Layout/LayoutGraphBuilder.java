package com.example.parq_indoor.Layout;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

public class LayoutGraphBuilder {
    public static LayoutGraph buildLayoutGraphFromJson(Context context, int resourceId) {
        LayoutGraph layoutGraph = new LayoutGraph();

        InputStream inputStream = context.getResources().openRawResource(resourceId);
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        String jsonString = scanner.hasNext() ? scanner.next() : "";

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray streetNodesJsonArray = jsonObject.getJSONArray("StreetNodes");
            JSONArray parkingNodesJsonArray = jsonObject.getJSONArray("ParkingNodes");
            JSONArray gateNodesJsonArray = jsonObject.getJSONArray("GateNodes");
            JSONArray arrowNodesJsonArray = jsonObject.getJSONArray("ArrowNodes");

            List<StreetNode> streetNodes = parseStreetNodes(streetNodesJsonArray);
            List<ParkingNode> parkingNodes = parseParkingNodes(parkingNodesJsonArray);
            List<GateNode> gateNodes = parseGateNodes(gateNodesJsonArray);
            List<ArrowNode> arrowNodes = parseArrowNodes(arrowNodesJsonArray);

            layoutGraph.nodes.addAll(streetNodes);
            layoutGraph.nodes.addAll(parkingNodes);
            layoutGraph.nodes.addAll(gateNodes);
            layoutGraph.nodes.addAll(arrowNodes);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        layoutGraph.buildEdges();

        return layoutGraph;
    }

    private static List<StreetNode> parseStreetNodes(JSONArray jsonArray) {
        List<StreetNode> streetNodes = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("id");
                float left = (float) jsonObject.getDouble("left");
                float top = (float) jsonObject.getDouble("top");
                float right = (float) jsonObject.getDouble("right");
                float bottom = (float) jsonObject.getDouble("bottom");

                EnumSet<RectangleNode.Side> paintBoundaries = EnumSet.noneOf(RectangleNode.Side.class);
                JSONArray paintBoundariesJsonArray = jsonObject.getJSONArray("paintBoundaries");
                for (int j = 0; j < paintBoundariesJsonArray.length(); j++) {
                    String sideString = paintBoundariesJsonArray.getString(j);
                    RectangleNode.Side side = RectangleNode.Side.valueOf(sideString);
                    paintBoundaries.add(side);
                }

                EnumSet<RectangleNode.Side> wallBoundaries = EnumSet.noneOf(RectangleNode.Side.class);
                JSONArray wallBoundariesJsonArray = jsonObject.getJSONArray("wallBoundaries");
                for (int j = 0; j < wallBoundariesJsonArray.length(); j++) {
                    String sideString = wallBoundariesJsonArray.getString(j);
                    RectangleNode.Side side = RectangleNode.Side.valueOf(sideString);
                    wallBoundaries.add(side);
                }

                List<Integer> adjacentIds = new ArrayList<>();
                JSONArray adjacentIdsJsonArray = jsonObject.getJSONArray("adjacentIds");
                for (int j = 0; j < adjacentIdsJsonArray.length(); j++) {
                    int adjacentId = adjacentIdsJsonArray.getInt(j);
                    adjacentIds.add(adjacentId);
                }

                StreetNode streetNode = new StreetNode(
                        id,
                        left,
                        top,
                        right,
                        bottom,
                        paintBoundaries,
                        wallBoundaries,
                        adjacentIds);

                streetNodes.add(streetNode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return streetNodes;
    }

    private static List<ParkingNode> parseParkingNodes(JSONArray jsonArray) {
        List<ParkingNode> parkingNodes = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("id");
                float left = (float) jsonObject.getDouble("left");
                float top = (float) jsonObject.getDouble("top");
                float right = (float) jsonObject.getDouble("right");
                float bottom = (float) jsonObject.getDouble("bottom");

                EnumSet<RectangleNode.Side> paintBoundaries = EnumSet.noneOf(RectangleNode.Side.class);
                JSONArray paintBoundariesJsonArray = jsonObject.getJSONArray("paintBoundaries");
                for (int j = 0; j < paintBoundariesJsonArray.length(); j++) {
                    String sideString = paintBoundariesJsonArray.getString(j);
                    RectangleNode.Side side = RectangleNode.Side.valueOf(sideString);
                    paintBoundaries.add(side);
                }

                EnumSet<RectangleNode.Side> wallBoundaries = EnumSet.noneOf(RectangleNode.Side.class);
                JSONArray wallBoundariesJsonArray = jsonObject.getJSONArray("wallBoundaries");
                for (int j = 0; j < wallBoundariesJsonArray.length(); j++) {
                    String sideString = wallBoundariesJsonArray.getString(j);
                    RectangleNode.Side side = RectangleNode.Side.valueOf(sideString);
                    wallBoundaries.add(side);
                }

                List<Integer> adjacentIds = new ArrayList<>();
                JSONArray adjacentIdsJsonArray = jsonObject.getJSONArray("adjacentIds");
                for (int j = 0; j < adjacentIdsJsonArray.length(); j++) {
                    int adjacentId = adjacentIdsJsonArray.getInt(j);
                    adjacentIds.add(adjacentId);
                }

                // unique for parking nodes
                boolean isOccupied = jsonObject.getBoolean("isOccupied");

                ParkingNode parkingNode = new ParkingNode(
                        id,
                        left,
                        top,
                        right,
                        bottom,
                        paintBoundaries,
                        wallBoundaries,
                        isOccupied,
                        adjacentIds);

                parkingNodes.add(parkingNode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return parkingNodes;
    }

    private static List<GateNode> parseGateNodes(JSONArray jsonArray) {
        List<GateNode> gateNodes = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("id");
                float left = (float) jsonObject.getDouble("left");
                float top = (float) jsonObject.getDouble("top");
                float right = (float) jsonObject.getDouble("right");
                float bottom = (float) jsonObject.getDouble("bottom");

                EnumSet<RectangleNode.Side> paintBoundaries = EnumSet.noneOf(RectangleNode.Side.class);
                JSONArray paintBoundariesJsonArray = jsonObject.getJSONArray("paintBoundaries");
                for (int j = 0; j < paintBoundariesJsonArray.length(); j++) {
                    String sideString = paintBoundariesJsonArray.getString(j);
                    RectangleNode.Side side = RectangleNode.Side.valueOf(sideString);
                    paintBoundaries.add(side);
                }

                EnumSet<RectangleNode.Side> wallBoundaries = EnumSet.noneOf(RectangleNode.Side.class);
                JSONArray wallBoundariesJsonArray = jsonObject.getJSONArray("wallBoundaries");
                for (int j = 0; j < wallBoundariesJsonArray.length(); j++) {
                    String sideString = wallBoundariesJsonArray.getString(j);
                    RectangleNode.Side side = RectangleNode.Side.valueOf(sideString);
                    wallBoundaries.add(side);
                }

                // unique for gate nodes: isEntrance, no adjacents
                boolean isEntrance = jsonObject.getBoolean("isEntrance");

                GateNode gateNode = new GateNode(
                        id,
                        left,
                        top,
                        right,
                        bottom,
                        paintBoundaries,
                        wallBoundaries,
                        isEntrance);

                gateNodes.add(gateNode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return gateNodes;
    }

    private static List<ArrowNode> parseArrowNodes(JSONArray jsonArray) {
        List<ArrowNode> arrowNodes = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("id");

                // unique for arrow nodes
                float startX = (float) jsonObject.getDouble("startX");
                float startY = (float) jsonObject.getDouble("startY");
                float endX = (float) jsonObject.getDouble("endX");
                float endY = (float) jsonObject.getDouble("endY");

                ArrowNode arrowNode = new ArrowNode(
                        id,
                        startX,
                        startY,
                        endX,
                        endY);

                arrowNodes.add(arrowNode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return arrowNodes;
    }
}

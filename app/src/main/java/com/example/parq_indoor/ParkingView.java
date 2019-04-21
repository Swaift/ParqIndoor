package com.example.parq_indoor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.parq_indoor.Layout.ArrowNode;
import com.example.parq_indoor.Layout.GateNode;
import com.example.parq_indoor.Layout.LayoutGraph;
import com.example.parq_indoor.Layout.LayoutNode;
import com.example.parq_indoor.Layout.ParkingNode;
import com.example.parq_indoor.Layout.RectangleNode;
import com.example.parq_indoor.Layout.StreetNode;

import java.util.List;

public class ParkingView extends View {
    private LayoutGraph layoutGraph;
    private Paint streetNodePaint;
    private Paint unoccupiedParkingNodePaint;
    private Paint occupiedParkingNodePaint;
    private Paint arrowNodePaint;
    private Paint paintBoundaryPaint;
    private Paint wallBoundaryPaint;
    private Paint occupiedSymbolRedPaint;
    private Paint occupiedSymbolWhitePaint;
    private float minX;
    private float maxX;
    private float minY;
    private float maxY;
    private final float PAINT_THICKNESS = 5; // dp
    private final float WALL_THICKNESS = 15; // dp
    private final float OCCUPIED_SYMBOL_RADIUS = 12; // dp
    private final float OCCUPIED_SYMBOL_RECTANGLE_RADIUS = 3;

    public ParkingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParkingView(Context context) {
        super(context);
        init();
    }

    private void init() {
        minX = getPaddingLeft();
        maxX = getWidth() - getPaddingRight();
        minY = getPaddingTop();
        maxY = getHeight() - getPaddingBottom();

        layoutGraph = new LayoutGraph();

        streetNodePaint = new Paint();
        streetNodePaint.setColor(Color.GRAY);
        streetNodePaint.setStyle(Paint.Style.FILL);
        streetNodePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        unoccupiedParkingNodePaint = new Paint();
        unoccupiedParkingNodePaint.setColor(Color.GREEN);
        unoccupiedParkingNodePaint.setStyle(Paint.Style.FILL);
        unoccupiedParkingNodePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        occupiedParkingNodePaint = new Paint();
        occupiedParkingNodePaint.setColor(Color.RED);
        occupiedParkingNodePaint.setStyle(Paint.Style.FILL);
        occupiedParkingNodePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        arrowNodePaint = new Paint();
        arrowNodePaint.setColor(Color.WHITE);
        arrowNodePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        paintBoundaryPaint = new Paint();
        paintBoundaryPaint.setColor(Color.YELLOW);
        paintBoundaryPaint.setStyle(Paint.Style.FILL);
        paintBoundaryPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        wallBoundaryPaint = new Paint();
        wallBoundaryPaint.setColor(Color.DKGRAY);
        wallBoundaryPaint.setStyle(Paint.Style.FILL);
        wallBoundaryPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        occupiedSymbolRedPaint = new Paint();
        occupiedSymbolRedPaint.setColor(Color.RED);
        occupiedSymbolRedPaint.setStyle(Paint.Style.FILL);
        occupiedSymbolRedPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        occupiedSymbolWhitePaint = new Paint();
        occupiedSymbolWhitePaint.setColor(Color.WHITE);
        occupiedSymbolWhitePaint.setStyle(Paint.Style.FILL);
        occupiedSymbolWhitePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    public void setLayoutGraph(LayoutGraph layoutGraph) {
        this.layoutGraph = layoutGraph;
    }

    // resize rect to be in px instead of dp
    // reposition rect to fit inside canvas padding
    private Rect adjustRectForCanvas(Rect oldRect) {
        Rect newRect = new Rect(oldRect);

        newRect.left = (int) (Utils.dpToPx(oldRect.left, getContext()) + minX);
        newRect.top = (int) (Utils.dpToPx(oldRect.top, getContext()) + minY);
        newRect.right = (int) (Utils.dpToPx(oldRect.right, getContext()) + minX);
        newRect.bottom = (int) (Utils.dpToPx(oldRect.bottom, getContext()) + minY);

        return newRect;
    }

    /**
     * https://stackoverflow.com/questions/6713757/how-do-i-draw-an-arrowhead-in-android
     */
    private void drawArrow(
            Paint paint,
            Canvas canvas,
            float from_x,
            float from_y,
            float to_x,
            float to_y)
    {
        float angle,anglerad, radius, lineangle;

        //values to change for other appearance *CHANGE THESE FOR OTHER SIZE ARROWHEADS*
        radius=15;
        angle=45;

        //some angle calculations
        anglerad= (float) (Math.PI*angle/180.0f);
        lineangle= (float) (Math.atan2(to_y-from_y,to_x-from_x));

        //tha line
        canvas.drawLine(from_x,from_y,to_x,to_y,paint);

        //tha triangle
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(to_x, to_y);
        path.lineTo((float)(to_x-radius*Math.cos(lineangle - (anglerad / 2.0))),
                (float)(to_y-radius*Math.sin(lineangle - (anglerad / 2.0))));
        path.lineTo((float)(to_x-radius*Math.cos(lineangle + (anglerad / 2.0))),
                (float)(to_y-radius*Math.sin(lineangle + (anglerad / 2.0))));
        path.close();

        canvas.drawPath(path, paint);
    }

    private void drawWallBoundaries(RectangleNode rectangleNode, Canvas canvas, Rect adjustedRect) {
        Rect leftBoundaryRect = new Rect(adjustedRect);
        Rect rightBoundaryRect = new Rect(adjustedRect);
        Rect topBoundaryRect = new Rect(adjustedRect);
        Rect bottomBoundaryRect = new Rect(adjustedRect);

        leftBoundaryRect.right = leftBoundaryRect.left + (int) Utils.dpToPx(WALL_THICKNESS / 2, getContext());
        rightBoundaryRect.left = rightBoundaryRect.right - (int) Utils.dpToPx(WALL_THICKNESS / 2, getContext());
        topBoundaryRect.bottom = topBoundaryRect.top + (int) Utils.dpToPx(WALL_THICKNESS / 2, getContext());
        bottomBoundaryRect.top = bottomBoundaryRect.bottom - (int) Utils.dpToPx(WALL_THICKNESS / 2, getContext());

        if (rectangleNode.wallBoundaries.contains(RectangleNode.Side.LEFT)) {
            canvas.drawRect(leftBoundaryRect, wallBoundaryPaint);
        }
        if (rectangleNode.wallBoundaries.contains(RectangleNode.Side.TOP)) {
            canvas.drawRect(topBoundaryRect, wallBoundaryPaint);
        }
        if (rectangleNode.wallBoundaries.contains(RectangleNode.Side.RIGHT)) {
            canvas.drawRect(rightBoundaryRect, wallBoundaryPaint);
        }
        if (rectangleNode.wallBoundaries.contains(RectangleNode.Side.BOTTOM)) {
            canvas.drawRect(bottomBoundaryRect, wallBoundaryPaint);
        }
    }

    private void drawPaintBoundaries(RectangleNode rectangleNode, Canvas canvas, Rect adjustedRect) {
        Rect leftBoundaryRect = new Rect(adjustedRect);
        Rect rightBoundaryRect = new Rect(adjustedRect);
        Rect topBoundaryRect = new Rect(adjustedRect);
        Rect bottomBoundaryRect = new Rect(adjustedRect);

        leftBoundaryRect.right = leftBoundaryRect.left + (int) Utils.dpToPx(PAINT_THICKNESS / 2, getContext());
        rightBoundaryRect.left = rightBoundaryRect.right - (int) Utils.dpToPx(PAINT_THICKNESS / 2, getContext());
        topBoundaryRect.bottom = topBoundaryRect.top + (int) Utils.dpToPx(PAINT_THICKNESS / 2, getContext());
        bottomBoundaryRect.top = bottomBoundaryRect.bottom - (int) Utils.dpToPx(PAINT_THICKNESS / 2, getContext());


        if (rectangleNode.paintBoundaries.contains(RectangleNode.Side.LEFT)) {
            canvas.drawRect(leftBoundaryRect, paintBoundaryPaint);
        }
        if (rectangleNode.paintBoundaries.contains(RectangleNode.Side.TOP)) {
            canvas.drawRect(topBoundaryRect, paintBoundaryPaint);
        }
        if (rectangleNode.paintBoundaries.contains(RectangleNode.Side.RIGHT)) {
            canvas.drawRect(rightBoundaryRect, paintBoundaryPaint);
        }
        if (rectangleNode.paintBoundaries.contains(RectangleNode.Side.BOTTOM)) {
            canvas.drawRect(bottomBoundaryRect, paintBoundaryPaint);
        }
    }

    private void drawOccupiedSymbol(Canvas canvas, float centerX, float centerY) {
        canvas.drawCircle(
                centerX,
                centerY,
                Utils.dpToPx(OCCUPIED_SYMBOL_RADIUS, getContext()),
                occupiedSymbolRedPaint);
        canvas.drawRoundRect(
                centerX - Utils.dpToPx(OCCUPIED_SYMBOL_RADIUS / 2, getContext()),
                centerY - Utils.dpToPx(OCCUPIED_SYMBOL_RADIUS / 6, getContext()),
                centerX + Utils.dpToPx(OCCUPIED_SYMBOL_RADIUS / 2, getContext()),
                centerY + Utils.dpToPx(OCCUPIED_SYMBOL_RADIUS / 6, getContext()),
                OCCUPIED_SYMBOL_RECTANGLE_RADIUS,
                OCCUPIED_SYMBOL_RECTANGLE_RADIUS,
                occupiedSymbolWhitePaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        List<LayoutNode> nodes = layoutGraph.nodes;

        for (LayoutNode layoutNode: nodes) {
            if (layoutNode instanceof StreetNode) {
                StreetNode streetNode = (StreetNode) layoutNode;
                Rect rect = adjustRectForCanvas(streetNode.rect);
                canvas.drawRect(rect, streetNodePaint);
                drawPaintBoundaries(streetNode, canvas, rect);
                drawWallBoundaries(streetNode, canvas, rect);
            }

            if (layoutNode instanceof ParkingNode) {
                ParkingNode parkingNode = (ParkingNode) layoutNode;
                Rect rect = adjustRectForCanvas(parkingNode.rect);
                if (parkingNode.isOccupied) {
//                    canvas.drawRect(rect, occupiedParkingNodePaint);
                    canvas.drawRect(rect, streetNodePaint);
                    drawOccupiedSymbol(canvas, rect.centerX(), rect.centerY());
                } else {
//                    canvas.drawRect(rect, unoccupiedParkingNodePaint);
                    canvas.drawRect(rect, streetNodePaint);
                }
                drawPaintBoundaries(parkingNode, canvas, rect);
                drawWallBoundaries(parkingNode, canvas, rect);
            }

            if (layoutNode instanceof GateNode) {
                GateNode gateNode = (GateNode) layoutNode;
                Rect rect = adjustRectForCanvas(gateNode.rect);
                canvas.drawRect(rect, streetNodePaint);
                drawPaintBoundaries(gateNode, canvas, rect);
                drawWallBoundaries(gateNode, canvas, rect);
            }

            if (layoutNode instanceof ArrowNode) {
                ArrowNode arrowNode = (ArrowNode) layoutNode;
                float startX = (int) (Utils.dpToPx(arrowNode.startX, getContext()) + minX);
                float startY = (int) (Utils.dpToPx(arrowNode.startY, getContext()) + minY);
                float endX = (int) (Utils.dpToPx(arrowNode.endX, getContext()) + minX);
                float endY = (int) (Utils.dpToPx(arrowNode.endY, getContext()) + minY);
                drawArrow(arrowNodePaint, canvas, startX, startY, endX, endY);
            }
        }
    }
}

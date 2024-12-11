import java.awt.*;
import javax.swing.*;
/**
 * A program to draw a Sierpinski's Triangle using recursion.
 * Written by Sebastian Boscan-Villalobos
 */
public class SierpinskiTriangle extends Canvas{
    // Constants for canvas and triangle sizes
    public static final int CANVAS_SIZE = 500;
    public static final int TRIANGLE_LENGTH = 450;

    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("Sierpinski's Triangle");
        frame.setSize(CANVAS_SIZE,CANVAS_SIZE);
        frame.add(new SierpinskiTriangle());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override //Optional
    public void paint(Graphics g) {
        drawTriangle(g);
    }

    /**
     * Draws the initial large triangle and starts the recursive drawing of the Sierpinski's Triangle.
     * 
     * @param g The Graphics object.
     */
    public void drawTriangle(Graphics g) {
        // Create and fill the base triangle
        Polygon baseTriangle = createBaseTriangle(TRIANGLE_LENGTH);
        g.fillPolygon(baseTriangle);

        // Create and draw the initial inverted triangle
        g.setColor(Color.WHITE);
        Polygon initialInverseTriangle = createTriangle(TRIANGLE_LENGTH / 2, TRIANGLE_LENGTH, TRIANGLE_LENGTH / 2);
        g.fillPolygon(initialInverseTriangle);

        // Start recursive drawing around the initial inverted triangle
        drawInverseTriangles(initialInverseTriangle, g);
    }

    /**
     * Creates the base triangle for the Sierpinski's Triangle.
     * 
     * @param length Horizontal/Vertical length of triangle.
     * @return Polygon representing the triangle.
     */
    public Polygon createBaseTriangle(int length) {
        Polygon triangle = new Polygon();
        triangle.addPoint(0, length);     //Bottom-Left point
        triangle.addPoint(length, length);  //Bottom-Right point
        triangle.addPoint(length/2, 0);   //Top-Center point
        return triangle;
    }

    /**
     * Creates an inverted triangle given a bottom point and length.
     * 
     * @param x      X-coordinate of the bottom point.
     * @param y      Y-coordinate of the bottom point.
     * @param length Horizontal/Vertical length of the triangle.
     * @return A polygon representing the triangle.
     */
    public Polygon createTriangle(int x, int y, int length) {
        Polygon invTriangle = new Polygon();
        invTriangle.addPoint(x-(length/2), y-length); //left-most point
        invTriangle.addPoint(x+(length/2), y-length); //right-most point
        invTriangle.addPoint(x, y); //bottom-most point
        return invTriangle;
    }

    /**
     * Recursively draws inverted triangles to create the Sierpinski's Triangle pattern.
     * 
     * @param originalTriangle The triangle around which inverted triangles will be drawn.
     * @param g                The Graphics object.
     */
    public void drawInverseTriangles(Polygon originalTriangle, Graphics g) {
        // Get the input triangle's dimensions
        int[] xpoints = originalTriangle.xpoints;
        int[] ypoints = originalTriangle.ypoints;
        int originalLength = xpoints[1] - xpoints[0]; 

        // Base condition: Stop recursion when triangle length is below 4px
        if (originalLength < 4) return;

        // Create inverted triangles around the current triangle
        Polygon bottomLeftTriangle = createTriangle(xpoints[2]-originalLength/2, ypoints[2], originalLength/2);
        Polygon bottomRightTriangle = createTriangle(xpoints[2]+originalLength/2, ypoints[2], originalLength/2);
        Polygon topMiddleTriangle = createTriangle(xpoints[2], ypoints[2]-originalLength, originalLength/2);

        // Draws the triangles
        g.fillPolygon(bottomLeftTriangle);
        g.fillPolygon(bottomRightTriangle);
        g.fillPolygon(topMiddleTriangle);

        // Recursively draw the next levels
        drawInverseTriangles(bottomLeftTriangle, g);
        drawInverseTriangles(bottomRightTriangle, g);
        drawInverseTriangles(topMiddleTriangle, g);
    }
}

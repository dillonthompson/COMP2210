import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Extractor.java. Implements feature extraction for collinear points in
 * two dimensional data.
 *
 * @author  Dillon Thompson (djt0026@auburn.edu)
 * @author  Dean Hendrix (dh@auburn.edu)
 * @version TODAY
 *
 */
public class Extractor {
   
   /** raw data: all (x,y) points from source data. */
   private Point[] points;
   
   /** lines identified from raw data. */
   private SortedSet<Line> lines;
  
   /**
    * Builds an extractor based on the points in the file named by filename. 
    */
   public Extractor(String filename) {
      try {
         Scanner extract = new Scanner(new File(filename));
         int size = extract.nextInt();
         points = new Point[size];
         int i = 0;

         while (extract.hasNext()) {
            int x = extract.nextInt();
            int y = extract.nextInt();
            Point c = new Point(x, y);
            points[i] = c;
            i++;
         }
      }
      catch (Exception err) {
         System.out.println("scanning error");
      }
   }
  
   /**
    * Builds an extractor based on the points in the Collection named by pcoll. 
    *
    * THIS METHOD IS PROVIDED FOR YOU AND MUST NOT BE CHANGED.
    */
   public Extractor(Collection<Point> pcoll) {
      points = pcoll.toArray(new Point[]{});
   }
  
   /**
    * Returns a sorted set of all line segments of exactly four collinear
    * points. Uses a brute-force combinatorial strategy. Returns an empty set
    * if there are no qualifying line segments.
    */
   public SortedSet<Line> getLinesBrute() {
      lines = new TreeSet<Line>();
      Point[] pointsBySlope = Arrays.copyOf(points, points.length); 

      for (int i = 3; i < pointsBySlope.length; i++) {
         for (int j = 2; j < i; j++) {
            for (int k = 1; k < j; k++) {
               for ( int l = 0; l < k; l++) {
                  double sl1 = pointsBySlope[l].slopeTo(pointsBySlope[k]);
                  double sl2 = pointsBySlope[l].slopeTo(pointsBySlope[j]);
                  double sl3 = pointsBySlope[l].slopeTo(pointsBySlope[i]);
                  if (sl1 == sl2 && sl1 == sl3) {
                     Line theline = new Line();
                     theline.add(pointsBySlope[l]);
                     theline.add(pointsBySlope[k]);
                     theline.add(pointsBySlope[j]);
                     theline.add(pointsBySlope[i]);
                     lines.add(theline);
                  }
               }
            }
         }
      }

      return lines;
   }
  
   /**
    * Returns a sorted set of all line segments of at least four collinear
    * points. The line segments are maximal; that is, no sub-segments are
    * identified separately. A sort-and-scan strategy is used. Returns an empty
    * set if there are no qualifying line segments.
    */
   public SortedSet<Line> getLinesFast() {
      lines = new TreeSet<Line>();
      Point[] pointsBySlope = Arrays.copyOf(points, points.length);

      for (int referencePoint = 0; referencePoint < points.length; referencePoint++) {
         Arrays.sort(pointsBySlope, points[referencePoint].slopeOrder);

         int count = 0;
            
         for (int currentPoint = 0; currentPoint < points.length - 1; currentPoint = currentPoint + count) {
            count = 0;
            int index = 0;
            while (currentPoint + index < points.length 
               && points[referencePoint].slopeOrder.compare(pointsBySlope[currentPoint], pointsBySlope[currentPoint + index]) == 0) {
               index++;
               count++;
            }
            if (count > 2) {
               Line theline = new Line();
               theline.add(points[referencePoint]);
               for (int i = 0; i < count; i++) {
                  theline.add(pointsBySlope[currentPoint + i]);
               }
               lines.add(theline);
            }
         }
      }  
      return lines;
   }
   
}

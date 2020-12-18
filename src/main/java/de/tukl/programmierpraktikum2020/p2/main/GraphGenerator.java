package de.tukl.programmierpraktikum2020.p2.main;

import de.tukl.programmierpraktikum2020.p2.a1.Graph;
import de.tukl.programmierpraktikum2020.p2.a1.GraphException;
import de.tukl.programmierpraktikum2020.p2.a2.Color;
import org.poly2tri.Poly2Tri;
import org.poly2tri.triangulation.TriangulationPoint;
import org.poly2tri.triangulation.delaunay.DelaunayTriangle;
import org.poly2tri.triangulation.point.TPoint;
import org.poly2tri.triangulation.sets.PointSet;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GraphGenerator {
    private final Random rand;
    private final Map<Integer, Point2D> nodeCoordinates = new HashMap<>();

    public GraphGenerator(long seed) {
        this.rand = new Random(seed);
    }

    public Map<Integer, Point2D> getNodeCoordinates() {
        return nodeCoordinates;
    }

    public void generate(Graph<Color, Integer> graph, int size) throws GraphException {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be > 0!");
        }
        generateGraph(generateRandomPoints(size), graph);
    }

    private List<TriangulationPoint> generateRandomPoints(int size) {
        // Start with the four edges of the square [0.0, 1.0] x [0.0, 1.0]
        List<TriangulationPoint> points = new ArrayList<>();
        points.add(new TPoint(0, 0));
        points.add(new TPoint(1, 0));
        points.add(new TPoint(0, 1));
        points.add(new TPoint(1, 1));

        // Less than four points: Pick the required amount
        if (size < 4) {
            return points.subList(0, size);
        }

        // More than four points: generate additional points
        if (size > 4) {
            // Generate more points
            int candidatesCount = 3 + (int) (Math.log(size) / Math.log(1.5));
            while (points.size() < size) {
                // Generate some random points (candidates for being added)
                List<TPoint> randomPoints = Stream
                        .generate(() -> new TPoint(rand.nextDouble(), rand.nextDouble()))
                        .limit(candidatesCount)
                        .collect(Collectors.toList());

                // For each of these random points, calculate the minimum distance to all previously added points
                Map<TPoint, Double> distances = new HashMap<>();
                for (TPoint p : randomPoints) {
                    distances.put(p, points.stream().mapToDouble(p2 -> {
                        double dx = p2.getX() - p.getX();
                        double dy = p2.getY() - p.getY();
                        return Math.sqrt(dx * dx + dy * dy);
                    }).min().orElse(Double.POSITIVE_INFINITY));
                }

                // Pick the random point with the maximal distance to all other previously added points
                randomPoints.stream()
                        .max(Comparator.comparingDouble(distances::get))
                        .ifPresent(points::add);
            }
        }

        // Randomize the order
        Collections.shuffle(points, rand);
        return points;
    }

    private void generateGraph(List<TriangulationPoint> points, Graph<Color, Integer> graph) throws GraphException {
        // Create nodes
        Map<TriangulationPoint, Integer> nodeIds = new HashMap<>();
        for (TriangulationPoint point : points) {
            int nodeId = graph.addNode(Color.WHITE);
            nodeIds.put(point, nodeId);
            nodeCoordinates.put(nodeId, new Point2D.Double(point.getX(), point.getY()));
        }

        // Create edges
        if (points.size() > 3) {
            // Perform Delaunay triangulation to calculate non-overlapping edges
            PointSet ps = new PointSet(points);
            Poly2Tri.triangulate(ps);

            // Map: Point -> List of Triangles, which contain that point
            Map<TriangulationPoint, List<DelaunayTriangle>> trianglesForPoint = new LinkedHashMap<>();
            for (DelaunayTriangle triangle : ps.getTriangles()) {
                for (TriangulationPoint point : triangle.points) {
                    trianglesForPoint
                            .computeIfAbsent(point, p -> new LinkedList<>())
                            .add(triangle);
                }
            }

            // Map: Point -> List of neighbors
            Map<TriangulationPoint, Set<TriangulationPoint>> neighborsForPoint = new LinkedHashMap<>();
            for (Map.Entry<TriangulationPoint, List<DelaunayTriangle>> entry : trianglesForPoint.entrySet()) {
                Set<TriangulationPoint> neigh = neighborsForPoint.computeIfAbsent(entry.getKey(), p -> new LinkedHashSet<>());
                for (DelaunayTriangle t : entry.getValue()) {
                    neigh.addAll(Arrays.asList(t.points));
                }
            }

            // Add edges to graph (ensure there is always at least one edge)
            int remaining = neighborsForPoint.values().stream().mapToInt(Set::size).sum();
            boolean addedEdge = false;
            for (Map.Entry<TriangulationPoint, Set<TriangulationPoint>> entry : neighborsForPoint.entrySet()) {
                TriangulationPoint p1 = entry.getKey();
                for (TriangulationPoint p2 : entry.getValue()) {
                    if (--remaining == 0 && !addedEdge || rand.nextInt(2) == 0) { // 50% chance
                        graph.addEdge(nodeIds.get(p1), nodeIds.get(p2), rand.nextInt(4)); // random weight 0 to 3
                        addedEdge = true;
                    }
                }
            }
        } else { // size <= 3
            // Not enough points for Delaunay algorithm!
            // But <= 3 points won't have any crossing edges...
            int remaining = points.size() * points.size();
            boolean addedEdge = false;
            for (TriangulationPoint p1 : points) {
                for (TriangulationPoint p2 : points) {
                    if (--remaining == 0 && !addedEdge || rand.nextInt(2) == 0) { // 50% chance
                        graph.addEdge(nodeIds.get(p1), nodeIds.get(p2), rand.nextInt(4)); // random weight 0 to 3
                        addedEdge = true;
                    }
                }
            }
        }
    }
}

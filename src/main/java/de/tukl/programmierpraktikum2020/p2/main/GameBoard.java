package de.tukl.programmierpraktikum2020.p2.main;

import com.google.common.base.Function;
import de.tukl.programmierpraktikum2020.p2.a1.GraphException;
import de.tukl.programmierpraktikum2020.p2.a1.InvalidEdgeException;
import de.tukl.programmierpraktikum2020.p2.a1.InvalidNodeException;
import de.tukl.programmierpraktikum2020.p2.a2.Color;
import de.tukl.programmierpraktikum2020.p2.a2.ForcedColorException;
import de.tukl.programmierpraktikum2020.p2.a2.NegativeWeightException;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.RenderContext;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class for the GUI window that shows the board of a running game.
 */
class GameBoard {
    private final Game game;
    private final JFrame frame;
    private final JLabel label;
    private final BasicVisualizationServer<Integer, Edge> vv;

    // lock that prevents human interactions while computer player is moving
    private final AtomicBoolean humanCanPlay = new AtomicBoolean();

    // Class for edges in JUNG graphs
    private class Edge {
        private final int fromId;
        private final int toId;

        private Edge(int fromId, int toId) {
            this.fromId = fromId;
            this.toId = toId;
        }

        private int getWeight() throws InvalidEdgeException {
            return game.graph.getWeight(fromId, toId);
        }
    }

    GameBoard(Game game) throws GraphException {
        this(game, null);
    }

    GameBoard(Game game, Map<Integer, Point2D> nodeCoordinates) throws GraphException {
        this.game = game;
        this.frame = new JFrame("Graph Coloring Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1024, 768));
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BorderLayout(0, 5));
        frame.getContentPane().add(mainPanel);

        // Label
        this.label = new JLabel("Current Player");
        mainPanel.add(label, BorderLayout.PAGE_START);

        // Graph
        DirectedGraph<Integer, Edge> jgraph = constructJungGraph();
        Layout<Integer, Edge> layout = constructGraphLayout(jgraph, nodeCoordinates);
        vv = new BasicVisualizationServer<>(layout);
        configureRenderer(vv.getRenderContext(), jgraph);
        registerMouseEvent(layout, vv);
        mainPanel.add(vv, BorderLayout.CENTER);
        layout.setSize(new Dimension(vv.getWidth(), vv.getHeight()));
    }

    void show() {
        frame.setVisible(true);
        nextPlayer();
    }

    private void nextPlayer() {
        game.nextPlayer(this::repaint, () -> humanCanPlay.set(true));
    }

    private void repaint() {
        label.setText("Current Player: " + game.currentPlayer());
        frame.repaint();
    }

    private void clickNode(int nodeId) {
        if (humanCanPlay.getAndSet(false)) {
            try {
                game.move.setColor(nodeId, game.currentPlayer());
                nextPlayer();
            } catch (ForcedColorException e) {
                JOptionPane.showMessageDialog(
                        frame,
                        "The color of that node is forced to " + e.color + ". Please make another move!",
                        "Invalid move!",
                        JOptionPane.ERROR_MESSAGE
                );
                humanCanPlay.set(true);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void clickEdge(Edge edge) {
        if (humanCanPlay.getAndSet(false)) {
            try {
                Color fromColor = game.graph.getData(edge.fromId);
                Color toColor = game.graph.getData(edge.toId);
                int weight = edge.getWeight();
                int action = JOptionPane.showOptionDialog(
                        frame,
                        "It's Player " + game.currentPlayer() + "'s turn.\n"
                                + "You have selected an edge from a " + fromColor.name().toLowerCase() + " node to a " + toColor.name().toLowerCase() + " node.\n"
                                + "The edge weight currently is " + weight + ".\n"
                                + "Please chose an action for that edge:",
                        "Change weight",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Increase weight", "Decrease weight", "Abort"},
                        null
                );
                if (action == 0) { // increase
                    game.move.increaseWeight(edge.fromId, edge.toId);
                } else if (action == 1) { // decrease
                    try {
                        game.move.decreaseWeight(edge.fromId, edge.toId);
                    } catch (NegativeWeightException e) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Negative weight is not allowed. Please make another move!",
                                "Invalid move!",
                                JOptionPane.ERROR_MESSAGE
                        );
                        humanCanPlay.set(true);
                        return;
                    }
                } else { // abort
                    humanCanPlay.set(true);
                    return;
                }
                nextPlayer();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private DirectedGraph<Integer, Edge> constructJungGraph() throws InvalidNodeException {
        DirectedGraph<Integer, Edge> jgraph = new DirectedSparseGraph<>();
        int[] nodeIds = game.graph.getNodeIds().stream().mapToInt(x -> x).sorted().toArray();
        for (int nodeId : nodeIds) {
            jgraph.addVertex(nodeId);
        }
        for (int fromId : nodeIds) {
            for (int toId : game.graph.getOutgoingNeighbors(fromId).stream().mapToInt(x -> x).sorted().toArray()) {
                jgraph.addEdge(new Edge(fromId, toId), fromId, toId);
            }
        }
        return jgraph;
    }

    private Layout<Integer, Edge> constructGraphLayout(DirectedGraph<Integer, Edge> jgraph, Map<Integer, Point2D> nodeCoordinates) throws InvalidNodeException {
        Layout<Integer, Edge> layout;
        if (nodeCoordinates == null) {
            layout = new ISOMLayout<>(jgraph);
            frame.getRootPane().addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    layout.setSize(new Dimension(vv.getWidth(), vv.getHeight()));
                }
            });
        } else {
            layout = new Layout<>() {
                @Override
                public void initialize() {
                }

                @Override
                public void setInitializer(Function<Integer, Point2D> initializer) {
                }

                @Override
                public void setGraph(Graph<Integer, Edge> graph) {
                }

                @Override
                public Graph<Integer, Edge> getGraph() {
                    return jgraph;
                }

                @Override
                public void reset() {
                }

                @Override
                public void setSize(Dimension d) {
                }

                @Override
                public Dimension getSize() {
                    return null;
                }

                @Override
                public void lock(Integer integer, boolean state) {
                }

                @Override
                public boolean isLocked(Integer integer) {
                    return false;
                }

                @Override
                public void setLocation(Integer integer, Point2D location) {
                }

                @Override
                public Point2D apply(Integer nodeId) {
                    Point2D p = nodeCoordinates.get(nodeId); // coordinates in [0,1) x [0,1)
                    return new Point2D.Double(
                            p.getX() * (vv.getWidth() - 60) + 30,
                            p.getY() * (vv.getHeight() - 60) + 30
                    );
                }
            };
        }
        return layout;
    }

    private void configureRenderer(RenderContext<Integer, Edge> renderContext, DirectedGraph<Integer, Edge> jgraph) {
        // Node colors
        renderContext.setVertexFillPaintTransformer(node -> {
            Color color;
            try {
                assert node != null;
                color = game.graph.getData(node);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
                return null;
            }
            try {
                return (java.awt.Color) java.awt.Color.class.getField(color.name()).get(null);
            } catch (Exception e) {
                new Exception("Invalid color value: " + color).printStackTrace();
                System.exit(1);
                return null;
            }
        });

        // Edge labels
        renderContext.setEdgeLabelTransformer(edge -> {
            assert edge != null;
            try {
                return Integer.toString(edge.getWeight());
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
                return "";
            }
        });
    }

    private void registerMouseEvent(Layout<Integer, Edge> layout, BasicVisualizationServer<Integer, Edge> vv) {
        vv.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Point2D p = mouseEvent.getPoint();
                GraphElementAccessor<Integer, Edge> pick = vv.getPickSupport();
                Integer node = pick.getVertex(layout, p.getX(), p.getY());
                if (node != null) {
                    clickNode(node);
                } else {
                    Edge edge = pick.getEdge(layout, p.getX(), p.getY());
                    if (edge != null) {
                        clickEdge(edge);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });
    }
}

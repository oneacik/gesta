package com.ksidelta.library.gesta;

import com.ksidelta.library.gesta.matcher.ClosestPointsForwardMatcher;
import com.ksidelta.library.gesta.matcher.PatternMatcher;
import com.ksidelta.library.gesta.processor.pattern.PatternProcessorFactory;
import com.ksidelta.library.gesta.shapes.Point;
import com.ksidelta.library.gesta.shapes.pattern.NamedPattern;
import com.ksidelta.library.gesta.shapes.pattern.Pattern;
import com.ksidelta.library.gesta.vault.PatternVault;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;

import static com.ksidelta.library.gesta.shapes.pattern.PatternsFactory.createAngleLine;
import static com.ksidelta.library.gesta.shapes.pattern.PatternsFactory.createHorizontalLine;
import static com.ksidelta.library.gesta.shapes.pattern.PatternsFactory.createRunicP;
import static com.ksidelta.library.gesta.shapes.pattern.PatternsFactory.createVerticalLine;

;

/**
 * TODO: Document this class / interface here
 *
 * @since v7.4
 */
@Disabled
public class InteractiveGestureTesting {
    private PatternVault patternVault;


    class MotionTracker implements MouseMotionListener, MouseListener {
        List<Point> points;

        MotionTracker() {
            this.points = new LinkedList<>();
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Pattern p = new Pattern(points)
                    .scale(1, -1);//need to invert axis in this retarded shit
            patternVault
                    .matchPattern(p)
                    .ifPresent(x -> {
                        System.out.format("PATTERN %s SCORE %f", x.getNamedPattern().getName(), x.getScore());
                    });
            System.out.println("");
            points.clear();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            points.add(new Point(e.getX(), e.getY()));
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

        public void draw(Graphics g) {
            points.forEach(x -> g.drawLine((int) x.getX(), (int) x.getY(), (int) x.getX() + 1, (int) x.getY() + 1));
        }
    }


    JFrame jFrame = new JFrame() {
        MotionTracker motionTracker = new MotionTracker();

        {
            this.addMouseMotionListener(motionTracker);
            this.addMouseListener(motionTracker);
        }


        @Override
        public void paint(Graphics g) {
            g.clearRect(0, 0, this.getWidth(), this.getHeight());
            g.drawLine(0, 0, 500, 500);
            motionTracker.draw(g);
        }

    };

    @BeforeEach
    public void setUp() {
        PatternMatcher patternMatcher = new ClosestPointsForwardMatcher(5);
        this.patternVault = new PatternVault(patternMatcher, PatternProcessorFactory.createStandardChain(), createSimplePatterns());

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(500, 500);
        jFrame.setResizable(false);
    }


    private List<NamedPattern> createSimplePatterns() {
        List<NamedPattern> namedPatterns = new LinkedList<>();
        namedPatterns.add(new NamedPattern("horizontal", createHorizontalLine()));
        namedPatterns.add(new NamedPattern("vertical", createVerticalLine()));
        namedPatterns.add(new NamedPattern("45* angle", createAngleLine(Math.PI * 1 / 4)));
        namedPatterns.add(new NamedPattern("Py!", createRunicP()));
        return namedPatterns;
    }

    @Test
    public void fuckit() throws InterruptedException {
        jFrame.show();

        while (true) {
            Thread.sleep(100);
            jFrame.repaint();
        }
    }


}

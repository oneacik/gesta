package com.ksidelta.library.gesta.shapes.pattern;

import com.ksidelta.library.gesta.shapes.Point;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatternTest {

    @Test
    public void getLength() {
        Pattern p = new Pattern(Arrays.asList(
                new Point(0, 0),
                new Point(1, 0),
                new Point(1, 1)
        ));
        assertEquals(p.getLength(), 2.0, 0.00001);
    }

    @Test
    public void minusIsCorrectlyCalculated() {
        Pattern a = new Pattern(Arrays.asList(new Point(0, 0), new Point(1, 0)));
        Pattern b = new Pattern(Arrays.asList(new Point(0, 1), new Point(1, 1)));

        Point result = b.minus(a);
        assertEquals(result.getX(), 0, 0.0001);
        assertEquals(result.getY(), 1.0, 0.0001);
    }
}

package com.ksidelta.library.gesta.matcher

import com.ksidelta.library.gesta.shapes.Point
import com.ksidelta.library.gesta.shapes.pattern.Pattern

class ClosestPointsForwardMatcher(val forwardLookSteps: Int) : PatternMatcher {

    override fun match(first: Pattern, second: Pattern): Double {
        return _match(0.0, 0, first.points, second.points);
    }

    fun _match(error: Double, times: Int, compare: List<Point>, to: List<Point>): Double {

        if (compare.size == 1 && to.size == 1) {
            return (error + compare.first().squareDistance(to.first())) / (times + 1);
        }

        val base = compare.first();
        val pair = getBest(base, to)

        if (compare.size > 1) {
            return _match(
                    error = error + pair.second,
                    times = times + 1,
                    compare = to.dropSafe(returnMoreThanZero(pair.first)),
                    to = compare
            );
        } else {
            return _match(
                    error = error + pair.second,
                    times = times + 1,
                    compare = compare,
                    to = to.dropSafe(returnMoreThanZero(pair.first))
            );
        }

    }


    private fun getBest(compare: Point, to: List<Point>): Pair<Int, Double> {
        var max = Double.MAX_VALUE;
        var pos = -1;

        var i = 0

        val it = to.listIterator()
        while (i < to.size && i < forwardLookSteps) {
            val point = it.next()
            val squareDistance = compare.squareDistance(point);
            if (squareDistance < max) {
                max = squareDistance;
                pos = i;
            }
            i++;
        }
        return Pair(pos, max);
    }

    fun List<Point>.dropSafe(i: Int): List<Point> {
        return if (this.size > i) {
            this.drop(i)
        } else {
            this.drop(this.size - 1)
        }
    }

    fun returnMoreThanZero(i: Int): Int {
        return if (i > 0) i else 1;

    }

}

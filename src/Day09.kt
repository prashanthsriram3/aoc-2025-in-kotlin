import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Polygon
import kotlin.math.abs

fun main() {
    val gf = GeometryFactory()

    fun parseInput(input: List<String>): List<Pair<Double, Double>> =
        input.map { line ->
            line.split(",").map { it.toDouble() }.let { it[0] to it[1] }
        }

    data class RectanglePairs(val p1: Pair<Double, Double>, val p2: Pair<Double, Double>, val rectangle: Polygon) {
        fun area(): Long =
            (abs(p1.first - p2.first) + 1).toLong() * (abs(p1.second - p2.second) + 1).toLong()
    }

    fun rectangles(points: List<Pair<Double, Double>>): List<RectanglePairs> =
        points.flatMapIndexed { index, p1 ->
            points.drop(index + 1)
                .map { p2 -> p1 to p2 }
        }.filter { it.first.first != it.second.first && it.first.second != it.second.second }
            .map { (p1, p2) ->
                RectanglePairs(
                    p1,
                    p2,
                    gf.createPolygon(
                        arrayOf(
                            Coordinate(p1.first, p1.second),
                            Coordinate(p1.first, p2.second),
                            Coordinate(p2.first, p2.second),
                            Coordinate(p2.first, p1.second),
                            Coordinate(p1.first, p1.second)
                        )
                    )
                )
            }

    fun part1(input: List<String>): Long =
        rectangles(parseInput(input)).maxOf { it.area() }

    fun part2(input: List<String>): Long =
        parseInput(input).let { points ->
            val polygon = gf.createPolygon(
                points.map { Coordinate(it.first, it.second) }.toTypedArray() +
                        Coordinate(points[0].first, points[0].second)
            )
            val rectanglePairs = rectangles(points)
            rectanglePairs.filter {
                polygon.contains(it.rectangle)
            }.maxOf { it.area() }
        }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 50L)
    check(part2(testInput) == 24L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}

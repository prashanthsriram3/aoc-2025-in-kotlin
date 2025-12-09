import kotlin.math.abs

fun main() {
    data class AreaPairs(val p1: Pair<Long, Long>, val p2: Pair<Long, Long>) {
        fun area() =
            (abs(p1.first - p2.first) + 1) * (abs(p1.second - p2.second) + 1)
    }

    fun parseInput(input: List<String>): List<Pair<Long, Long>> =
        input.map { line ->
            line.split(",").map { it.toLong() }.let { it[0] to it[1] }
        }

    fun part1(input: List<String>): Long =
        parseInput(input).let { points ->
            points.flatMapIndexed { index, p1 ->
                points.drop(index + 1).map { p2 -> AreaPairs(p1, p2) }
            }.maxOf { it.area() }
        }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 50L)
//    check(part2(testInput) == 25272L)

    val input = readInput("Day09")
    part1(input).println()
//    part2(input).println()
}

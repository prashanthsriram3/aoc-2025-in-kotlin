import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    data class EuclideanPairs(val p1: Triple<Long, Long, Long>, val p2: Triple<Long, Long, Long>) {
        fun distance() =
            sqrt(
                (p1.first - p2.first).toDouble().pow(2) +
                        (p1.second - p2.second).toDouble().pow(2) +
                        (p1.third - p2.third).toDouble().pow(2)
            )
    }

    fun parseInput(input: List<String>): List<Triple<Long, Long, Long>> =
        input.map { line ->
            line.split(",").map { it.toLong() }.let { Triple(it[0], it[1], it[2]) }
        }

    fun part1(input: List<String>, noOfCircuits: Int): Long =
        parseInput(input).let { points ->
            points.flatMapIndexed { index, p1 ->
                points.drop(index + 1).map { p2 -> EuclideanPairs(p1, p2) }
            }.sortedBy { it.distance() }.take(noOfCircuits).let { pointPairs ->
                val circuits = mutableListOf<Set<Triple<Long, Long, Long>>>()
                pointPairs.forEach { pointPair ->
                    val setContainingP1 = circuits.find { it.contains(pointPair.p1) }
                    val setsContainingP2 = circuits.find { it.contains(pointPair.p2) }
                    if (setContainingP1 != null) {
                        circuits.remove(setContainingP1)
                    }
                    if (setsContainingP2 != null) {
                        circuits.remove(setsContainingP2)
                    }
                    circuits.add((setContainingP1 ?: setOf(pointPair.p1)).union(setsContainingP2 ?: setOf(pointPair.p2)))
                }
                circuits.map { it.size }.sortedByDescending { it }.take(3).fold(1L) { acc, i ->
                    acc * i
                }
            }
        }

    fun part2(input: List<String>): Long =
        parseInput(input).let { points ->
            points.flatMapIndexed { index, p1 ->
                points.drop(index + 1).map { p2 -> EuclideanPairs(p1, p2) }
            }.sortedBy { it.distance() }.let { pointPairs ->
                val circuits = mutableListOf<Set<Triple<Long, Long, Long>>>()
                pointPairs.forEach { pointPair ->
                    val setContainingP1 = circuits.find { it.contains(pointPair.p1) }
                    val setsContainingP2 = circuits.find { it.contains(pointPair.p2) }
                    if (setContainingP1 != null) {
                        circuits.remove(setContainingP1)
                    }
                    if (setsContainingP2 != null) {
                        circuits.remove(setsContainingP2)
                    }
                    circuits.add((setContainingP1 ?: setOf(pointPair.p1)).union(setsContainingP2 ?: setOf(pointPair.p2)))
                    if (circuits.size == 1 && circuits[0].size == points.size) {
                        return@let pointPair.p1.first * pointPair.p2.first
                    }
                }
                -1L
            }
        }

    val testInput = readInput("Day08_test")
    check(part1(testInput, 10) == 40L)
    check(part2(testInput) == 25272L)

    val input = readInput("Day08")
    part1(input, 1000).println()
    part2(input).println()
}

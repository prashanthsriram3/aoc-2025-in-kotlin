fun main() {
    fun parseInput(input: List<String>): Pair<List<LongRange>, List<Long>> =
        input.indexOfFirst { it.isBlank() }.let { breakIndex ->
            val ranges = input.subList(0, breakIndex).map { line ->
                val (start, end) = line.split("-").map { it.toLong() }
                start..end
            }
            val ids = input.subList(breakIndex + 1, input.size).map { it.toLong() }
            ranges to ids
        }

    fun part1(input: List<String>): Int =
        parseInput(input).let { (ranges, ids) ->
            ids.count { id -> ranges.any { id in it } }
        }

    fun part2(input: List<String>): Long =
        parseInput(input).first.sortedBy { it.first }.let { ranges ->
            ranges.drop(1).fold(mutableListOf(ranges[0])) { acc, range ->
                val last = acc.last()
                if (range.first <= last.last + 1) {
                    acc[acc.lastIndex] = last.first..maxOf(last.last, range.last)
                } else {
                    acc.add(range)
                }
                acc
            }.fold(0) { count, range -> count + (range.last - range.first + 1) }
        }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

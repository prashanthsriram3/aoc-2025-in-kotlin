fun main() {
    fun parseInput(input: List<String>): List<LongRange> =
        input[0].split(",")
            .map {
                val (first, last) = it.split('-').map { n -> n.toLong() }
                first..last
            }

    fun part1(input: List<String>): Long =
        parseInput(input)
            .fold(0) { acc, range ->
                acc + range.filter {
                    val firstPart = it.toString().substring(0, it.toString().length / 2)
                    val secondPart = it.toString().substring(it.toString().length / 2)
                    firstPart == secondPart
                }.sum()
            }

    fun part2(input: List<String>): Long =
        parseInput(input)
            .fold(0) { acc, range ->
                acc + range.filter {
                    (1..(it.toString().length / 2)).any { len ->
                        it.toString().chunked(len).toSet().size == 1
                    }
                }.sum()
            }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

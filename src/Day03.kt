fun main() {
    fun maxForNumberOfDigits(line: String, noOfDigits: Int): Long =
        (0..<noOfDigits).fold("" to -1) { acc, i ->
            val maxNextIndexedValue =
                line.substring(acc.second + 1, line.length - noOfDigits + i + 1)
                    .withIndex().maxBy { it.value.digitToInt() }
            acc.first + maxNextIndexedValue.value to (acc.second + 1 + maxNextIndexedValue.index)
        }.first.toLong()

    fun part1(input: List<String>): Long =
        input.sumOf { maxForNumberOfDigits(it, 2) }

    fun part2(input: List<String>): Long =
        input.sumOf { maxForNumberOfDigits(it, 12) }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357L)
    check(part2(testInput) == 3121910778619L)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

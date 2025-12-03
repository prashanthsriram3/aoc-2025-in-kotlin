fun main() {
    fun maxForNumberOfDigits(line: String, noOfDigits: Int): Long {
        val maxIndexes: MutableList<Int> = mutableListOf()
        val maxValues: MutableList<Char> = mutableListOf()
        val maxFirstIndexedValue = line.substring(0, line.length - noOfDigits + 1).withIndex().maxBy { it.value.digitToInt() }
        maxIndexes.add(maxFirstIndexedValue.index)
        maxValues.add(maxFirstIndexedValue.value)
        (1..(noOfDigits - 1)).forEach { i ->
            val maxNextIndexedValue = line.substring(maxIndexes[i - 1] + 1, line.length - noOfDigits + i + 1)
                .withIndex().maxBy { it.value.digitToInt() }
            maxIndexes.add(maxIndexes[i - 1] + 1 + maxNextIndexedValue.index)
            maxValues.add(maxNextIndexedValue.value)
        }
        return maxValues.joinToString("").toLong()
    }

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

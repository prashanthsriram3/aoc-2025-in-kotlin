fun main() {
    fun part1(input: List<String>): Int {
        val startingColumn = input[0].indexOfFirst { it == 'S' }
        var splits = 0
        val beams = mutableSetOf(startingColumn)
        (1 until input.size).forEach { rowIndex ->
            val currentBeams = beams.toSet()
            currentBeams.forEach { beam ->
                if (input[rowIndex][beam] == '^') {
                    beams.remove(beam)
                    beams.addAll(setOf(beam - 1, beam + 1))
                    splits += 1
                }
            }
        }
        return splits
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 21)
//    check(part2(testInput) == 3263827L)

    val input = readInput("Day07")
    part1(input).println()
//    part2(input).println()
}

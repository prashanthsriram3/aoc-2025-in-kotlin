enum class Direction {
    L, R
}

fun main() {
    fun getNewPosition(direction: Direction, rotation: Int, position: Int): Int {
        return when (direction) {
            Direction.L -> position - rotation
            Direction.R -> position + rotation
        }
    }

    fun parseInput(input: List<String>): List<Pair<Direction, Int>> =
        input.map { it[0].toString() to it.substring(1).toInt() }
            .map { (direction, rotation) -> Direction.valueOf(direction) to rotation }

    fun part1(input: List<String>): Int =
        parseInput(input)
            .fold(0 to 50) { acc, (direction, rotation) ->
                val (countZeros, position) = acc
                val newPositionModulo100 = getNewPosition(direction, rotation, position).mod(100)
                val newCountZeros = if (newPositionModulo100 == 0) countZeros + 1 else countZeros
                newCountZeros to newPositionModulo100
            }
            .first

    fun part2(input: List<String>): Int =
        parseInput(input)
            .fold(0 to 50) { acc, (direction, rotation) ->
                val (countZeros, position) = acc
                val newPosition = getNewPosition(direction, rotation % 100, position)
                val newCountZeros =
                    if (newPosition == 0 || (position != 0 && newPosition !in 1..99)) countZeros + 1 else countZeros
                (newCountZeros + (rotation / 100)) to newPosition.mod(100)
            }
            .first

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

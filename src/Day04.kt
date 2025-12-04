fun main() {
    val directions = listOf(
        -1 to -1, -1 to 0, -1 to 1,
        0 to -1, 0 to 1,
        1 to -1, 1 to 0, 1 to 1
    )

    fun parseInput(input: List<String>): List<MutableList<Char>> =
        input.map { it.toMutableList() }

    fun checkAdjacentCount(grid: List<List<Char>>, row: Int, col: Int, rowSize: Int, colSize: Int): Int =
        directions.fold(0) { count, (dr, dc) ->
            val newRow = row + dr
            val newCol = col + dc
            if (newRow in 0 until rowSize &&
                newCol in 0 until colSize &&
                grid[newRow][newCol] == '@'
            ) {
                count + 1
            } else {
                count
            }
        }

    fun checkAndRemoveRolls(grid: List<MutableList<Char>>, rowSize: Int, colSize: Int): Int {
        val toRemove = mutableListOf<Pair<Int, Int>>()
        grid.forEachIndexed { rowIndex, chars ->
            chars.forEachIndexed { colIndex, char ->
                if (char == '@' && checkAdjacentCount(grid, rowIndex, colIndex, rowSize, colSize) < 4) {
                    toRemove.add(rowIndex to colIndex)
                }
            }
        }
        toRemove.forEach { (r, c) ->
            grid[r][c] = '.'
        }
        return toRemove.size
    }

    fun part1(input: List<String>): Int {
        val grid = parseInput(input)
        val rowSize = grid.size
        val colSize = grid[0].size
        return checkAndRemoveRolls(grid, rowSize, colSize)
    }

    fun part2(input: List<String>): Int {
        val grid = parseInput(input)
        val rowSize = grid.size
        val colSize = grid[0].size
        var rollsRemoved = checkAndRemoveRolls(grid, rowSize, colSize)
        var totalRollsRemoved = rollsRemoved
        while (rollsRemoved > 0) {
            rollsRemoved = checkAndRemoveRolls(grid, rowSize, colSize)
            totalRollsRemoved += rollsRemoved
        }
        return totalRollsRemoved
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

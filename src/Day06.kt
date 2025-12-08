enum class Operation(val symbol: String) {
    ADD("+"), MULTIPLY("*");

    companion object {
        fun fromSymbol(symbol: String): Operation {
            return entries.first { it.symbol == symbol }
        }
    }
}

fun main() {
    fun part1(input: List<String>): Long =
        (input.subList(0, input.size - 1).map { line ->
            line.trim().split(" ").filter { it.trim().isNotEmpty() }.map { it.toLong() }
        } to input.last().trim().split(" ").filter { it.trim().isNotEmpty() }.map { Operation.fromSymbol(it.trim()) })
            .let { (numbers, operations) ->
                val columnTotals: MutableList<Long> = numbers[0].toMutableList()
                numbers.subList(1, numbers.size).forEach { row ->
                    row.forEachIndexed { index, value ->
                        when (operations[index]) {
                            Operation.ADD -> columnTotals[index] += value
                            Operation.MULTIPLY -> columnTotals[index] *= value
                        }
                    }
                }
                columnTotals.sum()
            }

    fun part2(input: List<String>): Long {
        val operations = input.last()
        var opIndex = 0
        var currentTotalFirst = true
        var currentTotal = 0L
        val columnTotals: MutableList<Long> = mutableListOf()
        (0 until input[0].length).forEach { colIndex ->
            val numberStr = input.subList(0, input.size - 1).map { it[colIndex] }.filter { it.isDigit() }.joinToString("")
            if (numberStr.isEmpty()) {
                columnTotals.add(currentTotal)
                opIndex = colIndex + 1
                currentTotalFirst = true
                currentTotal = 0L
            } else {
                val value = numberStr.toLong()
                currentTotal = if (currentTotalFirst) {
                    currentTotalFirst = false
                    value
                } else {
                    when (Operation.fromSymbol(operations[opIndex].toString())) {
                        Operation.ADD -> currentTotal + value
                        Operation.MULTIPLY -> currentTotal * value
                    }
                }
            }
        }
        columnTotals.add(currentTotal)
        return columnTotals.sum()
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

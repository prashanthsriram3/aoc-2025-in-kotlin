fun main() {
    fun part1and2(input: List<String>): Pair<Int, Long> {
        var splits = 0
        val paths = mutableMapOf<Int, Long>()

        input.forEach { line ->
            line.forEachIndexed { i, c ->
                when (c) {
                    'S' -> {
                        paths[i] = 1
                    }
                    '^' -> {
                        if (paths.containsKey(i)) {
                            val count = paths[i]!!
                            splits += 1

                            paths[i - 1] = (paths[i - 1] ?: 0) + count
                            paths[i + 1] = (paths[i + 1] ?: 0) + count

                            paths.remove(i)
                        }
                    }
                }
            }
        }

        return splits to paths.values.sum()
    }

    val testInput = readInput("Day07_test")
    val (part1, part2) = part1and2(testInput)
    check(part1 == 21)
    check(part2 == 40L)

    val input = readInput("Day07")
    part1and2(input).println()
}

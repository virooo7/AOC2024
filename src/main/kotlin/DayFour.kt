package aoc2024

enum class Direction {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST
}

class DayFour(var matrix: List<List<Char>>) {

    private val word = "XMAS"

    private fun findWord(
        wordIndex: Int,
        direction: Direction,
        row: Int,
        column: Int,
    ): Boolean {

        if(wordIndex >= word.length){
            return true
        }

        if(row < 0 || row >= matrix.size || column < 0 || column >= matrix[0].size){
            return false
        }

        var result = false

        if(matrix[row][column] == word[wordIndex]){
            var tempRow = row
            var tempColumn = column
            when(direction){
                Direction.NORTH -> {
                    tempRow--
                }
                Direction.NORTH_EAST -> {
                    tempRow--
                    tempColumn++
                }
                Direction.EAST -> {
                    tempColumn++
                }
                Direction.SOUTH_EAST -> {
                    tempRow++
                    tempColumn++
                }
                Direction.SOUTH -> {
                    tempRow++
                }
                Direction.SOUTH_WEST -> {
                    tempRow++
                    tempColumn--
                }
                Direction.WEST -> {
                    tempColumn--
                }
                Direction.NORTH_WEST -> {
                    tempRow--
                    tempColumn--
                }
            }

            result = findWord(wordIndex + 1, direction, tempRow, tempColumn)
        }
        return result
    }

    fun day4Part1(): Int {

        var count = 0
        for (row in matrix.indices) {
            for (column in matrix[row].indices) {
                if (findWord(0, Direction.NORTH, row, column)){
                    count++
                }
                if (findWord(0, Direction.NORTH_EAST, row, column)){
                    count++
                }
                if (findWord(0, Direction.EAST, row, column)){
                    count++
                }
                if (findWord(0, Direction.SOUTH_EAST, row, column)){
                    count++
                }
                if (findWord(0, Direction.SOUTH, row, column)){
                    count++
                }
                if (findWord(0, Direction.SOUTH_WEST, row, column)){
                    count++
                }
                if (findWord(0, Direction.WEST, row, column)){
                    count++
                }
                if (findWord(0, Direction.NORTH_WEST, row, column)){
                    count++
                }
            }
        }

        return count
    }


    fun day4Part2(): Int {

        var count = 0
        for (row in 1 ..  matrix.size - 2) {
            for (column in 1 .. matrix[row].size - 2) {
                if(matrix[row][column] == 'A'){
                    if((matrix[row-1][column-1] == 'M' && matrix[row+1][column+1] == 'S' &&
                        matrix[row-1][column+1] == 'M' && matrix[row+1][column-1] == 'S') ||
                        (matrix[row-1][column-1] == 'M' && matrix[row+1][column+1] == 'S' &&
                         matrix[row-1][column+1] == 'S' && matrix[row+1][column-1] == 'M') ||
                        (matrix[row-1][column-1] == 'S' && matrix[row+1][column+1] == 'M' &&
                                matrix[row-1][column+1] == 'M' && matrix[row+1][column-1] == 'S') ||
                        (matrix[row-1][column-1] == 'S' && matrix[row+1][column+1] == 'M' &&
                                matrix[row-1][column+1] == 'S' && matrix[row+1][column-1] == 'M')
                        ){
                            count++

                    }

                }
            }
        }

        return count
    }
}

fun main () {
    var matrix = readFileAsMatrix("src/main/resources/DayFour.txt")
    var obj = DayFour(matrix)
    println("Output for day4 part1: ${obj.day4Part1()}")
    println("Output for day4 part2: ${obj.day4Part2()}")
}
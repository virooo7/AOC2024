package aoc2024

import java.io.File

fun parseFileDaySix() : Pair<List<List<Char>>, Int> {
    var file = File("src/main/resources/DaySix.txt")
    var output = mutableListOf<List<Char>>()
    var guardLocation = 0
    var lineIndex = 0

    file.forEachLine { line ->
        var row = mutableListOf<Char>()
        for(i in line.indices){
            row.add(line[i])
            if(line[i] == '^'){
                guardLocation = (lineIndex * line.length) + i
            }
        }
        output.add(row)
        lineIndex ++
    }

    return Pair(output, guardLocation)
}

class DaySix(var grid : List<List<Char>>, var guardLocation: Int){

    val barrier = '@'

    class Coordinates(val row: Int, val column: Int)

    private fun getCoordinatesFromLocation (location : Int) : Coordinates {
        var row = location / grid[0].size
        var column = location % grid[0].size

        return Coordinates(row, column)
    }

    enum class Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    fun day6Part1 () : Int{
        var guardCoordinates = getCoordinatesFromLocation(guardLocation)
        var visitedLocations = mutableSetOf<Int>()
        var direction = Direction.UP
        var row = guardCoordinates.row
        var column = guardCoordinates.column

        while(row >=0 && row < grid.size && column >=0 && column < grid[0].size){
            visitedLocations.add((row * grid.size) + column)
            when(direction){
                Direction.UP -> {
                    if((row - 1) >= 0 && grid[row - 1][column] == '#'){
                        direction = Direction.RIGHT
                        column++
                    } else {
                        row--
                    }
                }
                Direction.RIGHT -> {
                    if((column + 1) < grid[0].size && grid[row][column + 1] == '#'){
                        direction = Direction.DOWN
                        row++
                    } else {
                        column++
                    }
                }
                Direction.DOWN-> {
                    if((row + 1) < grid.size && grid[row+1][column] == '#'){
                        direction = Direction.LEFT
                        column--
                    } else {
                        row++
                    }
                }
                Direction.LEFT -> {
                    if((column - 1) >= 0 && grid[row][column - 1] == '#'){
                        direction = Direction.UP
                        row --
                    } else {
                        column--
                    }
                }
            }
        }

        return visitedLocations.size
    }

    fun printGridAndVisitedLocations(grid: List<List<Char>>, visitedLocations : Set<String>, file: File? = null) {
        val copy = copyGrid(grid)
        var copyString = mutableListOf<MutableList<String>>()

        for(row in copy){
            copyString.add(row.map { item -> item.toString() }.toMutableList())
        }

        for(location in visitedLocations){
            val digitRegex = Regex("\\d+")
            val digitsMatch = digitRegex.find(location)
            val loc = digitsMatch?.value!!.toInt()
            val row = loc / grid[0].size
            val column = loc % grid[0].size

            if(copyString[row][column] != "^") {
                copyString[row][column] = location
            }

            if(copy[row][column] != '^') {
                copy[row][column] = '*'
            }
        }

        for(row in copy){
            val str = row.joinToString("")
            println(str)
            file?.writeText("$str\n")
        }

        println()

        for(row in copyString){
            val str = row.joinToString("")
            println(str)
        }
        println()
    }

    fun getNextCoordinates() {

    }

    fun runSimulation(grid: List<List<Char>>, guardLocation: Int) : Set<String>?{
        val guardCoordinates = getCoordinatesFromLocation(guardLocation)
        var row = guardCoordinates.row
        var column = guardCoordinates.column
        val visitedLocations = mutableSetOf<String>()
        var direction = Direction.UP
        while(row >=0 && row < grid.size && column >=0 && column < grid[0].size){
            val currentLocation = "${(row * grid[0].size) + column}${direction}"
            if(visitedLocations.contains(currentLocation)){
                //printGridAndVisitedLocations(grid, visitedLocations)
                return null
            }
            visitedLocations.add(currentLocation)

            when(direction){
                Direction.UP -> {
                    if((row - 1) >= 0 && (grid[row - 1][column] == '#' || grid[row - 1][column] == '@')){
                        direction = Direction.RIGHT
                        //column++
                    } else {
                        row--
                    }
                }
                Direction.RIGHT -> {
                    if((column + 1) < grid[0].size && (grid[row][column + 1] == '#' || grid[row][column + 1] == '@')){
                        direction = Direction.DOWN
                        //row++
                    } else {
                        column++
                    }
                }
                Direction.DOWN-> {
                    if((row + 1) < grid.size && (grid[row+1][column] == '#' || grid[row+1][column] == '@')){
                        direction = Direction.LEFT
                        //column--
                    } else {
                        row++
                    }
                }
                Direction.LEFT -> {
                    if((column - 1) >= 0 && (grid[row][column - 1] == '#' || grid[row][column - 1] == '@')){
                        direction = Direction.UP
                        //row --
                    } else {
                        column--
                    }
                }
            }
        }
        return visitedLocations
    }

    fun copyGrid(grid: List<List<Char>>) : List<MutableList<Char>>{
        val result = mutableListOf<MutableList<Char>>()
        for(row in grid){
            result.add(row.toMutableList())
        }
        return result
    }

    fun day6Part2() : Int{
        val guardCoordinates = getCoordinatesFromLocation(guardLocation)
        val visitedLocations = runSimulation(grid, guardLocation)
        //printGridAndVisitedLocations(grid, visitedLocations!!)
        val barrierPositions = mutableSetOf<Int>()
        if(visitedLocations != null){
            for(location in visitedLocations){
                val directionRegex = Regex("""[^0-9]+""")
                val directionMatch =  directionRegex.find(location)
                val direction = Direction.valueOf(directionMatch?.value!!)

                val digitRegex = Regex("\\d+")
                val digitsMatch = digitRegex.find(location)
                val loc = digitsMatch?.value!!.toInt()
                val row = loc / grid[0].size
                val column = loc % grid[0].size
                val copyGrid = copyGrid(grid)
                var newBarrierPosition = -1

                when(direction){
                    Direction.UP -> {
                        if(row - 1 >= 0){
                            copyGrid[row - 1][column] = barrier
                            newBarrierPosition = ((row - 1) * grid[0].size) + column
                        }
                    }
                    Direction.RIGHT -> {
                        if(column + 1 < grid[0].size){
                            copyGrid[row][column + 1] = barrier
                            newBarrierPosition = (row * grid[0].size) + column + 1
                        }
                    }
                    Direction.DOWN -> {
                        if(row + 1 < grid.size){
                            copyGrid[row + 1][column] = barrier
                            newBarrierPosition = ((row + 1) * grid[0].size) + column
                        }
                    }
                    Direction.LEFT -> {
                        if(column - 1 >= 0){
                            copyGrid[row][column - 1] = barrier
                            newBarrierPosition = (row * grid[0].size) + column - 1
                        }
                    }
                }

                val newBarrierPositionRow = newBarrierPosition / grid[0].size
                val newBarrierPositionColumn = newBarrierPosition % grid[0].size
                if(newBarrierPositionRow == guardCoordinates.row && newBarrierPositionColumn == guardCoordinates.column){
                    newBarrierPosition = -1
                }

                if(newBarrierPosition > -1 && !barrierPositions.contains(newBarrierPosition)){
                    val result = runSimulation(copyGrid, guardLocation)
                    if(result == null){
                        barrierPositions.add(newBarrierPosition)
                        //println("********************************************** \n $copyGrid")
                    }
                }
            }
        }

        return barrierPositions.size
    }
}

fun main(){
    var grid = parseFileDaySix()
    var obj = DaySix(grid.first, grid.second)
    println("Output of Day 6 Part 1: ${obj.day6Part1()}")
    println("Output of Day 6 Part 2: ${obj.day6Part2()}")
}
package aoc2024

import java.io.File
import kotlin.math.abs

fun parseDayEight() : List<String>{
    val file = File("src/main/resources/DayEight.txt")
    return file.readLines()
}

class Coordinates (val row: Int, val column: Int)

fun calculateAbsolutePosition(row: Int, column: Int, columnSize: Int) : Int{
    return (row * columnSize) + column
}

fun calculateCoordinatesFromAbsolute(absLocation : Int, columnSize: Int) : Coordinates {
    return Coordinates(absLocation / columnSize, absLocation % columnSize)
}

class DayEight(val input : List<String>){

    private val columnSize = input[0].length

    fun dayEightPart1() : Int {
        val map = mutableMapOf<Char, MutableList<Int>>()
        for(row in input.indices){
            for(column in input[row].indices){
                val character = input[row][column]
                if(character.isDigit() || character.isLetter()){
                    val absLocation = calculateAbsolutePosition(row, column, input[row].length)
                    if(map.containsKey(character)){
                        map[character]!!.add(absLocation)
                    }
                    else {
                        map[character] = mutableListOf(absLocation)
                    }
                }
            }
        }

        var antinodesLocation = mutableSetOf<Int>()
        for(pairs in map){
            for(i in 0..<pairs.value.size - 1){
                for(j in i + 1 ..<pairs.value.size){
                    val coordinateI = calculateCoordinatesFromAbsolute(pairs.value[i], columnSize)
                    val coordinateJ = calculateCoordinatesFromAbsolute(pairs.value[j], columnSize)

                    val rowDiff = coordinateJ.row - coordinateI.row
                    val columnDiff = coordinateJ.column - coordinateI.column

                    val antinode1Row = coordinateI.row - rowDiff
                    val antinode1Column = coordinateI.column - columnDiff

                    if(antinode1Row >=0 && antinode1Row < input.size &&
                        antinode1Column >=0 && antinode1Column < columnSize){
                        antinodesLocation.add(calculateAbsolutePosition(antinode1Row, antinode1Column, columnSize))
                    }

                    val antinode2Row = coordinateJ.row + rowDiff
                    val antinode2Column = coordinateJ.column + columnDiff

                    if(antinode2Row >=0 && antinode2Row < input.size &&
                        antinode2Column >=0 && antinode2Column < columnSize){
                        antinodesLocation.add(calculateAbsolutePosition(antinode2Row, antinode2Column, columnSize))
                    }
                }
            }
        }

        return antinodesLocation.size
    }

    fun dayEightPart2() : Int {
        val map = mutableMapOf<Char, MutableList<Int>>()
        for(row in input.indices){
            for(column in input[row].indices){
                val character = input[row][column]
                if(character.isDigit() || character.isLetter()){
                    val absLocation = calculateAbsolutePosition(row, column, input[row].length)
                    if(map.containsKey(character)){
                        map[character]!!.add(absLocation)
                    }
                    else {
                        map[character] = mutableListOf(absLocation)
                    }
                }
            }
        }

        val antinodesLocation = mutableSetOf<Int>()
        for(pairs in map){
            for(i in 0..<pairs.value.size - 1){
                for(j in i + 1 ..<pairs.value.size){

                    antinodesLocation.add(pairs.value[i])
                    antinodesLocation.add(pairs.value[j])

                    val coordinateI = calculateCoordinatesFromAbsolute(pairs.value[i], columnSize)
                    val coordinateJ = calculateCoordinatesFromAbsolute(pairs.value[j], columnSize)

                    val rowDiff = coordinateJ.row - coordinateI.row
                    val columnDiff = coordinateJ.column - coordinateI.column

                    var antinode1Row = coordinateI.row - rowDiff
                    var antinode1Column = coordinateI.column - columnDiff

                    while(antinode1Row >=0 && antinode1Row < input.size &&
                        antinode1Column >=0 && antinode1Column < columnSize){

                        antinodesLocation.add(calculateAbsolutePosition(antinode1Row, antinode1Column, columnSize))

                        antinode1Row -= rowDiff
                        antinode1Column -= columnDiff
                    }

                    var antinode2Row = coordinateJ.row + rowDiff
                    var antinode2Column = coordinateJ.column + columnDiff

                    while(antinode2Row >=0 && antinode2Row < input.size &&
                        antinode2Column >=0 && antinode2Column < columnSize){

                        antinodesLocation.add(calculateAbsolutePosition(antinode2Row, antinode2Column, columnSize))

                        antinode2Row  += rowDiff
                        antinode2Column += columnDiff
                    }
                }
            }
        }

        return antinodesLocation.size
    }
}


fun main(){
    val lines = parseDayEight()
    val obj = DayEight(lines)
    println("Output for Day 8 Part 1: ${obj.dayEightPart1()}")
    println("Output for Day 8 Part 2: ${obj.dayEightPart2()}")

}
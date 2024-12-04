package aoc2024

import java.io.File

fun parseFileByLine(fileName: String) : List<List<Int>> {
    var file = File(fileName)
    var output = mutableListOf<List<Int>>()
    file.forEachLine { line ->
        var values = line.split(" ")
        var line = mutableListOf<Int>()
        for(value in values){
            line.add(value.toInt())
        }

        output.add(line)

    }

    return output
}

fun readFile(fileName: String) : String{
    var file = File(fileName)
    return file.readText()
}
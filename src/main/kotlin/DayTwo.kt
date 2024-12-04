package aoc2024

import kotlin.math.abs

enum class SortOder {
    NONE,
    AESC,
    DESC
}

fun isSafe(report: List<Int>) : Boolean {
    var sortOrder = SortOder.NONE
    sortOrder = if(report[1] > report[0]){
        SortOder.AESC
    } else if(report[1] < report[0]){
        SortOder.DESC
    } else{
        return false
    }

    for(index in 1..<report.size){
        if(sortOrder == SortOder.AESC &&
            (report[index] <= report[index - 1] ||
                    abs(report[index] - report[index - 1] ) > 3)){
            return false
        }

        if(sortOrder == SortOder.DESC &&
            (report[index] >= report[index - 1] ||
                    abs(report[index] - report[index - 1] ) > 3)){
            return false
        }
    }

    return true
}

fun day2Part1(matrix: List<List<Int>>) : Int{
    var safeCount = 0
    for (report in matrix){
        if(isSafe(report)){
            print("report: ${report.toString()}\n")
            safeCount++
        }

    }

    return safeCount
}



fun day2Part2(matrix: List<List<Int>>) : Int{

    var safeCount = 0
    for (report in matrix){
        if(!isSafe(report)) {
            for (i in report.indices) {
                var tempReport = report.toMutableList()
                tempReport.removeAt(i)
                if (isSafe(tempReport)) {
                    safeCount++
                    break
                }
            }
        }else{
            safeCount++
        }

    }
    return safeCount
}

fun main(){
    val matrix = parseFileByLine("src/main/resources/DayTwo.txt")
    print("Output day 2 part 1: ${day2Part1(matrix)}")
    print("Output day 2 part 2: ${day2Part2(matrix)}")
}
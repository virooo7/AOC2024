package aoc2024

import java.io.File
import kotlin.math.pow

fun parseFileDaySeven() : List<Pair<Long, List<Int>>> {
    val file = File("src/main/resources/DaySeven.txt")
    val result = mutableListOf<Pair<Long, List<Int>>>()
    file.forEachLine { line ->
        val splitArray = line.split(':')

        val key = splitArray[0].toLong()
        val values = splitArray[1].split(' ')
        result.add(Pair(key, values.toList().filter{ item -> item.isNotEmpty() }.map { item -> item.toInt() }))
    }

    return result
}

class DaySeven (val inputs: List<Pair<Long, List<Int>>>){

    fun  performOperation(a: Long, b: Int, operator: Char) : Long {
        return when (operator) {
            '0' -> a + b
            '1' ->  a * b
            '2' -> "$a$b".toLong()
            else -> throw Exception("Unexpected value")
        }
    }

    fun numberToByteString(value : Int, size: Int) : String {
        var temp = value
        var result = ""
        if(temp == 0) {
            result = "0"
        }
        while(temp > 0){
            val remainder = temp % 2
            temp /= 2
            result = remainder.toString() + result
        }

        if(result.length < size) {
            val prefix = List<Char>(size - result.length) {_: Int -> '0' }.joinToString(separator = "")
            result = prefix + result
        }

        return result
    }

    fun numberToByteStringThree(value : Int, size: Int) : String {
        var temp = value
        var result = ""
        if(temp == 0) {
            result = "0"
        }
        while(temp > 0){
            val remainder = temp % 3
            temp /= 3
            result = remainder.toString() + result
        }

        if(result.length < size) {
            val prefix = List<Char>(size - result.length) {_: Int -> '0' }.joinToString(separator = "")
            result = prefix + result
        }

        return result
    }


    fun day7Part1() : Long{
        var sum = 0L

        for(input in inputs){
            val size = input.second.size - 1
            val value = 2.0.pow(size.toDouble()).toInt()
            for(i in 0..< value){
                var byteString = numberToByteString(i, size)
                val desiredResult = input.first
                var result = input.second[0].toLong()
                for(j in 1 ..< input.second.size){
                    result = performOperation(result, input.second[j], byteString[j-1])
                }
                if(result == desiredResult){
                    sum += result
                    break
                }
            }
        }

        return sum
    }

    fun day7Part2() : Long{
        var sum = 0L

        for(input in inputs){
            val size = input.second.size - 1
            val value = 3.0.pow(size.toDouble()).toInt()
            for(i in 0..< value){
                var byteString = numberToByteStringThree(i, size)
                val desiredResult = input.first
                var result = input.second[0].toLong()
                for(j in 1 ..< input.second.size){
                    result = performOperation(result, input.second[j], byteString[j-1])
                }
                if(result == desiredResult){
                    sum += result
                    break
                }
            }
        }

        return sum
    }
}

fun main(){
    var input = parseFileDaySeven()
    val obj = DaySeven(input)
    println("Output Day 7 Part 1: ${obj.day7Part1()}")
    println("Output Day 7 Part 2: ${obj.day7Part2()}")
}
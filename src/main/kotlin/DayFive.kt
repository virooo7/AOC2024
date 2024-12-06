package aoc2024

import java.io.File

var dependencies = mutableMapOf<Int, MutableSet<Int>>()
var pages = mutableListOf<List<Int>>()

fun parseFile(){
    var file = File("src/main/resources/DayFive.txt")
    var lines = file.readLines()
    var index = 0
    var line = lines[index]
    while(line.isNotEmpty()){
        var numbers = line.split('|')
        if(dependencies.containsKey(numbers[0].toInt())){
            dependencies[numbers[0].toInt()]!!.add(numbers[1].toInt())
        }else{
            dependencies[numbers[0].toInt()] = mutableSetOf(numbers[1].toInt())
        }
        line = lines[++index]
    }

    index++

    while (index < lines.size){
        pages.add(lines[index].split(',').map { item -> item.toInt() })
        index++
    }
}

class DayFive(var graph : Map<Int, Set<Int>>, var pages: List<List<Int>>){

    fun day5Part1() : Int{

        var sum = 0

        for(page in pages){
            var validPage = true
            var midValue = 0
            for( i in 0 ..< page.size - 1){
                for(j in i+1..< page.size){
                    if(!graph.containsKey(page[i]) || graph[page[i]]?.contains(page[j]) == false){
                        validPage = false
                    }
                }
                if(i == (page.size / 2)){
                    midValue = page[i]
                }
            }
            if(validPage){
                sum += midValue
            }
        }

        return sum
    }

    fun day5Part2() : Int {
        var sum = 0

        for(page in pages){
            var validPage = true
            for( i in 0 ..< page.size - 1){
                for(j in i+1..< page.size){
                    if(!graph.containsKey(page[i]) || graph[page[i]]?.contains(page[j]) == false){
                        validPage = false
                    }
                }
            }
            if(!validPage){
                val sorted_page = page.sortedWith{v1, v2 ->
                    when {
                        graph.containsKey(v1) && graph[v1]!!.contains(v2) -> -1
                        graph.containsKey(v2) && graph[v2]!!.contains(v1) -> 1
                        else -> 0
                    }
                }

                sum += sorted_page[sorted_page.size / 2]
            }
        }

        return sum
    }
}

fun main (){
    parseFile()
    val obj = DayFive(dependencies, pages)
    println("Output day 5 part 1: ${obj.day5Part1()}")
    println("Output day 5 part 2: ${obj.day5Part2()}")

}
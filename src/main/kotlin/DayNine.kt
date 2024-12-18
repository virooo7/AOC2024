package aoc2024

import java.io.File

fun parseDayNine() : String{
    val file = File("src/main/resources/DayNine.txt")
    return file.readText()
}


class DayNine(val input : String) {

    fun day9Part1 () : Long {
        val output = mutableListOf<Int>()
        val mutableInput = input.toList().map { i -> i.digitToInt() }.toMutableList()
        var phaseOne = true
        var index = 0
        var checksum = 0L
        var checksumIndex = 0
        var id = 0
        var freeSpace = 0

        var rightSideFileIndex = input.length - 1
        var rightSideFileId = input.length / 2
        var rightSideFileSize = mutableInput[rightSideFileIndex]

        while(index <= rightSideFileIndex){
            if(phaseOne){
                checksum += (checksumIndex * id)
                output.add(id)
                mutableInput[index]--
                checksumIndex++

                if(mutableInput[index] == 0){
                    index++
                    phaseOne = false
                    freeSpace = mutableInput[index]
                }
            }else{
                if(freeSpace <= 0) {
                    phaseOne = true
                    index++
                    id++
                } else{

                    if(rightSideFileSize <=0) {
                        rightSideFileIndex -= 2
                        rightSideFileSize = mutableInput[rightSideFileIndex]
                        rightSideFileId--
                    }

                    checksum += (rightSideFileId*checksumIndex)
                    output.add(rightSideFileId)
                    mutableInput[index]--
                    mutableInput[rightSideFileIndex]--
                    freeSpace--
                    rightSideFileSize--
                    checksumIndex++
                }
            }

        }

        return checksum
    }

    fun day9Part2 () : Long {
        val output = mutableListOf<Int>()
        val mutableInput = input.toList().map { i -> i.digitToInt() }.toMutableList()
        var phaseOne = true
        var index = 0
        var checksum = 0L
        var checksumIndex = 0
        var id = 0
        var freeSpace = 0

        var rightSideFileIndex = input.length - 1
        var rightSideFileId = input.length / 2
        var rightSideFileSize = mutableInput[rightSideFileIndex]

        while(index <= rightSideFileIndex){
            if(phaseOne){
                checksum += (checksumIndex * id)
                output.add(id)
                mutableInput[index]--
                checksumIndex++

                if(mutableInput[index] == 0){
                    index++
                    phaseOne = false
                    freeSpace = mutableInput[index]
                }
            }else{
                if(freeSpace <= 0) {
                    phaseOne = true
                    index++
                    id++
                } else{

                    if(rightSideFileSize <=0) {
                        rightSideFileIndex -= 2
                        rightSideFileSize = mutableInput[rightSideFileIndex]
                        rightSideFileId--
                    }


                    checksum += (rightSideFileId*checksumIndex)
                    output.add(rightSideFileId)
                    mutableInput[index]--
                    mutableInput[rightSideFileIndex]--
                    freeSpace--
                    rightSideFileSize--
                    checksumIndex++
                }
            }

        }

        return checksum
    }
}

fun main(){
    val text = parseDayNine()
    val obj = DayNine(text)
    println("Output of day nine part 1: ${obj.day9Part1()}")
}
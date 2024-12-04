package aoc2024

fun parseMulFunction(mul_string: String) : Pair<Int, Int>{
    var regex_number = "\\d+".toRegex()
    var regex_comma = ",".toRegex()

    val first_number_match = regex_number.find(mul_string, 0)
    val regex_comm_match = regex_comma.find(mul_string, first_number_match?.range?.last!!)
    val second_number_match = regex_number.find(mul_string, regex_comm_match?.range?.last!!)

    return Pair<Int, Int>(first_number_match?.value?.toInt()!!, second_number_match?.value?.toInt()!!)

}

fun day3Part1(text: String) : Int {
    var regex = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex()
    var mul_index = 0
    var sum = 0
    while(mul_index < text.length){
        var match = regex.find(text, mul_index)
        if(match != null){
            println("value: ${match.value}")
            var numbers = parseMulFunction(match.value)
            sum += (numbers.first * numbers.second)
        }
        mul_index = match?.range?.last ?: text.length
    }

    return sum
}

fun day3Part2(text: String) : Int {

    var do_val = true
    var mul_regex = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex()
    var do_regex = "do\\(\\)".toRegex()
    var dont_regex = "don't\\(\\)".toRegex()
    var mul_index = 0
    var sum = 0
    while(mul_index < text.length){

        var mul_match = mul_regex.find(text, mul_index)
        var dont_match = dont_regex.find(text, mul_index)
        var do_match = do_regex.find(text, mul_index)

        if(mul_match != null){
            if(dont_match != null && dont_match.range.last < mul_match.range.first){
                do_val = false
            }

            if(do_match != null &&
                do_match.range.last < mul_match.range.first){
                do_val = true
            }

            if(do_val) {
                println("value: ${mul_match.value}")
                var numbers = parseMulFunction(mul_match.value)
                sum += (numbers.first * numbers.second)
            }
        }

        mul_index = mul_match?.range?.last ?: text.length
    }

    return sum
}


fun main(){
    var text = readFile("src/main/resources/DayThree.txt")
    // println("Output Day3 part 1: ${day3Part1(text)}")
    println("Output Day3 part 2: ${day3Part2(text)}")
}

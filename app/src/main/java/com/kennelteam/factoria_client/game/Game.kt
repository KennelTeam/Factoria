package com.kennelteam.factoria_client.game
import java.lang.Exception
import kotlin.random.Random

val simple_primes = arrayOf(2, 3, 5, 7, 11, 13)
val medium_primes = arrayOf(17, 23, 29, 31, 37, 41)
val hard_primes = arrayOf(43, 47, 53, 59, 61, 67, 71, 73)
val simple_numbers = arrayOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53,
    59, 61, 67, 71, 73)

class Game {
    private val hardness : Int = 4
    private var num : Int = 1
    private var simple_divs = mutableListOf<MutableList<Int>>()
    private var score : Int = 0
    private var progress = 0
    init {
        var cur_div : Int
        simple_divs.add(mutableListOf())
        for (i in 1..hardness) {
            cur_div = simple_primes[Random.nextInt(simple_primes.size)]
            num *= cur_div
            simple_divs.last().add(cur_div)
        }
        simple_divs.add(mutableListOf())
        for (i in 1..(hardness / 2)) {
            cur_div = medium_primes[Random.nextInt(medium_primes.size)]
            num *= cur_div
            simple_divs.last().add(cur_div)
        }
        simple_divs.add(mutableListOf())
        for (i in 1..(hardness / 3)) {
            cur_div = hard_primes[Random.nextInt(hard_primes.size)]
            num *= cur_div
            simple_divs.last().add(cur_div)
        }
        simple_divs.reverse()
    }

    fun get_num() : Int = this.num

    fun get_score() : Int = this.score

    fun get_progress() : Int = this.progress

    fun get_hardness() : Int = this.hardness

    fun get_question() : Array<Int> {
        /*val right_answer = this.simple_divs.elementAt(Random.nextInt(this.simple_divs.size))
        simple_numbers.shuffle()*/
        simple_divs.last().shuffle()
        val right_answer = this.simple_divs.last().last()
        var options: Array<Int> = arrayOf()
        if (simple_primes.contains(right_answer)){
            options = simple_primes
        }
        if (medium_primes.contains(right_answer)) {
            options = medium_primes
        }
        if (hard_primes.contains(right_answer)) {
            options = hard_primes
        }
        if (options.size == 0) {
            throw Exception("Not found prime!")
        }
        options.shuffle()
        val variants = mutableListOf<Int>(right_answer)
        for (i in 1..options.size) {
            if (!this.simple_divs.last().contains(options[i])) {
                variants.add(options[i])
                if (variants.size == 3) {
                    break
                }
            }
        }
        variants.shuffle()
        return variants.toIntArray().toTypedArray()
    }

    fun check_answer(answer: Int) : Boolean {
        return when (this.num % answer) {
            0 -> {
                this.num /= answer
                //if (this.num % answer != 0) { this.simple_divs.remove(answer) }
                this.simple_divs.last().removeLast()
                if (this.simple_divs.last().size == 0) {
                    this.simple_divs.removeLast()
                }
                score++
                progress++
                true
            }
            else -> {
                score--
                false
            }
        }
    }

    fun end_game() : Boolean = when (this.num) {
        1 -> true
        else -> false
    }
}
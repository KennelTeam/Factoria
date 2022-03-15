package com.kennelteam.factoria_client.game
import kotlin.random.Random

val simple_primes = arrayOf(2, 3, 5, 7, 11)
val medium_primes = arrayOf(13, 17, 23, 29, 31)
val hard_primes = arrayOf(37, 41, 43, 47, 53, 59, 61, 67, 71, 73)
val simple_numbers = arrayOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53,
    59, 61, 67, 71, 73)

class Game {
    private val hardness : Int = 4
    private var num : Int = 1
    private var simple_divs = mutableSetOf<Int>()
    private var score : Int = 0
    private var progress = 0
    init {
        var cur_div : Int
        for (i in 1..hardness) {
            cur_div = simple_primes[Random.nextInt(simple_primes.size)]
            num *= cur_div
            simple_divs.add(cur_div)
        }
        for (i in 1..(hardness / 2)) {
            cur_div = medium_primes[Random.nextInt(medium_primes.size)]
            num *= cur_div
            simple_divs.add(cur_div)
        }
        for (i in 1..(hardness / 3)) {
            cur_div = hard_primes[Random.nextInt(hard_primes.size)]
            num *= cur_div
            simple_divs.add(cur_div)
        }
    }

    fun get_num() : Int = this.num

    fun get_score() : Int = this.score

    fun get_progress() : Int = this.progress

    fun get_hardness() : Int = this.hardness

    fun get_question() : Array<Int> {
        val right_answer = this.simple_divs.elementAt(Random.nextInt(this.simple_divs.size))
        simple_numbers.shuffle()
        var wrong_answer_idx = 0
        while (this.num % simple_numbers[wrong_answer_idx] == 0) { wrong_answer_idx++ }
        val wrong_answer1 = simple_numbers[wrong_answer_idx]
        simple_numbers.shuffle()
        while (this.num % simple_numbers[wrong_answer_idx] == 0 &&
            simple_numbers[wrong_answer_idx] != wrong_answer1) { wrong_answer_idx++ }
        val wrong_answer2 = simple_numbers[wrong_answer_idx]
        val variants = arrayOf(right_answer, wrong_answer1, wrong_answer2)
        variants.shuffle()
        return variants
    }

    fun check_answer(answer: Int) : Boolean {
        return when (this.num % answer) {
            0 -> {
                this.num /= answer
                if (this.num % answer != 0) { this.simple_divs.remove(answer) }
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
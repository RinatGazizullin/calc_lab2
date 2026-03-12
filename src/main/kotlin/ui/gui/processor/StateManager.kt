package ui.gui.processor

import core.basic.Subscriber
import core.processor.ExpressionProcessor

class StateManager(val expressionProcessor: ExpressionProcessor) {
    val inputs = expressionProcessor.exps.map { exp -> exp.body }.toMutableList()
    val tokens = mutableListOf("x", "y")
    var epsilon = "1E-6"
    val borders: MutableMap<String, Pair<String, String>> = mutableMapOf()
    private val subscribers: MutableSet<Subscriber> = mutableSetOf()

    init {
        for (token in tokens) {
            borders[token] = Pair("0", "1")
        }
    }

    fun subscribe(subscriber: Subscriber) {
        subscribers.add(subscriber)
    }

    fun sendMessage() {
        subscribers.forEach { subscriber -> subscriber.changed() }
    }

    fun first(): String {
        val tokens = expressionProcessor.tokens.sorted()

        if (tokens.size > 1) {
            return tokens[0]
        }
        return "1"
    }

    fun second(): String {
        val tokens = expressionProcessor.tokens.sorted()

        if (tokens.size > 2) {
            return tokens[1]
        }
        return "2"
    }
}
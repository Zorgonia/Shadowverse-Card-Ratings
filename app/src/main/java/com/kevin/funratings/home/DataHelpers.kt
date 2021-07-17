package com.kevin.funratings.home

import com.kevin.funratings.model.HomeCard

object DataHelpers {
    fun getLocales(data: String): List<String> {
        val locales = mutableListOf<String>()
        var toParse = data
        var current = toParse.findAnyOf(listOf("data-lang-type=\""))
        while (current != null) {
            val endIndex = toParse.findAnyOf(listOf("\""), current.first + current.second.length)
            val locale = toParse.substring(current.first + current.second.length, endIndex?.first ?: current.first + current.second.length + 2)
            locales.add(locale)
            toParse = toParse.substring(endIndex?.first?.plus(endIndex.second.length) ?: current.first + current.second.length + 2)
            current = toParse.findAnyOf(listOf("data-lang-type=\""))
           // Log.d("woops", "$current")
        }
        return locales
    }

    fun getCardStrings(data: String): List<String> {
        val cardStrings = mutableListOf<String>()
        var toParse = data
        var current = toParse.findAnyOf(listOf("class=\"el-card-visual-content\""))
        while (current != null) {
            val startIndex = current.first - 12
            val endIndex = toParse.findAnyOf(listOf("</a>"), startIndex) ?: Pair(startIndex + 1, " ")
            cardStrings.add(toParse.substring(startIndex, endIndex.first))
            toParse = toParse.substring(endIndex.first)
            current = toParse.findAnyOf(listOf("class=\"el-card-visual-content\""))
        }
        return cardStrings
    }

    fun parseCardStrings(cardStrings: List<String>): List<HomeCard> {
        val cardList = mutableListOf<HomeCard>()
        for (card in cardStrings) {
            val id = card.substring(1, 10)
            val image = findEndingQuote(card, "data-src=\"", "?")
            val textImage = findEndingQuote(card, "<img src=\"", "?")
            val cardName = findEndingQuote(card, "el-card-visual-name\">", "</")
            cardList.add(HomeCard(id, image, textImage, cardName))
        }
        return cardList
    }

    //only gives the class strings, doesnt associate numbers but right now theyre in order on the html
    fun parseClasses(data: String): List<String> {
        val classes = mutableListOf<String>()
        var toParse = data
        var current = toParse.findAnyOf(listOf("/?clan="))
        while (current != null) {
            val classNamePosition = toParse.findAnyOf(listOf("class=\"el-balloon\">")) ?: return classes
            val endTagPosition = toParse.findAnyOf(listOf("</p>"), classNamePosition.first + classNamePosition.second.length + 1) ?: return classes
            val className = toParse.substring(classNamePosition.first + classNamePosition.second.length + 1, endTagPosition.first)
            classes.add(className)
            toParse = toParse.substring(endTagPosition.first)
            current = toParse.findAnyOf(listOf("/?clan="))
        }
        return classes
    }
    private fun findEndingQuote(original:String, toFind: String, endingLimiter: String): String {
        original.findAnyOf(listOf(toFind))?.let {
            val end = original.findAnyOf(listOf(endingLimiter), it.first + it.second.length)
            return original.substring(it.first + it.second.length, end?.first ?: return "")
        }
        return ""
    }
}
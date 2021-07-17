package com.kevin.funratings.detail

import android.util.Log
import com.kevin.funratings.model.*

object CardDetailHelpers {

    fun extractCardInfo(data: String, locale:String ): CardDetail {
        val isFollower = data.findAnyOf(listOf("is-atk")) != null && (if (data.indexOf("card-relative") != -1) data.indexOf("is-atk") < data.indexOf("card-relative") else true)
        val name = findLastDataEnding(data, "breadcrumb-content-list\">", "</li>")
        val image = getDataAndEndingPoint(data.substringAfter("card-main-image\">"), "src=\"", "?")
        val imageText = getDataAndEndingPoint(data.substringAfter("card-main-image-cardname"), "src=\"", "?")
        val effect = getDataAndEndingPoint(data, "\"card-content-skill\">", "</p>")
        val description = getDataAndEndingPoint(data, "\"card-content-description\">", "</p>")
        val sidebar = dealWithSidebar(data, locale)

        val related = getRelatedCards(data)
        val relatedText = if(related.isEmpty()) Pair(0,"") else getDataAndEndingPoint(data, "card-relative-heading\">", "</h2>")

        if (isFollower) {
            val evoImage =
                getDataAndEndingPoint(data.substringAfterLast("card-main-image\">"), "src=\"", "?")
            val evoImageText = getDataAndEndingPoint(
                data.substringAfterLast("card-main-image-cardname"),
                "src=\"",
                "?"
            )

            val atk = getDataAndEndingPoint(data, "atk\">", "</p>")
            val hp = getDataAndEndingPoint(data, "life\">", "</p>")

            val textAfterNonEvoDescription = data.substring(description.first)
            val evoAtk = getDataAndEndingPoint(textAfterNonEvoDescription,"atk\">", "</p>")
            val evoHp = getDataAndEndingPoint(textAfterNonEvoDescription,"life\">", "</p>")
            val evoEffect = getDataAndEndingPoint(textAfterNonEvoDescription,"\"card-content-skill\">", "</p>")
            val evoDescription = getDataAndEndingPoint(textAfterNonEvoDescription,"\"card-content-description\">", "</p>")

            val preEvoWord = getDataAndEndingPoint(data, "l-inline-block\">", "</p>")
            val postEvoWord = findLastDataEnding(data, "l-inline-block\">", "</p>")


            return FollowerCardDetail(name.second, sidebar, image.second,imageText.second,CardEffect(effect.second.trim(),description.second,preEvoWord.second.trim(), atk.second.trim(),hp.second.trim()),related, relatedText.second,CardEffect(evoEffect.second.trim(),evoDescription.second.trim(),postEvoWord.second.trim(),evoAtk.second.trim(),evoHp.second.trim()), evoImage.second, evoImageText.second)

        }
        return CardDetail(name.second,sidebar,image.second,imageText.second, CardEffect(effect.second.trim(),description.second), related, relatedText.second)

    }

    private fun dealWithSidebar(data: String, locale:String): SidebarCardDetail {
        val side = SidebarCardDetail()
        val tempSidebardata = mutableListOf<String>()
        var toParse = data
        var current = toParse.findAnyOf(listOf("el-label\">"))
        val localeJPTW = locale == "ja" || locale == "zh-tw"
        for (el in (if (localeJPTW) 0..6 else 0..5) ){
            current?.let { curr ->
                val stringUpToCurr = toParse.substring(curr.first)
                if (el == 4) {
                    val firstPart = stringUpToCurr.substring(curr.second.length, stringUpToCurr.indexOf("</span>") - 1)
                    val vialReg =
                        getDataAndEndingPoint(toParse.substring(curr.first), "left\">", "</p>")
                    val startSpan = toParse.substring(curr.first).findAnyOf(listOf("<span>"))
                    startSpan?.let { star ->
                        val end = toParse.substring(curr.first)
                            .substring(star.first + star.second.length)
                            .findAnyOf(listOf("</span>"))
                        end?.let {
                            val vialPrem = toParse.substring(curr.first).substring(
                                star.first + star.second.length,
                                star.first + star.second.length + end.first
                            )
                            tempSidebardata.add("$firstPart ${vialReg.second} / ${if (vialReg.second == "-") vialReg.second else vialPrem}")
                        }
                    }
                } else {
                    val firstPart = stringUpToCurr.substring(curr.second.length, stringUpToCurr.indexOf("</span>") - 1)
                    val startSpan = toParse.substring(curr.first)
                        .findAnyOf(listOf(if ((localeJPTW && el == 6) || (!localeJPTW && el == 5)) "</span>" else "<span>"))
                    startSpan?.let { star ->
                        val end = toParse.substring(curr.first)
                            .substring(star.first + star.second.length)
                            .findAnyOf(listOf("</span>"))
                        end?.let {
                            tempSidebardata.add("$firstPart ${toParse.substring(curr.first).substring(star.first + star.second.length + 2, star.first + star.second.length + end.first - 1)}")
                        }
                    }

                }
                toParse = toParse.substring(curr.first + curr.second.length)
                current = toParse.findAnyOf(listOf("el-label\">"))
            }
        }
        Log.d("shit", "$tempSidebardata")
        side.type = tempSidebardata[0]
        side.clan = tempSidebardata[1]
        side.rarity = tempSidebardata[2]
        side.vialCost = tempSidebardata[3]
        side.vialRefund = tempSidebardata[4]
        side.cv = if(localeJPTW) tempSidebardata[5] else ""
        side.cardpack = if(localeJPTW) tempSidebardata[6] else tempSidebardata[5]

        return side
    }

    private fun getRelatedCards(data: String): List<HomeCard> {
        val listRelated = mutableListOf<HomeCard>()
        if (data.indexOf("card-relative") == -1 ) return listRelated
        var toParse = data.substringAfter("card-relative-cards\">")
        toParse = toParse.substringAfter("<a ")
        var curr = toParse.findAnyOf(listOf("href=\"/card/"))
        while (curr != null) {
            val id = getDataAndEndingPoint(toParse, "href=\"/card/", "\"")
            val name = getDataAndEndingPoint(toParse, "detail-name\">", "</p>")
            val image = getDataAndEndingPoint(toParse, "<img src=\"", "?")
            val imageText = getDataAndEndingPoint(toParse.substring(image.first), "<img src=\"", "?")
            listRelated.add(HomeCard(id.second, image.second, imageText.second, name.second))
            toParse = toParse.substringAfter("<a ")
            curr = toParse.findAnyOf(listOf("href=\"/card/"))
        }
        return listRelated
    }

    private fun getDataAndEndingPoint(data: String, start: String, end: String):Pair<Int, String> {
        val startingPoint = data.findAnyOf(listOf(start))
        startingPoint?.let { star ->
            val endingPoint = data.findAnyOf(listOf(end), star.first + star.second.length)
            endingPoint?.let { en ->
                return Pair(en.first + en.second.length,data.substring(star.first + star.second.length, en.first))
            }
        }
        return Pair(0, "")
    }



    private fun findLastDataEnding(data: String, start: String, end: String):Pair<Int, String> {
        val startingPoint = data.findLastAnyOf(listOf(start))
        startingPoint?.let { star ->
            val endingPoint = data.findAnyOf(listOf(end), star.first + star.second.length)
            endingPoint?.let { en ->
                return Pair(en.first + en.second.length,data.substring(star.first + star.second.length, en.first))
            }
        }
        return Pair(0, "")
    }
}
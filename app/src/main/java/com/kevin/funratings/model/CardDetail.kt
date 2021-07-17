package com.kevin.funratings.model

open class CardDetail(
    open val name: String,
    open val sidebarDetail: SidebarCardDetail,
    open val image: String,
    open val imageText: String,
    open val cardEffect: CardEffect,
    open val relatedCards: List<HomeCard>,
    open val relatedText: String
) {
    override fun toString(): String {
        return "name $name type $sidebarDetail $image $imageText $cardEffect $relatedCards"
    }
}
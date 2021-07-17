package com.kevin.funratings.model

data class FollowerCardDetail(
    override val name: String,
    override val sidebarDetail: SidebarCardDetail,
    override val image: String,
    override val imageText: String,
    override var cardEffect: CardEffect,
    override val relatedCards: List<HomeCard>,
    override val relatedText: String,
    var evoCardEffect: CardEffect,
    val evoImage: String,
    val evoImageText: String,
    ): CardDetail(name, sidebarDetail, image, imageText, cardEffect, relatedCards,relatedText)

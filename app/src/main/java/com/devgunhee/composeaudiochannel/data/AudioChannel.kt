package com.devgunhee.composeaudiochannel.data

import androidx.annotation.DrawableRes

data class Program(
    val channelId: Int,
    val title: String,
    val artist: String,
    val runningTime: String,
    val totalSeconds: Int,
    val currentSeconds: Int = 0,
    @DrawableRes val image: Int
)

data class AudioChannel(
    val id: Int,
    val number: String,
    val name: String,
    val selected: Boolean = false,
    val program: Program? = null,
)
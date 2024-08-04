package com.devgunhee.composeaudiochannel.data

import javax.inject.Inject

class AudioChannelRepository @Inject constructor(
    private val audioDataSource: AudioDataSource
) {
    fun getAudioChannels() = audioDataSource.audioChannels

    fun getNextProgram(audioChannel: AudioChannel) =
        audioDataSource.programs
            .filter { it.channelId == audioChannel.id }
            .nextProgram(audioChannel.program)

    private fun List<Program>.nextProgram(program: Program?) = get((indexOfFirst { it.title == program?.title } + 1) % size)
}
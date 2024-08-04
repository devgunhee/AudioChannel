package com.devgunhee.composeaudiochannel.ui

import androidx.lifecycle.ViewModel
import com.devgunhee.composeaudiochannel.data.AudioChannel
import com.devgunhee.composeaudiochannel.data.AudioChannelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AudioChannelViewModel @Inject constructor(
    private val audioChannelRepository: AudioChannelRepository
) : ContainerHost<State, String>, ViewModel() {

    override val container = container<State, String>(State())

    init {
        intent {
            reduce {
                state.copy(
                    audioChannels = audioChannelRepository.getAudioChannels()
                )
            }
        }
    }

    fun updateProgram(position: Int) {
        intent {
            val program = updateProgram(state.audioChannels[position])

            reduce {
                if (state.currentChannelPosition == position) {
                    state.copy(
                        audioChannels = state.audioChannels.toMutableList().apply {
                            this[position] = program
                        },
                        currentChannel = program
                    )

                } else {
                    state.copy(
                        audioChannels = state.audioChannels.toMutableList().apply {
                            this[position] = program
                        }
                    )
                }
            }
        }
    }

    fun tuneChannel(position: Int) {
        intent {
            reduce {
                state.copy(
                    audioChannels = state.audioChannels.mapIndexed { index, audioChannel ->
                        audioChannel.copy(selected = index == position)
                    },
                    currentChannel = state.audioChannels[position],
                    currentChannelPosition = position
                )
            }
        }
    }

    private fun updateProgram(audioChannel: AudioChannel): AudioChannel =
        when {
            audioChannel.program == null ||
                    audioChannel.program.totalSeconds == audioChannel.program.currentSeconds -> {
                audioChannel.copy(
                    program = audioChannelRepository.getNextProgram(audioChannel)
                )
            }

            else -> {
                audioChannel.copy(
                    program = audioChannel.program.copy(
                        currentSeconds = audioChannel.program.currentSeconds + 1
                    )
                )
            }
        }
}

data class State(
    val audioChannels: List<AudioChannel> = emptyList(),
    val currentChannel: AudioChannel? = null,
    val currentChannelPosition: Int = 0
)
package com.devgunhee.composeaudiochannel.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devgunhee.composeaudiochannel.data.AudioChannel
import com.devgunhee.composeaudiochannel.data.Program
import com.devgunhee.composeaudiochannel.ui.infinitePager.HorizontalInfinitePager
import com.devgunhee.composeaudiochannel.ui.infinitePager.rememberInfinitePagerState
import com.devgunhee.composeaudiochannel.ui.theme.ComposeAudioChannelTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState

const val PAGE_COUNT = 5
const val PAGER_HEIGHT = 3.0f
const val EPG_HEIGHT = 1.0f
const val PROGRAM_UPDATE_PERIOD = 1_000L
const val SELECTED_IMAGE_RATIO = 0.95F
const val UNSELECTED_IMAGE_RATIO = 0.8F
const val EXTRA_CHANNEL_CONTAINER = 120
const val NORMAL_CHANNEL_CONTAINER = 80

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeAudioChannelTheme {
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxSize()
                ) {
                    AudioChannel(lifecycleOwner = this@MainActivity)
                }
            }
        }
    }
}

@Composable
fun AudioChannel(
    modifier: Modifier = Modifier,
    viewModel: AudioChannelViewModel = viewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    val state by viewModel.collectAsState()

    Column(modifier = modifier) {
        if (state.audioChannels.isNotEmpty())
            Pager(
                modifier = Modifier.weight(PAGER_HEIGHT),
                list = state.audioChannels,
                onUpdate = { viewModel.updateProgram(it) },
                onTuneChannel = { viewModel.tuneChannel(it) },
                lifecycleOwner = lifecycleOwner
            )

        if (state.currentChannel != null)
            EpgContainer(
                modifier = Modifier.weight(EPG_HEIGHT),
                audioChannel = state.currentChannel!!
            )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Pager(
    modifier: Modifier = Modifier,
    list: List<AudioChannel>,
    onUpdate: (Int) -> Unit,
    onTuneChannel: (Int) -> Unit,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val infinitePagerState = rememberInfinitePagerState(itemCount = { list.size })

    LaunchedEffect(infinitePagerState) {
        snapshotFlow { infinitePagerState.middlePage }.collect { page ->
            onTuneChannel(page)
        }
    }

    HorizontalInfinitePager(
        state = infinitePagerState,
        modifier = modifier.background(Color.Gray),
        pageSize = object : PageSize {
            override fun Density.calculateMainAxisPageSize(
                availableSpace: Int,
                pageSpacing: Int
            ): Int {
                return (availableSpace - 2 * pageSpacing) / PAGE_COUNT
            }
        },
        verticalAlignment = Alignment.CenterVertically,
    ) { page ->
        DisposableEffect(lifecycleOwner) {
            var job: Job? = null
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    job?.cancel()
                    job = CoroutineScope(Dispatchers.Default).launch {
                        while (isActive) {
                            delay(PROGRAM_UPDATE_PERIOD)
                            onUpdate(page)
                        }
                    }
                } else if (event == Lifecycle.Event.ON_STOP) {
                    job?.cancel()
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                job?.cancel()
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }

        Channel(audioChannel = list[page])
    }
}

@Composable
fun Channel(
    modifier: Modifier = Modifier,
    audioChannel: AudioChannel,
    maxWidthSize : Int = LocalConfiguration.current.screenWidthDp / PAGE_COUNT,
    maxHeightSize : Int = (LocalConfiguration.current.screenHeightDp * (PAGER_HEIGHT / 2) / (PAGER_HEIGHT + EPG_HEIGHT)).toInt(),
    containerSize: Int = maxWidthSize.coerceAtMost(maxHeightSize)
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ImageContainer(
            imageRes = audioChannel.program?.image,
            containerSize = containerSize,
            selected = audioChannel.selected
        )

        when {
            containerSize >= EXTRA_CHANNEL_CONTAINER  -> {
                ExtraChannelContainer(
                    audioChannel = audioChannel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(containerSize.dp)
                )
            }
            containerSize >= NORMAL_CHANNEL_CONTAINER  -> {
                NormalChannelContainer(
                    audioChannel = audioChannel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(containerSize.dp)
                )
            }
            else  -> {
                SmallChannelContainer(
                    audioChannel = audioChannel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(containerSize.dp)
                )
            }
        }
    }
}

@Composable
fun ImageContainer(
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int? = null,
    containerSize: Int,
    selected: Boolean,
    shape: Shape = CircleShape
) {
    Box(modifier = modifier.size(containerSize.dp), contentAlignment = Alignment.Center) {
        Image(
            painter = if (imageRes == null) ColorPainter(MaterialTheme.colorScheme.primary) else painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(if (selected) containerSize.dp * SELECTED_IMAGE_RATIO else containerSize.dp * UNSELECTED_IMAGE_RATIO)
                .clip(shape)
        )
    }
}

@Composable
fun ExtraChannelContainer(
    modifier: Modifier = Modifier,
    audioChannel: AudioChannel
) {
    Column (
        modifier = modifier
            .padding(3.dp)
            .border(1.dp, MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(10.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = audioChannel.number, color = MaterialTheme.colorScheme.onPrimary, fontSize= 15.sp)
        Text(text = audioChannel.name, color = MaterialTheme.colorScheme.onSecondary, fontSize= 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)

        if (audioChannel.program != null) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = audioChannel.program.title, color = MaterialTheme.colorScheme.onPrimary, fontSize= 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = audioChannel.program.artist, color = MaterialTheme.colorScheme.onSecondary, fontSize= 8.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(5.dp))
            LinearProgressIndicator(
                progress = audioChannel.program.currentSeconds.toFloat() / audioChannel.program.totalSeconds.toFloat(),
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSecondary,
                trackColor = MaterialTheme.colorScheme.secondary)
            Text(text = audioChannel.program.runningTime, color = MaterialTheme.colorScheme.onSecondary, fontSize= 7.sp)
        }
    }
}

@Composable
fun NormalChannelContainer(
    modifier: Modifier = Modifier,
    audioChannel: AudioChannel
) {
    Column (
        modifier = modifier
            .padding(3.dp)
            .border(1.dp, MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(10.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (audioChannel.program != null) {
            Text(text = audioChannel.program.title, color = MaterialTheme.colorScheme.onPrimary, fontSize= 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = audioChannel.program.artist, color = MaterialTheme.colorScheme.onPrimary, fontSize= 8.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(5.dp))
            LinearProgressIndicator(
                progress = audioChannel.program.currentSeconds.toFloat() / audioChannel.program.totalSeconds.toFloat(),
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSecondary,
                trackColor = MaterialTheme.colorScheme.secondary)
            Text(text = audioChannel.program.runningTime, color = MaterialTheme.colorScheme.onPrimary, fontSize= 7.sp)
        }
    }
}

@Composable
fun SmallChannelContainer(
    modifier: Modifier = Modifier,
    audioChannel: AudioChannel
) {
    Column (
        modifier = modifier
            .padding(3.dp)
            .border(1.dp, MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(10.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (audioChannel.program != null) {
            LinearProgressIndicator(
                progress = audioChannel.program.currentSeconds.toFloat() / audioChannel.program.totalSeconds.toFloat(),
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSecondary,
                trackColor = MaterialTheme.colorScheme.secondary)
        }
    }
}

private fun epgContainerConstraints(): ConstraintSet {
    return ConstraintSet {
        val startLine = createGuidelineFromStart(0.1f)
        val endLine = createGuidelineFromEnd(0.1f)

        val programImage = createRefFor("programImage")
        val spacer = createRefFor("spacer")
        val programContainer = createRefFor("programContainer")

        constrain(programImage) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(startLine)
            end.linkTo(spacer.start)
        }

        constrain(spacer) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(programImage.end)
            end.linkTo(programContainer.start)
        }

        constrain(programContainer) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(spacer.end)
            end.linkTo(endLine)
            width = Dimension.fillToConstraints
        }
    }
}

@Composable
fun EpgContainer(
    modifier: Modifier = Modifier,
    audioChannel: AudioChannel
) {
    BoxWithConstraints(modifier = modifier) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp),
            constraintSet = epgContainerConstraints()
        ) {
            Image(painter = if (audioChannel.program == null) ColorPainter(MaterialTheme.colorScheme.primary) else painterResource(audioChannel.program.image),
                contentDescription = null,
                modifier = Modifier
                    .size(maxHeight / 2)
                    .layoutId("programImage"))

            Spacer(modifier = Modifier
                .width(maxWidth / 10)
                .layoutId("spacer"))

            ConstraintLayout(modifier = Modifier.layoutId("programContainer")) {
                if (audioChannel.program != null) EpgProgramContainer(program = audioChannel.program)
            }
        }
    }
}

@Composable
fun EpgProgramContainer(
    modifier: Modifier = Modifier,
    program: Program,
) {
    BoxWithConstraints(modifier = modifier) {
        if (maxHeight > 60.dp)
            NormalEpgProgramContainer(program = program)
        else
            SmallEpgProgramContainer(program = program)
    }
}

@Composable
fun NormalEpgProgramContainer(
    modifier: Modifier = Modifier,
    program: Program,
) {
    Column(modifier = modifier) {
        Text(text = program.title, color = MaterialTheme.colorScheme.onPrimary, fontSize= 15.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = program.artist, color = MaterialTheme.colorScheme.onSecondary, fontSize= 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        LinearProgressIndicator(
            progress = program.currentSeconds.toFloat() / program.totalSeconds.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp),
            color = MaterialTheme.colorScheme.onSecondary,
            trackColor = MaterialTheme.colorScheme.secondary)
        Text(text = program.runningTime, color = MaterialTheme.colorScheme.onSecondary, fontSize= 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun SmallEpgProgramContainer(
    modifier: Modifier = Modifier,
    program: Program,
) {
    LinearProgressIndicator(
        progress = program.currentSeconds.toFloat() / program.totalSeconds.toFloat(),
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.onSecondary,
        trackColor = MaterialTheme.colorScheme.secondary
    )
}
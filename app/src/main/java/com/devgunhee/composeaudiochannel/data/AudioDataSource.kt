package com.devgunhee.composeaudiochannel.data

import com.devgunhee.composeaudiochannel.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioDataSource @Inject constructor() {
    val programs = listOf(
        Program(
            channelId = 1,
            title = "For Her Chill Upbeat Summel Travel Vlog and IG Music Royalty Free Use",
            artist = "Liderc",
            runningTime = "2:13",
            totalSeconds = 133,
            image = R.drawable.image_album_1
        ),
        Program(
            channelId = 1,
            title = "Sunlit Rhythms",
            artist = "Top Flow",
            runningTime = "1:45",
            totalSeconds = 105,
            image = R.drawable.image_album_2
        ),
        Program(
            channelId = 1,
            title = "Movement",
            artist = "SoulProd Music",
            runningTime = "2:35",
            totalSeconds = 155,
            image = R.drawable.image_album_3
        ),
        Program(
            channelId = 1,
            title = "Better Day",
            artist = "Penguin music",
            runningTime = "1:30",
            totalSeconds = 90,
            image = R.drawable.image_album_4
        ),
        Program(
            channelId = 1,
            title = "Funk in Kingdom",
            artist = "White Records",
            runningTime = "1:03",
            totalSeconds = 63,
            image = R.drawable.image_album_5
        ),
        Program(
            channelId = 2,
            title = "Perfect Beauty",
            artist = "Good B Music",
            runningTime = "7:20",
            totalSeconds = 440,
            image = R.drawable.image_album_6
        ),
        Program(
            channelId = 2,
            title = "Groovy Ambient Funk",
            artist = "Mood mode",
            runningTime = "2:16",
            totalSeconds = 136,
            image = R.drawable.image_album_7
        ),
        Program(
            channelId = 2,
            title = "Drive Breakbeat",
            artist = "Rockot",
            runningTime = "1:49",
            totalSeconds = 109,
            image = R.drawable.image_album_8
        ),
        Program(
            channelId = 2,
            title = "Retro Groove",
            artist = "lucafrancini",
            runningTime = "2:38",
            totalSeconds = 158,
            image = R.drawable.image_album_9
        ),
        Program(
            channelId = 2,
            title = "Ethereal Vistas",
            artist = "Denys Brodovskyi",
            runningTime = "4:01",
            totalSeconds = 241,
            image = R.drawable.image_album_10
        ),
        Program(
            channelId = 3,
            title = "Once in Paris",
            artist = "Pump up the mind",
            runningTime = "2:12",
            totalSeconds = 132,
            image = R.drawable.image_album_11
        ),
        Program(
            channelId = 3,
            title = "Titanium",
            artist = "Alisia Beats",
            runningTime = "1:46",
            totalSeconds = 106,
            image = R.drawable.image_album_12
        ),
        Program(
            channelId = 3,
            title = "Good Night",
            artist = "FASSounds",
            runningTime = "2:27",
            totalSeconds = 147,
            image = R.drawable.image_album_13
        ),
        Program(
            channelId = 3,
            title = "Risk",
            artist = "Studio Kolomna",
            runningTime = "1:12",
            totalSeconds = 72,
            image = R.drawable.image_album_14
        ),
        Program(
            channelId = 3,
            title = "Eternity",
            artist = "Leva",
            runningTime = "2:24",
            totalSeconds = 144,
            image = R.drawable.image_album_15
        ),
        Program(
            channelId = 4,
            title = "Separation",
            artist = "William King",
            runningTime = "2:19",
            totalSeconds = 139,
            image = R.drawable.image_album_16
        ),
        Program(
            channelId = 4,
            title = "Awaken",
            artist = "Onoychenko music",
            runningTime = "3:00",
            totalSeconds = 180,
            image = R.drawable.image_album_17
        ),
        Program(
            channelId = 4,
            title = "Forest Lullaby",
            artist = "Leafs",
            runningTime = "2:18",
            totalSeconds = 138,
            image = R.drawable.image_album_18
        ),
        Program(
            channelId = 4,
            title = "The Beat of Nature",
            artist = "Olexy",
            runningTime = "2:53",
            totalSeconds = 173,
            image = R.drawable.image_album_19
        ),
        Program(
            channelId = 4,
            title = "Waterfall",
            artist = "RomanSenky Music",
            runningTime = "2:44",
            totalSeconds = 164,
            image = R.drawable.image_album_20
        ),
        Program(
            channelId = 5,
            title = "Inside you",
            artist = "Lemon music studio",
            runningTime = "2:09",
            totalSeconds = 129,
            image = R.drawable.image_album_21
        ),
        Program(
            channelId = 5,
            title = "A Long Way",
            artist = "SergePavkin Music",
            runningTime = "4:33",
            totalSeconds = 273,
            image = R.drawable.image_album_22
        ),
        Program(
            channelId = 5,
            title = "Science Documentary",
            artist = "Lexin Music",
            runningTime = "2:07",
            totalSeconds = 127,
            image = R.drawable.image_album_23
        ),
        Program(
            channelId = 5,
            title = "Trap Future Bass",
            artist = "Royal Music",
            runningTime = "2:06",
            totalSeconds = 126,
            image = R.drawable.image_album_24
        ),
        Program(
            channelId = 5,
            title = "Heroes among us",
            artist = "White Records",
            runningTime = "0:54",
            totalSeconds = 54,
            image = R.drawable.image_album_25
        ),
        Program(
            channelId = 6,
            title = "Relaxed Vlog",
            artist = "Ashot Denielyan Composer",
            runningTime = "2:20",
            totalSeconds = 140,
            image = R.drawable.image_album_26
        ),
        Program(
            channelId = 6,
            title = "Inspiring Cinematic Ambient",
            artist = "Lexin Music",
            runningTime = "3:09",
            totalSeconds = 189,
            image = R.drawable.image_album_27
        ),
        Program(
            channelId = 6,
            title = "Reflected Light",
            artist = "SergePavkin Music",
            runningTime = "3:44",
            totalSeconds = 224,
            image = R.drawable.image_album_28
        ),
        Program(
            channelId = 6,
            title = "Morning in the mountains",
            artist = "CalvinClavier",
            runningTime = "2:52",
            totalSeconds = 172,
            image = R.drawable.image_album_29
        ),
        Program(
            channelId = 6,
            title = "Golden hour",
            artist = "CalvinClavier",
            runningTime = "2:57",
            totalSeconds = 177,
            image = R.drawable.image_album_30
        ),
        Program(
            channelId = 7,
            title = "Deep Meditation",
            artist = "Grand Project",
            runningTime = "1:39",
            totalSeconds = 99,
            image = R.drawable.image_album_31
        ),
        Program(
            channelId = 7,
            title = "Fall of the Roman Empire",
            artist = "White Records",
            runningTime = "4:23",
            totalSeconds = 263,
            image = R.drawable.image_album_32
        ),
        Program(
            channelId = 7,
            title = "A call to the soul",
            artist = "markotopa",
            runningTime = "2:39",
            totalSeconds = 159,
            image = R.drawable.image_album_33
        ),
        Program(
            channelId = 7,
            title = "Flight of your Muse",
            artist = "White Records",
            runningTime = "7:11",
            totalSeconds = 431,
            image = R.drawable.image_album_34
        ),
        Program(
            channelId = 7,
            title = "Pixel Rage",
            artist = "Neura Flow",
            runningTime = "1:42",
            totalSeconds = 102,
            image = R.drawable.image_album_35
        ),
        Program(
            channelId = 8,
            title = "Drive to Triumph",
            artist = "Top Flow",
            runningTime = "1:32",
            totalSeconds = 92,
            image = R.drawable.image_album_36
        ),
        Program(
            channelId = 8,
            title = "Solitude (Dark Ambient Electronic)",
            artist = "lucafrancini",
            runningTime = "2:38",
            totalSeconds = 158,
            image = R.drawable.image_album_37
        ),
        Program(
            channelId = 8,
            title = "Da Vinci Long",
            artist = "Grand Project",
            runningTime = "3:08",
            totalSeconds = 188,
            image = R.drawable.image_album_38
        ),
        Program(
            channelId = 8,
            title = "Winter Legacy (The Four Seasons)",
            artist = "White Records",
            runningTime = "1:34",
            totalSeconds = 94,
            image = R.drawable.image_album_39
        ),
        Program(
            channelId = 8,
            title = "A Small Miracle",
            artist = "Romarecord1973",
            runningTime = "1:16",
            totalSeconds = 76,
            image = R.drawable.image_album_40
        ),
        Program(
            channelId = 9,
            title = "Morning Garden - Acoustic Chill",
            artist = "Olexy",
            runningTime = "3:53",
            totalSeconds = 233,
            image = R.drawable.image_album_41
        ),
        Program(
            channelId = 9,
            title = "Playing in Color",
            artist = "29811401",
            runningTime = "1:22",
            totalSeconds = 82,
            image = R.drawable.image_album_42
        ),
        Program(
            channelId = 9,
            title = "Smooth Waters",
            artist = "SergePavkin Music",
            runningTime = "4:59",
            totalSeconds = 299,
            image = R.drawable.image_album_43
        ),
        Program(
            channelId = 9,
            title = "Travel Vibe",
            artist = "BoDleasons",
            runningTime = "2:39",
            totalSeconds = 159,
            image = R.drawable.image_album_44
        ),
        Program(
            channelId = 9,
            title = "Touching Emotional Peaceful Cinematic Beautiful Music",
            artist = "Denis Pavlov Music",
            runningTime = "3:45",
            totalSeconds = 225,
            image = R.drawable.image_album_45
        ),
        Program(
            channelId = 10,
            title = "AMBIENT PIANO x RELAXING (prod. by Black-Py)",
            artist = "BlackPy Beats",
            runningTime = "10:25",
            totalSeconds = 625,
            image = R.drawable.image_album_46
        ),
        Program(
            channelId = 10,
            title = "Song From A Secret Garden (Sad Piano)",
            artist = "CalvinClavier",
            runningTime = "3:42",
            totalSeconds = 222,
            image = R.drawable.image_album_47
        ),
        Program(
            channelId = 10,
            title = "Thinking Of Good Time - Soft Piano Music",
            artist = "CalvinClavier",
            runningTime = "2:31",
            totalSeconds = 151,
            image = R.drawable.image_album_48
        ),
        Program(
            channelId = 10,
            title = "Piano Moment",
            artist = "Good B Music",
            runningTime = "4:33",
            totalSeconds = 273,
            image = R.drawable.image_album_49
        ),
        Program(
            channelId = 10,
            title = "Ambient Piano and Strings",
            artist = "Good B Music",
            runningTime = "3:37",
            totalSeconds = 217,
            image = R.drawable.image_album_50
        ),
    )

    val audioChannels = listOf(
        AudioChannel(
            id = 1,
            number = "1",
            name = "Billboard Hot 100",
        ),
        AudioChannel(
            id = 2,
            number = "2",
            name = "Jazz",
        ),
        AudioChannel(
            id = 3,
            number = "3",
            name = "Pop",
        ),
        AudioChannel(
            id = 4,
            number = "4",
            name = "R&B",
        ),
        AudioChannel(
            id = 5,
            number = "5",
            name = "Hiphop",
        ),
        AudioChannel(
            id = 6,
            number = "6",
            name = "Rock",
        ),
        AudioChannel(
            id = 7,
            number = "7",
            name = "Meditation",
        ),
        AudioChannel(
            id = 8,
            number = "8",
            name = "Kids",
        ),
        AudioChannel(
            id = 9,
            number = "9",
            name = "Classic",
        ),
        AudioChannel(
            id = 10,
            number = "10",
            name = "Piano",
        ),
    )
}
package com.tilikki.movipedia.ui.component

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tilikki.movipedia.model.Video
import com.tilikki.movipedia.model.VideoType
import com.tilikki.movipedia.ui.theme.getCardBackgroundColor
import com.tilikki.movipedia.ui.theme.getChipBackgroundColor

@Composable
fun VideoCard(
    video: Video,
    modifier: Modifier = Modifier,
    onVideoCardClick: (url: String) -> Unit = {},
) {
    Card(
        backgroundColor = getCardBackgroundColor(),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .padding(4.dp)
            .widthIn(max = 200.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .clickable { onVideoCardClick(video.getVideoUrl()) }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally)
            ) {
                AsyncImage(
                    model = video.getThumbnailUrl(),
                    placeholder = ColorPainter(Color.Gray),
                    contentDescription = video.name,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 80.dp, minWidth = 120.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.FillWidth
                )
            }
            Column(
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 2.dp,
                    bottom = 8.dp
                )
            ) {
                Text(
                    text = video.name,
                    modifier = Modifier.padding(vertical = 2.dp),
                    style = MaterialTheme.typography.h6,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                )
                Text(
                    text = video.publishedDate,
                    modifier = Modifier.padding(vertical = 2.dp),
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = video.type.toString().uppercase(),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .background(
                            color = getChipBackgroundColor(),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

@Composable
fun VideoList(
    videos: List<Video>,
    modifier: Modifier = Modifier,
) {
    LazyRow(modifier = modifier.animateContentSize()) {
        items(videos) { video ->
            val context = LocalContext.current
            VideoCard(
                video = video,
                modifier = Modifier.padding(4.dp),
                onVideoCardClick = { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Preview
@Composable
private fun ShowVideoCard() {
    val video = Video(
        id = "s1234567890",
        name = "Wakanda - Teaser Video",
        type = VideoType.TEASER,
        language = "en",
        country = "USA",
        key = "RlOB3UALvrQ",
        site = "YouTube",
        resolution = 1080,
        official = true,
        publishedDate = "2022-10-03T13:00:01.000Z"
    )
    VideoCard(video = video)
}

@Preview
@Composable
private fun ShowVideoCardLongerTitle() {
    val video = Video(
        id = "s1234567890",
        name = "Wakanda - Teaser Video - even longer extended",
        type = VideoType.TEASER,
        language = "en",
        country = "USA",
        key = "RlOB3UALvrQ",
        site = "YouTube",
        resolution = 1080,
        official = true,
        publishedDate = "2022-10-03T13:00:01.000Z"
    )
    VideoCard(video = video)
}

@Preview
@Composable
private fun ShowVideoListLongerTitle() {
    val videos = listOf(
        Video(
            id = "s1234567880",
            name = "Wakanda Wakanda Wakanda",
            type = VideoType.TEASER,
            language = "",
            country = "",
            key = "RlOB3UALvrQ",
            site = "YouTube",
            resolution = 1080,
            official = true,
            publishedDate = "2022-10-06T13:00:01.000Z"
        ),
        Video(
            id = "s1234567890",
            name = "Wakanda - Teaser Video - even longer extended",
            type = VideoType.TEASER,
            language = "",
            country = "",
            key = "RlOB3UALvrQ",
            site = "YouTube",
            resolution = 1080,
            official = true,
            publishedDate = "2022-10-03T13:00:01.000Z"
        ),
        Video(
            id = "s1234567899",
            name = "Wakanda blooper: nippon paint",
            type = VideoType.BLOOPERS,
            language = "",
            country = "",
            key = "RlOB3UALvrQ",
            site = "YouTube",
            resolution = 1080,
            official = true,
            publishedDate = "2022-10-13T13:00:01.000Z"
        ),
    )
    VideoList(videos = videos)
}

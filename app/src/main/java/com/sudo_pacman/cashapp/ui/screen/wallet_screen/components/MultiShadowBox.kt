import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MultiShadowBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
    onTap: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .fillMaxWidth()
        ,
        contentAlignment = Alignment.Center
    ) {
        // Bottom dark shadow 1
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .offset(y = 1.dp) // pastga suramiz
                .blur(
                    radius = 2.dp,
                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                )
                .background(
                    color = Color(0x1C19171A).copy(alpha = 0.05f),
                    shape = RoundedCornerShape(12.dp)
                )
        )

        // Bottom dark shadow 2
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .offset(y = 2.dp) // pastga suramiz
                .blur(
                    radius = 3.dp,
                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                )
                .background(
                    color = Color(0x1C19171A).copy(alpha = 0.10f),
                    shape = RoundedCornerShape(12.dp)
                )
        )


        // Actual button with gradient and bevel border
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    color = Color(0xffF7F8FC),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current,
                    onClick = onTap
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            contentAlignment = Alignment.CenterStart,
            content = {
                content()
            }
        )
    }
}

package aleh.ahiyevich.criminal.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeasonsU(
    val imageUrl: String = "",
    val openSeason: Boolean = false,
    val nameSeason: String = ""
): Parcelable

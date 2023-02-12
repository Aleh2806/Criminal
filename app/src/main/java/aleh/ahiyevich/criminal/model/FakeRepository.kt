package aleh.ahiyevich.criminal.model

import aleh.ahiyevich.criminal.R
import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crimes(
    var isOpen: Boolean,
    val nameCrime: String,
    @DrawableRes val imageCrime: Int
) : Parcelable


object FakeRepository {

    val nameCrime = mapOf(
        CrimeId.ONE to "Дело №1",
        CrimeId.TWO to "Дело №2",
        CrimeId.THREE to "Дело №3",
        CrimeId.FOUR to "Дело №4",
        CrimeId.FIVE to "Дело №5",
        CrimeId.SIX to "Дело №6",
        CrimeId.SEVEN to "Дело №7",
        CrimeId.EIGHT to "Дело №8",
        CrimeId.NINE to "Дело №9",
        CrimeId.TEN to "Дело №10"
    )


    val imageCrime = mapOf(
        CrimeId.ONE to R.drawable.ic_baseline_home_24,
        CrimeId.TWO to R.drawable.ic_launcher_foreground,
        CrimeId.THREE to R.drawable.ic_launcher_foreground,
        CrimeId.FOUR to R.drawable.ic_launcher_foreground,
        CrimeId.FIVE to R.drawable.ic_launcher_foreground,
        CrimeId.SIX to R.drawable.ic_launcher_foreground,
        CrimeId.SEVEN to R.drawable.ic_launcher_foreground,
        CrimeId.EIGHT to R.drawable.ic_launcher_foreground,
        CrimeId.NINE to R.drawable.ic_launcher_foreground,
        CrimeId.TEN to R.drawable.ic_launcher_foreground
    )

    val isOpen = mapOf(
        CrimeId.ONE to true,
        CrimeId.TWO to true,
        CrimeId.THREE to false,
        CrimeId.FOUR to false,
        CrimeId.FIVE to false,
        CrimeId.SIX to false,
        CrimeId.SEVEN to false,
        CrimeId.EIGHT to false,
        CrimeId.NINE to false,
        CrimeId.TEN to false
    )
}

package aleh.ahiyevich.criminal.model

import aleh.ahiyevich.criminal.R
import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crimes(
    var isOpen: Boolean,
    val nameCrime: String,
    val descriptionCrime: String,
    @DrawableRes val imageCrime: Int
) : Parcelable

@Parcelize
data class Seasons(
    val isOpenSeason: Boolean,
    val nameSeason: String,
    @DrawableRes val imageSeason: Int
): Parcelable




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

    val descriptionCrime = mapOf(
        CrimeId.ONE to "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        CrimeId.TWO to "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        CrimeId.THREE to "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        CrimeId.FOUR to "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        CrimeId.FIVE to "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        CrimeId.SIX to "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        CrimeId.SEVEN to "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        CrimeId.EIGHT to "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        CrimeId.NINE to "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        CrimeId.TEN to "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
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

    val nameSeason = mapOf(
        SeasonsId.ONE to "Сезон 1",
        SeasonsId.TWO to "Сезон 2",
        SeasonsId.THREE to "Сезон 3",
        SeasonsId.FOUR to "Сезон 4",
        SeasonsId.FIVE to "Сезон 5",
        SeasonsId.SIX to "Сезон 6",
        SeasonsId.SEVEN to "Сезон 7",
        SeasonsId.EIGHT to "Сезон 8",
        SeasonsId.NINE to "Сезон 9",
        SeasonsId.TEN to "Сезон 10"
    )

    val imageSeason = mapOf(
        SeasonsId.ONE to R.drawable.ic_baseline_home_24,
        SeasonsId.TWO to R.drawable.ic_launcher_foreground,
        SeasonsId.THREE to R.drawable.ic_launcher_foreground,
        SeasonsId.FOUR to R.drawable.ic_launcher_foreground,
        SeasonsId.FIVE to R.drawable.ic_launcher_foreground,
        SeasonsId.SIX to R.drawable.ic_launcher_foreground,
        SeasonsId.SEVEN to R.drawable.ic_launcher_foreground,
        SeasonsId.EIGHT to R.drawable.ic_launcher_foreground,
        SeasonsId.NINE to R.drawable.ic_launcher_foreground,
        SeasonsId.TEN to R.drawable.ic_launcher_foreground
    )

    val isOpenSeason = mapOf(
        SeasonsId.ONE to true,
        SeasonsId.TWO to false,
        SeasonsId.THREE to false,
        SeasonsId.FOUR to false,
        SeasonsId.FIVE to false,
        SeasonsId.SIX to false,
        SeasonsId.SEVEN to false,
        SeasonsId.EIGHT to false,
        SeasonsId.NINE to false,
        SeasonsId.TEN to false
    )

}

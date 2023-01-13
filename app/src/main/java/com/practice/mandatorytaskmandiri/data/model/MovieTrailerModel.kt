import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieTrailerModel(
    val id: Int?,
    val results: List<Result?>?
) : Parcelable {
    @Parcelize
    data class Result(
        val id: String?,
        val iso31661: String?,
        val iso6391: String?,
        val key: String?,
        val name: String?,
        val official: Boolean?,
        val publishedAt: String?,
        val site: String?,
        val size: Int?,
        val type: String?
    ) : Parcelable
}

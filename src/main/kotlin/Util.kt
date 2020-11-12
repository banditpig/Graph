
/**
 * Maybe
 *
 * @param T
 * @constructor Create empty Maybe
 */
sealed class Maybe<out T> {
    object None : Maybe<Nothing>()

    /**
     * Just
     *
     * @param T
     * @property value
     * @constructor Create empty Just
     */
    data class Just<T>(val value: T) : Maybe<T>()
}


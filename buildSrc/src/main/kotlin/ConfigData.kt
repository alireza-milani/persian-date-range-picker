/**
 * @author Alireza Milani
 * @since 1.0.0
 */
object ConfigData {

    private const val versionMajor = 0
    private const val versionMinor = 0
    private const val versionPatch = 1

    /**
     * Version code is made according below pattern
     * XYYZZ; X = Major, Y = minor, Z = Patch level
     */
    const val versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch * 10

    /**
     * Version name is made according below pattern
     * X.Y.Z; X = Major, Y = minor, Z = Patch level
     */
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"

    const val compileSdk = 32
    const val minSdk = 21
    const val targetSdk = 32
}
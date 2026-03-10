package core.utils

class TextUtils {
    companion object {
        fun prepare(s: String): String {
            val pre = prepareOnly(s)
            return if (pre.isNotEmpty() && pre[0] == '.') "0$pre" else pre
        }

        fun prepareOnly(s: String): String {
            return s.trim().replace(",", ".")
        }
    }
}

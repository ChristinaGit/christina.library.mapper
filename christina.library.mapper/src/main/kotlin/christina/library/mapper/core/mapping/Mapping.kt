package christina.library.mapper.core.mapping

import android.support.annotation.CheckResult

interface Mapping<in Source : Any, Destination : Any> {
    @CheckResult
    fun performMapping(source: Source): Destination

    fun performMapping(source: Source, destination: Destination)
}
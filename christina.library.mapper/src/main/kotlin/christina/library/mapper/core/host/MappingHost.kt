package christina.library.mapper.core.host

import android.support.annotation.CheckResult
import christina.library.mapper.core.descriptor.MappingDescriptor
import christina.library.mapper.core.mapping.Mapping

interface MappingHost {
    @CheckResult
    fun <Source : Any, Destination : Any> findMapping(
        descriptor: MappingDescriptor<Source, Destination>
    ): Mapping<Source, Destination>?
}
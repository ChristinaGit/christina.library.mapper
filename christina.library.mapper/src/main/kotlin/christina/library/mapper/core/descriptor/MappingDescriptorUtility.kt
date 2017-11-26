package christina.library.mapper.core.descriptor

import android.support.annotation.CheckResult

@CheckResult
inline fun <reified Source : Any, reified Destination : Any> mappingDescriptor() =
    MappingDescriptor(Source::class, Destination::class)

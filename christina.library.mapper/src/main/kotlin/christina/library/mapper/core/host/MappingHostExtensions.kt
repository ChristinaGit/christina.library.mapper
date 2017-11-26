package christina.library.mapper.core.host

import android.support.annotation.CheckResult
import christina.library.mapper.core.descriptor.MappingDescriptor
import kotlin.reflect.KClass

@CheckResult
fun <Source : Any, Destination : Any> MappingHost.findMapping(
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>) =
    findMapping(MappingDescriptor(sourceClass, destinationClass))

@CheckResult
inline fun <reified Source : Any, reified Destination : Any> MappingHost.findMapping() =
    findMapping(Source::class, Destination::class)
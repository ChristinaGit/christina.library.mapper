package christina.library.mapper.core.descriptor

import kotlin.reflect.KClass

data class MappingDescriptor<Source : Any, Destination : Any>(
    val sourceClass: KClass<Source>,
    val destinationClass: KClass<Destination>
)
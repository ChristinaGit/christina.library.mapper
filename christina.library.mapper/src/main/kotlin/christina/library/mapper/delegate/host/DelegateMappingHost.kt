package christina.library.mapper.delegate.host

import christina.common.exception.reasonableException
import christina.library.mapper.core.descriptor.MappingDescriptor
import christina.library.mapper.core.exception.MapperErrorReason.MAPPING_ALREADY_EXISTS
import christina.library.mapper.core.exception.MapperException
import christina.library.mapper.core.host.MappingHost
import christina.library.mapper.core.mapping.Mapping
import christina.library.mapper.delegate.mapping.DelegateMapping

class DelegateMappingHost : MappingHost {
    private val mappings: MutableMap<MappingDescriptor<Any, Any>, Mapping<Any, Any>> = mutableMapOf()

    fun <Source : Any, Destination : Any> addMapping(
        descriptor: MappingDescriptor<Source, Destination>,
        mapper: DelegateMapping<Source, Destination>
    ) {
        @Suppress("UNCHECKED_CAST")
        val rawDescriptor = descriptor as MappingDescriptor<Any, Any>
        if (mappings.containsKey(rawDescriptor)) {
            throw MapperException(cause = reasonableException(MAPPING_ALREADY_EXISTS, descriptor.toString()))
        }

        @Suppress("UNCHECKED_CAST")
        mappings[rawDescriptor] = mapper as Mapping<Any, Any>
    }

    override fun <Source : Any, Destination : Any> findMapping(
        descriptor: MappingDescriptor<Source, Destination>
    ): Mapping<Source, Destination>? {
        @Suppress("UNCHECKED_CAST")
        return mappings[descriptor as MappingDescriptor<Any, Any>] as Mapping<Source, Destination>?
    }
}
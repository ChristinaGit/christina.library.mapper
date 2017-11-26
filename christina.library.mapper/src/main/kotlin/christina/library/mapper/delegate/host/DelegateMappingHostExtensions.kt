package christina.library.mapper.delegate.host

import christina.library.mapper.core.descriptor.MappingDescriptor
import christina.library.mapper.delegate.mapping.DelegateMapping
import kotlin.reflect.KClass

fun <Source : Any, Destination : Any> DelegateMappingHost.addMapping(
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>,
    mapping: DelegateMapping<Source, Destination>
) = addMapping(MappingDescriptor(sourceClass, destinationClass), mapping)

inline fun <reified Source : Any, reified Destination : Any> DelegateMappingHost.addMapping(
    mapping: DelegateMapping<Source, Destination>
) = addMapping(Source::class, Destination::class, mapping)

fun <Source : Any, Destination : Any> DelegateMappingHost.addMapping(
    descriptor: MappingDescriptor<Source, Destination>,
    mapping: (Source) -> Destination,
    receiverMapping: (Source, Destination) -> Unit
) = addMapping(descriptor, DelegateMapping(mapping, receiverMapping))

fun <Source : Any, Destination : Any> DelegateMappingHost.addMapping(
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>,
    mapping: (Source) -> Destination,
    receiverMapping: (Source, Destination) -> Unit
) = addMapping(MappingDescriptor(sourceClass, destinationClass), mapping, receiverMapping)

inline fun <reified Source : Any, reified Destination : Any> DelegateMappingHost.addMapping(
    noinline mapping: (Source) -> Destination,
    noinline receiverMapping: (Source, Destination) -> Unit
) = addMapping(Source::class, Destination::class, mapping, receiverMapping)

fun <Source : Any, Destination : Any> DelegateMappingHost.addMapping(
    descriptor: MappingDescriptor<Source, Destination>,
    mapping: (Source) -> Destination
) = addMapping(descriptor, DelegateMapping(mapping, null))

fun <Source : Any, Destination : Any> DelegateMappingHost.addMapping(
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>,
    mapping: (Source) -> Destination
) = addMapping(MappingDescriptor(sourceClass, destinationClass), mapping)

inline fun <reified Source : Any, reified Destination : Any> DelegateMappingHost.addMapping(
    noinline mapping: (Source) -> Destination
) = addMapping(Source::class, Destination::class, mapping)

fun <Source : Any, Destination : Any> DelegateMappingHost.addReceiverMapping(
    descriptor: MappingDescriptor<Source, Destination>,
    receiverMapping: (Source, Destination) -> Unit
) = addMapping(descriptor, DelegateMapping(null, receiverMapping))

fun <Source : Any, Destination : Any> DelegateMappingHost.addReceiverMapping(
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>,
    receiverMapping: (Source, Destination) -> Unit
) = addReceiverMapping(MappingDescriptor(sourceClass, destinationClass), receiverMapping)

inline fun <reified Source : Any, reified Destination : Any> DelegateMappingHost.addReceiverMapping(
    noinline receiverMapping: (Source, Destination) -> Unit
) = addReceiverMapping(Source::class, Destination::class, receiverMapping)
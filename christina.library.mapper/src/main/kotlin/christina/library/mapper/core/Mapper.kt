package christina.library.mapper.core

import android.support.annotation.CheckResult
import christina.library.mapper.core.descriptor.MappingDescriptor
import christina.library.mapper.core.mapping.Mapping
import kotlin.reflect.KClass

interface Mapper {
    @CheckResult
    fun <Source : Any, Destination : Any> getMapping(
        descriptor: MappingDescriptor<Source, Destination>
    ): Mapping<Source, Destination>

    @CheckResult
    fun <Source : Any, Destination : Any> map(
        descriptor: MappingDescriptor<Source, Destination>,
        source: Source
    ): Destination

    fun <Source : Any, Destination : Any> map(
        descriptor: MappingDescriptor<Source, Destination>,
        source: Source,
        destination: Destination
    )

    @CheckResult
    fun <Source : Any, Destination : Any> mapNullable(
        descriptor: MappingDescriptor<Source, Destination>,
        source: Source?
    ): Destination?

    fun <Source : Any, Destination : Any> mapNullable(
        descriptor: MappingDescriptor<Source, Destination>,
        source: Source?,
        destination: Destination
    )

    @CheckResult
    fun <Source : Any, Destination : Any> runtimeMap(
        destinationClass: KClass<Destination>,
        source: Source
    ): Destination

    fun <Source : Any, Destination : Any> runtimeMap(
        destinationClass: KClass<Destination>,
        source: Source,
        destination: Destination
    )

    @CheckResult
    fun <Source : Any, Destination : Any> runtimeMapNullable(
        destinationClass: KClass<Destination>,
        source: Source?
    ): Destination?

    fun <Source : Any, Destination : Any> runtimeMapNullable(
        destinationClass: KClass<Destination>,
        source: Source?,
        destination: Destination
    )

    @CheckResult
    fun <Source : Any, Destination : Any> mapMany(
        descriptor: MappingDescriptor<Source, Destination>,
        sourceCollection: Iterable<Source>
    ): List<Destination>

    fun <Source : Any, Destination : Any> mapMany(
        descriptor: MappingDescriptor<Source, Destination>,
        sourceCollection: Iterable<Source>,
        destinationCollection: List<Destination>
    )

    @CheckResult
    fun <Source : Any, Destination : Any> mapManyNullable(
        descriptor: MappingDescriptor<Source, Destination>,
        sourceCollection: Iterable<Source?>
    ): List<Destination?>

    fun <Source : Any, Destination : Any> mapManyNullable(
        descriptor: MappingDescriptor<Source, Destination>,
        sourceCollection: Iterable<Source?>,
        destinationCollection: MutableList<Destination?>
    )

    @CheckResult
    fun <Source : Any, Destination : Any> runtimeMapMany(
        destinationClass: KClass<Destination>,
        sourceCollection: Iterable<Source>
    ): List<Destination>

    fun <Source : Any, Destination : Any> runtimeMapMany(
        destinationClass: KClass<Destination>,
        sourceCollection: Iterable<Source>,
        destinationCollection: List<Destination>
    )

    @CheckResult
    fun <Source : Any, Destination : Any> runtimeMapManyNullable(
        destinationClass: KClass<Destination>,
        sourceCollection: Iterable<Source?>
    ): List<Destination?>

    fun <Source : Any, Destination : Any> runtimeMapManyNullable(
        destinationClass: KClass<Destination>,
        sourceCollection: Iterable<Source?>,
        destinationCollection: MutableList<Destination?>
    )
}

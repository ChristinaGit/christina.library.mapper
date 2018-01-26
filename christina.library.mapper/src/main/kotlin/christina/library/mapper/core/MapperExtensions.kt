package christina.library.mapper.core

import android.support.annotation.CheckResult
import christina.library.mapper.core.descriptor.MappingDescriptor
import kotlin.reflect.KClass

@CheckResult
fun <Source : Any, Destination : Any> Mapper.getMapping(
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>
) = getMapping(MappingDescriptor(sourceClass, destinationClass))

@CheckResult
inline fun <reified Source : Any, reified Destination : Any> Mapper.getMapping() =
    getMapping(Source::class, Destination::class)

//region Static

@CheckResult
fun <Source : Any, Destination : Any> Mapper.map(
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>,
    source: Source
) = map(MappingDescriptor(sourceClass, destinationClass), source)

fun <Source : Any, Destination : Any> Mapper.map(
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>,
    source: Source,
    destination: Destination
) = map(MappingDescriptor(sourceClass, destinationClass), source, destination)

@CheckResult
inline fun <reified Source : Any, reified Destination : Any> Mapper.map(
    source: Source
) = map(Source::class, Destination::class, source)

inline fun <reified Source : Any, reified Destination : Any> Mapper.map(
    source: Source,
    destination: Destination
) = map(Source::class, Destination::class, source, destination)

//region Nullable

@CheckResult
fun <Source : Any, Destination : Any> Mapper.mapNullable(
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>,
    source: Source?
) = mapNullable(MappingDescriptor(sourceClass, destinationClass), source)

fun <Source : Any, Destination : Any> Mapper.mapNullable(
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>,
    source: Source?,
    destination: Destination
) = mapNullable(MappingDescriptor(sourceClass, destinationClass), source, destination)

@CheckResult
inline fun <reified Source : Any, reified Destination : Any> Mapper.mapNullable(
    source: Source?
) = mapNullable(Source::class, Destination::class, source)

inline fun <reified Source : Any, reified Destination : Any> Mapper.mapNullable(
    source: Source?,
    destination: Destination
) = mapNullable(Source::class, Destination::class, source, destination)

//endregion

//endregion

//region Runtime

@CheckResult
inline fun <reified Source : Any, reified Destination : Any> Mapper.runtimeMap(
    source: Source
) = runtimeMap(Destination::class, source)

@Suppress("UNCHECKED_CAST")
inline fun <reified Source : Any, reified Destination : Any> Mapper.runtimeMap(
    source: Source,
    destination: Destination
) = runtimeMap(destination::class as KClass<Destination>, source, destination)

//region Nullable

@CheckResult
inline fun <reified Source : Any, reified Destination : Any> Mapper.runtimeMapNullable(
    source: Source?
) = runtimeMapNullable(Destination::class, source)

inline fun <reified TSource : Any, reified TDestination : Any> Mapper.runtimeMapNullable(
    source: TSource?,
    destination: TDestination
) = runtimeMapNullable(TDestination::class, source, destination)

//endregion

//endregion

//region Many

//region Static

@CheckResult
fun <Source : Any, Destination : Any> Mapper.mapMany(
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>,
    sourceCollection: Iterable<Source>
) = mapMany(MappingDescriptor(sourceClass, destinationClass), sourceCollection)

fun <Source : Any, Destination : Any> Mapper.mapMany(
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>,
    sourceCollection: Iterable<Source>,
    destinationCollection: List<Destination>
) = mapMany(MappingDescriptor(sourceClass, destinationClass), sourceCollection, destinationCollection)

@CheckResult
inline fun <reified Source : Any, reified Destination : Any> Mapper.mapMany(
    sourceCollection: Iterable<Source>
) = mapMany(Source::class, Destination::class, sourceCollection)

inline fun <reified Source : Any, reified Destination : Any> Mapper.mapMany(
    sourceCollection: Iterable<Source>,
    destinationCollection: List<Destination>
) = mapMany(Source::class, Destination::class, sourceCollection, destinationCollection)

//region Nullable

@CheckResult
fun <Source : Any, Destination : Any> Mapper.mapManyNullable(
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>,
    sourceCollection: Iterable<Source?>
) = mapManyNullable(MappingDescriptor(sourceClass, destinationClass), sourceCollection)

fun <Source : Any, Destination : Any> Mapper.mapManyNullable(
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>,
    sourceCollection: Iterable<Source?>,
    destinationCollection: MutableList<Destination?>
) = mapManyNullable(MappingDescriptor(sourceClass, destinationClass), sourceCollection, destinationCollection)

@CheckResult
inline fun <reified Source : Any, reified Destination : Any> Mapper.mapManyNullable(
    sourceCollection: Iterable<Source?>
) = mapManyNullable(Source::class, Destination::class, sourceCollection)

inline fun <reified Source : Any, reified Destination : Any> Mapper.mapManyNullable(
    sourceCollection: Iterable<Source?>,
    destinationCollection: MutableList<Destination?>
) = mapManyNullable(Source::class, Destination::class, sourceCollection, destinationCollection)

//endregion

//endregion

//region Runtime

@CheckResult
inline fun <reified Source : Any, reified Destination : Any> Mapper.runtimeMapMany(
    sourceCollection: Iterable<Source>
) = runtimeMapMany(Destination::class, sourceCollection)

inline fun <reified Source : Any, reified Destination : Any> Mapper.runtimeMapMany(
    sourceCollection: Iterable<Source>,
    destinationCollection: List<Destination>
) = runtimeMapMany(Destination::class, sourceCollection, destinationCollection)

//region Nullable

@CheckResult
inline fun <reified Source : Any, reified Destination : Any> Mapper.runtimeMapManyNullable(
    sourceCollection: Iterable<Source?>
) = runtimeMapManyNullable(Destination::class, sourceCollection)

inline fun <reified Source : Any, reified Destination : Any> Mapper.runtimeMapManyNullable(
    sourceCollection: Iterable<Source?>,
    destinationCollection: MutableList<Destination?>
) = runtimeMapManyNullable(Destination::class, sourceCollection, destinationCollection)

//endregion

//endregion

//endregion
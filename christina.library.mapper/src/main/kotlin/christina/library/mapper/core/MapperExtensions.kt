package christina.library.mapper.core

import android.support.annotation.CheckResult
import christina.library.mapper.core.descriptor.MappingDescriptor
import kotlin.reflect.KClass

@CheckResult
fun <TSource : Any, TDestination : Any> Mapper.getMapping(
    sourceClass: KClass<TSource>,
    destinationClass: KClass<TDestination>
) = getMapping(MappingDescriptor(sourceClass, destinationClass))

@CheckResult
inline fun <reified TSource : Any, reified TDestination : Any> Mapper.getMapping() =
    getMapping(TSource::class, TDestination::class)

//region Static

@CheckResult
fun <TSource : Any, TDestination : Any> Mapper.map(
    sourceClass: KClass<TSource>,
    destinationClass: KClass<TDestination>,
    source: TSource
) = map(MappingDescriptor(sourceClass, destinationClass), source)

fun <TSource : Any, TDestination : Any> Mapper.map(
    sourceClass: KClass<TSource>,
    destinationClass: KClass<TDestination>,
    source: TSource,
    destination: TDestination
) = map(MappingDescriptor(sourceClass, destinationClass), source, destination)

@CheckResult
inline fun <reified TSource : Any, reified TDestination : Any> Mapper.map(
    source: TSource
) = map(TSource::class, TDestination::class, source)

inline fun <reified TSource : Any, reified TDestination : Any> Mapper.map(
    source: TSource,
    destination: TDestination
) = map(TSource::class, TDestination::class, source, destination)

//region Nullable

@CheckResult
fun <TSource : Any, TDestination : Any> Mapper.mapNullable(
    sourceClass: KClass<TSource>,
    destinationClass: KClass<TDestination>,
    source: TSource?
) = mapNullable(MappingDescriptor(sourceClass, destinationClass), source)

fun <TSource : Any, TDestination : Any> Mapper.mapNullable(
    sourceClass: KClass<TSource>,
    destinationClass: KClass<TDestination>,
    source: TSource?,
    destination: TDestination
) = mapNullable(MappingDescriptor(sourceClass, destinationClass), source, destination)

@CheckResult
inline fun <reified TSource : Any, reified TDestination : Any> Mapper.mapNullable(
    source: TSource?
) = mapNullable(TSource::class, TDestination::class, source)

inline fun <reified TSource : Any, reified TDestination : Any> Mapper.mapNullable(
    source: TSource?,
    destination: TDestination
) = mapNullable(TSource::class, TDestination::class, source, destination)

//endregion

//endregion

//region Runtime

@CheckResult
inline fun <reified TSource : Any, reified TDestination : Any> Mapper.runtimeMap(
    source: TSource
) = runtimeMap(TDestination::class, source)

@Suppress("UNCHECKED_CAST")
inline fun <reified TSource : Any, reified TDestination : Any> Mapper.runtimeMap(
    source: TSource,
    destination: TDestination
) = runtimeMap(destination::class as KClass<TDestination>, source, destination)

//region Nullable

@CheckResult
inline fun <reified TSource : Any, reified TDestination : Any> Mapper.runtimeMapNullable(
    source: TSource?
) = runtimeMapNullable(TDestination::class, source)

inline fun <reified TSource : Any, reified TDestination : Any> Mapper.runtimeMapNullable(
    source: TSource?,
    destination: TDestination
) = runtimeMapNullable(TDestination::class, source, destination)

//endregion

//endregion

//region Many

//region Static

@CheckResult
fun <TSource : Any, TDestination : Any> Mapper.mapMany(
    sourceClass: KClass<TSource>,
    destinationClass: KClass<TDestination>,
    sourceCollection: Iterable<TSource>
) = mapMany(MappingDescriptor(sourceClass, destinationClass), sourceCollection)

fun <TSource : Any, TDestination : Any> Mapper.mapMany(
    sourceClass: KClass<TSource>,
    destinationClass: KClass<TDestination>,
    sourceCollection: Iterable<TSource>,
    destinationCollection: List<TDestination>
) = mapMany(MappingDescriptor(sourceClass, destinationClass), sourceCollection, destinationCollection)

@CheckResult
inline fun <reified TSource : Any, reified TDestination : Any> Mapper.mapMany(
    sourceCollection: Iterable<TSource>
) = mapMany(TSource::class, TDestination::class, sourceCollection)

inline fun <reified TSource : Any, reified TDestination : Any> Mapper.mapMany(
    sourceCollection: Iterable<TSource>,
    destinationCollection: List<TDestination>
) = mapMany(TSource::class, TDestination::class, sourceCollection, destinationCollection)

//region Nullable

@CheckResult
fun <TSource : Any, TDestination : Any> Mapper.mapManyNullable(
    sourceClass: KClass<TSource>,
    destinationClass: KClass<TDestination>,
    sourceCollection: Iterable<TSource?>
) = mapManyNullable(MappingDescriptor(sourceClass, destinationClass), sourceCollection)

fun <TSource : Any, TDestination : Any> Mapper.mapManyNullable(
    sourceClass: KClass<TSource>,
    destinationClass: KClass<TDestination>,
    sourceCollection: Iterable<TSource?>,
    destinationCollection: MutableList<TDestination?>
) = mapManyNullable(MappingDescriptor(sourceClass, destinationClass), sourceCollection, destinationCollection)

@CheckResult
inline fun <reified TSource : Any, reified TDestination : Any> Mapper.mapManyNullable(
    sourceCollection: Iterable<TSource?>
) = mapManyNullable(TSource::class, TDestination::class, sourceCollection)

inline fun <reified TSource : Any, reified TDestination : Any> Mapper.mapManyNullable(
    sourceCollection: Iterable<TSource?>,
    destinationCollection: MutableList<TDestination?>
) = mapManyNullable(TSource::class, TDestination::class, sourceCollection, destinationCollection)

//endregion

//endregion

//region Runtime

@CheckResult
inline fun <reified TSource : Any, reified TDestination : Any> Mapper.runtimeMapMany(
    sourceCollection: Iterable<TSource>
) = runtimeMapMany(TDestination::class, sourceCollection)

inline fun <reified TSource : Any, reified TDestination : Any> Mapper.runtimeMapMany(
    sourceCollection: Iterable<TSource>,
    destinationCollection: List<TDestination>
) = runtimeMapMany(TDestination::class, sourceCollection, destinationCollection)

//region Nullable

@CheckResult
inline fun <reified TSource : Any, reified TDestination : Any> Mapper.runtimeMapManyNullable(
    sourceCollection: Iterable<TSource?>
) = runtimeMapManyNullable(TDestination::class, sourceCollection)

inline fun <reified TSource : Any, reified TDestination : Any> Mapper.runtimeMapManyNullable(
    sourceCollection: Iterable<TSource?>,
    destinationCollection: MutableList<TDestination?>
) = runtimeMapManyNullable(TDestination::class, sourceCollection, destinationCollection)

//endregion

//endregion

//endregion
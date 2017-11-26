package christina.library.mapper.core.utility

import android.support.annotation.CheckResult
import christina.library.mapper.core.Mapper
import christina.library.mapper.core.descriptor.MappingDescriptor
import christina.library.mapper.core.mapMany
import christina.library.mapper.core.mapManyNullable
import kotlin.reflect.KClass

//region Static

@CheckResult
fun <Source : Any, Destination : Any> Iterable<Source>.mapWith(
    mapper: Mapper,
    descriptor: MappingDescriptor<Source, Destination>
) = mapper.mapMany(descriptor, this)

fun <Source : Any, Destination : Any> Iterable<Source>.mapWith(
    mapper: Mapper,
    descriptor: MappingDescriptor<Source, Destination>,
    destinationCollection: List<Destination>
) = mapper.mapMany(descriptor, this, destinationCollection)

fun <Source : Any, Destination : Any> Iterable<Source>.mapWith(
    mapper: Mapper,
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>
) = mapper.mapMany(sourceClass, destinationClass, this)

fun <Source : Any, Destination : Any> Iterable<Source>.mapWith(
    mapper: Mapper,
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>,
    destinationCollection: List<Destination>
) = mapper.mapMany(sourceClass, destinationClass, this, destinationCollection)

@CheckResult
inline fun <reified Source : Any, reified Destination : Any> Iterable<Source>.mapWith(
    mapper: Mapper
) = mapper.mapMany<Source, Destination>(this)

inline fun <reified Source : Any, reified Destination : Any> Iterable<Source>.mapWith(
    mapper: Mapper,
    destinationCollection: List<Destination>
) = mapper.mapMany(this, destinationCollection)

//region Nullable

@CheckResult
fun <Source : Any, Destination : Any> Iterable<Source?>.mapNullableWith(
    mapper: Mapper,
    descriptor: MappingDescriptor<Source, Destination>
) = mapper.mapManyNullable(descriptor, this)

fun <Source : Any, Destination : Any> Iterable<Source?>.mapNullableWith(
    mapper: Mapper,
    descriptor: MappingDescriptor<Source, Destination>,
    destinationCollection: MutableList<Destination?>
) = mapper.mapManyNullable(descriptor, this, destinationCollection)

@CheckResult
fun <Source : Any, Destination : Any> Iterable<Source?>.mapNullableWith(
    mapper: Mapper,
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>
) = mapper.mapManyNullable(sourceClass, destinationClass, this)

fun <Source : Any, Destination : Any> Iterable<Source?>.mapNullableWith(
    mapper: Mapper,
    sourceClass: KClass<Source>,
    destinationClass: KClass<Destination>,
    destinationCollection: MutableList<Destination?>
) = mapper.mapManyNullable(sourceClass, destinationClass, this, destinationCollection)

@CheckResult
inline fun <reified Source : Any, reified Destination : Any> Iterable<Source?>.mapNullableWith(
    mapper: Mapper
) = mapper.mapManyNullable(Source::class, Destination::class, this)

inline fun <reified Source : Any, reified Destination : Any> Iterable<Source?>.mapNullableWith(
    mapper: Mapper,
    destinationCollection: MutableList<Destination?>
) = mapper.mapManyNullable(Source::class, Destination::class, this, destinationCollection)

//endregion

//endregion

//region Runtime

@CheckResult
fun <Source : Any, Destination : Any> Iterable<Source>.runtimeMapWith(
    mapper: Mapper,
    destinationClass: KClass<Destination>
) = mapper.runtimeMapMany(destinationClass, this)

fun <Source : Any, Destination : Any> Iterable<Source>.runtimeMapWith(
    mapper: Mapper,
    destinationClass: KClass<Destination>,
    destinationCollection: List<Destination>
) = mapper.runtimeMapMany(destinationClass, this, destinationCollection)

@CheckResult
inline fun <reified Source : Any, reified Destination : Any> Iterable<Source>.runtimeMapWith(
    mapper: Mapper
) = mapper.runtimeMapMany(Destination::class, this)

inline fun <reified Source : Any, reified Destination : Any> Iterable<Source>.runtimeMapWith(
    mapper: Mapper,
    destinationCollection: List<Destination>
) = mapper.runtimeMapMany(Destination::class, this, destinationCollection)

//region Nullable

@CheckResult
fun <Source : Any, Destination : Any> Iterable<Source?>.runtimeMapNullableWith(
    mapper: Mapper,
    destinationClass: KClass<Destination>
) = mapper.runtimeMapManyNullable(destinationClass, this)

fun <Source : Any, Destination : Any> Iterable<Source?>.runtimeMapNullableWith(
    mapper: Mapper,
    destinationClass: KClass<Destination>,
    destinationCollection: MutableList<Destination?>
) = mapper.runtimeMapManyNullable(destinationClass, this, destinationCollection)

@CheckResult
inline fun <reified Source : Any, reified Destination : Any> Iterable<Source?>.runtimeMapNullableWith(
    mapper: Mapper
) = mapper.runtimeMapManyNullable(Destination::class, this)

inline fun <reified Source : Any, reified Destination : Any> Iterable<Source?>.runtimeMapNullableWith(
    mapper: Mapper,
    destinationCollection: MutableList<Destination?>
) = mapper.runtimeMapManyNullable(Destination::class, this, destinationCollection)

//endregion

//endregion
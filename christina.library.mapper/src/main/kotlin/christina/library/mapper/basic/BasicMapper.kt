package christina.library.mapper.basic

import christina.common.exception.reasonableException
import christina.common.ignore
import christina.library.mapper.core.Mapper
import christina.library.mapper.core.descriptor.MappingDescriptor
import christina.library.mapper.core.exception.MapperErrorReason.MAPPING_COLLECTION_DIFFERENT_SIZE
import christina.library.mapper.core.exception.MapperErrorReason.MAPPING_NOT_FOUND
import christina.library.mapper.core.exception.MapperException
import christina.library.mapper.core.host.MappingHost
import christina.library.mapper.core.mapping.Mapping
import kotlin.reflect.KClass

open class BasicMapper(protected val host: MappingHost) : Mapper {
    override fun <Source : Any, Destination : Any> getMapping(
        descriptor: MappingDescriptor<Source, Destination>
    ): Mapping<Source, Destination> = host.findMapping(descriptor)
        ?: throw MapperException(cause = reasonableException(MAPPING_NOT_FOUND, descriptor.toString()))

    override fun <Source : Any, Destination : Any> map(
        descriptor: MappingDescriptor<Source, Destination>,
        source: Source
    ): Destination = getMapping(descriptor).run { return@run performMapping(source) }

    override fun <Source : Any, Destination : Any> map(
        descriptor: MappingDescriptor<Source, Destination>,
        source: Source,
        destination: Destination
    ) = getMapping(descriptor).run { performMapping(source, destination) }.ignore()

    override fun <Source : Any, Destination : Any> mapNullable(
        descriptor: MappingDescriptor<Source, Destination>,
        source: Source?
    ): Destination? = source?.let { return@let map(descriptor, it) }

    override fun <Source : Any, Destination : Any> mapNullable(
        descriptor: MappingDescriptor<Source, Destination>,
        source: Source?,
        destination: Destination
    ) = source?.let { map(descriptor, it, destination) }.ignore()

    @Suppress("UNCHECKED_CAST")
    override fun <Source : Any, Destination : Any> runtimeMap(
        destinationClass: KClass<Destination>,
        source: Source
    ): Destination = map(MappingDescriptor(source::class as KClass<Source>, destinationClass), source)

    @Suppress("UNCHECKED_CAST")
    override fun <Source : Any, Destination : Any> runtimeMap(
        destinationClass: KClass<Destination>,
        source: Source,
        destination: Destination
    ) = map(MappingDescriptor(source::class as KClass<Source>, destinationClass), source, destination)

    override fun <Source : Any, Destination : Any> runtimeMapNullable(
        destinationClass: KClass<Destination>,
        source: Source?
    ): Destination? = source?.let { return@let runtimeMap(destinationClass, it) }

    override fun <Source : Any, Destination : Any> runtimeMapNullable(
        destinationClass: KClass<Destination>,
        source: Source?,
        destination: Destination
    ) = source?.let { runtimeMap(destinationClass, it, destination) }.ignore()

    override fun <Source : Any, Destination : Any> mapMany(
        descriptor: MappingDescriptor<Source, Destination>,
        sourceCollection: Iterable<Source>
    ): List<Destination> {
        val mapping = getMapping(descriptor)

        return sourceCollection.map { return@map mapping.performMapping(it) }
    }

    override fun <Source : Any, Destination : Any> mapMany(
        descriptor: MappingDescriptor<Source, Destination>,
        sourceCollection: Iterable<Source>,
        destinationCollection: List<Destination>
    ) {
        val mapping = getMapping(descriptor)

        val sourceIterator = sourceCollection.iterator()
        val destinationIterator = destinationCollection.iterator()
        while (true) {
            val hasNexSource = sourceIterator.hasNext()
            val hasNexDestination = destinationIterator.hasNext()

            if (hasNexDestination != hasNexSource) {
                throw MapperException(cause = reasonableException(MAPPING_COLLECTION_DIFFERENT_SIZE, descriptor.toString()))
            }
            val hasNext = hasNexSource && hasNexDestination
            if (!hasNext) {
                break
            }
            val source = sourceIterator.next()
            val destination = destinationIterator.next()

            mapping.performMapping(source, destination)
        }
    }

    override fun <Source : Any, Destination : Any> mapManyNullable(
        descriptor: MappingDescriptor<Source, Destination>,
        sourceCollection: Iterable<Source?>
    ): List<Destination?> {
        val mapping = getMapping(descriptor)

        return sourceCollection.map { source -> source?.let { return@let mapping.performMapping(it) } }
    }

    override fun <Source : Any, Destination : Any> mapManyNullable(
        descriptor: MappingDescriptor<Source, Destination>,
        sourceCollection: Iterable<Source?>,
        destinationCollection: MutableList<Destination?>
    ) {
        val mapping = getMapping(descriptor)

        val sourceIterator = sourceCollection.iterator()
        val destinationIterator = destinationCollection.listIterator()
        while (true) {
            val hasNexSource = sourceIterator.hasNext()
            val hasNexDestination = destinationIterator.hasNext()

            if (hasNexDestination != hasNexSource) {
                throw MapperException(cause = reasonableException(MAPPING_COLLECTION_DIFFERENT_SIZE))
            }
            val hasNext = hasNexSource && hasNexDestination
            if (!hasNext) {
                break
            }
            val source = sourceIterator.next()
            val destination = destinationIterator.next()

            if (source !== null) {
                if (destination == null) {
                    destinationIterator.set(mapping.performMapping(source))
                } else {
                    mapping.performMapping(source, destination)
                }
            } else {
                destinationIterator.set(null)
            }
        }
    }

    override fun <Source : Any, Destination : Any> runtimeMapMany(
        destinationClass: KClass<Destination>,
        sourceCollection: Iterable<Source>
    ): List<Destination> = sourceCollection.map { return@map runtimeMap(destinationClass, it) }

    override fun <Source : Any, Destination : Any> runtimeMapMany(
        destinationClass: KClass<Destination>,
        sourceCollection: Iterable<Source>,
        destinationCollection: List<Destination>
    ) {
        val sourceIterator = sourceCollection.iterator()
        val destinationIterator = destinationCollection.iterator()
        while (true) {
            val hasNexSource = sourceIterator.hasNext()
            val hasNexDestination = destinationIterator.hasNext()

            if (hasNexDestination != hasNexSource) {
                throw MapperException(cause = reasonableException(MAPPING_COLLECTION_DIFFERENT_SIZE))
            }
            val hasNext = hasNexSource && hasNexDestination
            if (!hasNext) {
                break
            }
            val source = sourceIterator.next()
            val destination = destinationIterator.next()

            runtimeMap(destinationClass, source, destination)
        }
    }

    override fun <Source : Any, Destination : Any> runtimeMapManyNullable(
        destinationClass: KClass<Destination>,
        sourceCollection: Iterable<Source?>
    ): List<Destination?> = sourceCollection.map { return@map runtimeMapNullable(destinationClass, it) }

    override fun <Source : Any, Destination : Any> runtimeMapManyNullable(
        destinationClass: KClass<Destination>,
        sourceCollection: Iterable<Source?>,
        destinationCollection: MutableList<Destination?>
    ) {
        val sourceIterator = sourceCollection.iterator()
        val destinationIterator = destinationCollection.listIterator()
        while (true) {
            val hasNexSource = sourceIterator.hasNext()
            val hasNexDestination = destinationIterator.hasNext()

            if (hasNexDestination != hasNexSource) {
                throw MapperException(cause = reasonableException(MAPPING_COLLECTION_DIFFERENT_SIZE))
            }
            val hasNext = hasNexSource && hasNexDestination
            if (!hasNext) {
                break
            }
            val source = sourceIterator.next()
            val destination = destinationIterator.next()

            if (source !== null) {
                if (destination == null) {
                    destinationIterator.set(runtimeMap(destinationClass, source))
                } else {
                    runtimeMap(destinationClass, source, destination)
                }
            } else {
                destinationIterator.set(null)
            }
        }
    }
}
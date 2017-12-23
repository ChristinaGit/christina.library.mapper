package christina.library.mapper

import christina.common.divisibleBy
import christina.common.exception.ReasonableException
import christina.common.exception.hasReason
import christina.library.mapper.core.Mapper
import christina.library.mapper.core.descriptor.MappingDescriptor
import christina.library.mapper.core.exception.MapperErrorReason
import christina.library.mapper.core.exception.MapperException
import christina.library.mapper.core.host.MappingHost
import christina.library.mapper.core.mapping.Mapping
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@Suppress("JoinDeclarationAndAssignment")
abstract class MapperTests(
    private val mapperFactory: () -> Mapper
) {
    companion object {
        open class Source {
            var first: String? = null
            var second: Int? = null
            var third: String? = null
        }

        open class Destination {
            var dstFirst: String? = null
            var dstSecond: Int? = null
            var dstThird: String? = null
        }

        fun generateSource(index: Int = 0) =
            Source().apply {
                first = "source $index first value"
                second = (43 * index) % 31
                third = "source $index third value"
            }

        fun generateSourceCollection(size: Int = 10) =
            (0 until size).map { generateSource(it) }

        fun generateSourceCollectionNullable(size: Int = 10, withNulls: Boolean = false) =
            (0 until size).map { if (withNulls && it divisibleBy 3) null else generateSource(it) }

        fun generateDestination(index: Int = 0) =
            spyk(Destination().apply {
                dstFirst = "destination $index first value"
                dstSecond = (443 * index) % 319
                dstThird = "destination $index third value"
            })

        fun generateDestinationCollection(size: Int = 10) =
            (0 until size).map { generateDestination(it) }

        fun generateDestinationCollectionNullable(size: Int = 10, withNulls: Boolean = false) =
            (0 until size).map { if (withNulls && it divisibleBy 2) null else generateDestination(it) }

        val mappingDescriptor = MappingDescriptor(Source::class, Destination::class)

        val mapping: Mapping<Source, Destination> =
            mockk<Mapping<Source, Destination>>().apply {
                every {
                    performMapping(any())
                } answers {
                    plainMapping(firstArg())
                }

                every {
                    performMapping(any(), any())
                } answers {
                    plainMapping(firstArg(), secondArg())
                }
            }

        val mappingHost: MappingHost =
            mockk<MappingHost>().apply {
                every { findMapping(any<MappingDescriptor<*, *>>()) } answers {
                    when (firstArg<MappingDescriptor<*, *>>()) {
                        mappingDescriptor -> mapping
                        else              -> null
                    }
                }
            }

        private fun plainMapping(source: Source): Destination =
            Destination().also {
                plainMapping(source, it)
            }

        private fun plainMapping(source: Source, destination: Destination) {
            destination.apply {
                dstFirst = source.first
                dstSecond = source.second
                dstThird = source.third
            }
        }

        fun assertEquals(source: Source?, destination: Destination?) {
            if (source != null && destination != null) {
                assertEquals(source.first, destination.dstFirst)
                assertEquals(source.second, destination.dstSecond)
                assertEquals(source.third, destination.dstThird)
            } else {
                assertTrue(source == null)
                assertTrue(destination == null)
            }
        }

        fun assertEquals(
            sourceCollection: Iterable<Source?>?,
            destinationCollection: Iterable<Destination?>?
        ) {
            if (sourceCollection != null && destinationCollection != null) {
                val sourceIterator = sourceCollection.iterator()
                val destinationIterator = destinationCollection.iterator()

                while (true) {
                    val hasNextSource = sourceIterator.hasNext()
                    val hasNextDestination = destinationIterator.hasNext()

                    assertTrue(hasNextDestination == hasNextSource)

                    val hasNext = hasNextSource && hasNextDestination
                    if (!hasNext) {
                        break
                    }
                    val source = sourceIterator.next()
                    val destination = destinationIterator.next()

                    assertEquals(source, destination)
                }
            } else {
                assertTrue(sourceCollection == null)
                assertTrue(destinationCollection == null)
            }
        }

        fun assertNotChanged(destination: Destination) {
            verify(exactly = 0) {
                destination.dstFirst = any()
                destination.dstSecond = any()
                destination.dstThird = any()
            }
        }
    }

    fun generateMapper() = mapperFactory()

    lateinit var mapper: Mapper

    @Before
    fun setup() {
        mapper = generateMapper()
    }

    @Test
    fun `getMapping_returnExistingMapping`() {
        //region Arrange
        val mapping: Mapping<*, *>
        //endregion

        //region Act
        mapping = mapper.getMapping(mappingDescriptor)
        //endregion

        //region Assert
        assertEquals(mapping, Companion.mapping)
        //endregion
    }

    @Test
    fun `getMapping_throwExceptionForNonExistentMapping`() {
        //region Arrange
        val exception: ReasonableException?
        //endregion

        //region Act
        exception = try {
            mapper.getMapping(MappingDescriptor(Destination::class, Source::class))
            null
        } catch (e: MapperException) {
            e.cause as ReasonableException
        }
        //endregion

        //region Assert
        assertTrue(exception !== null && exception.hasReason(MapperErrorReason.MAPPING_NOT_FOUND))
        //endregion
    }

    @Test
    fun `map_withoutReceiver`() {
        //region Arrange
        val source = generateSource()
        val destination: Destination
        //endregion

        //region Act
        destination = mapper.map(mappingDescriptor, source)
        //endregion

        //region Assert
        assertEquals(source, destination)
        //endregion
    }

    @Test
    fun `map_withReceiver`() {
        //region Arrange
        val source = generateSource()
        val destination = generateDestination()
        //endregion

        //region Act
        mapper.map(mappingDescriptor, source, destination)
        //endregion

        //region Assert
        assertEquals(source, destination)
        //endregion
    }

    @Test
    fun `mapNullable_withoutReceiver_notNull`() {
        //region Arrange
        val source = generateSource()
        val destination: Destination?
        //endregion

        //region Act
        destination = mapper.mapNullable(mappingDescriptor, source)
        //endregion

        //region Assert
        assertNotNull(destination)
        assertEquals(source, destination!!)
        //endregion
    }

    @Test
    fun `mapNullable_withReceiver_notNull`() {
        //region Arrange
        val source = generateSource()
        val destination = generateDestination()
        //endregion

        //region Act
        mapper.mapNullable(mappingDescriptor, source, destination)
        //endregion

        //region Assert
        assertEquals(source, destination)
        //endregion
    }

    @Test
    fun `mapNullable_withoutReceiver_null`() {
        //region Arrange
        val destination: Destination?
        //endregion

        //region Act
        destination = mapper.mapNullable(mappingDescriptor, null)
        //endregion

        //region Assert
        assertNull(destination)
        //endregion
    }

    @Test
    fun `mapNullable_withReceiver_null`() {
        //region Arrange
        val destination = generateDestination()
        //endregion

        //region Act
        mapper.mapNullable(mappingDescriptor, null, destination)
        //endregion

        //region Assert
        assertNotChanged(destination)
        //endregion
    }

    @Test
    fun `runtimeMap_withoutReceiver`() {
        //region Arrange
        val source = generateSource()
        val destination: Destination
        //endregion

        //region Act
        destination = mapper.runtimeMap(Destination::class, source as Any)
        //endregion

        //region Assert
        assertEquals(source, destination)
        //endregion
    }

    @Test
    fun `runtimeMap_withReceiver`() {
        //region Arrange
        val source = generateSource()
        val destination = generateDestination()
        //endregion

        //region Act
        mapper.runtimeMap(Destination::class, source as Any, destination)
        //endregion

        //region Assert
        assertEquals(source, destination)
        //endregion
    }

    @Test
    fun `runtimeMapNullable_withoutReceiver_notNull`() {
        //region Arrange
        val source = generateSource()
        val destination: Destination?
        //endregion

        //region Act
        destination = mapper.runtimeMapNullable(Destination::class, source as Any?)
        //endregion

        //region Assert
        assertNotNull(destination)
        assertEquals(source, destination!!)
        //endregion
    }

    @Test
    fun `runtimeMapNullable_withReceiver_notNull`() {
        //region Arrange
        val source = generateSource()
        val destination = generateDestination()
        //endregion

        //region Act
        mapper.runtimeMapNullable(Destination::class, source as Any?, destination)
        //endregion

        //region Assert
        assertEquals(source, destination)
        //endregion
    }

    @Test
    fun `runtimeMapNullable_withoutReceiver_null`() {
        //region Arrange
        val destination: Destination?
        //endregion

        //region Act
        destination = mapper.runtimeMapNullable(Destination::class, null)
        //endregion

        //region Assert
        assertNull(destination)
        //endregion
    }

    @Test
    fun `runtimeMapNullable_withReceiver_null`() {
        //region Arrange
        val destination = generateDestination()
        //endregion

        //region Act
        mapper.runtimeMapNullable(Destination::class, null, destination)
        //endregion

        //region Assert
        assertNotChanged(destination)
        //endregion
    }

    @Test
    fun `mapMany_withoutReceiver`() {
        //region Arrange
        val sourceCollection = generateSourceCollection()
        val destinationCollection: List<Destination>
        //endregion

        //region Act
        destinationCollection = mapper.mapMany(mappingDescriptor, sourceCollection)
        //endregion

        //region Assert
        assertEquals(sourceCollection, destinationCollection)
        //endregion
    }

    @Test
    fun `mapMany_withReceiver`() {
        //region Arrange
        val sourceCollection = generateSourceCollection()
        val destinationCollection = generateDestinationCollection()
        //endregion

        //region Act
        mapper.mapMany(mappingDescriptor, sourceCollection, destinationCollection)
        //endregion

        //region Assert
        assertEquals(sourceCollection, destinationCollection)
        //endregion
    }

    @Test
    fun `mapManyNullable_withoutReceiver_notNull`() {
        //region Arrange
        val sourceCollection = generateSourceCollectionNullable()
        val destinationCollection: List<Destination?>?
        //endregion

        //region Act
        destinationCollection = mapper.mapManyNullable(mappingDescriptor, sourceCollection)
        //endregion

        //region Assert
        assertNotNull(destinationCollection)
        assertFalse(sourceCollection.contains(null))
        assertFalse(destinationCollection.contains(null))
        assertEquals(sourceCollection, destinationCollection)
        //endregion
    }

    @Test
    fun `mapManyNullable_withReceiver_notNull`() {
        //region Arrange
        val sourceCollection = generateSourceCollectionNullable()
        val destinationCollection = generateDestinationCollectionNullable().toMutableList()
        //endregion

        //region Act
        mapper.mapManyNullable(mappingDescriptor, sourceCollection, destinationCollection)
        //endregion

        //region Assert
        assertNotNull(destinationCollection)
        assertFalse(sourceCollection.contains(null))
        assertFalse(destinationCollection.contains(null))
        assertEquals(sourceCollection, destinationCollection)
        //endregion
    }

    @Test
    fun `mapManyNullable_withoutReceiver_null`() {
        //region Arrange
        val sourceCollection = generateSourceCollectionNullable(withNulls = true)
        val destinationCollection: List<Destination?>?
        //endregion

        //region Act
        destinationCollection = mapper.mapManyNullable(mappingDescriptor, sourceCollection)
        //endregion

        //region Assert
        assertNotNull(destinationCollection)
        assertTrue(sourceCollection.contains(null))
        assertTrue(destinationCollection.contains(null))
        assertEquals(sourceCollection, destinationCollection)
        //endregion
    }

    @Test
    fun `mapManyNullable_withReceiver_null`() {
        //region Arrange
        val sourceCollection = generateSourceCollectionNullable(withNulls = true)
        val destinationCollection = generateDestinationCollectionNullable(withNulls = true).toMutableList()
        //endregion

        //region Act
        mapper.mapManyNullable(mappingDescriptor, sourceCollection, destinationCollection)
        //endregion

        //region Assert
        assertTrue(sourceCollection.contains(null))
        assertTrue(destinationCollection.contains(null))
        assertEquals(sourceCollection, destinationCollection)
        //endregion
    }

    @Test
    fun `runtimeMapMany_withoutReceiver`() {
        //region Arrange
        val sourceCollection = generateSourceCollection()
        val destinationCollection: List<Destination>?
        //endregion

        //region Act
        destinationCollection = mapper.runtimeMapMany(Destination::class, sourceCollection as Iterable<Any>)
        //endregion

        //region Assert
        assertNotNull(destinationCollection)
        assertEquals(sourceCollection, destinationCollection)
        //endregion
    }

    @Test
    fun `runtimeMapMany_withReceiver`() {
        //region Arrange
        val sourceCollection = generateSourceCollection()
        val destinationCollection = generateDestinationCollection()
        //endregion

        //region Act
        mapper.runtimeMapMany(Destination::class, sourceCollection as Iterable<Any>, destinationCollection)
        //endregion

        //region Assert
        assertEquals(sourceCollection, destinationCollection)
        //endregion
    }

    @Test
    fun `runtimeMapManyNullable_withoutReceiver_notNull`() {
        //region Arrange
        val sourceCollection = generateSourceCollectionNullable()
        val destinationCollection: List<Destination?>?
        //endregion

        //region Act
        destinationCollection = mapper.runtimeMapManyNullable(Destination::class, sourceCollection as Iterable<Any?>)
        //endregion

        //region Assert
        assertNotNull(destinationCollection)
        assertFalse(sourceCollection.contains(null))
        assertFalse(destinationCollection.contains(null))
        assertEquals(sourceCollection, destinationCollection)
        //endregion
    }

    @Test
    fun `runtimeMapManyNullable_withReceiver_notNull`() {
        //region Arrange
        val sourceCollection = generateSourceCollectionNullable()
        val destinationCollection = generateDestinationCollectionNullable().toMutableList()
        //endregion

        //region Act
        mapper.runtimeMapManyNullable(Destination::class, sourceCollection as Iterable<Any?>, destinationCollection)
        //endregion

        //region Assert
        assertFalse(sourceCollection.contains(null))
        assertFalse(destinationCollection.contains(null))
        assertEquals(sourceCollection, destinationCollection)
        //endregion
    }

    @Test
    fun `runtimeMapManyNullable_withoutReceiver_null`() {
        //region Arrange
        val sourceCollection = generateSourceCollectionNullable(withNulls = true)
        val destinationCollection: List<Destination?>?
        //endregion

        //region Act
        destinationCollection = mapper.runtimeMapManyNullable(Destination::class, sourceCollection)
        //endregion

        //region Assert
        assertNotNull(destinationCollection)
        assertTrue(sourceCollection.contains(null))
        assertTrue(destinationCollection.contains(null))
        assertEquals(sourceCollection, destinationCollection)
        //endregion
    }

    @Test
    fun `runtimeMapManyNullable_withReceiver_null`() {
        //region Arrange
        val sourceCollection = generateSourceCollectionNullable(withNulls = true)
        val destinationCollection = generateDestinationCollectionNullable(withNulls = true).toMutableList()
        //endregion

        //region Act
        mapper.runtimeMapManyNullable(Destination::class, sourceCollection, destinationCollection)
        //endregion

        //region Assert
        assertNotNull(destinationCollection)
        assertTrue(sourceCollection.contains(null))
        assertTrue(destinationCollection.contains(null))
        assertEquals(sourceCollection, destinationCollection)
        //endregion
    }
}
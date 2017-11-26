package christina.library.mapper.delegate.mapping

import christina.common.exception.reasonableException
import christina.library.mapper.core.exception.MapperErrorReasons.MAPPING_WITHOUT_RECEIVER_NOT_SUPPORTED
import christina.library.mapper.core.exception.MapperErrorReasons.MAPPING_WITH_RECEIVER_NOT_SUPPORTED
import christina.library.mapper.core.exception.MapperException
import christina.library.mapper.core.mapping.Mapping

class DelegateMapping<in Source : Any, Destination : Any>(
    private val mapping: ((Source) -> Destination)?,
    private val receiverMapping: ((Source, Destination) -> Unit)?
) : Mapping<Source, Destination> {
    override fun performMapping(source: Source): Destination =
        if (mapping === null) {
            throw MapperException(cause = reasonableException(MAPPING_WITHOUT_RECEIVER_NOT_SUPPORTED))
        } else {
            mapping.invoke(source)
        }

    override fun performMapping(source: Source, destination: Destination) =
        if (receiverMapping === null) {
            throw MapperException(cause = reasonableException(MAPPING_WITH_RECEIVER_NOT_SUPPORTED))
        } else {
            receiverMapping.invoke(source, destination)
        }
}
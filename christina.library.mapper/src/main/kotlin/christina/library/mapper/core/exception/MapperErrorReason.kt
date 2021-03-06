package christina.library.mapper.core.exception

enum class MapperErrorReason {
    MAPPING_NOT_FOUND,
    MAPPING_ALREADY_EXISTS,
    MAPPING_WITHOUT_RECEIVER_NOT_SUPPORTED,
    MAPPING_WITH_RECEIVER_NOT_SUPPORTED,
    MAPPING_COLLECTION_DIFFERENT_SIZE
}
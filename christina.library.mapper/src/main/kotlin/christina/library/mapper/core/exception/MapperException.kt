package christina.library.mapper.core.exception

import christina.common.exception.ExceptionDefaultSettings.ENABLE_SUPPRESSION
import christina.common.exception.ExceptionDefaultSettings.WRITABLE_STACKTRACE

open class MapperException
@JvmOverloads
constructor(
    message: String? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = ENABLE_SUPPRESSION,
    writableStackTrace: Boolean = WRITABLE_STACKTRACE
) : Exception(message, cause, enableSuppression, writableStackTrace)
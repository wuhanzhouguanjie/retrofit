package com.lark.framework.frame.network.data

/**
 * 具体error继承自IError，命名规范：<模块><功能><具体错误>
 * msg: msg是提示给用户的信息，msg为null是不做提示
 * serverCode: 服务端返回的错误码
 * exception: 特定场景异常收集，方便打印日志排查问题
 * data: 附属数据
 */
open class IError(
  open val msg: String = "",
  open var serverCode: Int = 0,
  open val exception: Throwable? = null,
  open var data: Any? = null,
) {

  override fun toString(): String {
    return "${this.javaClass.simpleName}(msg='$msg', serverCode=$serverCode, exception=$exception, data=$data)"
  }

  fun getStatError() =
    "${this.javaClass.simpleName}_${serverCode}_${msg}_${exception?.message ?: ""}"

}

/**
 * http状态码不是成功状态错误
 */
class NetworkHttpStatusNoSuccessError(override val exception: Throwable) : IError()

/**
 * http执行错误
 */
class NetworkHttpExecuteError(override val exception: Throwable) : IError()

/**
 * http响应包体为null
 */
class NetworkHttpResBodyNullError(override val exception: Throwable) : IError()

/**
 * 通用参数错误
 */
class CommonParamError(override val msg: String = "") : IError()

/**
 * 数据null错误
 */
class CommonDataNullError : IError()

/**
 * 切换状态时相同
 */
class CommonSwitchStateSameError : IError()

/**
 * 当前activity为null
 */
class CurrentActivityNullError : IError()

/**
 * IO异常
 */
class CommonIOError(override val exception: Throwable) : IError()

class CommonCollectionElementAddExistError : IError()

class CommonCollectionElementRemoveNoExistError : IError()

class CommonUrlError(override var data: Any?) : IError()

class CommonTimeoutError : IError()

sealed class Rlt<out R> {

  data class Success<out T>(val data: T) : Rlt<T>()

  data class Failed(val error: IError) : Rlt<Nothing>()

  override fun toString(): String {
    return when (this) {
      is Success<*> -> "Success[data=$data]"
      is Failed -> "Failed[error=$error]"
    }
  }

}

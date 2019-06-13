package com.romanidze.documenti.config.security.handlers

import com.romanidze.documenti.config.security.errors.ServiceError

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.ConversionNotSupportedException
import org.springframework.beans.TypeMismatchException
import org.springframework.dao.DataIntegrityViolationException

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import javax.validation.ConstraintViolationException

@RestControllerAdvice
class RESTExceptionHandler: ResponseEntityExceptionHandler() {

    companion object{
        val logger: Logger = LogManager.getLogger(RESTExceptionHandler::class)
    }


    override fun handleMissingServletRequestParameter(ex: MissingServletRequestParameterException,
                                                      headers: HttpHeaders,
                                                      status: HttpStatus,
                                                      request: WebRequest): ResponseEntity<Any> {

        val errorMessage = "Отсутствует параметр ${ex.parameterName} типа ${ex.parameterType}"
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.BAD_REQUEST, errorMessage, ex)

        return ResponseEntity(serviceError, serviceError.status!!)

    }

    override fun handleHttpMediaTypeNotSupported(ex: HttpMediaTypeNotSupportedException,
                                                 headers: HttpHeaders,
                                                 status: HttpStatus,
                                                 request: WebRequest): ResponseEntity<Any> {

        val sb = StringBuilder()

        sb.append("Для данного запроса тип мультимедиа ").append(ex.contentType).append(" не поддерживается.")
          .append("Поддерживаемые типы: ")

        ex.supportedMediaTypes
          .forEach {
              sb.append(it).append(", ")
          }

        val errorMessage = sb.substring(0, sb.length - 2)
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, errorMessage, ex)

        return ResponseEntity(serviceError, serviceError.status!!)

    }

    override fun handleHttpRequestMethodNotSupported(ex: HttpRequestMethodNotSupportedException,
                                                     headers: HttpHeaders,
                                                     status: HttpStatus,
                                                     request: WebRequest): ResponseEntity<Any> {

        val sb = StringBuilder()

        sb.append("В данном случае тип запроса ").append(ex.method).append(" не поддерживается.")
          .append("Поддерживаемые типы: ")

        ex.supportedHttpMethods!!
          .forEach{
              sb.append(it).append(", ")
          }

        val errorMessage = sb.substring(0, sb.length - 2)
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.METHOD_NOT_ALLOWED, errorMessage, ex)

        return ResponseEntity(serviceError, serviceError.status!!)

    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException,
                                              headers: HttpHeaders,
                                              status: HttpStatus,
                                              request: WebRequest): ResponseEntity<Any> {

        val errorMessage = "Ошибка валидации модели"
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.BAD_REQUEST)
        serviceError.message = errorMessage
        serviceError.addValidationFieldErrors(ex.bindingResult.fieldErrors)
        serviceError.addValidationObjectErrors(ex.bindingResult.globalErrors)

        return ResponseEntity(serviceError, serviceError.status!!)

    }

    override fun handleBindException(ex: BindException,
                                     headers: HttpHeaders,
                                     status: HttpStatus,
                                     request: WebRequest): ResponseEntity<Any> {

        val errorMessage = "Запрос продан к неверной сущности"
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.BAD_REQUEST)
        serviceError.message = errorMessage
        serviceError.addValidationFieldErrors(ex.bindingResult.fieldErrors)
        serviceError.addValidationObjectErrors(ex.bindingResult.globalErrors)

        return ResponseEntity(serviceError, serviceError.status!!)

    }

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException,
                                              headers: HttpHeaders,
                                              status: HttpStatus,
                                              request: WebRequest): ResponseEntity<Any> {

        val errorMessage = "Некорректное тело JSON-запроса"
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.BAD_REQUEST, errorMessage, ex)
        return ResponseEntity(serviceError, serviceError.status!!)

    }

    override fun handleHttpMessageNotWritable(ex: HttpMessageNotWritableException,
                                              headers: HttpHeaders,
                                              status: HttpStatus,
                                              request: WebRequest): ResponseEntity<Any> {

        val errorMessage = "Ошибка при создании тела JSON"
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, ex)
        return ResponseEntity(serviceError, serviceError.status!!)

    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    protected fun handleDataIntegityViolation(ex: DataIntegrityViolationException,
                                              request: WebRequest): ResponseEntity<Any>{

        val errorMessage: String
        val serviceError: ServiceError

        if(ex.cause is ConstraintViolationException){

            errorMessage = "При сохранении в БД сущность не прошла валидацию"
            logger.error(errorMessage)

            serviceError = ServiceError(HttpStatus.CONFLICT, errorMessage, ex.cause as ConstraintViolationException)
            return ResponseEntity(serviceError, serviceError.status!!)

        }
        errorMessage = "Произошла ошибка на сервере"
        logger.error(errorMessage)

        serviceError = ServiceError(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, ex)
        return ResponseEntity(serviceError, serviceError.status!!)

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatch(ex: MethodArgumentTypeMismatchException,
                                                   request: WebRequest): ResponseEntity<Any>{

        val errorMessage = "Параметр ${ex.name} типа ${ex.value} " +
                           "не конвертируется в тип ${ex.requiredType!!.simpleName}"
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.BAD_REQUEST)
        serviceError.message = errorMessage
        serviceError.debugMessage = ex.message

        return ResponseEntity(serviceError, serviceError.status!!)

    }

    override fun handleTypeMismatch(ex: TypeMismatchException,
                                    headers: HttpHeaders,
                                    status: HttpStatus,
                                    request: WebRequest): ResponseEntity<Any> {

        val errorMessage = "Значение ${ex.value} для ${ex.propertyName} должно быть типа ${ex.requiredType}"
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.BAD_REQUEST)
        serviceError.message = errorMessage
        serviceError.debugMessage = ex.message

        return ResponseEntity(serviceError, serviceError.status!!)

    }

    override fun handleMissingServletRequestPart(ex: MissingServletRequestPartException,
                                                 headers: HttpHeaders,
                                                 status: HttpStatus,
                                                 request: WebRequest): ResponseEntity<Any> {

        val errorMessage = "В запросе нет заголовка ${ex.requestPartName}"
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.BAD_REQUEST)
        serviceError.message = errorMessage
        serviceError.debugMessage = ex.message

        return ResponseEntity(serviceError, serviceError.status!!)

    }

    override fun handleNoHandlerFoundException(ex: NoHandlerFoundException,
                                               headers: HttpHeaders,
                                               status: HttpStatus,
                                               request: WebRequest): ResponseEntity<Any> {

        val errorMessage = "Для ${ex.httpMethod} на ${ex.requestURL} с заголовками ${ex.headers} " +
                           "не найден нужный контроллер"
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.NOT_FOUND)
        serviceError.message = errorMessage
        serviceError.debugMessage = ex.message

        return ResponseEntity(serviceError, serviceError.status!!)

    }

    @ExceptionHandler(Exception::class)
    protected fun handleUnclassifiedException(ex: Exception, request: WebRequest): ResponseEntity<Any>{

        val errorMessage = "Произошла неизвестная ошибка"
        val errorDebugMessage = "Причина ошибки: ${ex.cause}"

        ex.printStackTrace()

        logger.error("$errorMessage \n $errorDebugMessage")

        val serviceError = ServiceError(HttpStatus.BAD_REQUEST)
        serviceError.message = errorMessage
        serviceError.debugMessage = errorDebugMessage

        return ResponseEntity(serviceError, serviceError.status!!)

    }

    override fun handleHttpMediaTypeNotAcceptable(ex: HttpMediaTypeNotAcceptableException,
                                                  headers: HttpHeaders,
                                                  status: HttpStatus, request: WebRequest): ResponseEntity<Any> {

        val sb = StringBuilder()

        sb.append("Данный тип медиа неприемлем. Поддерживаемые типы: ")

        ex.supportedMediaTypes
          .forEach {
              sb.append(it).append(", ")
          }

        val errorMessage = sb.substring(0, sb.length - 2)
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.NOT_ACCEPTABLE, errorMessage, ex)
        serviceError.message = errorMessage
        serviceError.debugMessage = ex.message

        return ResponseEntity(serviceError, serviceError.status!!)

    }

    override fun handleMissingPathVariable(ex: MissingPathVariableException,
                                           headers: HttpHeaders,
                                           status: HttpStatus,
                                           request: WebRequest): ResponseEntity<Any> {

        val errorMessage = "В URL запроса не хватает переменной ${ex.variableName} для параметра ${ex.parameter} " +
                           "с типом ${ex.parameter.nestedParameterType.simpleName}"
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, ex)
        serviceError.message = errorMessage
        serviceError.debugMessage = ex.message

        return ResponseEntity(serviceError, serviceError.status!!)

    }

    override fun handleServletRequestBindingException(ex: ServletRequestBindingException,
                                                      headers: HttpHeaders,
                                                      status: HttpStatus,
                                                      request: WebRequest): ResponseEntity<Any> {

        val errorMessage = "Получено неприемлемое тело запроса"
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.BAD_REQUEST)
        serviceError.message = errorMessage
        serviceError.debugMessage = ex.message

        return ResponseEntity(serviceError, serviceError.status!!)

    }

    override fun handleConversionNotSupported(ex: ConversionNotSupportedException,
                                              headers: HttpHeaders,
                                              status: HttpStatus,
                                              request: WebRequest): ResponseEntity<Any> {

        val errorMessage = "Не найден конвертор ${ex.propertyName} типа ${ex.requiredType}"
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, ex)
        serviceError.message = errorMessage
        serviceError.debugMessage = ex.message

        return ResponseEntity(serviceError, serviceError.status!!)

    }

}
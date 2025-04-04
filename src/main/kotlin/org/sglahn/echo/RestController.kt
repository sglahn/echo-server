package org.sglahn.echo

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class EchoController() {
    private val LOG: Logger = LoggerFactory.getLogger(EchoController::class.java)

    @Autowired
    private val request: HttpServletRequest? = null

    @Value( "\${echo-server.id}" )
    val id: String = "default"

    @RequestMapping("/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun echo(@RequestBody(required = false) rawBody: ByteArray?) : ResponseEntity<EchoPayload> {
        val headers = HashMap<String, String>()
        request!!.headerNames.asIterator().forEach { name ->
            if (name == "cookie") return@forEach
            headers[name] = request.getHeader(name)
        }
        val cookies = HashMap<String, String>()
        if (request.cookies != null) {
            request.cookies.forEach { cookie ->
                cookies[cookie.name] = cookie.value
            }
        }

        val parameters = HashMap<String, String>()
        request.parameterMap.forEach() { (name, values) ->
            parameters[name] = values.joinToString(",")
        }

        val response = EchoPayload(
            id,
            request!!.protocol,
            request.method,
            headers,
            cookies,
            parameters,
            request.servletPath,
            if (rawBody != null) Base64.getEncoder().encodeToString(rawBody) else null
        )
        LOG.info("Received: $response")

        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
}

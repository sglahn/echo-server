package org.sglahn.echo

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class EchoPayload(var echoServerId: String,
                       var protocol: String,
                       var method: String,
                       var headers: Map<String, String>,
                       var cookies: Map<String, String>,
                       var parameters: Map<String, String>,
                       var path: String?,
                       var body: String?)

package org.bialydunajec.authorization.server.security

data class OAuth2UserSnapshot(
        val userId: String,
        val emailAddress: String,
        val username: String?,
        val password: String,
        val enabled: Boolean
)
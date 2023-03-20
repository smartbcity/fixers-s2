package s2.bdd.auth

typealias OrganizationId = String
typealias UserId = String

interface AuthedUserDTO {
    val id: UserId
    val memberOf: OrganizationId?
    val roles: Array<String>
}

data class AuthedUser(
    override val id: UserId,
    override val memberOf: OrganizationId?,
    override val roles: Array<String>
): AuthedUserDTO

fun AuthedUserDTO.hasRole(role: String) = role in roles
fun AuthedUserDTO.hasRole(role: Role) = role.value in roles
fun AuthedUserDTO.hasRoles(vararg roles: String) = roles.all(this.roles::contains)
fun AuthedUserDTO.hasRoles(vararg roles: Role) = roles.map(Role::value).all(this.roles::contains)
fun AuthedUserDTO.hasOneOfRoles(vararg roles: Role) = roles.map(Role::value).any(this.roles::contains)
fun AuthedUserDTO.hasOneOfRoles(vararg roles: String) = roles.any(this.roles::contains)

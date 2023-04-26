package dave.devs.model

data class Countries(
    val name: String,
    val capital: String
) {
    fun matchSearchQuery(query: String): Boolean {
        val matchingQueries = listOf(
            "$name $capital",
            "$name$capital",
            "${name.first()}${capital.first()}"
        )

        return matchingQueries.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
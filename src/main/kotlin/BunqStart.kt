
import com.bunq.sdk.context.ApiContext.*
import com.bunq.sdk.context.ApiEnvironmentType
import com.bunq.sdk.context.BunqContext
import com.bunq.sdk.context.ApiContext
import com.bunq.sdk.http.Pagination
import com.bunq.sdk.model.generated.endpoint.Payment
import com.bunq.sdk.model.generated.endpoint.MonetaryAccountBank
import java.io.File


fun main() {
    // run this once to register, then comment out
    registerApiKey()

    // get your accounts
    getContext()
    val payments = getAllPayment(MonetaryAccountBank(), 1)
    printPayments(payments)
}

fun registerApiKey() {
    val apiKey = File("src/main/resources/api-key.txt")
        .readLines()[0]
        .trim()

    val environmentType = ApiEnvironmentType.PRODUCTION
    val deviceDescription = "Kotlin"

    val apiContext = create(
        environmentType, apiKey,
        deviceDescription
    )
    apiContext.save()
}

fun getContext() {
    val apiContext = ApiContext.restore()
    BunqContext.loadApiContext(apiContext)
}


fun getAllPayment(monetaryAccountBank: MonetaryAccountBank, count: Int): List<Payment> {
    val pagination = Pagination()
    pagination.count = count

    return Payment
        .list(monetaryAccountBank.id, pagination.urlParamsCountOnly)
        .value
}

fun printPayments(payments: List<Payment>) {
    payments.forEach { println(it.alias) }
}

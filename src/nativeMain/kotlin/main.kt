import platform.windows.NULL

/**
 * resolve IPv4, converts string of address and cidr to IPv4Address object with address and cidr
 *
 * @param: address: String, address in style of <ip address>/<cidr>, so 192.168.0.1/24
 *
 * @return: IPv4Address(Array<Int>, Int)
 */
fun resolveIPv4 (address: String): IPv4Address {
    val splits: List<String> = address.split("/")
    val ipv4: String = splits.first()
    val strNetmask: String = splits.last()
    val cidr = strNetmask.toInt()
    val strOctets: List<String> = ipv4.split(".")
    val octetsList: MutableList<Int> = mutableListOf()
    for (octet in strOctets) {
        octetsList.add(element = octet.toInt())
    }
    val octets = octetsList.toTypedArray()
    return IPv4Address(address = octets, cidr = cidr)
}




fun main() {
    print("Welcome to the Subnet Calculator. Press any key to continue...")
    val uselessInput = readlnOrNull()

    do {
        print("Enter an IPv4 address with CIDR (without space) or 'q' to quit: ")
        val input = readlnOrNull()
        if (input === null  || input == "q") {
            continue
        }

        try {
            val myAddress = resolveIPv4(input)
            myAddress.printInfo()
        } catch (e:Exception) {
            println("Layer 8 caused an Error:")
            println(e)
            println()
        }

    } while (input != "q")
}




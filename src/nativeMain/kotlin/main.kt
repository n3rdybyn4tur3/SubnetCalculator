
class Octet (var octet: Int)
class IPv4Address (var address: MutableList<Octet>, var netmask: Int){
    fun printInfo () {
        print("netmask: ")
        println(netmask)
        println("address (octets): ")
        print("     ")
        var counter = 0
        for (i in address) {
            print(i.octet)
            if (counter < 3) {
                print(".")
            }
            counter += 1
        }
        println()
    }
}

fun resolveIPv4 (address: String): IPv4Address {
    println(address)
    val splits: List<String> = address.split("/")
    val ipv4: String = splits.first()
    val strNetmask: String = splits.last()
    val netmask = strNetmask.toInt()
    val strOctets: List<String> = ipv4.split(".")
    var octets: MutableList<Octet> = mutableListOf()
    for (octet in strOctets) {
        octets.add(element = Octet(octet.toInt()))
    }

    return IPv4Address(address = octets, netmask= netmask)
}










fun main() {
    println("Let's create a subnet calculator")
    var myAddress = resolveIPv4("192.168.0.1/24")
    println("Return")
    myAddress.printInfo()
}





class Octet (var octet: Int)
class IPv4Address (private var address: List<Octet>, private var cidr: Int){

    private val subnetMask = cidrToSubnetMask()
    private val network = getNetwork()
    private val wildcardMask = getWildcard()
    private val broadcast = getBroadcast()
    private val usableRange = getRange()
    private val totalHosts = getTotalHosts()
    private val usableHosts = getUsableHosts()



    private fun cidrToSubnetMask(): Array<Int> {
        val mask: Array<Int> = arrayOf(0,0,0,0)
        for (i in 0..<cidr) {
            mask[i/8] = mask[i/8] + (1 shl (7-i%8))
        }
        return mask
    }


    private fun getNetwork(): Array<Int> {
        return arrayOf(0,0,0,0)
    }


    private fun getWildcard(): Array<Int> {
        return arrayOf(0,0,0,0)
    }

    private fun getBroadcast(): Array<Int> {
        return arrayOf(0,0,0,0)
    }


    private fun getRange(): String {
        return "0.0.0.0 - 255.255.255.255"
    }


    private fun getTotalHosts(): Int {
        return 0
    }


    private fun getUsableHosts(): Int {
        return 0
    }



    fun printInfo () {
        var strAddress= ""
        for (i in address) {
            strAddress += i.octet
            strAddress += "."
        }
        strAddress = strAddress.dropLast(1)
        println("address: $strAddress")

        val strNetwork = printOctets(network)
        println("network address: $strNetwork")

        println("CIDR: /$cidr")

        val strSubnetMask = printOctets(subnetMask)
        println("subnet mask: $strSubnetMask")

        val strWildcardMask = printOctets(wildcardMask)
        println("wildcard mask: $strWildcardMask")

        val strBroadcast = printOctets(broadcast)
        println("broadcast address: $strBroadcast")

        println("Usable Host Range: $usableRange")
        println("Total Hosts: $totalHosts")
        println("Usable Hosts: $usableHosts")

        println()
    }
}

fun printOctets(octets: Array<Int>): String {
    var result = ""
    for (i in octets) {
        result += i
        result += "."
    }

    return result.dropLast(1)
}


fun resolveIPv4 (address: String): IPv4Address {
    println(address)
    val splits: List<String> = address.split("/")
    val ipv4: String = splits.first()
    val strNetmask: String = splits.last()
    val cidr = strNetmask.toInt()
    val strOctets: List<String> = ipv4.split(".")
    val octets: MutableList<Octet> = mutableListOf()
    for (octet in strOctets) {
        octets.add(element = Octet(octet.toInt()))
    }

    return IPv4Address(address = octets, cidr = cidr)
}










fun main() {
    println("Let's create a subnet calculator")
    val myAddress = resolveIPv4("192.168.0.1/24")

    myAddress.printInfo()
}




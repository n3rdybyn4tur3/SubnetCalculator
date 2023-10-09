import kotlin.math.pow

class IPv4Address (private var address: Array<Int>, private var cidr: Int){

    private val bRange: Int = 32-cidr
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
        var network: Array<Int> = arrayOf(0,0,0,0)
        for (i in 0..3) {
            val localVar: Int = address[i] and subnetMask[i]
            network[i] = localVar
        }
        return network
    }


    private fun getWildcard(): Array<Int> {
        var wildcard = arrayOf(0,0,0,0)
        for (i in 0..<bRange) {
            wildcard[3-i/8] = wildcard[3-i/8] + (1 shl (i%8))
        }
        return wildcard
    }

    private fun getBroadcast(): Array<Int> {
        var broad = network.copyOf()
        //FIXME: broadcast gets calculate wrong
        for (i in 0..<bRange) {
            broad[3-i/8] = broad[3-1/8] + (1 shl (i%8))
        }
        return broad
    }


    private fun getRange(): String {
        var firstIP = network.copyOf()
        var lastIP = broadcast.copyOf()
        firstIP[3] = firstIP[3] +1
        lastIP[3] = lastIP[3] -1

        var strFirstIP = printOctets(firstIP)
        var strLastIP = printOctets(lastIP)
        return "$strFirstIP - $strLastIP"
    }


    private fun getTotalHosts(): Int {
        val total = 2.0.pow(bRange)
        return total.toInt()
    }


    private fun getUsableHosts(): Int {
        return totalHosts-2
    }



    fun printInfo () {
        val strAddress = printOctets(address)
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
    var octetsList: MutableList<Int> = mutableListOf()
    for (octet in strOctets) {
        octetsList.add(element = octet.toInt())
    }
    val octets = octetsList.toTypedArray()
    return IPv4Address(address = octets, cidr = cidr)
}










fun main() {
    println("Let's create a subnet calculator")
    val myAddress = resolveIPv4("192.168.0.1/24")

    myAddress.printInfo()
}




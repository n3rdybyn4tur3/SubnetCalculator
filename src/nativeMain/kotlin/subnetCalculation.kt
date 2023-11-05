import kotlin.math.pow


/**
 * class contains all information about an IPv4 address and calculates them based on submitted address and CIDR netmask
 *
 * public attributes: None
 * public methods: printInfo() - prints information about all IPv4 address
 *
 * @param:
 * - address: Array<Int>, octets of IPv4 address
 * - cidr: Int, int of cidr netmask
 *
 * @sample:
 * IPv4Address(arrayOf(192, 168, 0, 1), 24)
 */
class IPv4Address (private var address: Array<Int>, private var cidr: Int){
    /**
     * subnetMask: Array<Int>, array of subnet mask: arrayOf(255,255,255,0)
     * network: Array<Int>, array of network address octets: arrayOf(192,168,0,0)
     * wildcardMask: Array<Int>, array of wildcard mask: arrayOf(0,0,0,255)
     * broadcast: Array<Int>, array of broadcast address octets: arrayOf(192.168.0.255)
     * usableRange: String, string of usable host ranges: "192.168.0.1 - 192.168.0.254"
     * totalHosts: Int, integer with total host addresses of the network: 256
     * usableHosts: Int, integer with usable host addresses of the network: 254
     */


    private val bRange: Int = 32-cidr
    private val subnetMask = cidrToSubnetMask()
    private val network = getNetwork()
    private val wildcardMask = getWildcard()
    private val broadcast = getBroadcast()
    private val usableRange = getRange()
    private val totalHosts = getTotalHosts()
    private val usableHosts = getUsableHosts()


    /**
     * CIDR to subnet mask
     */
    private fun cidrToSubnetMask(): Array<Int> {
        val mask: Array<Int> = arrayOf(0,0,0,0)
        for (i in 0..<cidr) {
            mask[i/8] = mask[i/8] + (1 shl (7-i%8))
        }
        return mask
    }


    /**
     * network address
     */
    private fun getNetwork(): Array<Int> {
        val network: Array<Int> = arrayOf(0,0,0,0)
        for (i in 0..3) {
            val localVar: Int = address[i] and subnetMask[i]
            network[i] = localVar
        }
        return network
    }


    /**
     * wildcard mask
     */
    private fun getWildcard(): Array<Int> {
        val wildcard = arrayOf(0,0,0,0)
        for (i in 0..<bRange) {
            wildcard[3-i/8] = wildcard[3-i/8] + (1 shl (i%8))
        }
        return wildcard
    }

    /**
     * broadcast address
     */
    private fun getBroadcast(): Array<Int> {
        val broad = network.copyOf()
        for (i in 0..<4) {
            broad[i] = broad[i] + wildcardMask[i]
        }
        return broad
    }


    /**
     * usable host range
     */
    private fun getRange(): String {
        val firstIP = network.copyOf()
        val lastIP = broadcast.copyOf()
        firstIP[3] = firstIP[3] +1
        lastIP[3] = lastIP[3] -1

        val strFirstIP = printOctets(firstIP)
        val strLastIP = printOctets(lastIP)
        return "$strFirstIP - $strLastIP"
    }


    /**
     * total hosts
     */
    private fun getTotalHosts(): Int {
        val total = 2.0.pow(bRange)
        return total.toInt()
    }


    /**
     * usable hosts
     */
    private fun getUsableHosts(): Int {
        return totalHosts-2
    }


    /**
     * print Info about IPv4 address
     *
     * @return: None
     */
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

/**
 * print Octets, generates out of octets string with dot notation
 *
 * @param: octets: Array<Int>, e.g. ip address, subnet mask, ...
 *
 * @return: string, octet1.octet2.octet3.octet4
 */
fun printOctets(octets: Array<Int>): String {
    var result = ""
    for (i in octets) {
        result += i
        result += "."
    }

    return result.dropLast(1)
}
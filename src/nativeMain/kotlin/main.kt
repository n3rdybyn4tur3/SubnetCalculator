
class Octet (var octet: Int)
class IPv4Address (var address: List<Octet>, var CIDR: Int){

    val subentmask = cidrToSubnetmask(CIDR)


    fun cidrToSubnetmask(CIDR: Int): Array<Int> {
        var mask: Array<Int> = arrayOf(0,0,0,0)
        for (i in 0..<CIDR) {
            mask[i/8] = mask[i/8] + (1 shl (7-i%8))
        }
        return mask
    }
    fun printInfo () {
        var strAddress: String= ""
        for (i in address) {
            strAddress += i.octet
            strAddress += "."
        }
        strAddress.drop(1).dropLast(1)
        println("address: $strAddress")

        var strSubnetmask: String =""
        for (i in subentmask) {
            strSubnetmask += i
            strSubnetmask += "."
        }
        strSubnetmask.drop(1).dropLast(1)
        println("subnet mask: $strSubnetmask")

        println("CIDR: /$CIDR")

        println()
    }
}

fun resolveIPv4 (address: String): IPv4Address {
    println(address)
    val splits: List<String> = address.split("/")
    val ipv4: String = splits.first()
    val strNetmask: String = splits.last()
    val CIDR = strNetmask.toInt()
    val strOctets: List<String> = ipv4.split(".")
    val octets: MutableList<Octet> = mutableListOf()
    for (octet in strOctets) {
        octets.add(element = Octet(octet.toInt()))
    }

    return IPv4Address(address = octets, CIDR= CIDR)
}










fun main() {
    println("Let's create a subnet calculator")
    val myAddress = resolveIPv4("192.168.0.1/24")
    println("Return")
    myAddress.printInfo()
    //println()
    //println()
    /*
    for (i: Int in 1..32) {
        print("Mask /$i: ")
        cidrToSubnetmask(i)
        println()
    }
    */
}




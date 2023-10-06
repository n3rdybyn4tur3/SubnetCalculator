fun resolveIPv4 (address: String): Array<Int> {
    println(address)
    return arrayOf(123, 45, 67, 89)
}










fun main() {
    println("Let's create a subnet calculator")
    for (octet in resolveIPv4("test")) {
        println(octet)
    }
}




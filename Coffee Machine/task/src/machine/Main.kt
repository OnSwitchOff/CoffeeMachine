package machine

fun main() {
    val machine = CoffeeMachine()
    machine.canIMakeNCups()
}

class CoffeeMachine {
    private var water: Int = 0
    private var milk: Int = 0
    private var beans: Int = 0
    private var totalCupsByBeans: Int = 0
    private var totalCupsByMilk: Int = 0
    private var totalCupsByWater: Int = 0
    private val totalPossibleCups: Int
    get() {
        return when {
            this.totalCupsByBeans <= this.totalCupsByMilk && this.totalCupsByBeans <= this.totalCupsByWater -> this.totalCupsByBeans
            this.totalCupsByMilk <= this.totalCupsByWater -> this.totalCupsByMilk
            else -> this.totalCupsByWater
        }
    }

    constructor(_water: Int, _milk: Int, _beans: Int) {
        this.water = _water
        this.milk = _milk
        this.beans = _beans
        calcTotalCups(this.water, this.milk, this.beans)
    }

    constructor() {
        this.water = askInt("Write how many ml of water the coffee machine has:")
        this.milk = askInt("Write how many ml of milk the coffee machine has:")
        this.beans = askInt("Write how many grams of coffee beans the coffee machine has:")
        calcTotalCups(this.water, this.milk, this.beans)
    }

    private fun calcTotalCups (_water: Int, _milk: Int, _beans: Int) {
        this.totalCupsByWater = _water / waterPerCup
        this.totalCupsByMilk = _milk / milkPerCup
        this.totalCupsByBeans = _beans / beansPerCup
    }

    companion object {
        private const val waterPerCup = 200
        private const val milkPerCup = 50
        private const val beansPerCup = 15

        fun calcTotalIngredients(cupNumber: Int) {
            println("For $cupNumber cups of coffee you will need:")
            println("${cupNumber * waterPerCup} ml of water")
            println("${cupNumber * milkPerCup} ml of milk")
            println("${cupNumber * beansPerCup} g of coffee beans")
        }
    }

    private fun askInt(message: String): Int {
        println(message)
        return readLine()!!.toInt()
    }

    fun canIMakeNCups() {
        canIMakeNCups(askInt("Write how many cups of coffee you will need:"))
    }

    private fun canIMakeNCups(cupNumber: Int) {
        println(
            when {
                cupNumber == totalPossibleCups -> "Yes, I can make that amount of coffee"
                cupNumber < totalPossibleCups -> "Yes, I can make that amount of coffee(and even ${totalPossibleCups - cupNumber} more than that)"
                else -> "No, I can make only $totalPossibleCups cups of coffee"
            }
        )
    }
}

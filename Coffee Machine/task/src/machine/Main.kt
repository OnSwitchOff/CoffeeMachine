package machine

fun main() {
    val machine = CoffeeMachine(400, 540, 120, 9, 550)
    machine.menu()
}

enum class CofeeType {
    ESPRESSO {
        override val number: Int = 1
        override val waterPerCup: Int = 250
        override val milkPerCup: Int = 0
        override val beansPerCup: Int = 16
        override val cost: Int = 4
    },
    LATTE {
        override val number: Int = 2
        override val waterPerCup: Int = 350
        override val milkPerCup: Int = 75
        override val beansPerCup: Int = 20
        override val cost: Int = 7
    },
    CAPPUCCINO {
        override val number: Int = 3
        override val waterPerCup: Int = 200
        override val milkPerCup: Int = 100
        override val beansPerCup: Int = 12
        override val cost: Int = 6
    };
    abstract val number: Int
    abstract val waterPerCup: Int
    abstract val milkPerCup: Int
    abstract val beansPerCup: Int
    abstract val cost: Int
}

class CoffeeMachine() {
    private var water: Int = 0
    private var milk: Int = 0
    private var beans: Int = 0
    private var cups: Int = 0
    private var money: Int = 0
    private var chosenCoffee: CofeeType = CofeeType.ESPRESSO

    constructor(_water: Int, _milk: Int, _beans: Int, _cups: Int, _money: Int) : this() {
        this.water = _water
        this.milk = _milk
        this.beans = _beans
        this.cups = _cups
        this.money =_money
    }

    private val totalCupsByBeans: Int
        get() {
            return try {
                this.beans / this.chosenCoffee.beansPerCup
            } catch (e: ArithmeticException) {
                Int.MAX_VALUE
            }
        }
    private val totalCupsByMilk: Int
        get() {
            return try { this.milk / this.chosenCoffee.milkPerCup
                } catch (e: ArithmeticException) {
                Int.MAX_VALUE
            }
        }
    private val totalCupsByWater: Int
        get() {
            return try {
                return this.water / this.chosenCoffee.waterPerCup
            } catch (e: ArithmeticException) {
                Int.MAX_VALUE
            }
        }
    private val totalPossibleCups: Int
        get() {
            return when {
                this.totalCupsByBeans <= this.totalCupsByMilk
                        && this.totalCupsByBeans <= this.cups
                        && this.totalCupsByBeans <= this.totalCupsByWater -> this.totalCupsByBeans
                this.totalCupsByMilk <= this.totalCupsByWater
                        && this.totalCupsByMilk <= this.cups -> this.totalCupsByMilk
                this.totalCupsByWater <= this.cups -> this.totalCupsByWater
                else -> this.cups
            }
        }

    fun menu() {
        while (true) {
            println("Write action (buy, fill, take, remaining, exit):")
            when (readLine()!!) {
                "buy" -> buy()
                "fill" -> fill()
                "take" -> take()
                "remaining" -> printBalance()
                "exit" -> return
            }
        }
    }

    private fun fill() {
        this.water += askInt("Write how many ml of water do you want to add:")
        this.milk += askInt("Write how many ml of milk do you want to add:")
        this.beans += askInt("Write how many grams of coffee beans do you want to add:")
        this.cups += askInt("Write how many disposable cups of coffee do you want to add:")
    }

    private fun take() {
        this.money = 0
        println("I gave you \$$money")
    }

    private fun buy() {
        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
        when (readLine()!!) {
            "1" -> chosenCoffee = CofeeType.ESPRESSO
            "2" -> chosenCoffee = CofeeType.LATTE
            "3" -> chosenCoffee = CofeeType.CAPPUCCINO
            "back" -> return
        }

        if (totalPossibleCups > 0) {
            println("I have enough resources, making you a coffee!")
            money += chosenCoffee.cost
            water -= chosenCoffee.waterPerCup
            milk -= chosenCoffee.milkPerCup
            beans -= chosenCoffee.beansPerCup
            cups--
        }
        else {
            if (this.water < chosenCoffee.waterPerCup) println("Sorry, not enough water!")
            if (this.milk < chosenCoffee.milkPerCup) println("Sorry, not enough milk!")
            if (this.beans < chosenCoffee.beansPerCup) println("Sorry, not enough coffee beans!")
            if (this.cups < 1) println("Sorry, not enough disposable cups!")
        }
    }

    private fun printBalance() {
        println("The coffee machine has:")
        println("$water of water")
        println("$milk of milk")
        println("$beans of coffee beans")
        println("$cups of disposable cups")
        println("$money of money")
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

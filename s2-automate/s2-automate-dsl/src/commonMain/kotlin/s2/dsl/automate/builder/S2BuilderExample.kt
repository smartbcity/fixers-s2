package s2.dsl.automate.builder

import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2Role
import s2.dsl.automate.S2State

object Role: S2Role

open class OrderBookState(override var position: Int): S2State {
    object Created: OrderBookState(0)
    object Published: OrderBookState(1)
    object Closed: OrderBookState(2)

    override fun equals(other: Any?): Boolean {
        return other is OrderBookState && other.position == position
    }

    override fun hashCode(): Int {
        return position
    }
}

typealias OrderBookId = String

interface OrderBookCreateCommand: S2InitCommand
interface OrderBookMergeCommand: S2Command<OrderBookId>
interface OrderBookUpdateCommand: S2Command<OrderBookId>
interface OrderBookPublishCommand: S2Command<OrderBookId>
interface OrderBookBuyCommand: S2Command<OrderBookId>
interface OrderBookSellCommand: S2Command<OrderBookId>
interface OrderBookBurnCommand: S2Command<OrderBookId>
interface OrderBookCloseCommand: S2Command<OrderBookId>
interface OrderBookUpdatePriceCommand: S2Command<OrderBookId>

val decidingPriceAutomate = s2<String, S2State> {}
val orderAutomate = s2<String, S2State> {}

val withTransactions = s2<OrderBookId, OrderBookState> {
    name = "WithTransactions"
    init<OrderBookCreateCommand> {
        to = OrderBookState.Created
        role = Role
    }
    transaction<OrderBookMergeCommand> {
        from = OrderBookState.Created
        to = OrderBookState.Created
        role = Role
    }
    transaction<OrderBookUpdateCommand> {
        from = OrderBookState.Created
        to = OrderBookState.Created
        role = Role
    }
    transaction<OrderBookPublishCommand> {
        from = OrderBookState.Created
        to = OrderBookState.Published
        role = Role
    }
    transaction<OrderBookBuyCommand> {
        from = OrderBookState.Published
        to = OrderBookState.Published
        role = Role
    }
    transaction<OrderBookSellCommand> {
        from = OrderBookState.Published
        to = OrderBookState.Published
        role = Role
    }
    transaction<OrderBookBurnCommand> {
        from = OrderBookState.Published
        to = OrderBookState.Published
        role = Role
    }
    transaction<OrderBookCloseCommand> {
        from = OrderBookState.Created
        to = OrderBookState.Closed
        role = Role
    }
    transaction<OrderBookCloseCommand> {
        from = OrderBookState.Published
        to = OrderBookState.Closed
        role = Role
    }
    submachine {
        automate = orderAutomate
        autostart = false
        singleton = false // maybe unnecessary, indicate that there can be multiple instances (ex: multiple orders for same orderbook)
        startsOn = listOf( // OrderAutomate would need at least one initial state configured (maybe with init function?)
            OrderBookBuyCommand::class,
            OrderBookSellCommand::class,
            OrderBookBurnCommand::class
        )
        endsOn = listOf(OrderBookCloseCommand::class) // OrderAutomate would need at least one final state configured
        /*
         * "startsOn" and "endsOn" could also be defined directly in the related transactions instead of here
         * ex: transaction<OrderBookBuyCommand> { starts = OrderAutomate }
         * ex: transaction<OrderBookCloseCommand> { ends = OrderAutomate }
         */
    }
}

val withNodes = s2<OrderBookId, OrderBookState> {
    name = "WithNodes"
    init<OrderBookCreateCommand> {
        to = OrderBookState.Created
        role = Role
    }
    node {
        state = OrderBookState.Created

        transaction<OrderBookMergeCommand> {
            role = Role
        }
        transaction<OrderBookUpdateCommand> {
            role = Role
        }
        transaction<OrderBookPublishCommand> {
            to = OrderBookState.Published
            role = Role
        }
        transaction<OrderBookCloseCommand> {
            to = OrderBookState.Closed
            role = Role
        }
    }
    node {
        state = OrderBookState.Published

        transaction<OrderBookBuyCommand> {
            role = Role
        }
        transaction<OrderBookSellCommand> {
            role = Role
        }
        transaction<OrderBookBurnCommand> {
            role = Role
        }
        transaction<OrderBookUpdatePriceCommand> {
            role = Role
        }
        transaction<OrderBookCloseCommand> {
            to = OrderBookState.Closed
            role = Role
        }
    }
    node { // unnecessary if empty
        state = OrderBookState.Closed
    }
    submachine {
        automate = orderAutomate
        autostart = false
        singleton = false // maybe unnecessary, indicate that there can be multiple instances (ex: multiple orders for same orderbook)
        startsOn = listOf( // OrderAutomate would need at least one initial state configured (maybe with init function?)
            OrderBookBuyCommand::class,
            OrderBookSellCommand::class,
            OrderBookBurnCommand::class
        )
        endsOn = listOf(OrderBookCloseCommand::class) // OrderAutomate would need at least one final state configured
        /*
         * "startsOn" and "endsOn" could also be defined directly in the related transactions instead of here
         * ex: transaction<OrderBookBuyCommand> { starts = OrderAutomate }
         * ex: transaction<OrderBookCloseCommand> { ends = OrderAutomate }
         */
    }
    submachine {
        automate = decidingPriceAutomate
        autostart = false
        blocking = true
        startsOn = listOf(OrderBookUpdatePriceCommand::class)
        endsOn = listOf(OrderBookCloseCommand::class)
    }
}

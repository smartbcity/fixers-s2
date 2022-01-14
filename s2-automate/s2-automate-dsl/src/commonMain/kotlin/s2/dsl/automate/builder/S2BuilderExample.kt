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
}

val withSteps = s22<OrderBookId, OrderBookState> {
    name = "WithSteps"
    init<OrderBookCreateCommand> {
        to = OrderBookState.Created
        role = Role
    }
    step {
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

        automate {
            config = DecidingPriceAutomate // automate with some validation and stuff
            autostart = true // DecidingPriceAutomate would need at least one initial state configured
            blocking = true // DecidingPriceAutomate would need at least one final state configured
        }
    }
    step {
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

        automate {
            config = OrderAutomate
            autostart = false
            singleton = false // maybe unnecessary, indicate that there can be multiple instances (ex: multiple orders for same orderbook)
            startsOn = listOf( // OrderAutomate would need at least one initial state configured (maybe with init function?)
                OrderBookBuyCommand::class, OrderBookSellCommand::class, OrderBookBurnCommand::class
            )
            endsOn = listOf(OrderBookCloseCommand::class) // OrderAutomate would need at least one final state configured
            /*
             * "startsOn" and “endsOn" could also be defined directly in the related transactions instead of here
             * ex: transaction<OrderBookBuyCommand> { starts = OrderAutomate }
             * ex: transaction<OrderBookCloseCommand> { ends = OrderAutomate }
             */
        }
        automate {
            config = DecidingPriceAutomate
            autostart = false
            startsOn = listOf(OrderBookUpdatePriceCommand::class)
            endsOn = listOf(OrderBookCloseCommand::class)
        }
    }
    final { // could use "step" with a param "final = true"
        state = OrderBookState.Closed
    }
}

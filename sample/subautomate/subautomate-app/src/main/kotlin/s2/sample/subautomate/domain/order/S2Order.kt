package s2.sample.subautomate.domain.order

import s2.dsl.automate.S2Role
import s2.dsl.automate.S2State
import s2.dsl.automate.builder.s2

object Role: S2Role


enum class OrderState(override var position: Int): S2State {
    Pending(0),
    Validated(1),
    Canceled(2),
    Closed(3)
}

val orderAutomate = s2 {
    name = "S2Order"
    transaction<OrderBuyCommand> {
        to = OrderState.Pending
        role = Role
    }
    transaction<OrderSellCommand> {
        to = OrderState.Pending
        role = Role
    }
    transaction<OrderBurnCommand> {
        to = OrderState.Pending
        role = Role
    }
}

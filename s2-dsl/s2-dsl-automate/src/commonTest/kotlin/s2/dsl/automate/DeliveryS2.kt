package s2.dsl.automate

import kotlinx.serialization.Serializable

val DeliveryStateMachine = S2Automate(
		name = "Delivery",
		init = S2InitTransition(
				to = DeliveryState.Created,
				role = ColisActivRole.Operator,
				command = CreateCommand::class,
		),
		transitions = arrayOf(
				S2Transition(
						from = DeliveryState.Created,
						to = DeliveryState.Verified,
						role = ColisActivRole.Operator,
						command = VerifyCommand::class.simpleName!!,
				)
		)
)

sealed class DeliveryState(override val position: Int): S2State {
	object Created: DeliveryState(0)
	object Verified: DeliveryState(1)
	object Rejected: DeliveryState(2)
	object AddedInPrime: DeliveryState(3)
	object Paid: DeliveryState(4)

}

sealed class ColisActivRole(val name: String): S2Role {
	object Operator: ColisActivRole("operator")
	object Admin: ColisActivRole("admin")
	object Carier: ColisActivRole("carrier")
	object Funder: ColisActivRole("funder")
}

interface DeliveryInitCommand: S2InitCommand
interface DeliveryCommand: S2Command<String>

@Serializable
class CreateCommand(
): DeliveryInitCommand

@Serializable
class VerifyCommand(
	override val id: String
): DeliveryCommand

//package s2.automate.sourcing.automate
//
//import kotlinx.serialization.descriptors.SerialDescriptor
//import s2.dsl.automate.S2Role
//import s2.dsl.automate.S2State
//import s2.dsl.automate.S2TransitionCommandAttribute
//import kotlin.js.JsExport
//import kotlin.js.JsName
//import kotlin.reflect.KClass
//
//@JsExport
//@JsName("S2StormingTransition")
//open class S2StormingTransition (
//	open val from: S2State?,
//	open val to: S2State,
//	open val role: S2Role,
//	open val msg: KClass<*>,
//)
//
//fun SerialDescriptor.toS2TransitionCommand(): S2TransitionCommandAttribute {
//	return S2TransitionCommandAttribute(
//		name = this.serialName,
//		type = this.kind.toString().lowercase(),
//		optional = this.isNullable,
//	)
//}

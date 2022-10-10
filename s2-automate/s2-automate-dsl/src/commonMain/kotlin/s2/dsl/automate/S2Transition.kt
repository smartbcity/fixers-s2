package s2.dsl.automate

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.serializer
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KClass

@JsExport
@JsName("S2InitTransition")
open class S2InitTransition(
	open val to: S2State,
	open val role: S2Role,
	open val cmd: KClass<out S2InitCommand>,
)

@JsExport
@JsName("S2Transition")
open class S2Transition(
    open val from: S2State?,
    open val to: S2State,
    open val role: S2Role,
    open val cmd: KClass<out Cmd>,
    open val evt: KClass<out Evt>?,
)

@JsExport
@JsName("S2TransitionCommand")
open class S2TransitionCommand(
	open val name: String,
	open val attributes: Array<S2TransitionCommandAttribute>,
)

@JsExport
@JsName("S2TransitionCommandAttribute")
open class S2TransitionCommandAttribute(
	open val name: String,
	open val type: String,
	open val optional: Boolean,
)

@InternalSerializationApi
fun KClass<out S2Command<*>>.toS2TransitionCommand(): S2TransitionCommand {
	String::class.serializer().descriptor.isNullable
	val descriptor = serializer().descriptor
	val atributes = (0..descriptor.elementsCount - 1).map {
		descriptor.getElementDescriptor(it).toS2TransitionCommand()
	}.toTypedArray()

	return S2TransitionCommand(
		name = this.simpleName!!,
		attributes = atributes
	)
}

fun SerialDescriptor.toS2TransitionCommand(): S2TransitionCommandAttribute {
	return S2TransitionCommandAttribute(
		name = this.serialName,
		type = this.kind.toString().lowercase(),
		optional = this.isNullable,
	)
}

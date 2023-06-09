package s2.spring.sourcing.ssm

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

@OptIn(ExperimentalSerializationApi::class)
open class PolymorphicEnumSerializer<T>(private val enumSerializer: KSerializer<T>) : KSerializer<T> {
	override val descriptor: SerialDescriptor = buildClassSerialDescriptor(enumSerializer.descriptor.serialName)
	{
		element("value", enumSerializer.descriptor)
	}

	override fun deserialize(decoder: Decoder): T =
		decoder.decodeStructure(descriptor)
		{
			decodeElementIndex(descriptor)
			decodeSerializableElement(descriptor, 0, enumSerializer)
		}

	override fun serialize(encoder: Encoder, value: T) =
		encoder.encodeStructure(descriptor)
		{
			encodeSerializableElement(descriptor, 0, enumSerializer, value)
		}
}

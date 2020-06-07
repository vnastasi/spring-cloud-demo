package md.vnastasi.cloud.client.model

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import md.vnastasi.cloud.client.model.disruption.DisruptionPayloadWrapper
import md.vnastasi.cloud.client.model.notification.NotificationPayloadWrapper

@JsonDeserialize(using = BaseDisruptionWrapper.Deserializer::class)
interface BaseDisruptionWrapper {

    val id: String

    val type: String

    val title: String

    class Deserializer : StdDeserializer<BaseDisruptionWrapper>(BaseDisruptionWrapper::class.java) {

        override fun deserialize(parser: JsonParser?, ctxt: DeserializationContext?): BaseDisruptionWrapper? {
            val node = parser?.readValueAsTree<TreeNode>()
            return when {
                node?.get(DisruptionPayloadWrapper.MARKER_PROPERTY) != null -> parser.codec?.treeToValue(node, DisruptionPayloadWrapper::class.java)
                node?.get(NotificationPayloadWrapper.MARKER_PROPERTY) != null -> parser.codec?.treeToValue(node, NotificationPayloadWrapper::class.java)
                else -> null
            }
        }
    }
}

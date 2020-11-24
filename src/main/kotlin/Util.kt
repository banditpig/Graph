import java.io.*
import java.time.LocalDateTime
import java.util.*
import javax.xml.stream.XMLOutputFactory

import javax.xml.stream.XMLStreamWriter

/**
 * Maybe
 *
 * @param T
 * @constructor Create empty Maybe
 */
sealed class Maybe<out T> {
    object None : Maybe<Nothing>()

    /**
     * Just
     *
     * @param T
     * @property value
     * @constructor Create empty Just
     */
    data class Just<T>(val value: T) : Maybe<T>()
}
fun <T> outputDot(g: Graph<T>, path: Collection<Node<T>> = emptyList()) {



    val gName = "G"
    var doneEdges = mutableSetOf<String>()
    var dot = StringBuilder("graph $gName {").append("\n")
    for(n in g.nodes()){
        val id = n.id.toString()
        dot.append(id)
        if (path.contains(n)){
           dot.append(" [color=red]").append("\n")
        }else{
            dot.append("\n")
        }
        for (c in g.edgesFor(n)){
            val edgeStr = id + " -- " + c.node.id.toString()
            val edgeStrRev = c.node.id.toString() + " -- " + id
            if (!doneEdges.contains(edgeStr) && !doneEdges.contains(edgeStrRev)){
                dot.append(edgeStr)
                val label = c.weightToParent.toString()
                if (path.contains(c.node) && path.contains(n)){
                    dot.append(" [label = $label, color=red ];")
                }else{
                    dot.append(" [label = $label];")
                }
                dot.append("\n")
                doneEdges.add(edgeStr)
                doneEdges.add(edgeStrRev)
            }


        }

    }
    dot.append("}")
    println(dot.toString())
}
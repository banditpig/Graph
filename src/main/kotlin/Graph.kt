import java.util.*
import kotlin.collections.HashMap

sealed class Maybe<out T> {
    object None : Maybe<Nothing>()
    data class Just<T>(val t: T) : Maybe<T>()
}
data class Node<T>(val id: T, val weight: Double) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node<*>

        if (id != other.id) return false
        if (weight != other.weight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + weight.hashCode()
        return result
    }
}

class Graph<T>(){

    private val edges: HashMap<Node<T>, MutableCollection<Node<T>>  > = hashMapOf<Node<T>, MutableCollection<Node<T>>  >()

    fun addEdge(from: Node<T>, to: Node<T>){
        when(edges.contains(from)){
            true -> edges[from]!!.add(to)
            false -> {
                edges[from] =  mutableListOf<Node<T>>(to)
            }
        }
    }
    fun nodes() : Collection<Node<T>> {
      return (edges.keys union edges.values.flatten()) //sets will remove dups.
    }
    fun adjacent(n1: Node<T>, n2: Node<T>): Boolean{
        return edges.containsKey(n1) && edges[n1]!!.contains(n2)
                ||
               edges.containsKey(n2) && edges[n2]!!.contains(n1)
    }
    fun neighbours(n: Node<T> ): Maybe<Collection<Node<T>>>{

        if(edges.containsKey(n)) {
           return Maybe.Just(Collections.unmodifiableList(edges[n]!!.toList()))
        }
        return Maybe.None
    }

}

fun  main(args: Array<String>) {
    val nodes = listOf<String>()



    print("x")




}
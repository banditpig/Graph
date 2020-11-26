
class Tree<T>(private val root: Node<T>) : DiGraph<T>(){
    constructor(root: T) : this(Node(root))

    fun addChildTo(parent: Node<T>, child: Node<T>){
        addEdge(parent, child)
    }
    fun addChildTo(parent: T, child: T){
        addEdge(parent, child)
    }
    fun children(parent: T) = children(Node(parent))

    fun children(parent: Node<T>): Collection<Node<T>> =
        neighbours(parent)


    fun countChilden(parent: Node<T>) =
        children(parent).size

}
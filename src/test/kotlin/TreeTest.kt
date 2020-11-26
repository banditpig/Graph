import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class TreeTest {

    @Test
    fun basic_tree_checks(){
        val root = 0
        val t = Tree<Int>(root)
        assertTrue(t.children(root).isEmpty())

        t.addChildTo(root, 1)
        t.addChildTo(root, 2)
        t.addChildTo(root, 3)
        assertTrue(t.children(root).size == 3)

        t.addChildTo(1, 4)
        t.addChildTo(1, 5)
        t.addChildTo(1, 6)
        assertTrue(t.children(1).size == 3)
        val c = t.children(1)
        (4..6).map { assertTrue(t.children(1).map { it -> it.id }.contains(it)) }


        t.addChildTo(2, 7)
        t.addChildTo(2, 8)
        assertTrue(t.children(2).size == 2)
        (7..8).map { assertTrue(t.children(2).map { it -> it.id }.contains(it)) }


        t.addChildTo(3, 10)
        assertTrue(t.children(3).size == 1)
        (10..10).map { assertTrue(t.children(3).map { it -> it.id }.contains(it)) }

        outputDot(t, emptyList(), "tree.dot")

    }
}
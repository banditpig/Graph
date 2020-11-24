import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class UtilKtTest {

    @Test
    fun processOneLine() {

        //to check that we get the correct number of correct triples
        val str = "a,1.0,,b,1.0,1,c,1.0,2.0,d,1.0,3"
        val res = processLine(str)
        //expect 4 triples
        assertEquals(4, res.size)
        assertTrue(res.contains(Triple("a", "1.0", "")))
        assertTrue(res.contains(Triple("b", "1.0", "1")))
        assertTrue(res.contains(Triple("c", "1.0", "2.0")))
        assertTrue(res.contains(Triple("d", "1.0", "3")))

    }

    @Test
    fun processAllLines() {

        //to check that we get the correct number of correct triples
        val str = "a,1.0,,b,1.0,1,c,1.0,2.0,d,1.0,3/" +
                "f,1.0,,g,1.0,1,h,1.0,2.0,i,1.0,3"

        val res = processForAllLines(str)
        //expect 4 triples
        assertEquals(2, res.size)
        assertTrue(res[0].contains(Triple("a", "1.0", "")))
        assertTrue(res[0].contains(Triple("b", "1.0", "1")))
        assertTrue(res[0].contains(Triple("c", "1.0", "2.0")))
        assertTrue(res[0].contains(Triple("d", "1.0", "3")))

        assertTrue(res[1].contains(Triple("f", "1.0", "")))
        assertTrue(res[1].contains(Triple("g", "1.0", "1")))
        assertTrue(res[1].contains(Triple("h", "1.0", "2.0")))
        assertTrue(res[1].contains(Triple("i", "1.0", "3")))

    }

    @Test
    fun graphFromStr() {

        val str = "a,1.0,,b,1.0,1,c,1.0,1,d,1.0,1*b,1.0,,c,1.0,1,e,1.0,1*c,1.0,,e,1.0,1,f,1.0,1*d,1.0,,f,1.0,1,g,1.0,1*e,1.0,,h,1.0,1*f,1.0,,h,1.0,1,i,1.0,1,j,1.0,1,g,1.0,1*g,1.0,,k,1.0,1*h,1.0,,o,1.0,1,l,1.0,1*i,1.0,,l,1.0,1,m,1.0,1,j,1.0,1*j,1.0,,m,1.0,1,n,1.0,4,k,1.0,1*k,1.0,,n,1.0,1,r,1.0,1*l,1.0,,o,1.0,1,m,1.0,1*m,1.0,,o,1.0,1,p,1.0,1,n,1.0,1*n,1.0,,q,1.0,1,r,1.0,1*o,1.0,,s,1.0,1,p,1.0,1*p,1.0,,s,1.0,1,t,1.0,1,q,1.0,1*q,1.0,,t,1.0,1,r,1.0,1*r,1.0,,t,1.0,1*s,1.0,,z,1.0,1*t,1.0,,z,1.0,1"


        val g = graphFromString(str)
        val path = dijkstra(g, Node("a"),Node("p"))
        outputDot(g, path)
    }
}
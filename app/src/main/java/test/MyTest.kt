package test

import android.graphics.Rect
import android.util.SparseArray

class MyTest {
    companion object{
        @JvmStatic
        fun main(args: Array<String>){
            val list = SparseArray<Rect>()

            list.clear()

            var s = list[0]

            println(s)

//            list.add("1")
//            list.add("2")
//            list.add("3")
//
//            val child = list.removeAt(2)
//            list.remove(child)
//            list.add(0, child)
//
//            for (index in 0 until list.size){
//                println(list[index])
//            }

//            for (index in list.size - 1 downTo 0){
//                println(list[index])
//            }
        }
    }
}
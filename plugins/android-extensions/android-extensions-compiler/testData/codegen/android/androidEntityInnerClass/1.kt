package test

import android.app.Activity
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.layout.*
import kotlinx.android.extensions.*

class R {
    class id {
        companion object {
            const val login = 5
        }
    }
}

class MyActivity(): Activity() {
    val loginItem = Button(this)
    val entity = MyEntity(loginItem)

    init {
        entity.login
    }

    inner class MyEntity(override val containerView: View) : LayoutContainer
}

fun box(): String {
    return "OK"
}

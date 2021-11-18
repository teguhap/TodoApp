package com.teguhap.taptodo.data

import java.util.*

data class TodoList(val id : String? = null, val title : String = " "
                    , val desc : String = " ", val date : String =" " ,
                    val priorityItem : Int = 0, val priority: String = " ",val itemCategory : String =" ",
                    val isChecked :  Boolean = false)

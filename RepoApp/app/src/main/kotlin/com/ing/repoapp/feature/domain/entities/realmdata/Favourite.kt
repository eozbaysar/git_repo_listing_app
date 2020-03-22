package com.ing.repoapp.feature.domain.entities.realmdata

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Favourite(
    @PrimaryKey  var id: Int=0,
     var name: String?="",
     var username:String?=""
) : RealmObject()
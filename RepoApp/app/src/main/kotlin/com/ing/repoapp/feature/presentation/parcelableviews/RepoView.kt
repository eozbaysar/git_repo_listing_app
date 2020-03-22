package com.ing.repoapp.feature.presentation.parcelableviews

import android.os.Parcel
import com.ing.repoapp.core.platform.KParcelable
import com.ing.repoapp.core.platform.parcelableCreator
import java.security.acl.Owner

data class RepoView(val id: Int,
                    val name: String,
                    val avatar_url: String,
                    val username:String,
                    val stargazers_count:Int,
                    val open_issues:Int
) : KParcelable {

    companion object {
        @JvmField val CREATOR = parcelableCreator(::RepoView)
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeString(name)
            writeString(avatar_url)
            writeString(username)
            writeInt(stargazers_count)
            writeInt(open_issues)
        }
    }

}

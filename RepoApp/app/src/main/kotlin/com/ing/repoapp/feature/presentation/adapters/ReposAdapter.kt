package com.ing.repoapp.feature.presentation.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ing.repoapp.R
import com.ing.repoapp.core.navigation.Navigator
import com.ing.repoapp.feature.domain.entities.realmdata.Favourite
import com.ing.repoapp.feature.presentation.parcelableviews.RepoView
import inflate
import io.realm.Realm
import kotlinx.android.synthetic.main.row_repo.view.*
import javax.inject.Inject
import kotlin.properties.Delegates


class ReposAdapter
@Inject constructor() : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    internal var collection: List<RepoView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (RepoView, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.row_repo))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repoView: RepoView, clickListener: (RepoView, Navigator.Extras) -> Unit) {

            var fav: Favourite? = Realm.getDefaultInstance().where<Favourite>(Favourite::class.java)
                .equalTo("id", repoView.id).findFirst()

            when (fav) {
                null -> itemView.ic_favourite.visibility=View.GONE
                else -> {
                    itemView.ic_favourite.visibility==View.VISIBLE
                }
            }

            itemView.repoName.text = repoView.name

            itemView.setOnClickListener {
                clickListener(
                    repoView,
                    Navigator.Extras(itemView.repoName)
                )
            }

        }
    }
}

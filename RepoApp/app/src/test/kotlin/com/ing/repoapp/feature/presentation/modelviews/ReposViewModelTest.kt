import com.ing.repoapp.AndroidTest
import com.ing.repoapp.core.functional.Either.Right
import com.ing.repoapp.feature.domain.entities.Owner
import com.ing.repoapp.feature.domain.entities.Repo
import com.ing.repoapp.feature.domain.usecases.GetRepos
import com.ing.repoapp.feature.presentation.modelviews.ReposViewModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import kotlinx.coroutines.experimental.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class MoviesViewModelTest : AndroidTest() {

    private lateinit var reposViewModel: ReposViewModel

    @Mock
    private lateinit var getRepos: GetRepos

    @Before
    fun setUp() {
        reposViewModel = ReposViewModel(getRepos)
    }

    @Test
    fun `loading repos should update live data`() {
        val repoList = listOf(
            Repo(230303429,
                "flutter_workmanager",
                Owner("eozbaysar", "https://avatars1.githubusercontent.com/u/12526860?v=4"),
                0,
                0
            ),
            Repo(249014016,
                "git_repo_listing_app",
                Owner("eozbaysar", "https://avatars1.githubusercontent.com/u/12526860?v=4"),
                0,
                0
            )
        )

        given { runBlocking { getRepos.run(eq(any())) } }.willReturn(Right(repoList))


        reposViewModel.repos.observeForever {
            it!!.size shouldEqualTo 2
            it[0].id shouldEqualTo 230303429
            it[0].name shouldEqualTo "flutter_workmanager"
            it[1].id shouldEqualTo 249014016
            it[1].name shouldEqualTo "git_repo_listing_app"
        }

        runBlocking { reposViewModel.loadRepos("eozbaysar") }

    }
}
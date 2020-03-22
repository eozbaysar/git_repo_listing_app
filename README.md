# Project Architecture - The Clean Architecture

In accordance with the SOLID rules, a 3-layer architecture was used as a general principle to reduce the dependency between each layer and write easy to test.
<p align="center">
<img src="https://raw.githubusercontent.com/eozbaysar/git_repo_listing_app/master/RepoApp/summary/clean_architecture_reloaded_layers.png" alt="Repo App Architecture"/>
</p>

## UI Layer - MVVM

MVVM was used as the UI layer to seperation of concern.
<p align="center">
<img src="https://raw.githubusercontent.com/eozbaysar/git_repo_listing_app/master/RepoApp/summary/clean_architecture_reloaded_mvvm_app.png" alt="Repo App UI Architecture"/>
</p>


## 3rd Libraries and Components:

*   Kotlin **[Coroutines]** for background operations.
*   Reactive UIs using **LiveData** observables.
*   A **data layer** with a repository and two data sources (local using **[RealmDb]** and remote **[Refrofit]**)
*   Inter-class dependencies were injectioned using **[Dagger2]**
*   A collection of unit and activity **tests** were run on **[Robolectric]** and using **[Mockito]**.



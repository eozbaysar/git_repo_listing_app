package com.ing.repoapp.feature.domain.entities
import com.ing.repoapp.core.exception.Failure.FeatureFailure

class RepoFailure {
    class ListNotAvailable: FeatureFailure()
    class NonExistentRepo: FeatureFailure()
}


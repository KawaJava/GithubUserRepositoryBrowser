package io.github.kawajava.GithubUserRepositoryBrowser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GithubRepositoryMapper {

    public RepositoryResponse toResponse(GithubRepository repository, List<GithubBranch> branches
    ) {
        return new RepositoryResponse(repository.name(), repository.owner().login(), mapBranches(branches)
        );
    }

    private List<BranchResponse> mapBranches(List<GithubBranch> branches) {
        return branches.stream()
                .map(this::toBranchResponse)
                .toList();
    }

    private BranchResponse toBranchResponse(GithubBranch branch) {
        return new BranchResponse(
                branch.name(),
                branch.commit().sha()
        );
    }
}


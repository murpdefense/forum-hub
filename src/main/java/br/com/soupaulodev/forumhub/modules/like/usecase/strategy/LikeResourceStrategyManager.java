package br.com.soupaulodev.forumhub.modules.like.usecase.strategy;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.like.entity.ResourceType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manager for like resource strategies.
 * This class is responsible for managing the like resource strategies.
 *
 * @author <a href="https://soupaulodev.com.br>soupaulodev</a>
 */
@Component
public class LikeResourceStrategyManager {

    private final Map<ResourceType, LikeResourceStrategy> strategyMap;

    /**
     * Constructor.
     *
     * @param strategies The list of strategies
     */
    public LikeResourceStrategyManager(List<LikeResourceStrategy> strategies) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(LikeResourceStrategy::getResourceType, strategy -> strategy));
    }

    /**
     * Gets the strategy for a resource type.
     *
     * @param resourceType The resource type
     * @return The strategy
     */
    public LikeResourceStrategy getStrategy(ResourceType resourceType) {
        LikeResourceStrategy strategy = strategyMap.get(resourceType);
        if (strategy == null) {
            throw new ResourceNotFoundException("Resource type not found");
        }
        return strategy;
    }
}

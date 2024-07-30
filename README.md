# TinkerPop `Traversal.Admin.lock()` performance issue

A simple script that reproduces a performance issue with `applyStrategies` / `lock` in TinkerPop 3.7.2

I noticed that many of the children steps were getting locked more than once, which shouldn't be necessary. I think the problem is [here](https://github.com/apache/tinkerpop/blob/3.7.2/gremlin-core/src/main/java/org/apache/tinkerpop/gremlin/process/traversal/util/DefaultTraversal.java#L333). `applyTraversalRecursively`, called from the traversal root, should apply the `lock()` to all children, grandchildren, and so on. But each child also calls `applyTraversalRecursively`, meaning the number of times a step is locked scales with the depth of the traversal.

# Results
```sh
# TinkerPop 3.5.3
Applied strategies in PT3.695S

# TinkerPop 3.7.2
Applied strategies in PT15.079S
```
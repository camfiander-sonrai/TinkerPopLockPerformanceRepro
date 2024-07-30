import java.time.Duration

graph = TinkerFactory.createGratefulDead()
g = graph.traversal()

t = g.V().or(
    __.has("name", P.within([
        "DARK STAR", "ST. STEPHEN", "CHINA CAT SUNFLOWER"
    ] as Set)),
    __.has("songType", P.eq("cover"))
).where(
    __.coalesce(
        __.where(
            __.union(
                __.as("a").inE("sungBy").choose(
                    __.has("weight"), __.has("weight", P.gt(1)), __.identity()
                ).outV().filter(__.has("weight", P.lt(1))),
                __.as("a").outE("followedBy").choose(
                    __.has("weight"), __.has("weight", P.gt(1)), __.identity()
                ).inV().where(
                    __.coalesce(
                        __.where(
                            __.union(
                                __.as("a").outE("followedBy").choose(
                                    __.has("weight"), __.has("weight", P.gt(1)), __.identity()
                                ).inV().has("songType", P.neq("cover")).where(
                                    __.coalesce(
                                        __.where(
                                            __.union(
                                                __.as("a").outE("followedBy").choose(
                                                    __.has("weight"), __.has("weight", P.gt(1)), __.identity()
                                                ).inV().where(
                                                    __.coalesce(
                                                        __.where(
                                                            __.union(
                                                                __.as("a").outE("followedBy").choose(
                                                                    __.has("weight"), __.has("weight", P.gt(1)), __.identity()
                                                                ).inV().where(
                                                                    __.coalesce(
                                                                        __.where(
                                                                            __.union(
                                                                                __.as("a").outE("followedBy").choose(
                                                                                    __.has("weight"), __.has("weight", P.gt(1)), __.identity()
                                                                                ).inV().has("songType", P.within(["original"]))
                                                                            )
                                                                        )
                                                                    )
                                                                )
                                                            )
                                                        )
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            ).dedup().select("a")
        )
    )
)
startTime = System.currentTimeMillis()
t.applyStrategies()
endTime = System.currentTimeMillis()
println("Applied strategies in " + Duration.ofMillis(endTime - startTime))

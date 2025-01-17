package com.jmhreif.sdn_update_entity_round2;

import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;

public record Movie(@Id String movieId,
                    @Version Long version,
                    String title,
                    String plot,
                    String poster,
                    String url,
                    String imdbId,
                    String tmdbId,
                    String released,
                    Long year,
                    Long runtime,
                    Long budget,
                    Long revenue,
                    Long imdbVotes,
                    Double imdbRating,
                    String[] languages,
                    String[] countries) {
}

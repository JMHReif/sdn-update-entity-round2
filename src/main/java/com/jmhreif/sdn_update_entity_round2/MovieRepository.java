package com.jmhreif.sdn_update_entity_round2;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface MovieRepository extends Neo4jRepository<Movie, String> {
    @Query("MATCH (m:Movie)<-[r:ACTED_IN]-(p:Person)" +
            "RETURN m, collect(r), collect(p) LIMIT 20;")
    Iterable<Movie> findMoviesSubset();

    @Query("MATCH (m:Movie {movieId: $movieId}) " +
            "SET m.year = toInteger($year) " +
            "RETURN m;")
    Movie updateYear(String movieId, Long year);
}

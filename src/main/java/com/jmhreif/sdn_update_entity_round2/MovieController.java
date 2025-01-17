package com.jmhreif.sdn_update_entity_round2;

import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieRepository movieRepository;
    private final Neo4jTemplate neo4jTemplate;

    public MovieController(MovieRepository movieRepository, Neo4jTemplate neo4jTemplate) {
        this.movieRepository = movieRepository;
        this.neo4jTemplate = neo4jTemplate;
    }

    @GetMapping()
    Iterable<Movie> findMovies() {
        return movieRepository.findMoviesSubset();
    }

    //Creates/updates entity, but overwrites values not in json
    @PostMapping("/save")
    Movie save(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    //Only updates specified properties
    @PatchMapping("/updateYear")
    Movie updateYear(@RequestParam String movieId, @RequestParam Long year) {
        return movieRepository.updateYear(movieId, year);
    }

    //Only updates properties set in method
    @PatchMapping("/patchYear")
    Movie patchYear(@RequestBody Movie moviePatch) {
        Movie existingMovie = movieRepository.findById(moviePatch.getMovieId()).get();
        existingMovie.setYear(moviePatch.getYear());

        return movieRepository.save(existingMovie);
    }

    //Only updates properties in projection
    @PatchMapping("/projectionAsMovie")
    MovieDTOProjection saveProjectionAsMovie(@RequestBody MovieDTOProjection movieDTO) {
        return neo4jTemplate.save(Movie.class).one(movieDTO);
    }

    //Only updates properties in projection (ignores other values)
    @PatchMapping("/movieAsProjection")
    MovieDTOProjection saveMovieAsProjection(@RequestBody Movie movie) {
        return neo4jTemplate.saveAs(movie, MovieDTOProjection.class);
    }
}

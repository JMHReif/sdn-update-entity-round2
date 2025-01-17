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
    Iterable<Movie> findAllMovies() {
        return movieRepository.findMoviesSubset();
    }

    @PostMapping("/save")
    Movie save(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    @PutMapping("/updateYear")
    Movie updateYear(@RequestParam String movieId, @RequestParam Long year) {
        return movieRepository.updateYear(movieId, year);
    }

    @PutMapping("/projection4Domain")
    MovieDTOProjection saveMovieDTOAsDomain(@RequestBody MovieDTOProjection movieDTO) {
        return neo4jTemplate.save(Movie.class).one(movieDTO);
    }

    @PutMapping("/domain4Projection")
    MovieDTOProjection saveMovieDTOAsProjection(@RequestBody Movie movie) {
        return neo4jTemplate.saveAs(movie, MovieDTOProjection.class);
    }

    @PatchMapping("/patchYear")
    Movie patchYear(@RequestBody Movie moviePatch) {
        return neo4jTemplate.save(moviePatch);
    }
}

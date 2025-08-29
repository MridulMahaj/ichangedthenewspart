package VASService.mywork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final WebClient webClient = WebClient.create("http://localhost:8081");
    // <-- assuming rtnews runs on :8081

    @PostMapping
    public Mono<ResponseEntity<String>> getNewsData(@RequestBody Map<String, String> body) {
        String story = body.get("story");

        return webClient.get()
                .uri("/api/news/full-story?story={story}&sortBy=RELEVANCE&region=US&lang=en", story)
                .retrieve()
                .bodyToMono(String.class)
                .map(ResponseEntity::ok);
    }
}

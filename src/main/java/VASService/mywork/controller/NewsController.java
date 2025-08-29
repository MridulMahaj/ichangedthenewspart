package VASService.mywork.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final WebClient webClient = WebClient.create("http://localhost:8081");
    // <-- assuming rtnews runs on :8081

    @PostMapping
    public Mono<ResponseEntity<String>> getNewsData(@RequestBody Map<String, String> body) {
        String story = body.get("story");

        // If story looks like plain text, encode it
        // If it’s already an ID, just use as-is
        String uri = story.startsWith("CAAq")
                ? "/api/news/full-story?story=" + story + "&sortBy=RELEVANCE&region=US&lang=en"
                : "/api/news/full-story?story=" + story + "&sortBy=RELEVANCE&region=US&lang=en";

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .map(ResponseEntity::ok);
    }

}

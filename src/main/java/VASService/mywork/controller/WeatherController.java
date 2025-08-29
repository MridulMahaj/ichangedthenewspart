package VASService.mywork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WebClient webClient = WebClient.create("http://localhost:8082");
    // <-- assuming weather service runs on :8082

    @PostMapping
    public Mono<ResponseEntity<String>> getWeatherData(@RequestBody Map<String, String> body) {
        String city = body.get("city");

        // Basic validation
        if (city == null || city.trim().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().body("City parameter is required."));
        }

        String uri = "/api/weather/current?city=" + city.trim();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .map(ResponseEntity::ok);
    }
}

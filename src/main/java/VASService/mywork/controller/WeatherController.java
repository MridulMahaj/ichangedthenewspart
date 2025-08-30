package VASService.mywork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    // Weather service running on 8082
    private final WebClient webClient = WebClient.create("http://localhost:8082");

    @PostMapping
    public Mono<ResponseEntity<String>> getWeatherData(@RequestBody Map<String, String> body) {
        String city = body.get("city");

        if (city == null || city.trim().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().body("City parameter is required."));
        }

        // Forward request to weather service
        String uri = "/api/weather/current?city=" + city.trim();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class) // Return JSON as string
                .map(ResponseEntity::ok)
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just(ResponseEntity.status(500).body("Failed to fetch weather data."));
                });
    }
}

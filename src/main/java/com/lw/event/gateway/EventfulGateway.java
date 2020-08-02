package com.lw.event.gateway;

import com.lw.event.Search;
import com.lw.event.Search.Events.Event;
import com.lw.event.helper.EventConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.lw.event.helper.EventConstant.*;

@Slf4j
@Component
public class EventfulGateway {

//    @Async
//    public Future<List<Search>> getSearches(MultiValueMap queryParamMap) {
//        List<Search> searchList = new ArrayList<>();
//        queryParamMap.set(EVENTPAGENUMBER, "1");
//        Search firstSearch = fetchSearch(queryParamMap).block();
//        searchList.add(firstSearch);
//        if (firstSearch.getPageCount() > 1) {
//            List<Search> secondAsyncSearches = Flux.fromIterable(IntStream.rangeClosed(2, firstSearch.getPageCount()).boxed().collect(Collectors.toList()))
//                    .parallel()
//                    .runOn(Schedulers.elastic())
//                    .flatMap(i -> {
//                        queryParamMap.set(EVENTPAGENUMBER, Integer.toString(i));
//                        return fetchSearch(queryParamMap);
//                    })
//                    .collectSortedList((s1, s2) -> s2.getPageNumber() - s1.getPageNumber())
//                    .block();
//            searchList.addAll(secondAsyncSearches);
//        }
//        AsyncResult<List<Search>> searchListFuture = new AsyncResult<>(searchList);
//        return searchListFuture;
//    }

    public List<Search> getSearches(MultiValueMap queryParamMap) {
        List<Search> searchList = new ArrayList<>();
        queryParamMap.set(EVENTPAGENUMBER, "1");
        Search firstSearch = fetchSearch(queryParamMap).block();
        searchList.add(firstSearch);
        if (firstSearch.getPageCount() > 1) {
            List<MultiValueMap> mapList = new ArrayList<>();
            IntStream.rangeClosed(2, firstSearch.getPageCount()).forEach(i -> {
                MultiValueMap map = new LinkedMultiValueMap<>();
                map.addAll(queryParamMap);
                map.set(EVENTPAGENUMBER, Integer.toString(i));
                mapList.add(map);
            });
            List<Search> secondAsyncSearches = Flux.fromIterable(mapList)
                    .parallel()
                    .runOn(Schedulers.elastic())
                    .flatMap(map -> {
                        return fetchSearch(map);
                    })
//                    .collectSortedList((s1, s2) -> s2.getPageNumber() - s1.getPageNumber())
                    .sequential()
                    .collectList()
                    .block();
            searchList.addAll(secondAsyncSearches);
        }
        return searchList;
    }

    public Mono<Search> fetchSearch(MultiValueMap queryParamMap) {

        log.info("[EventfulGateway] Fetching... event: {}, page: {}", queryParamMap.get(EVENTCATEGORY), queryParamMap.get(EVENTPAGENUMBER));

        WebClient webClient = WebClient.create("http://api.eventful.com");
        Mono<Search> searchMono = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                    .path("/rest/events/search")
                    .queryParams(queryParamMap)
                    .build())
                .retrieve()
                .bodyToMono(Search.class);
        return searchMono;
    }
}

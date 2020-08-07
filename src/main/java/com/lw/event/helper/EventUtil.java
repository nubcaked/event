package com.lw.event.helper;

import com.lw.event.dto.EventDTO;
import io.crnk.core.queryspec.FilterSpec;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.queryspec.SortSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.lw.event.helper.EventConstant.*;

@Slf4j
@Component
public class EventUtil {

    public static Map<String, List<String>> getFilterMap(QuerySpec querySpec) {
        Map<String, List<String>> filterMap = new HashMap<>();
        if (querySpec.getFilters() != null && querySpec.getFilters().size() > 0) {
            for (FilterSpec filterSpec : querySpec.getFilters()) {
                if (filterSpec.toString().contains(EVENTLOCATION)) {
                    filterMap.put(EVENTLOCATION, Arrays.asList(filterSpec.getValue().toString().replace("[", "").replace("]", "").replace(" ", "").split(",")));
                }
                if (filterSpec.toString().contains(EVENTCATEGORY)) {
                    filterMap.put(EVENTCATEGORY, Arrays.asList(filterSpec.getValue().toString().replace("[", "").replace("]", "").replace(" ", "").split(",")));
                }
            }
        }
        return filterMap;
    }

    public static Map<String, String> getSortMap(QuerySpec querySpec) {
        Map<String, String> sortMap = new HashMap<>();
        if (querySpec.getSort() != null && querySpec.getSort().size() > 0) {
            for (SortSpec sortSpec : querySpec.getSort()) {
                if (sortSpec.toString().contains(EVENTCATEGORY)) {
                    sortMap.put(EVENTCATEGORY, sortSpec.getDirection().name());
                }
                if (sortSpec.toString().contains(EVENTSTARTTIME)) {
                    sortMap.put(EVENTSTARTTIME, sortSpec.getDirection().name());
                }
            }
        }
        return sortMap;
    }

    public static void sortByCategoryAndByStartTime(List<EventDTO> eventDTOs, Map<String, String> sortMap) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String categorySort = sortMap.get(EVENTCATEGORY);
        String startTimeSort = sortMap.get(EVENTSTARTTIME);
        Collections.sort(eventDTOs, Comparator.comparing(EventDTO::getCategory, (s1, s2) -> {
            if (categorySort == null) {
                return 0;
            } else if (categorySort.equals("ASC")) {
                return s1.compareTo(s2);
            } else {
                return s2.compareTo(s1);
            }
        }).thenComparing(EventDTO::getStartTime, (s1, s2) -> {
            if (startTimeSort == null) {
                return 0;
            } else {
                Date d1, d2;
                try {
                    d1 = dateFormatter.parse(s1);
                    d2 = dateFormatter.parse(s2);
                } catch (ParseException e) {
                    log.error(e.getMessage());
                    return 0;
                }
                if (startTimeSort.equals("ASC")) {
                    return d1.compareTo(d2);
                } else {
                    return d2.compareTo(d1);
                }
            }
        }));
    }

    public static List<List<String>> getSortList(QuerySpec querySpec) {
        List<List<String>> sortList = new ArrayList<>();
        if (querySpec.getSort() != null && querySpec.getSort().size() > 0) {
            for (SortSpec sortSpec : querySpec.getSort()) {
                if (sortSpec.toString().contains(EVENTCATEGORY)) {
                    sortList.add(new ArrayList<>(Arrays.asList(EVENTCATEGORY, sortSpec.getDirection().toString())));
                }
                if (sortSpec.toString().contains(EVENTSTARTTIME)) {
                    sortList.add(new ArrayList<>(Arrays.asList(EVENTSTARTTIME, sortSpec.getDirection().toString())));
                }
            }
        }
        return sortList;
    }
}

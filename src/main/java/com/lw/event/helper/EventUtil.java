package com.lw.event.helper;

import io.crnk.core.queryspec.FilterSpec;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.queryspec.SortSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.lw.event.helper.EventConstant.*;

@Slf4j
@Component
public class EventUtil {

    public static Map<String, List<String>> getFilterMap(QuerySpec querySpec) {
        Map<String, List<String>> filterMap = new HashMap<>();
        if (querySpec.getFilters().size() > 0) {
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

    public static List<List<String>> getSortList(QuerySpec querySpec) {
        List<List<String>> sortList = new ArrayList<>();
        if (querySpec.getSort().size() > 0) {
            for (SortSpec sortSpec : querySpec.getSort()) {
                if (sortSpec.toString().contains(EVENTSTARTTIME)) {
                    sortList.add(new ArrayList<>(Arrays.asList(EVENTSTARTTIME, sortSpec.getDirection().toString())));
                }
            }
        }
        return sortList;
    }
}

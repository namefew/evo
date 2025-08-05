import com.chief.evo.dto.RoadParser;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Base64Test {
    public static void main(String[] args) {;
        String winNumberRoad = "YAYNj1Y8yrMxTo9TJOxR71W9Cxxq0mzbtyMdKfcnQJ82TLwWr0CbMpT4UepItX5cWTEoR60fJhhXr0qHai2Z1Sxhn3KV62ABQyChgA==";
        List<Integer> list = RoadParser.RouletteParser.parseBoot(winNumberRoad);
        System.out.println(list.stream().map(i -> i + " ").collect(Collectors.joining( ",")));
    }

}

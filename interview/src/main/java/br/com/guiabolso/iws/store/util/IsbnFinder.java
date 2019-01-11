package br.com.guiabolso.iws.store.util;

import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.regex.Pattern.compile;

public class IsbnFinder implements Function<Element, String> {
    static String regex1 = "\\d{3}-?\\d{10}";
    static  String regex2 = "^\\d{9}[\\d|X]$";
    static  String regex3 = "ISBN\\x20(?=.{13}$)\\d{1,5}([- ])\\d{1,7}\\1\\d{1,6}\\1(\\d|X)$";
    static  String regex4 = "^(97(8|9))?\\d{9}(\\d|X)$";
    static  String regex5 = "ISBN-13.*(978[-]*\\d{10}) ";

    static final List<Pattern> rxs = Arrays.asList(compile(regex1),compile(regex2),compile(regex3),compile(regex4),compile(regex5));

    public String apply(Element element) {
        Optional<String> hasIsbn = element.attributes().asList().stream().map(att ->{
            String match = null;
            for (Pattern rx : rxs)
                if (rx.matcher(att.getValue()).matches()) match = att.getValue();
            return match;
        }).collect(Collectors.toList()).stream().filter(Objects::nonNull).findFirst();

        return hasIsbn.orElseGet(() -> {
            String match = null;
            for (Pattern rx : rxs)
                if (rx.matcher(element.html()).matches()) match = element.html();
            return match;
        });
    }
}
